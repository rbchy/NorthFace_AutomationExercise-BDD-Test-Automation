package com.northface.automation.pages.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.northface.automation.pages.BasePage;
import com.northface.automation.utils.ConfigReader;

/**
 * Page Object for the demo shop login / signup page (/login).
 * Uses the site's stable data-qa hooks.
 */
public class DemoLoginPage extends BasePage {

    // ---------- Login form ----------
    private final By loginEmail    = By.cssSelector("input[data-qa='login-email']");
    private final By loginPassword = By.cssSelector("input[data-qa='login-password']");
    private final By loginButton   = By.cssSelector("button[data-qa='login-button']");
    private final By loginError    = By.xpath("//form[@action='/login']//p");

    // ---------- Signup form ----------
    private final By signupName   = By.cssSelector("input[data-qa='signup-name']");
    private final By signupEmail  = By.cssSelector("input[data-qa='signup-email']");
    private final By signupButton = By.cssSelector("button[data-qa='signup-button']");
    private final By accountInfoHeader = By.xpath(
            "//*[contains(translate(text(),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),"
          + "'ENTER ACCOUNT INFORMATION')]");
    // Google consent overlay (shared with other demo pages).
    private final By consentButton = By.cssSelector(".fc-cta-consent, button.fc-cta-consent, .fc-button-label");

    public DemoLoginPage(WebDriver driver) {
        super(driver);
    }

    /** Opens the login/signup page. */
    public DemoLoginPage open() {
        driver.get(ConfigReader.get("demo.url") + "login");
        dismissConsentIfPresent();
        return this;
    }

    public void dismissConsentIfPresent() {
        try {
            if (isDisplayed(consentButton)) {
                click(consentButton);
            }
        } catch (Exception ignored) {
        }
    }

    // ---------- Login (negative flow) ----------

    /** Submits the login form with the given credentials. */
    public void loginAs(String email, String password) {
        type(loginEmail, email);
        type(loginPassword, password);
        click(loginButton);
    }

    /** @return true when the "incorrect credentials" error is displayed. */
    public boolean isLoginErrorDisplayed() {
        return isDisplayed(loginError);
    }

    public String getLoginError() {
        return isDisplayed(loginError) ? getText(loginError) : "";
    }

    // ---------- Signup (positive flow) ----------

    /** Enters a name + email and submits the signup form. */
    public void startSignup(String name, String email) {
        type(signupName, name);
        type(signupEmail, email);
        click(signupButton);
    }

    /** @return true when the "Enter Account Information" page is shown. */
    public boolean isAccountInfoPageDisplayed() {
        return isDisplayed(accountInfoHeader);
    }
}
