package com.northface.automation.stepdefinitions;

import static org.testng.Assert.assertTrue;

import com.northface.automation.pages.demo.DemoCartPage;
import com.northface.automation.pages.demo.DemoHomePage;
import com.northface.automation.pages.demo.DemoProductsPage;
import com.northface.automation.utils.DriverManager;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Steps for the automation-friendly demo shop (automationexercise.com):
 * search -> add to cart -> cart. Distinct Gherkin phrasing ("shopper")
 * keeps these separate from the North Face ("user") steps.
 */
public class DemoShopSteps {

    private final TestContext context;

    public DemoShopSteps(TestContext context) {
        this.context = context;
    }

    @Given("the shopper is on the demo shop")
    public void the_shopper_is_on_the_demo_shop() {
        DemoHomePage home = new DemoHomePage(DriverManager.getDriver());
        home.open();
        context.setDemoHomePage(home);
    }

    @Then("the demo shop home page should load")
    public void the_demo_shop_home_page_should_load() {
        assertTrue(context.getDemoHomePage().isLogoDisplayed(),
                "Demo shop logo was not displayed");
    }

    @When("the shopper searches products for {string}")
    public void the_shopper_searches_products_for(String term) {
        DemoProductsPage products = context.getDemoHomePage().goToProducts();
        products.search(term);
        context.setDemoProductsPage(products);
    }

    @Then("the demo product results should be displayed")
    public void the_demo_product_results_should_be_displayed() {
        assertTrue(context.getDemoProductsPage().hasResults(),
                "Expected product tiles in the demo search results, but found none");
    }

    @And("the shopper adds the first product to the cart")
    public void the_shopper_adds_the_first_product_to_the_cart() {
        DemoCartPage cart = context.getDemoProductsPage().addFirstProductAndViewCart();
        context.setDemoCartPage(cart);
    }

    @Then("the demo cart should contain at least {int} item")
    public void the_demo_cart_should_contain_at_least_item(int expected) {
        int actual = context.getDemoCartPage().getItemCount();
        assertTrue(actual >= expected,
                "Expected at least " + expected + " item(s) in the demo cart but found " + actual);
    }

    @Then("no demo products should be shown")
    public void no_demo_products_should_be_shown() {
        assertTrue(!context.getDemoProductsPage().hasResults(),
                "Expected no products for a gibberish term, but results were shown");
    }

    @Then("the demo cart should offer checkout")
    public void the_demo_cart_should_offer_checkout() {
        assertTrue(context.getDemoCartPage().isProceedToCheckoutDisplayed(),
                "Proceed-to-checkout control was not displayed on the demo cart");
    }
}
