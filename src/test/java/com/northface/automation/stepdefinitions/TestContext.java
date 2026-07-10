package com.northface.automation.stepdefinitions;

import com.northface.automation.pages.CartPage;
import com.northface.automation.pages.HomePage;
import com.northface.automation.pages.ProductPage;
import com.northface.automation.pages.SearchResultsPage;
import com.northface.automation.pages.demo.DemoCartPage;
import com.northface.automation.pages.demo.DemoHomePage;
import com.northface.automation.pages.demo.DemoProductsPage;

/**
 * Simple state holder shared across all step-definition classes within a
 * scenario. Cucumber's PicoContainer creates one instance per scenario and
 * injects it into every step class constructor, so pages created in one step
 * class are visible to the others (no static state, thread-safe).
 */
public class TestContext {

    // ---------- North Face pages ----------
    private HomePage homePage;
    private SearchResultsPage searchResultsPage;
    private ProductPage productPage;
    private CartPage cartPage;

    public HomePage getHomePage() {
        return homePage;
    }

    public void setHomePage(HomePage homePage) {
        this.homePage = homePage;
    }

    public SearchResultsPage getSearchResultsPage() {
        return searchResultsPage;
    }

    public void setSearchResultsPage(SearchResultsPage searchResultsPage) {
        this.searchResultsPage = searchResultsPage;
    }

    public ProductPage getProductPage() {
        return productPage;
    }

    public void setProductPage(ProductPage productPage) {
        this.productPage = productPage;
    }

    public CartPage getCartPage() {
        return cartPage;
    }

    public void setCartPage(CartPage cartPage) {
        this.cartPage = cartPage;
    }

    // ---------- Demo shop pages (automationexercise.com) ----------
    private DemoHomePage demoHomePage;
    private DemoProductsPage demoProductsPage;
    private DemoCartPage demoCartPage;

    public DemoHomePage getDemoHomePage() {
        return demoHomePage;
    }

    public void setDemoHomePage(DemoHomePage demoHomePage) {
        this.demoHomePage = demoHomePage;
    }

    public DemoProductsPage getDemoProductsPage() {
        return demoProductsPage;
    }

    public void setDemoProductsPage(DemoProductsPage demoProductsPage) {
        this.demoProductsPage = demoProductsPage;
    }

    public DemoCartPage getDemoCartPage() {
        return demoCartPage;
    }

    public void setDemoCartPage(DemoCartPage demoCartPage) {
        this.demoCartPage = demoCartPage;
    }
}
