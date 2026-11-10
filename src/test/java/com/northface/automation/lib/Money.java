package com.northface.automation.lib;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Rounds money to cents the way a till does (half-up on the decimal value, not the binary double). */
final class Money {

    private Money() {
    }

    static double round2(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
