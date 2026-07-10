package com.northface.automation.stepdefinitions;

import static org.testng.Assert.assertTrue;

import com.northface.automation.pages.CartPage;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

/**
 * Steps for adding a product to the cart and validating cart contents.
 */
public class CartSteps {

    private final TestContext context;

    public CartSteps(TestContext context) {
        this.context = context;
    }

    @And("the user adds the product to the cart")
    public void the_user_adds_the_product_to_the_cart() {
        context.getProductPage().addToCart();
    }

    @Then("the cart should contain at least {int} item")
    public void the_cart_should_contain_at_least_item(int expected) {
        // Navigate to the cart page from the global header, then assert count.
        CartPage cartPage = context.getHomePage().goToCart();
        context.setCartPage(cartPage);
        assertTrue(cartPage.getItemCount() >= expected,
                "Expected at least " + expected + " item(s) in the cart but found "
                        + cartPage.getItemCount());
    }
}
