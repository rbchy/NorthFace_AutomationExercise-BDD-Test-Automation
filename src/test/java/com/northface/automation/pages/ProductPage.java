package com.northface.automation.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Page Object for the Product Detail Page (PDP):
 * title, price, size selection and add-to-cart.
 */
public class ProductPage extends BasePage {

    // ---------- Locators ----------
    private final By productTitle    = By.cssSelector("h1, [data-testid='product-title']");
    private final By productPrice    = By.cssSelector("[data-testid='product-price'], .product-price, [class*='price']");
    private final By sizeOptions     = By.cssSelector("[data-testid='size-selector'] button, [class*='size'] button, button[aria-label*='Size']");
    private final By addToCartButton = By.cssSelector("[data-testid='add-to-cart'], button[aria-label*='Add to'], button[class*='add-to-cart']");
    private final By miniCartConfirm = By.cssSelector("[data-testid='mini-cart'], [class*='added-to-cart'], [aria-label*='added']");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    // ---------- Queries ----------

    public boolean isProductTitleDisplayed() {
        return isDisplayed(productTitle);
    }

    public String getProductTitle() {
        return getText(productTitle);
    }

    public boolean isPriceDisplayed() {
        return isDisplayed(productPrice);
    }

    public boolean isAddToCartDisplayed() {
        return isDisplayed(addToCartButton);
    }

    // ---------- Actions ----------

    /** Selects the first available (enabled) size, if a size selector exists. */
    public void selectFirstAvailableSize() {
        List<WebElement> sizes = driver.findElements(sizeOptions);
        for (WebElement size : sizes) {
            if (size.isEnabled() && size.isDisplayed()) {
                size.click();
                return;
            }
        }
    }

    /** Selects a size then adds the product to the cart. */
    public void addToCart() {
        selectFirstAvailableSize();
        scrollIntoView(addToCartButton);
        click(addToCartButton);
    }

    /** @return true when the mini-cart / "added" confirmation is shown. */
    public boolean isAddedToCartConfirmationDisplayed() {
        return isDisplayed(miniCartConfirm);
    }
}
