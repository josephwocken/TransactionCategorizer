package com.wocken.transactioncategorizer;

import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/*
    some spending categories will include the same banking types
    because i get more granular than them. if i find a transaction,
    in 'Food & Dining', for example, it will fall under GROCERIES, or
    RESTAURANT, depending on the {@link mapFromBankType()} function impl.

    'Shopping' category will currently fall into 'OTHER' spending category
    since it's a catch all. The bank type could apply to categories, like..
    'HOME', 'PERSONAL_ALLOWANCE', 'GROCERIES', or 'PERSONAL_CARE'
 */
public enum SpendingCategory {
    AUTO_INSURANCE(
            Lists.newArrayList(),
            Lists.newArrayList(
                    "Auto & Transport"
            ),
            BigDecimal.valueOf(-56.83)),
    AUTO_GAS(Lists.newArrayList(
            Pattern.compile(".*Speedway.*"),
            Pattern.compile(".*CORBORNS.*"),
            Pattern.compile(".*[hH][oO][lL][iI][dD][aA][yY].*"),
            Pattern.compile(".*Kwik\\sTrip.*"),
            Pattern.compile(".*ROD.*COUNTRY\\sCORNER\\sALMELUND.*"),
            Pattern.compile(".*[bB][pP]\\s[gG][lL][oO][bB][aA][lL].*"),
            Pattern.compile(".*[pP][hH][iI][lL][lL][iI][pP][sS].*66.*"),
            Pattern.compile(".*[kK][uU][mM].*")
    ),
            Lists.newArrayList(
                    "Gas & Fuel"
            ),
            BigDecimal.ZERO),
    AUTO_REPAIRS_AND_MAINTENANCE(
            Lists.newArrayList(
                    Pattern.compile(".*LLOYDS\\sAUTOMOTIVE.*"),
                    Pattern.compile(".*ADVANCE\\sAUTO\\sPARTS.*"),
                    Pattern.compile(".*[aA][uU][tT][oO].*")
            ),
            Lists.newArrayList(),
            BigDecimal.ZERO),
    ELECTRIC_BILL(
            Lists.newArrayList(
                    Pattern.compile(".*Xcel.*")
            ),
            Lists.newArrayList(),
            BigDecimal.ZERO),
    INTERNET_BILL(Lists.newArrayList(
            Pattern.compile(
                    ".*Comcast.*")
    ),
            Lists.newArrayList(
                    "Internet"
            ),
            BigDecimal.ZERO),
    CELL_PHONE_BILL(
            Lists.newArrayList(
                    Pattern.compile(".*BOOST\\sMOBILE.*")
            ),
            Lists.newArrayList(),
            BigDecimal.ZERO),
    TV_BILL(Lists.newArrayList(), Lists.newArrayList(), BigDecimal.ZERO),
    OFFICE_SUPPLIES(
            Lists.newArrayList(
                    Pattern.compile(".*USPS.*"),
                    Pattern.compile(".*PAPER\\sSOUR.*")
            ),
            Lists.newArrayList(),
            BigDecimal.ZERO),
    ENTERTAINMENT(
            Lists.newArrayList(
                    Pattern.compile(".*WELCH\\sVILLAGE\\SKI.*"),
                    Pattern.compile(".*VAIL\\sPASS.*"),
                    Pattern.compile(".*[bB][aA][rR][nN][eE][sS].*[nN][oO][bB][lL][eE].*"),
                    Pattern.compile(".*HABBERSTAD\\sHOUSE\\sBED.*"), //TODO: does this belong here?
                    Pattern.compile(".*MINNESOTA\\sSTATE\\sPARKS.*"), //TODO: does this belong here?
                    Pattern.compile(".*HafnarfjordurIS.*"),
                    Pattern.compile(".*Heradsskolinn.*"),
                    Pattern.compile(".*SNAEFELLSBAERIS.*"),
                    Pattern.compile(".*Grundarfirdi.*"),
                    Pattern.compile(".*BUDIR.*"),
                    Pattern.compile(".*Lagoon.*"),
                    Pattern.compile(".*R[eE][yY][kK][jJ][aA][vV][iI][kK].*"),
                    Pattern.compile(".*Borgartuni.*"),
                    Pattern.compile(".*Orkan.*"),
                    Pattern.compile(".*Landeigandafelag.*"),
                    Pattern.compile(".*N1.*"),
                    Pattern.compile(".*Englendingavik.*"),
                    Pattern.compile(".*Arion.*"),
                    Pattern.compile(".*Laugarvatni.*"),
                    Pattern.compile(".*Slettuvegur.*"),
                    Pattern.compile(".*Adalgotu.*"),
                    Pattern.compile(".*Kirkjufell.*"),
                    Pattern.compile(".*Seljalandsfoss.*"),
                    Pattern.compile(".*Icewear.*"),
                    Pattern.compile(".*[mM][aA][tT][hH][uU][sS].*"),
                    Pattern.compile(".*[iI][cC][eE][lL][aA][nN][dD].*"),
                    Pattern.compile(".*[mM][iI][nN][nN].*[hH][iI][sS][tT].*"),
                    Pattern.compile(".*[pP][oO][sS][tT][hH][oO][lL][fF].*[iI][sS].*"),
                    Pattern.compile(".*[dD][eE][lL][tT][aA].*[aA][iI][rR].*"),
                    Pattern.compile(".*[hH][oO][sS][tT][eE][lL].*"),
                    Pattern.compile(".*[rR][eE][nN][tT][aA][lL].*[cC][aA][rR].*"),
                    Pattern.compile(".*[pP][yY][rR][oO].*[cC][iI][tT][yY].*"),
                    Pattern.compile(".*[tT][aA].*[tT][rR][aA][vV][eE][lL].*"),
                    Pattern.compile(".*[bB][iI][rR][dD].*"),
                    Pattern.compile(".*[eE][mM][bB][lL][aA][zZ][oO][nN].*"),
                    Pattern.compile(".*[aA][pP][pP][lL][eE].*[fF][aA][rR][mM].*"),
                    Pattern.compile(".*[hH][aA][rR][dD].*[cC][iI][dD][eE][rR].*"),
                    Pattern.compile(".*[cC][oO][uU][rR][tT][yY][aA][rR][dD].*"),
                    Pattern.compile(".*DAVIDSON.*")
            ),
            Lists.newArrayList("Entertainment"),
            BigDecimal.ZERO),
    LIFE_INSURANCE(Lists.newArrayList(), Lists.newArrayList(), BigDecimal.valueOf(-67)),
    GROCERIES(
            Lists.newArrayList(
                    Pattern.compile(".*TARGET.*S[aA][iI][nN][tT]\\sP[aA][uU][lL].*"),
                    Pattern.compile(".*TARGET.*ST\\sPAUL.*"),
                    Pattern.compile(".*TARGET.*WOODBURY.*"),
                    Pattern.compile(".*TARGET\\sDEBIT\\sCRD\\sLocation.*TARGET.*"),
                    Pattern.compile(".*Target.*"),
                    Pattern.compile(".*COBORNS.*"),
                    Pattern.compile(".*Kowalski.*"),
                    Pattern.compile(".*Whole\\sFoods\\sMarket.*"),
                    Pattern.compile(".*PERRIER\\sWINES.*LIQUORS.*"),
                    Pattern.compile(".*[sS][aA][mM].*[cC][lL][uU][bB].*"),
                    Pattern.compile(".*[tT][oO][tT][aA][lL].*[wW][iI][nN][eE].*"),
                    Pattern.compile(".*[cC][aA][sS][hH].*[wW][iI][sS][eE].*"),
                    Pattern.compile(".*[tT][eE][aA][lL].*"),
                    Pattern.compile(".*[aA][lL][dD][iI].*"),
                    Pattern.compile(".*[cC][uU][bB].*[fF][oO][oO][dD].*"),
                    Pattern.compile(".*[lL][iI][qQ][uU][oO][rR].*"),
                    Pattern.compile(".*[hH][yY]-[vV][eE][eE].*"),
                    Pattern.compile(".*[mM][aA][rR][kK][eE][tT].*"),
                    Pattern.compile(".*[lL][uU][nN][dD].*"),
                    Pattern.compile(".*[mM][eE][aA][tT].*"),
                    Pattern.compile(".*[wW]almart.*"),
                    Pattern.compile(".*[jJ]erry.*[fF]ood.*")
            ),
            Lists.newArrayList(
                    "Food & Dining"
            ), BigDecimal.ZERO),
    RESTAURANTS(
            Lists.newArrayList(
                    Pattern.compile(".*BIG\\sBOWL.*"),
                    Pattern.compile(".*PAD\\sTHAI\\sRESTAURANT.*"),
                    Pattern.compile(".*GRAND\\sPIZZA.*"),
                    Pattern.compile(".*Grand\\sOle\\sCreamery.*"),
                    Pattern.compile(".*WALDMANN\\sSAINT\\sPAUL.*"),
                    Pattern.compile(".*LA\\sGROLLA.*"),
                    Pattern.compile(".*RED\\sCOW.*"),
                    Pattern.compile(".*THE.*GNOME.*S[aA][iI][nN][tT].*P[aA][uU][lL].*"),
                    Pattern.compile(".*PARLOUR\\sST\\sPAUL.*"),
                    Pattern.compile(".*DIXIESONGRAND.*"),
                    Pattern.compile(".*SWEENEYS\\sSALOON.*"),
                    Pattern.compile(".*[nN][iI][nN][aA].*"),
                    Pattern.compile(".*[bB][rR][eE][wW][iI][nN][gG].*"),
                    Pattern.compile(".*J\\.\\sSelby.*"),
                    Pattern.compile(".*Old\\sBarn\\sResort.*"),
                    Pattern.compile(".*[dD][aA][iI][rR][yY].*[qQ][uU][eE][eE][nN].*"),
                    Pattern.compile(".*[pP][uU][nN][cC][hH]\\s[pP][iI][zZ][zZ][aA].*"),
                    Pattern.compile(".*Old\\sVillage\\sHall.*"),
                    Pattern.compile(".*LOUBELLE.*ICE\\sCREAM.*"),
                    Pattern.compile(".*[cC][rR][iI][sS][pP].*[gG][rR][eE][eE][nN].*"),
                    Pattern.compile(".*[bB][rR][eE][aA][dD].*[pP][iI][cC][kK][lL][eE].*"),
                    Pattern.compile(".*[dD][iI][dD][dD][lL][eE][yY].*"),
                    Pattern.compile(".*[cC][aA][nN][dD][yY][lL][aA][nN][dD].*"),
                    Pattern.compile(".*[bB][rR][iI][cC][kK][hH][oO][uU][sS][eE].*"),
                    Pattern.compile(".*[sS][uU][pP][pP][eE][rR].*"),
                    Pattern.compile(".*[cC][aA][fF][eE].*"),
                    Pattern.compile("[jJ][iI][mM][mM][yY].*[jJ][oO][hH][nN].*"),
                    Pattern.compile(".*[bB][bB][qQ].*"),
                    Pattern.compile(".*[tT][aA][cC][oO].*"),
                    Pattern.compile(".*[tT][hH][aA][iI].*"),
                    Pattern.compile(".*[gG][uU][yY]'[sS].*[dD][iI][vV][eE].*"),
                    Pattern.compile(".*[pP][iI][gG][wW][iI][cC][hH].*"),
                    Pattern.compile(".*[dD][iI][sS][tT][iI][lL][lL].*"),
                    Pattern.compile(".*[rR][iI][sS][tT][oO][rR][aA][nN][tT][eE].*"),
                    Pattern.compile(".*[rR][eE][dD].*[cC][oO][wW].*"),
                    Pattern.compile(".*[sS][eE][bB][aA][sS][tT][iI][aA][nN].*[jJ][oO][eE].*"),
                    Pattern.compile(".*[sS][oO][uU][pP].*"),
                    Pattern.compile(".*[gG][iI][lL][lL][iI][sS]\\s[oO][nN]\\s[tT][hH][eE]\\s[gG][oO].*"),
                    Pattern.compile(".*[pP][iI][zZ][zZ][aA].*"),
                    Pattern.compile(".*[cC][aA][kK][eE].*"),
                    Pattern.compile(".*[cC][oO][nN][eE].*"),
                    Pattern.compile(".*[pP][hH][eE][aA][sS][aA][nN][tT].*"),
                    Pattern.compile(".*[cC][aA][rR][iI][bB][oO][uU].*"),
                    Pattern.compile(".*[cC][oO][fF][fF][eE][eE].*"),
                    Pattern.compile(".*McDonald.*"),
                    Pattern.compile(".*Bread.*"),
                    Pattern.compile(".*Chipotle.*"),
                    Pattern.compile(".*Restaurant.*"),
                    Pattern.compile(".*[Ss][uU][sS][hH][iI].*"),
                    Pattern.compile(".*[bB][rR][eE][wW].*")
            ),
            Lists.newArrayList(
                    "Food & Dining"
            ), BigDecimal.ZERO),
    CHARITY(
            Lists.newArrayList(
                    Pattern.compile(".*SAINT\\sTHOMAS\\sMORE.*"),
                    Pattern.compile(".*MPR\\s-\\sAPM.*"),
                    Pattern.compile(".*CSB\\sINSTITUTIONAL\\sADVANCE.*")
            ),
            Lists.newArrayList("Gifts & Donations"), BigDecimal.ZERO),
    GIFTS(
            Lists.newArrayList(
                    Pattern.compile(".*[pP][aA][pP][eE][rR]\\s[sS][oO][uU][rR][cC][eE].*")
            ),
            Lists.newArrayList("Gifts & Donations"), BigDecimal.ZERO),
    HEALTH_CARE(
            Lists.newArrayList(
                    Pattern.compile(".*M\\sHLTH\\sFV\\sMYCHART.*"),
                    Pattern.compile(".*SUMMIT\\sORTHOPEDICS.*"),
                    Pattern.compile(".*METRO\\sOPTICS\\sSAINT\\sPAUL.*"),
                    Pattern.compile(".*[eE][yY][eE].*"),
                    Pattern.compile(".*[oO][pP][tT][iI][cC][iI][aA][nN].*")
            ),
            Lists.newArrayList(),
            BigDecimal.ZERO
    ),
    RENT(Lists.newArrayList(), Lists.newArrayList(), BigDecimal.valueOf(-1500)),
    ELECTRONICS_AND_SOFTWARE(Lists.newArrayList(
            Pattern.compile(".*Amazon\\sWeb\\sServices.*"),
            Pattern.compile(".*APPLE\\sINC.*"),
            Pattern.compile(".*[bB][eE][sS][tT].*[bB][uU][yY].*"),
            Pattern.compile(".*[sS][pP][oO][tT][iI][fF][yY].*"),
            Pattern.compile(".*[sS][aA][mM][sS][uU][nN][gG].*"),
            Pattern.compile(".*GODADDY.*")
    ),
            Lists.newArrayList(
                    "Electronics & Software",
                    "Business Services"
            ),
            BigDecimal.ZERO),
    PERSONAL_CARE(
            Lists.newArrayList(
                    Pattern.compile(".*St\\sCroix\\sCleaners.*"),
                    Pattern.compile(".*[cC][vV][sS].*"),
                    Pattern.compile(".*NOW\\sBIKES\\sAND\\sFITNE.*"),
                    Pattern.compile(".*[aA][tT][hH][lL][eE][tT][aA].*"),
                    Pattern.compile(".*[eE][sS][tT][eE][tT][iI][cC][aA].*"),
                    Pattern.compile(".*[oO][lL][dD].*[nN][aA][vV][yY].*"),
                    Pattern.compile(".*[gG][rR][eE][aA][tT].*[cC][lL][iI][pP][sS].*"),
                    Pattern.compile(".*[dD][sS][wW].*"),
                    Pattern.compile(".*[wW][aA][lL][gG][rR][eE][eE][nN].*"),
                    Pattern.compile(".*[aA][rR][iI][tT][zZ][iI][aA].*"),
                    Pattern.compile(".*[bB][aA][nN][aA][nN][aA].*[rR][eE][pP].*"),
                    Pattern.compile(".*[uU][lL][tT][aA].*"),
                    Pattern.compile(".*[lL][uU][lL][uU].*")
            ),
            Lists.newArrayList(
                    "Personal Care",
                    "Health & Fitness"
            ),
            BigDecimal.ZERO),
    HOME(
            Lists.newArrayList(
                    Pattern.compile(".*Frattallone.*"),
                    Pattern.compile(".*CRATE.*BARREL.*"),
                    Pattern.compile(".*TARGET\\.COM.*"),
                    Pattern.compile(".*TARGET\\sDEBIT\\sCRD\\sTYPE.*"),
                    Pattern.compile(".*[cC][oO][nN][tT][aA][iI][nN][eE][rR].*"),
                    Pattern.compile(".*LOFT\\sANTIQUES.*"),
                    Pattern.compile(".*GAP\\sONLINE.*"),
                    Pattern.compile(".*ERGO.*FLOWERS.*PLANT.*"),
                    Pattern.compile(".*MADEWELL.*COM.*"),
                    Pattern.compile(".*AMZN.*"),
                    Pattern.compile(".*[aA][mM][aA][zZ][oO][nN].*"),
                    Pattern.compile(".*Amazon\\sMarketplace.*"),
                    Pattern.compile(".*Wayfair.*"),
                    Pattern.compile(".*Duluth\\sPack.*"),
                    Pattern.compile(".*IKEA.*"),
                    Pattern.compile(".*The\\sFoundry\\sHome\\sGoods.*"),
                    Pattern.compile(".*THEFOUNDRYHOMEGOODS.*"),
                    Pattern.compile(".*[rR][eE][iI].*"),
                    Pattern.compile(".*[mM][iI][cC][hH][aA][eE][lL][sS].*"),
                    Pattern.compile(".*[iI][nN][sS][pP][eE][cC][tT].*")
            ),
            Lists.newArrayList(
                    "Home"
            ),
            BigDecimal.ZERO),
    INCOME(
            Lists.newArrayList(
                    Pattern.compile(".*Expensify.*"),
                    Pattern.compile(".*DBA\\sInsperi\\sTYPE:\\sPAYROLL\\s\\sID.*"),
                    Pattern.compile(".*Income\\sfrom\\sInsperity.*"),
                    Pattern.compile(".*Interest\\sIncome.*"),
                    Pattern.compile(".*Deposit.*"),
                    Pattern.compile(".*[sS][eE][cC][uU][rR][iI][aA][nN].*"),
                    Pattern.compile(".*IRS\\s\\sTREAS\\s310\\sTYPE.*"),
                    Pattern.compile(".*[mM][aA][iI][nN][sS]'[lL]\\s[sS][eE][rR][vV][iI][cC][eE][sS].*"),
                    Pattern.compile(".*[pP][oO][sS][hH][mM][aA][rR][kK].*"),
                    Pattern.compile(".*[mM][eE][rR][cC][aA][rR][iI].*"),
                    Pattern.compile(".*[sS][pP][eE][cC][iI][aA][lL].*[oO][lL][yY][mM][pP][iI][cC][sS].*"),
                    Pattern.compile(".*[rR]eward.*[bB]axter.*")
            ),
            Lists.newArrayList(
                    "Paycheck"
            ),
            BigDecimal.ZERO),
    CHECK(
            Lists.newArrayList(
                    Pattern.compile(".*CHECK\\s#\\s.*")
            ),
            Lists.newArrayList(),
            BigDecimal.ZERO),
    RENTERS_POLICY(
            Lists.newArrayList(),
            Lists.newArrayList(),
            BigDecimal.valueOf(-8.32)),
    MORTGAGE(
            Lists.newArrayList(
                    Pattern.compile(".*[mM][oO][rR][tT][gG][aA][gG][eE].*"),
                    Pattern.compile(".*[tT][rR][uU][sS][tT][fF][uU][nN][dD].*")
            ),
            Lists.newArrayList(),
            BigDecimal.ZERO
    ),
    IGNORED(
            Lists.newArrayList(
                    Pattern.compile(".*Share.*01.*"), // transfer from/to savings account
                    Pattern.compile(".*Credit\\sCard\\sPayment.*"), // transfer to credit card (credit)
                    Pattern.compile(".*Withdrawal\\sHome\\sPmt\\sto\\scard.*"), // transfer from debit to credit card
                    Pattern.compile(".*Payment\\sto\\sMinnesota\\sLife.*"), // Sophie's Life Insurance, tracked manually since it's not paid monthly
                    Pattern.compile(".*[sS][tT][aA][tT][eE].*[fF][aA][rR][mM].*"), // renters policy, tracked manually since it's paid 2x per year - NOT VALIDATED YET
                    Pattern.compile(".*[pP][rR][oO][gG][rR][eE][sS][sS][iI][vV][eE].*"), // auto insurance tracked with default monthly value
                    Pattern.compile(".*#26 WALGREE #0-W 734 GRAND ST PAUL MN.*"), // walgreen's atm
                    Pattern.compile(".*[tT][rR][aA][nN][sS][fF][eE][rR].*"), // all transfers
                    Pattern.compile(".*XX8274.*") // mortgage down payment
            ),
            Lists.newArrayList(),
            BigDecimal.ZERO),
    OTHER(
            Lists.newArrayList(),
            Lists.newArrayList(), BigDecimal.ZERO
    );

    private final List<Pattern> patterns;
    private final List<String> bankTypes;
    private final BigDecimal defaultAmount;

    SpendingCategory(List<Pattern> patterns, List<String> bankTypes, BigDecimal defaultAmount) {
        this.patterns = Objects.requireNonNull(patterns);
        this.bankTypes = Objects.requireNonNull(bankTypes);
        this.defaultAmount = defaultAmount;
    }

    List<Pattern> getPatterns() {
        return this.patterns;
    }

    BigDecimal getDefaultAmount() {
        return this.defaultAmount;
    }

    public static SpendingCategory mapFromBankType(String bankType) {
        SpendingCategory category = SpendingCategory.OTHER;
        boolean bankTypeFoundForCategory = false;
        for (SpendingCategory currentCategory : SpendingCategory.values()) {
            if (bankTypeFoundForCategory) {
                continue;
            }
            for (String currentBankType : currentCategory.bankTypes) {
                if (bankType.equalsIgnoreCase(currentBankType)) {
                    category = currentCategory;
                    bankTypeFoundForCategory = true;
//                    System.out.println("Found spending category for bank" +
//                            " type. spending-category=" + currentCategory.name() + ". bank-type=" + bankType);
                    break;
                }
            }
        }
        return category;
    }
}
