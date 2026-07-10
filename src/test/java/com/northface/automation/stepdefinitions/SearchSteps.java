package com.northface.automation.stepdefinitions;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import com.northface.automation.pages.ProductPage;
import com.northface.automation.pages.SearchResultsPage;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Steps for product search, opening a product, and validating results.
 */
public class SearchSteps {

    private final TestContext context;

    public SearchSteps(TestContext context) {
        this.context = context;
    }

    @When("the user searches for {string}")
    public void the_user_searches_for(String term) {
        SearchResultsPage results = context.getHomePage().searchFor(term);
        context.setSearchResultsPage(results);
    }

    @Then("search results should be displayed")
    public void search_results_should_be_displayed() {
        assertTrue(context.getSearchResultsPage().hasResults(),
                "Expected at least one product in the results grid, but found none");
    }

    @Then("no product results should be shown")
    public void no_product_results_should_be_shown() {
        SearchResultsPage results = context.getSearchResultsPage();
        // Pass if the grid is empty OR an explicit "no results" message is shown.
        boolean empty = !results.hasResults() || results.isNoResultsMessageDisplayed();
        assertTrue(empty, "Expected no products for a gibberish term, but results were shown");
    }

    @And("the user opens the first product in the results")
    public void the_user_opens_the_first_product_in_the_results() {
        ProductPage productPage = context.getSearchResultsPage().openFirstProduct();
        context.setProductPage(productPage);
    }

    @Then("the product detail page should be displayed")
    public void the_product_detail_page_should_be_displayed() {
        assertTrue(context.getProductPage().isProductTitleDisplayed(),
                "Product detail page title was not displayed");
    }

    @Then("the product price should be displayed")
    public void the_product_price_should_be_displayed() {
        assertTrue(context.getProductPage().isPriceDisplayed(),
                "Product price was not displayed on the PDP");
    }
}
