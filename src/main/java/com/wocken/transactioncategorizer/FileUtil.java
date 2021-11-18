package com.wocken.transactioncategorizer;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.*;

public class FileUtil {

    private FileUtil () {}

    public static Reader createReader(String filepath) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filepath));
        return new InputStreamReader(bis);
    }

    public static CSVParser createParser(ExportType exportType, String filepath) throws IOException {
        Reader reader = createReader(filepath);
        CSVParser csvParser;
        if (exportType == ExportType.CREDIT) {
            csvParser = CSVFormat.DEFAULT
                    .withHeader(
                            Runner.TRANS_ID,
                            Runner.POST_DATE,
                            Runner.EFF_DATE,
                            Runner.TRANS_TYPE,
                            Runner.AMNT,
                            Runner.CHECK_NUM,
                            Runner.REF_NUM,
                            Runner.DESC,
                            Runner.TRANS_CAT,
                            Runner.TYPE,
                            Runner.BALANCE
                    )
                    .parse(reader);
        } else { // Debit
            csvParser = CSVFormat.DEFAULT
                    .withHeader(
                            Runner.TRANS_ID,
                            Runner.POST_DATE,
                            Runner.EFF_DATE,
                            Runner.TRANS_TYPE,
                            Runner.AMNT,
                            Runner.CHECK_NUM,
                            Runner.PAYEE,
                            Runner.DESC,
                            Runner.TRANS_CAT,
                            Runner.TYPE,
                            Runner.BALANCE
                    )
                    .parse(reader);
        }
        return csvParser;
    }
}
