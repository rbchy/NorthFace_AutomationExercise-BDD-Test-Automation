package com.northface.automation.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object for the product-listing / search-results grid (PLP).
 */
public class SearchResultsPage extends BasePage {

    // ---------- Locators ----------
    // North Face product tiles link to product-detail pages. Multiple patterns
    // are combined so the check survives minor markup changes.
    private final By productLinks = By.cssSelector(
            "a[href*='/p/'], [data-test-id*='product-tile'] a, [data-test-id*='product-card'] a, li[class*='product'] a");
    private final By noResultsMsg = By.xpath(
            "//*[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'no results') "
          + "or contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'did not match') "
          + "or contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'no products')]");

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    // ---------- Queries ----------

    /**
     * @return true when at least one product tile appears within the wait window.
     *         Waits explicitly because results render asynchronously after submit.
     */
    public boolean hasResults() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(productLinks));
            return count(productLinks) > 0;
        } catch (TimeoutException e) {
            return false;
        }
    }

    /** @return number of product tiles currently rendered. */
    public int getResultsCount() {
        return count(productLinks);
    }

    /** @return true when the site shows an explicit "no results" message. */
    public boolean isNoResultsMessageDisplayed() {
        // Short wait: no-results copy renders quickly once the query resolves.
        try {
            new org.openqa.selenium.support.ui.WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.presenceOfElementLocated(noResultsMsg));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    // ---------- Actions ----------

    /** Opens the first product tile in the grid. */
    public ProductPage openFirstProduct() {
        scrollIntoView(productLinks);
        click(productLinks);
        return new ProductPage(driver);
    }
}
