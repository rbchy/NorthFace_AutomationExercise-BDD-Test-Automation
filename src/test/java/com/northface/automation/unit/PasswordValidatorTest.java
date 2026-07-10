package com.northface.automation.unit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.northface.automation.lib.PasswordValidator;

/** JUnit 5 unit tests for {@link PasswordValidator}. */
@DisplayName("PasswordValidator unit tests")
class PasswordValidatorTest {

    @ParameterizedTest(name = "strong: {0}")
    @ValueSource(strings = {"Password1", "Abcdefg9", "Xy12wz9Q"})
    void acceptsStrongPasswords(String pw) {
        assertTrue(PasswordValidator.isStrong(pw), "should be strong: " + pw);
    }

    @ParameterizedTest(name = "weak: {0}")
    @ValueSource(strings = {
            "short1A",        // too short
            "alllowercase1",  // no uppercase
            "ALLUPPER1",       // no lowercase
            "NoDigitsHere",   // no digit
            "1234567",         // too short + no letters
            ""                 // empty
    })
    void rejectsWeakPasswords(String pw) {
        assertFalse(PasswordValidator.isStrong(pw), "should be weak: " + pw);
    }

    @Test
    void rejectsNull() {
        assertFalse(PasswordValidator.isStrong(null));
    }
}
