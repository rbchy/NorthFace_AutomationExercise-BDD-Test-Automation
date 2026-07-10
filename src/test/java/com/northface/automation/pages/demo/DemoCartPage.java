package com.northface.automation.pages.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.northface.automation.pages.BasePage;

/**
 * Page Object for the demo shop cart page (/view_cart).
 */
public class DemoCartPage extends BasePage {

    // ---------- Locators ----------
    private final By cartRows       = By.cssSelector("#cart_info_table tbody tr");
    private final By proceedButton  = By.cssSelector("a.check_out");

    public DemoCartPage(WebDriver driver) {
        super(driver);
    }

    /** @return number of line items in the cart. */
    public int getItemCount() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(cartRows));
        } catch (Exception ignored) {
            // No rows appeared within the wait -> treat as empty.
        }
        return count(cartRows);
    }

    public boolean isProceedToCheckoutDisplayed() {
        return isDisplayed(proceedButton);
    }
}
