package com.northface.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.northface.automation.utils.ConfigReader;

/**
 * Page Object for the North Face home page and its global header
 * (logo, collapsible search, primary navigation, cart).
 *
 * Locators verified against the live thenorthface.com/en-us DOM
 * (data-test-id attributes are the most stable hooks on this site).
 */
public class HomePage extends BasePage {

    // ---------- Locators (verified against live DOM) ----------
    private final By cookieAcceptButton = By.id("onetrust-accept-btn-handler");
    private final By siteLogo    = By.cssSelector("[data-test-id='vf-logo']");
    private final By navMens     = By.cssSelector("a[href='/en-us/mens']");
    private final By navWomens   = By.cssSelector("a[href='/en-us/womens']");
    private final By cartLink    = By.cssSelector("a[href='/en-us/cart'], a[href*='/cart']");

    // Search is collapsible: a screen-reader-only toggle reveals the input.
    private final By searchToggle = By.cssSelector("input[aria-controls='vf-search']");
    private final By searchInput  = By.cssSelector("#vf-search input[data-test-id='base-input'], input[data-test-id='base-input']");
    private final By searchSubmit = By.cssSelector("button[aria-label='Submit search']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    // ---------- Actions ----------

    /** Opens the base URL from config and dismisses the cookie banner if present. */
    public HomePage open() {
        driver.get(ConfigReader.get("base.url"));
        acceptCookiesIfPresent();
        return this;
    }

    /** Clicks "Accept" on the OneTrust cookie banner when it appears (best-effort). */
    public void acceptCookiesIfPresent() {
        try {
            if (isDisplayed(cookieAcceptButton)) {
                click(cookieAcceptButton);
            }
        } catch (Exception ignored) {
            // Banner not shown / already dismissed -> continue.
        }
    }

    /**
     * Detects an anti-bot page instead of the real home page. thenorthface.com is protected by
     * PerimeterX ("Press &amp; Hold to confirm you are a human", element id {@code px-captcha});
     * a CDN may also answer with an "Access Denied" page. Such a page says nothing about the
     * site itself, so the smoke steps report it as SKIPPED with this reason instead of failing
     * with a misleading "logo not displayed".
     *
     * @return a short description of the block, or {@code null} when the real page is shown
     */
    public String botWallReason() {
        String title = String.valueOf(driver.getTitle());
        String source = String.valueOf(driver.getPageSource());
        if (source.contains("px-captcha")) {
            return "PerimeterX human-verification challenge (px-captcha)";
        }
        if (source.contains("Press & Hold") || source.contains("Press &amp; Hold")) {
            return "PerimeterX 'Press & Hold' challenge";
        }
        if (title.toLowerCase().contains("access denied")) {
            return "CDN 'Access Denied' page";
        }
        return null;
    }

    /** One line for the log: what the browser actually got. */
    public String describeLoadedPage() {
        String source = String.valueOf(driver.getPageSource());
        return "title='" + driver.getTitle() + "', url=" + driver.getCurrentUrl() + ", html=" + source.length() + " chars";
    }

    /** @return true when the brand logo is present (a lightweight "page loaded" signal). */
    public boolean isLogoDisplayed() {
        return isDisplayed(siteLogo);
    }

    /**
     * Reveals the collapsible search box, types a term, and submits it.
     *
     * @param term what to search for
     * @return a SearchResultsPage object
     */
    public SearchResultsPage searchFor(String term) {
        // The toggle is screen-reader-only (sr-only), so a JS click is most reliable.
        WebElement toggle = driver.findElement(searchToggle);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", toggle);

        // Wait for the revealed input, then type and submit.
        WebElement input = waitForVisible(searchInput);
        input.clear();
        input.sendKeys(term);

        // Prefer the explicit submit button; fall back to ENTER.
        if (isDisplayed(searchSubmit)) {
            click(searchSubmit);
        } else {
            input.sendKeys(Keys.ENTER);
        }
        return new SearchResultsPage(driver);
    }

    /** Opens the Men's category page directly (stable, avoids hover-only mega-menu). */
    public void goToMens() {
        driver.get(ConfigReader.get("base.url") + "en-us/mens");
    }

    /** Opens the Women's category page directly. */
    public void goToWomens() {
        driver.get(ConfigReader.get("base.url") + "en-us/womens");
    }

    /** @return true if the Men's/Women's category links exist in the DOM. */
    public boolean isPrimaryNavDisplayed() {
        return count(navMens) > 0 || count(navWomens) > 0;
    }

    /** Opens the cart page. */
    public CartPage goToCart() {
        driver.get(ConfigReader.get("base.url") + "en-us/cart");
        return new CartPage(driver);
    }
}
