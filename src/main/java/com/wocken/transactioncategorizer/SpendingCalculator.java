package com.wocken.transactioncategorizer;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SpendingCalculator {
    private final Map<SpendingCategory, BigDecimal> categoryRunningTotals;

    public SpendingCalculator() {
        this.categoryRunningTotals = initializeCategoryTotals();
    }

    private Map<SpendingCategory, BigDecimal> initializeCategoryTotals() {
        Map<SpendingCategory, BigDecimal> map = new ConcurrentHashMap<>();
        for (SpendingCategory currentCat : SpendingCategory.values()) {
            map.put(currentCat, currentCat.getDefaultAmount());
        }
        return map;
    }

    public void addToCategory(SpendingCategory category, BigDecimal toAdd) {
        this.categoryRunningTotals.compute(category, (spendingCategory, currentTotal) -> currentTotal.add(toAdd));
    }

    public void subtractFromCategory(SpendingCategory category, BigDecimal toSubtract) {
        this.categoryRunningTotals.compute(category, (spendingCategory, currentTotal) -> currentTotal.subtract(toSubtract));
    }

    public String printTotals() {
        StringBuilder sb = new StringBuilder();
        sb.append("CATEGORY, AMOUNT\n");
        categoryRunningTotals.forEach(((spendingCategory, currentTotal) -> {
            sb.append(spendingCategory.name())
                    .append(", ")
                    .append(currentTotal.toString())
                    .append("\n");
        }));
        return sb.toString();
    }

    public Map<SpendingCategory, BigDecimal> getRunningTotals() {
        return this.categoryRunningTotals;
    }

    public BigDecimal getRunningTotalForCategory(SpendingCategory cat) {
        return this.categoryRunningTotals.get(cat);
    }
}
