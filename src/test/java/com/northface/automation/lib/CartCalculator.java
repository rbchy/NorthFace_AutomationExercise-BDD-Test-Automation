package com.northface.automation.lib;

import java.util.List;

/** Pure cart math helpers — subtotal, tax, shipping, grand total. */
public final class CartCalculator {

    private CartCalculator() {
    }

    public static double subtotal(List<Double> prices) {
        if (prices == null) {
            throw new IllegalArgumentException("prices list is null");
        }
        double sum = 0;
        for (double p : prices) {
            if (p < 0) {
                throw new IllegalArgumentException("negative price: " + p);
            }
            sum += p;
        }
        return round2(sum);
    }

    public static double applyTax(double amount, double taxPercent) {
        if (amount < 0) {
            throw new IllegalArgumentException("amount must be >= 0");
        }
        if (taxPercent < 0) {
            throw new IllegalArgumentException("taxPercent must be >= 0");
        }
        return round2(amount * (1 + taxPercent / 100.0));
    }

    /** Free shipping at or above the threshold; otherwise a flat fee. */
    public static double shipping(double subtotal, double threshold, double flatFee) {
        if (subtotal < 0 || threshold < 0 || flatFee < 0) {
            throw new IllegalArgumentException("values must be >= 0");
        }
        return subtotal >= threshold ? 0.0 : flatFee;
    }

    public static double grandTotal(double subtotal, double taxPercent, double shipping) {
        return round2(applyTax(subtotal, taxPercent) + shipping);
    }

    private static double round2(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}
