package com.northface.automation.stepdefinitions;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.northface.automation.utils.ConfigReader;
import com.northface.automation.utils.DriverManager;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

/**
 * Basic accessibility checks on the demo shop:
 * positive (page declares language + title) and a guard/negative check
 * (the search field must not lack an accessible name).
 */
public class AccessibilitySteps {

    private WebDriver driver;

    @Given("the shopper opens the demo shop for accessibility checks")
    public void the_shopper_opens_the_demo_shop_for_accessibility_checks() {
        driver = DriverManager.getDriver();
        driver.get(ConfigReader.get("demo.url"));
    }

    @Given("the shopper opens the demo products page for accessibility checks")
    public void the_shopper_opens_the_demo_products_page_for_accessibility_checks() {
        driver = DriverManager.getDriver();
        driver.get(ConfigReader.get("demo.url") + "products");
    }

    // ----- Positive -----
    @Then("the page should declare a document language")
    public void the_page_should_declare_a_document_language() {
        Object lang = ((JavascriptExecutor) driver)
                .executeScript("return document.documentElement.lang;");
        assertTrue(lang != null && !lang.toString().trim().isEmpty(),
                "The <html> element does not declare a lang attribute");
    }

    @Then("the page title should not be empty")
    public void the_page_title_should_not_be_empty() {
        String title = driver.getTitle();
        assertTrue(title != null && !title.trim().isEmpty(), "The page title is empty");
    }

    // ----- Negative / guard -----
    @Then("the product search field should have an accessible name")
    public void the_product_search_field_should_have_an_accessible_name() {
        WebElement input = driver.findElement(By.id("search_product"));
        String placeholder = input.getAttribute("placeholder");
        String ariaLabel = input.getAttribute("aria-label");
        boolean hasName = (placeholder != null && !placeholder.trim().isEmpty())
                || (ariaLabel != null && !ariaLabel.trim().isEmpty());
        assertTrue(hasName, "The product search field lacks an accessible name (placeholder/aria-label)");
    }
}
