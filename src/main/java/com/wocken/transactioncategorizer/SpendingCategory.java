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
            Pattern.compile(".*Speedway.*")
    ),
            Lists.newArrayList(
                    "Gas & Fuel"
            ),
            BigDecimal.ZERO),
    AUTO_REPAIRS_AND_MAINTENANCE(
            Lists.newArrayList(
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
                    Pattern.compile(".*MINNESOTA\\sSTATE\\sPARKS.*")
            ),
            Lists.newArrayList("Entertainment"),
            BigDecimal.ZERO),
    LIFE_INSURANCE(Lists.newArrayList(), Lists.newArrayList(), BigDecimal.valueOf(-67)),
    GROCERIES(
            Lists.newArrayList(
                    Pattern.compile(".*Target.*"),
                    Pattern.compile(".*[wW]almart.*"),
                    Pattern.compile(".*[jJ]erry.*[fF]ood.*")
            ),
            Lists.newArrayList(
                    "Food & Dining"
            ), BigDecimal.ZERO),
    RESTAURANTS(
            Lists.newArrayList(
                    Pattern.compile(".*[pP][iI][zZ][zZ][aA].*"),
                    Pattern.compile(".*[cC][aA][kK][eE].*"),
                    Pattern.compile(".*[cC][oO][nN][eE].*"),
                    Pattern.compile(".*[cC][oO][fF][fF][eE][eE].*"),
                    Pattern.compile(".*[Ss][uU][sS][hH][iI].*"),
                    Pattern.compile(".*[bB][rR][eE][wW].*")
            ),
            Lists.newArrayList(
                    "Food & Dining"
            ), BigDecimal.ZERO),
    GIFTS(
            Lists.newArrayList(
                    Pattern.compile(".*[pP][aA][pP][eE][rR]\\s[sS][oO][uU][rR][cC][eE].*")
            ),
            Lists.newArrayList("Gifts & Donations"), BigDecimal.ZERO),
    HEALTH_CARE(
            Lists.newArrayList(
                    Pattern.compile(".*[oO][pP][tT][iI][cC][iI][aA][nN].*")
            ),
            Lists.newArrayList(),
            BigDecimal.ZERO
    ),
    RENT(Lists.newArrayList(), Lists.newArrayList(), BigDecimal.valueOf(-1500)),
    ELECTRONICS_AND_SOFTWARE(Lists.newArrayList(
            Pattern.compile(".*Amazon\\sWeb\\sServices.*"),
            Pattern.compile(".*GODADDY.*")
    ),
            Lists.newArrayList(
                    "Electronics & Software",
                    "Business Services"
            ),
            BigDecimal.ZERO),
    PERSONAL_CARE(
            Lists.newArrayList(
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
                    Pattern.compile(".*[mM][iI][cC][hH][aA][eE][lL][sS].*"),
                    Pattern.compile(".*[iI][nN][sS][pP][eE][cC][tT].*")
            ),
            Lists.newArrayList(
                    "Home"
            ),
            BigDecimal.ZERO),
    INCOME(
            Lists.newArrayList(
                    Pattern.compile(".*<income statement here>.*")
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
    IGNORED(
            Lists.newArrayList(),
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
                    break;
                }
            }
        }
        return category;
    }
}
