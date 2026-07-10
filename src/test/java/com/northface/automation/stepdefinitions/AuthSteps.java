package com.northface.automation.stepdefinitions;

import static org.testng.Assert.assertTrue;

import com.northface.automation.pages.demo.DemoLoginPage;
import com.northface.automation.utils.DriverManager;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Functional auth tests on the demo shop: positive (start new registration)
 * and negative (invalid login is rejected).
 */
public class AuthSteps {

    private DemoLoginPage loginPage;

    @Given("the shopper is on the demo login page")
    public void the_shopper_is_on_the_demo_login_page() {
        loginPage = new DemoLoginPage(DriverManager.getDriver()).open();
    }

    // ----- Positive -----
    @When("the shopper starts signup with a new random email")
    public void the_shopper_starts_signup_with_a_new_random_email() {
        String uniqueEmail = "qa_" + System.currentTimeMillis() + "@example.com";
        loginPage.startSignup("QA Tester", uniqueEmail);
    }

    @Then("the account information page should be shown")
    public void the_account_information_page_should_be_shown() {
        assertTrue(loginPage.isAccountInfoPageDisplayed(),
                "Expected the 'Enter Account Information' page after starting signup");
    }

    // ----- Negative -----
    @When("the shopper logs in with invalid credentials")
    public void the_shopper_logs_in_with_invalid_credentials() {
        loginPage.loginAs("no_such_user_" + System.currentTimeMillis() + "@example.com", "wrongPassword!");
    }

    @Then("a login error should be shown")
    public void a_login_error_should_be_shown() {
        assertTrue(loginPage.isLoginErrorDisplayed(),
                "Expected an error message for invalid login credentials");
    }
}
