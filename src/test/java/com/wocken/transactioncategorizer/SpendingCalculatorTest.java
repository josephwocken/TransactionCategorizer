package com.wocken.transactioncategorizer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class SpendingCalculatorTest {

    private SpendingCalculator classUnderTest;

    @BeforeEach
    public void doBeforeEach() {
        classUnderTest = new SpendingCalculator();
    }

    @Test
    public void categories_initialized_to_zero() {
        for (SpendingCategory currentCategory : SpendingCategory.values()) {
            Assertions.assertEquals(currentCategory.getDefaultAmount(), classUnderTest.getRunningTotals().get(currentCategory));
        }
    }

    @Test
    public void renters_policy_is_8_32() {
        Assertions.assertEquals(
                BigDecimal.valueOf(-8.32),
                classUnderTest.getRunningTotalForCategory(SpendingCategory.RENTERS_POLICY)
        );
    }

    @Test
    public void addToCategory() {
        classUnderTest.addToCategory(
                SpendingCategory.AUTO_GAS,
                BigDecimal.ONE
        );
        BigDecimal actualTotal = classUnderTest.getRunningTotalForCategory(SpendingCategory.AUTO_GAS);
        Assertions.assertEquals(BigDecimal.ONE, actualTotal);
    }

    @Test
    public void subtractFromCategory() {
        classUnderTest.subtractFromCategory(
                SpendingCategory.AUTO_GAS,
                BigDecimal.valueOf(1.00)
        );
        BigDecimal actualTotal = classUnderTest.getRunningTotalForCategory(SpendingCategory.AUTO_GAS);
        Assertions.assertEquals(
                BigDecimal.valueOf(-1.00),
                actualTotal
        );
    }

}
