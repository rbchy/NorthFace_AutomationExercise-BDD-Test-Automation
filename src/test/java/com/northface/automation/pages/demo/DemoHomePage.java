package com.northface.automation.pages.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.northface.automation.pages.BasePage;
import com.northface.automation.utils.ConfigReader;

/**
 * Page Object for the demo shop home page (automationexercise.com).
 * This site is purpose-built for automation, so flows that the bot-protected
 * North Face site blocks (search, cart, checkout) can be exercised here.
 */
public class DemoHomePage extends BasePage {

    // ---------- Locators ----------
    private final By productsNav = By.cssSelector("a[href='/products']");
    private final By homeLogo    = By.cssSelector(".logo a, a[href='/'] img");
    // Google "Funding Choices" consent dialog occasionally overlays the page.
    private final By consentButton = By.cssSelector(".fc-cta-consent, button.fc-cta-consent, .fc-button-label");

    public DemoHomePage(WebDriver driver) {
        super(driver);
    }

    /** Opens the demo shop and dismisses the consent dialog if present. */
    public DemoHomePage open() {
        driver.get(ConfigReader.get("demo.url"));
        dismissConsentIfPresent();
        return this;
    }

    /** Best-effort dismissal of the Google consent overlay. */
    public void dismissConsentIfPresent() {
        try {
            if (isDisplayed(consentButton)) {
                click(consentButton);
            }
        } catch (Exception ignored) {
            // No consent dialog -> continue.
        }
    }

    /** @return true when the shop logo is visible (page-loaded signal). */
    public boolean isLogoDisplayed() {
        return isDisplayed(homeLogo);
    }

    /**
     * Opens the Products listing page directly by URL. Direct navigation is
     * more stable than clicking the nav link, which can be intercepted by ads.
     */
    public DemoProductsPage goToProducts() {
        driver.get(ConfigReader.get("demo.url") + "products");
        dismissConsentIfPresent();
        return new DemoProductsPage(driver);
    }
}
