package com.northface.automation.lib;

import java.math.BigDecimal;
import java.util.List;

/** Pure cart math helpers — subtotal, tax, shipping, grand total. */
public final class CartCalculator {

    private CartCalculator() {
    }

    public static double subtotal(List<Double> prices) {
        if (prices == null) {
            throw new IllegalArgumentException("prices list is null");
        }
        BigDecimal sum = BigDecimal.ZERO;
        for (Double p : prices) {
            if (p == null || !(p >= 0) || Double.isInfinite(p)) {
                throw new IllegalArgumentException("invalid price: " + p);
            }
            sum = sum.add(BigDecimal.valueOf(p));
        }
        return Money.round2(sum);
    }

    public static double applyTax(double amount, double taxPercent) {
        if (!(amount >= 0) || Double.isInfinite(amount)) {
            throw new IllegalArgumentException("amount must be >= 0");
        }
        if (!(taxPercent >= 0) || Double.isInfinite(taxPercent)) {
            throw new IllegalArgumentException("taxPercent must be >= 0");
        }
        // Decimal maths: 10.10 + 15% is 11.615 -> 11.62 (binary doubles gave 11.61).
        return Money.round2(BigDecimal.valueOf(amount)
                .multiply(BigDecimal.valueOf(100).add(BigDecimal.valueOf(taxPercent)))
                .movePointLeft(2));
    }

    /** Free shipping at or above the threshold; otherwise a flat fee. */
    public static double shipping(double subtotal, double threshold, double flatFee) {
        if (!(subtotal >= 0) || !(threshold >= 0) || !(flatFee >= 0)) {
            throw new IllegalArgumentException("values must be >= 0");
        }
        return subtotal >= threshold ? 0.0 : flatFee;
    }

    public static double grandTotal(double subtotal, double taxPercent, double shipping) {
        if (!(shipping >= 0) || Double.isInfinite(shipping)) {
            throw new IllegalArgumentException("shipping must be >= 0");
        }
        return Money.round2(BigDecimal.valueOf(applyTax(subtotal, taxPercent)).add(BigDecimal.valueOf(shipping)));
    }
}
