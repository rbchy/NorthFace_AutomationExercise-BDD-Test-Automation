package com.northface.automation.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.northface.automation.lib.PriceUtils;

/**
 * JUnit 5 unit tests for {@link PriceUtils} — pure logic, no browser, fast.
 * Run with: {@code mvn test -Punit}  (or double-click run-unit.bat).
 */
@DisplayName("PriceUtils unit tests")
class PriceUtilsTest {

    @Nested
    @DisplayName("parsePrice - positive")
    class ParsePositive {

        @ParameterizedTest(name = "\"{0}\" -> {1}")
        @CsvSource({
                "'$120.00', 120.00",
                "'1,234.56', 1234.56",
                "'Rs. 500', 500.0",
                "'99', 99.0"
        })
        void parsesValidPrices(String raw, double expected) {
            assertEquals(expected, PriceUtils.parsePrice(raw), 0.001);
        }
    }

    @Nested
    @DisplayName("parsePrice - negative")
    class ParseNegative {

        @Test
        void nullIsRejected() {
            assertThrows(IllegalArgumentException.class, () -> PriceUtils.parsePrice(null));
        }

        @Test
        void blankIsRejected() {
            assertThrows(IllegalArgumentException.class, () -> PriceUtils.parsePrice("   "));
        }

        @Test
        void nonNumericIsRejected() {
            assertThrows(IllegalArgumentException.class, () -> PriceUtils.parsePrice("abc"));
        }
    }

    @Nested
    @DisplayName("applyDiscount")
    class Discount {

        @Test
        void appliesPercentageCorrectly() {
            assertEquals(90.0, PriceUtils.applyDiscount(100, 10), 0.001);
        }

        @Test
        void zeroPercentLeavesPriceUnchanged() {
            assertEquals(100.0, PriceUtils.applyDiscount(100, 0), 0.001);
        }

        @Test
        void negativePercentIsRejected() {
            assertThrows(IllegalArgumentException.class, () -> PriceUtils.applyDiscount(100, -5));
        }

        @Test
        void over100PercentIsRejected() {
            assertThrows(IllegalArgumentException.class, () -> PriceUtils.applyDiscount(100, 150));
        }
    }
}
