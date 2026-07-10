package com.northface.automation.stepdefinitions;

import static org.testng.Assert.assertTrue;

import com.northface.automation.pages.HomePage;
import com.northface.automation.utils.DriverManager;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

/**
 * Steps shared across features: opening the site and generic title/URL checks.
 * <p>
 * Page Objects are created here and reused by the feature-specific step
 * classes through the {@link TestContext} so every class sees the same state.
 */
public class CommonSteps {

    private final TestContext context;

    // PicoContainer injects a shared TestContext into every step class.
    public CommonSteps(TestContext context) {
        this.context = context;
    }

    @Given("the user is on the North Face home page")
    public void the_user_is_on_the_north_face_home_page() {
        HomePage homePage = new HomePage(DriverManager.getDriver());
        homePage.open();
        context.setHomePage(homePage);
    }

    @Then("the brand logo should be displayed")
    public void the_brand_logo_should_be_displayed() {
        assertTrue(context.getHomePage().isLogoDisplayed(),
                "Brand logo was not displayed on the home page");
    }

    @Then("the primary navigation should be displayed")
    public void the_primary_navigation_should_be_displayed() {
        assertTrue(context.getHomePage().isPrimaryNavDisplayed(),
                "Primary navigation (Men's/Women's) was not displayed");
    }

    @Then("the page title should contain {string}")
    public void the_page_title_should_contain(String expected) {
        String title = DriverManager.getDriver().getTitle();
        assertTrue(title != null && title.toLowerCase().contains(expected.toLowerCase()),
                "Expected title to contain '" + expected + "' but was '" + title + "'");
    }

    @Then("the page URL should contain {string}")
    public void the_page_url_should_contain(String expected) {
        String url = DriverManager.getDriver().getCurrentUrl().toLowerCase();
        assertTrue(url.contains(expected.toLowerCase()),
                "Expected URL to contain '" + expected + "' but was '" + url + "'");
    }
}
