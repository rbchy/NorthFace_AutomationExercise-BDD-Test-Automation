package com.northface.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for the shopping cart / bag page.
 */
public class CartPage extends BasePage {

    // ---------- Locators ----------
    private final By cartItems       = By.cssSelector("[data-testid='cart-item'], .cart-item, li[class*='cart']");
    private final By emptyCartMsg    = By.xpath("//*[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'your cart is empty') or contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'bag is empty')]");
    private final By checkoutButton  = By.cssSelector("[data-testid='checkout'], button[aria-label*='Checkout'], a[href*='checkout']");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    // ---------- Queries ----------

    /** @return number of line items currently in the cart. */
    public int getItemCount() {
        return count(cartItems);
    }

    public boolean isCartEmpty() {
        return getItemCount() == 0 || isDisplayed(emptyCartMsg);
    }

    public boolean isCheckoutButtonDisplayed() {
        return isDisplayed(checkoutButton);
    }
}
