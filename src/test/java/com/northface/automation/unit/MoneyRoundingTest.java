package com.northface.automation.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.northface.automation.lib.CartCalculator;
import com.northface.automation.lib.PriceUtils;

/**
 * Half-cent cases that binary doubles round the wrong way. These are exact comparisons
 * (delta 0): a cent off on a receipt is a defect. All failed before the BigDecimal fix.
 */
@DisplayName("Money rounding to the cent")
class MoneyRoundingTest {

    @ParameterizedTest(name = "${0} - {1}% = ${2}")
    @CsvSource({
            "10.10, 25, 7.58",   // 7.575  (old code: 7.57)
            "10.10, 5,  9.60",   // 9.595  (old code: 9.59)
            "10.03, 50, 5.02",   // 5.015  (old code: 5.01)
            "10.05, 90, 1.01",   // 1.005  (old code: 1.00)
            "100.00, 10, 90.00",
            "0.00, 50, 0.00"
    })
    void discountRoundsHalfUp(double price, double percent, double expected) {
        assertEquals(expected, PriceUtils.applyDiscount(price, percent), 0.0);
    }

    @ParameterizedTest(name = "${0} + {1}% tax = ${2}")
    @CsvSource({
            "10.10, 15, 11.62",  // 11.615 (old code: 11.61)
            "10.02, 25, 12.53",  // 12.525 (old code: 12.52)
            "10.75, 18, 12.69",  // 12.685 (old code: 12.68)
            "19.99, 7.5, 21.49"  // 21.48925
    })
    void taxRoundsHalfUp(double amount, double percent, double expected) {
        assertEquals(expected, CartCalculator.applyTax(amount, percent), 0.0);
    }

    @Test
    void manySmallPricesAddUpExactly() {
        // 0.1 + 0.2 + ... in doubles drifts; ten 0.10 items must be exactly 1.00
        assertEquals(1.00, CartCalculator.subtotal(Arrays.asList(0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1)), 0.0);
        assertEquals(0.30, CartCalculator.subtotal(List.of(0.1, 0.2)), 0.0);
    }

    @Test
    void grandTotalAddsShippingAfterTax() {
        assertEquals(21.61, CartCalculator.grandTotal(10.10, 15, 9.99), 0.0); // 11.62 + 9.99
    }

    @ParameterizedTest
    @ValueSource(doubles = {Double.NaN, Double.POSITIVE_INFINITY, -0.01})
    void nonsenseAmountsAreRejected(double bad) {
        assertThrows(IllegalArgumentException.class, () -> PriceUtils.applyDiscount(bad, 10));
        assertThrows(IllegalArgumentException.class, () -> PriceUtils.applyDiscount(10, bad));
        assertThrows(IllegalArgumentException.class, () -> CartCalculator.applyTax(bad, 10));
        assertThrows(IllegalArgumentException.class, () -> CartCalculator.subtotal(List.of(5.0, bad)));
        assertThrows(IllegalArgumentException.class, () -> CartCalculator.grandTotal(10, 10, bad));
    }

    @Test
    void aMissingPriceInTheCartIsRejectedNotANullPointer() {
        assertThrows(IllegalArgumentException.class, () -> CartCalculator.subtotal(Arrays.asList(5.0, null)));
    }
}
