package com.northface.automation.pages.demo;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.northface.automation.pages.BasePage;

/**
 * Page Object for the demo shop Products / search-results listing.
 */
public class DemoProductsPage extends BasePage {

    // ---------- Locators ----------
    private final By searchInput   = By.id("search_product");
    private final By searchButton  = By.id("submit_search");
    private final By productTiles  = By.cssSelector(".features_items .product-image-wrapper");
    // In-flow "Add to cart" links (not the hover-overlay ones).
    private final By addToCartLinks = By.cssSelector(".features_items .productinfo a.add-to-cart");
    private final By viewCartLink  = By.cssSelector("#cartModal a[href='/view_cart']");

    public DemoProductsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Types a term into the product search and submits it. Tolerates an empty
     * result set (used by negative tests) instead of throwing on timeout.
     */
    public void search(String term) {
        type(searchInput, term);
        click(searchButton);
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(productTiles));
        } catch (org.openqa.selenium.TimeoutException ignored) {
            // No tiles for this term -> a valid "no results" outcome.
        }
    }

    /** @return number of product tiles currently displayed. */
    public int getResultsCount() {
        return count(productTiles);
    }

    public boolean hasResults() {
        return getResultsCount() > 0;
    }

    /**
     * Adds the first product to the cart and opens the cart via the modal.
     * JavaScript clicks are used to avoid interception by lazy-loaded ad frames.
     *
     * @return the cart page
     */
    public DemoCartPage addFirstProductAndViewCart() {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        List<WebElement> addButtons = driver.findElements(addToCartLinks);
        if (addButtons.isEmpty()) {
            throw new IllegalStateException("No 'Add to cart' controls found on the products page");
        }
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", addButtons.get(0));
        js.executeScript("arguments[0].click();", addButtons.get(0));

        // The confirmation modal offers a "View Cart" link.
        WebElement viewCart = wait.until(ExpectedConditions.elementToBeClickable(viewCartLink));
        js.executeScript("arguments[0].click();", viewCart);

        return new DemoCartPage(driver);
    }
}
