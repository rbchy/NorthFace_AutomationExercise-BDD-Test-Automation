package com.northface.automation.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.northface.automation.lib.CartCalculator;

/** JUnit 5 unit tests for {@link CartCalculator}. Run: {@code mvn test -Punit}. */
@DisplayName("CartCalculator unit tests")
class CartCalculatorTest {

    @Nested
    @DisplayName("subtotal")
    class Subtotal {
        @Test
        void sumsPrices() {
            assertEquals(60.5, CartCalculator.subtotal(List.of(10.0, 20.0, 30.5)), 0.001);
        }

        @Test
        void emptyCartIsZero() {
            assertEquals(0.0, CartCalculator.subtotal(List.of()), 0.001);
        }

        @Test
        void nullListIsRejected() {
            assertThrows(IllegalArgumentException.class, () -> CartCalculator.subtotal(null));
        }

        @Test
        void negativePriceIsRejected() {
            assertThrows(IllegalArgumentException.class,
                    () -> CartCalculator.subtotal(List.of(10.0, -5.0)));
        }
    }

    @Nested
    @DisplayName("applyTax")
    class Tax {
        @ParameterizedTest(name = "{0} + {1}% = {2}")
        @CsvSource({"100,10,110.0", "50,0,50.0", "200,8.5,217.0"})
        void appliesTax(double amount, double percent, double expected) {
            assertEquals(expected, CartCalculator.applyTax(amount, percent), 0.001);
        }

        @Test
        void negativeAmountIsRejected() {
            assertThrows(IllegalArgumentException.class, () -> CartCalculator.applyTax(-1, 10));
        }

        @Test
        void negativeTaxIsRejected() {
            assertThrows(IllegalArgumentException.class, () -> CartCalculator.applyTax(100, -1));
        }
    }

    @Nested
    @DisplayName("shipping")
    class Shipping {
        @ParameterizedTest(name = "subtotal {0}, threshold {1}, fee {2} -> {3}")
        @CsvSource({"100,50,9.99,0.0", "30,50,9.99,9.99", "50,50,9.99,0.0"})
        void computesShipping(double subtotal, double threshold, double fee, double expected) {
            assertEquals(expected, CartCalculator.shipping(subtotal, threshold, fee), 0.001);
        }

        @Test
        void negativeInputIsRejected() {
            assertThrows(IllegalArgumentException.class,
                    () -> CartCalculator.shipping(-1, 50, 9.99));
        }
    }

    @Nested
    @DisplayName("grandTotal")
    class Grand {
        @ParameterizedTest(name = "subtotal {0}, tax {1}%, ship {2} -> {3}")
        @CsvSource({"100,10,0,110.0", "30,0,9.99,39.99"})
        void computesGrandTotal(double subtotal, double percent, double ship, double expected) {
            assertEquals(expected, CartCalculator.grandTotal(subtotal, percent, ship), 0.001);
        }
    }
}
