package com.northface.automation.unit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.northface.automation.lib.EmailValidator;

/** JUnit 5 unit tests for {@link EmailValidator} (data validation). */
@DisplayName("EmailValidator unit tests")
class EmailValidatorTest {

    @ParameterizedTest(name = "valid: {0}")
    @ValueSource(strings = {
            "user@example.com",
            "first.last@sub.domain.co",
            "qa+tag@test.io",
            "a_b@c-d.com"
    })
    void acceptsValidEmails(String email) {
        assertTrue(EmailValidator.isValid(email), "should be valid: " + email);
    }

    @ParameterizedTest(name = "invalid: {0}")
    @ValueSource(strings = {
            "",
            "plainaddress",
            "user@nodot",
            "@example.com",
            "user@@example.com",
            "user name@example.com",
            "user@.com"
    })
    void rejectsInvalidEmails(String email) {
        assertFalse(EmailValidator.isValid(email), "should be invalid: " + email);
    }

    @Test
    void rejectsNull() {
        assertFalse(EmailValidator.isValid(null));
    }
}
