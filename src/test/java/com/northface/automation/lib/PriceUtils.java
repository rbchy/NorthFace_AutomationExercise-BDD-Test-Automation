package com.northface.automation.lib;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Pure, browser-free price helpers — an example "system under test" for unit
 * testing (parsing product price strings and applying discounts).
 */
public final class PriceUtils {

    // Matches the first number in a string: 1,234.56 / 120.00 / 99
    private static final Pattern NUMBER = Pattern.compile("[0-9][0-9,]*(\\.[0-9]+)?");

    private PriceUtils() {
    }

    /**
     * Parses a display price such as {@code "$120.00"}, {@code "1,234.56"} or
     * {@code "Rs. 500"} into a double.
     *
     * @throws IllegalArgumentException if the input is null, blank, or has no number
     */
    public static double parsePrice(String raw) {
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("price is null or blank");
        }
        Matcher m = NUMBER.matcher(raw);
        if (!m.find()) {
            throw new IllegalArgumentException("no numeric content in: " + raw);
        }
        return Double.parseDouble(m.group().replace(",", ""));
    }

    /**
     * Applies a percentage discount and rounds to 2 decimals.
     *
     * @param price   original price (>= 0)
     * @param percent discount percentage in [0, 100]
     * @throws IllegalArgumentException on out-of-range inputs
     */
    public static double applyDiscount(double price, double percent) {
        if (price < 0) {
            throw new IllegalArgumentException("price must be >= 0");
        }
        if (percent < 0 || percent > 100) {
            throw new IllegalArgumentException("percent must be within [0, 100]");
        }
        double discounted = price * (1 - percent / 100.0);
        return Math.round(discounted * 100.0) / 100.0;
    }
}
