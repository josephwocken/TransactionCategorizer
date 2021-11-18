package com.wocken.transactioncategorizer;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
    reads in both credit and debit exports.
    debit: we match against 'payee' column
    credit: we match against 'description' column

    based on the passed in transaction type argument, we'll
    chose which credit, or debit field to match against.
    output file will include both 'payee' and 'description' fields
    for simplicity. that way we combine the transactions into one output
 */
@Component
public class Runner implements ApplicationRunner {

    public static final String TRANS_ID = "Transaction ID";
    public static final String POST_DATE = "Posting Date";
    public static final String EFF_DATE = "Effective Date";
    public static final String TRANS_TYPE = "Transaction Type";
    public static final String AMNT = "Amount";
    public static final String CHECK_NUM = "Check Number";
    public static final String REF_NUM = "Reference Number";
    public static final String DESC = "Description";
    public static final String TRANS_CAT = "Transaction Category";
    public static final String TYPE = "Type";
    public static final String BALANCE = "Balance";
    public static final String PAYEE = "Payee";

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String creditInputFilepath = args.getOptionValues("credit-input-file").get(0);
        String debitInputFilepath = args.getOptionValues("debit-input-file").get(0);
        String allTransOutputFile = args.getOptionValues("all-trans-output-file").get(0);
        String reportOutputFile = args.getOptionValues("report-output-file").get(0);
        Files.deleteIfExists(Paths.get(allTransOutputFile));
        Files.deleteIfExists(Paths.get(reportOutputFile));
        CSVParser creditParser = FileUtil.createParser(ExportType.CREDIT, creditInputFilepath);
        CSVParser debitParser = FileUtil.createParser(ExportType.DEBIT, debitInputFilepath);
        CSVPrinter printer = new CSVPrinter(new FileWriter(allTransOutputFile), CSVFormat.DEFAULT);
        printer.printRecord(
                "INPUT_TYPE",
                TRANS_ID,
                POST_DATE,
                EFF_DATE,
                TRANS_TYPE,
                AMNT,
                CHECK_NUM,
                "Wocken " + DESC,
                "Original " + TRANS_CAT,
                "Wocken " + TRANS_CAT,
                BALANCE
        );
        final SpendingCalculator calculator = new SpendingCalculator();
        processForParser(ExportType.CREDIT, creditParser, printer, calculator);
        processForParser(ExportType.DEBIT, debitParser, printer, calculator);

        String calculatorTotals = calculator.printTotals();
        System.out.println(calculatorTotals);
        byte[] totalsBytes = calculatorTotals.getBytes();
        Path reportFilePath = Paths.get(reportOutputFile);
        Files.write(reportFilePath, totalsBytes);
        System.out.println("Exiting");

        System.exit(0);
    }

    private void processForParser(ExportType exportType, CSVParser csvParser, CSVPrinter printer, SpendingCalculator calculator) throws IOException {
        for (CSVRecord record : csvParser) {
            if (record.getRecordNumber() == 1L) {
                continue;
            }
            String fieldToMatchAgainst = mapFieldToMatchAgainst(record, exportType);
            if (null == fieldToMatchAgainst || fieldToMatchAgainst.isEmpty()) {
                System.out.println("null field to match against found. matching for " +
                        "transaction-type=" + exportType.name() + ". skipping");
                continue;
            }
            AtomicReference<SpendingCategory> spendingCategoryRef = new AtomicReference<>(SpendingCategory.OTHER);
            boolean patternIsMatched = false;
            for (SpendingCategory currentCategory : SpendingCategory.values()) {
                if (patternIsMatched) {
                    break;
                }
                 for (Pattern currentPattern : currentCategory.getPatterns()) {
                    Matcher m = currentPattern.matcher(fieldToMatchAgainst);
                    if (m.matches()) {
                        System.out.println(currentCategory.name() + ": " + fieldToMatchAgainst);
                        spendingCategoryRef.set(currentCategory);
                        patternIsMatched = true;
                        break;
                    }
                }
            }
            SpendingCategory mappedCategory = mapFromBankCategoryIfOther(record.get(TRANS_CAT), spendingCategoryRef.get());
            printer.printRecord(
                    exportType.name(),
                    record.get(TRANS_ID),
                    record.get(POST_DATE),
                    record.get(EFF_DATE),
                    record.get(TRANS_TYPE),
                    record.get(AMNT),
                    record.get(CHECK_NUM),
                    mapToDescription(record, exportType),
                    record.get(TRANS_CAT),
                    mappedCategory.name(),
                    record.get(BALANCE)
            );
            String transactionType = record.get(TRANS_TYPE);
            if (ExportType.CREDIT == exportType) {
                calculateForCreditExportType(exportType, calculator, record, mappedCategory, transactionType);
            } else if (ExportType.DEBIT == exportType) {
                calculateForDebitExportType(exportType, calculator, record, mappedCategory, transactionType);
            } else {
                throw new RuntimeException("Export Type isn't valid. valid-export-types="
                        + Arrays.toString(ExportType.values()) + ". found-export-type=" + exportType);
            }
            System.out.println("Description: " + fieldToMatchAgainst);
            System.out.println("Spending Category: " + mappedCategory.name());
            printer.flush();
        }
    }

    private void calculateForDebitExportType(ExportType exportType, SpendingCalculator calculator, CSVRecord record, SpendingCategory mappedCategory, String transactionType) {
        if ("CREDIT".equalsIgnoreCase(transactionType)) {
            // add (only positive values in export file)
            BigDecimal amountToAdd = new BigDecimal(record.get(AMNT));
            calculator.addToCategory(mappedCategory, amountToAdd);
        } else if ("DEBIT".equalsIgnoreCase(transactionType)) {
            // subtract (convert to absolute first)
            String absoluteAmount = convertToAbsolute(record.get(AMNT));
            BigDecimal amountToSubtract = new BigDecimal(absoluteAmount);
            calculator.subtractFromCategory(mappedCategory, amountToSubtract);
        } else if ("CHECK".equalsIgnoreCase(transactionType)) {
            String absoluteAmount = convertToAbsolute(record.get(AMNT));
            BigDecimal amountToSubtract = new BigDecimal(absoluteAmount);
            calculator.subtractFromCategory(mappedCategory, amountToSubtract);
        } else {
            throw new RuntimeException("Found unknown transaction-type for export-type=" + exportType + ". " +
                    "valid-trans-types='CREDIT', 'DEBIT'. found-trans-type=" + transactionType);
        }
    }

    private void calculateForCreditExportType(ExportType exportType, SpendingCalculator calculator, CSVRecord record, SpendingCategory mappedCategory, String transactionType) {
        if ("CREDIT".equalsIgnoreCase(transactionType)) {
            // add (convert negatives)
            String absoluteAmount = convertToAbsolute(record.get(AMNT));
            BigDecimal amountAdded = new BigDecimal(absoluteAmount);
            calculator.addToCategory(mappedCategory, amountAdded);
        } else if ("DEBIT".equalsIgnoreCase(transactionType)) {
            // subtract
            BigDecimal amountSpent = new BigDecimal(record.get(AMNT));
            calculator.subtractFromCategory(mappedCategory, amountSpent);
        } else {
            throw new RuntimeException("Found unknown transaction-type for export-type=" + exportType + ". " +
                    "valid-trans-types='CREDIT', 'DEBIT'. found-trans-type=" + transactionType);
        }
    }

    /**
     * could be -100.00 passed in, or 100.00
     * @param originalAmount
     * @return absolute value 100.00 in example
     */
    private String convertToAbsolute(String originalAmount) {
        if (null == originalAmount || originalAmount.isEmpty()) {
            return originalAmount;
        }
        String absoluteAmount = originalAmount;
        if (originalAmount.contains("-")) {
            absoluteAmount = originalAmount.substring(1);
        }
        return absoluteAmount;
    }

    private String mapToDescription(CSVRecord record, ExportType exportType) {
        String desc = "";
        if (exportType == ExportType.CREDIT) {
            desc = record.get(DESC);
        } else if (exportType == ExportType.DEBIT) {
            desc = record.get(PAYEE);
        }
        return desc;
    }

    private String mapFieldToMatchAgainst(CSVRecord record, ExportType exportType) {
        String fieldValueToMatch;
        if (exportType == ExportType.CREDIT) {
            fieldValueToMatch = record.get(DESC);
        } else if (exportType == ExportType.DEBIT) {
            fieldValueToMatch = record.get(PAYEE);
        } else {
            throw new RuntimeException("Failed to find field to match against.");
        }
        return fieldValueToMatch;
    }

    private SpendingCategory mapFromBankCategoryIfOther(String bankCategory, SpendingCategory mappedSpendingCategory) {
        if (null == bankCategory) {
            return null;
        }
        SpendingCategory mappedBankCategory;
        if (mappedSpendingCategory == SpendingCategory.OTHER) {
            mappedBankCategory = SpendingCategory.mapFromBankType(bankCategory);
        } else {
            mappedBankCategory = mappedSpendingCategory;
        }
        return mappedBankCategory;
    }
}
