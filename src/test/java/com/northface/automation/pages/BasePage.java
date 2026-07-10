package com.northface.automation.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.northface.automation.utils.ConfigReader;

/**
 * Parent of every Page Object.
 * <p>
 * Holds the shared {@link WebDriver} / {@link WebDriverWait} and a set of
 * reusable, null-safe interaction helpers (click, type, waits, text reads)
 * so individual pages stay thin and readable.
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(ConfigReader.getInt("explicit.wait")));
    }

    // ---------- Reusable interaction helpers ----------

    /** Waits for visibility, then returns the element. */
    protected WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /** Waits until the element is clickable, then clicks it. */
    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    /** Clears the field and types the given text. */
    protected void type(By locator, String text) {
        WebElement element = waitForVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    /** @return trimmed visible text of the element. */
    protected String getText(By locator) {
        return waitForVisible(locator).getText().trim();
    }

    /** @return true if at least one matching element is present and displayed. */
    protected boolean isDisplayed(By locator) {
        try {
            List<WebElement> elements = driver.findElements(locator);
            return !elements.isEmpty() && elements.get(0).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** @return number of elements matching the locator. */
    protected int count(By locator) {
        return driver.findElements(locator).size();
    }

    /** Smoothly scrolls the element into the viewport (helps with lazy-loaded grids). */
    protected void scrollIntoView(By locator) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }

    /** @return the current page title. */
    public String getPageTitle() {
        return driver.getTitle();
    }

    /** @return the current URL. */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
