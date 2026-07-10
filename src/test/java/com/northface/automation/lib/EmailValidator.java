package com.northface.automation.lib;

import java.util.regex.Pattern;

/** Simple RFC-ish email format validator. */
public final class EmailValidator {

    private static final Pattern PATTERN =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private EmailValidator() {
    }

    public static boolean isValid(String email) {
        return email != null && PATTERN.matcher(email).matches();
    }
}
