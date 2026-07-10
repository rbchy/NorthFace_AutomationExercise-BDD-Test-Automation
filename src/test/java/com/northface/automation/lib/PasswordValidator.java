package com.northface.automation.lib;

/** Password strength rules: length >= 8 with upper, lower and a digit. */
public final class PasswordValidator {

    private PasswordValidator() {
    }

    public static boolean isStrong(String pw) {
        if (pw == null || pw.length() < 8) {
            return false;
        }
        boolean upper = false, lower = false, digit = false;
        for (char c : pw.toCharArray()) {
            if (Character.isUpperCase(c)) {
                upper = true;
            } else if (Character.isLowerCase(c)) {
                lower = true;
            } else if (Character.isDigit(c)) {
                digit = true;
            }
        }
        return upper && lower && digit;
    }
}
