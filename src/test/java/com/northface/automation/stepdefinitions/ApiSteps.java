package com.northface.automation.stepdefinitions;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import com.northface.automation.utils.ConfigReader;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

/**
 * API tests against the demo shop's public REST API using RestAssured:
 * positive (product list returns data) and negative (missing parameters
 * are rejected).
 */
public class ApiSteps {

    private Response response;

    private String api(String path) {
        return ConfigReader.get("demo.url") + "api/" + path;
    }

    // ----- Positive -----
    @When("the products API is requested")
    public void the_products_api_is_requested() {
        response = RestAssured.get(api("productsList"));
    }

    @Then("the API responds with product data")
    public void the_api_responds_with_product_data() {
        assertEquals(response.getStatusCode(), 200, "Unexpected HTTP status");
        assertEquals(response.jsonPath().getInt("responseCode"), 200, "Unexpected API responseCode");
        assertTrue(response.jsonPath().getList("products").size() > 0,
                "Expected a non-empty products list from the API");
    }

    // ----- Negative -----
    @When("verifyLogin is called without required parameters")
    public void verify_login_is_called_without_required_parameters() {
        response = RestAssured.given().post(api("verifyLogin"));
    }

    @Then("the API reports a bad request")
    public void the_api_reports_a_bad_request() {
        assertEquals(response.jsonPath().getInt("responseCode"), 400,
                "Expected API responseCode 400 for a request missing required parameters");
    }

    // ----- Brands list (positive) -----
    @When("the brands API is requested")
    public void the_brands_api_is_requested() {
        response = RestAssured.get(api("brandsList"));
    }

    @Then("the API responds with brand data")
    public void the_api_responds_with_brand_data() {
        assertEquals(response.getStatusCode(), 200, "Unexpected HTTP status");
        assertEquals(response.jsonPath().getInt("responseCode"), 200, "Unexpected API responseCode");
        assertTrue(response.jsonPath().getList("brands").size() > 0,
                "Expected a non-empty brands list from the API");
    }

    // ----- Search products (positive) -----
    @When("products are searched via API for {string}")
    public void products_are_searched_via_api_for(String term) {
        response = RestAssured.given().formParam("search_product", term).post(api("searchProduct"));
    }

    @Then("the search API returns matching products")
    public void the_search_api_returns_matching_products() {
        assertEquals(response.jsonPath().getInt("responseCode"), 200, "Unexpected API responseCode");
        assertTrue(response.jsonPath().getList("products").size() > 0,
                "Expected products for a valid API search term");
    }

    // ----- Search products without a term (negative) -----
    @When("products are searched via API without a term")
    public void products_are_searched_via_api_without_a_term() {
        response = RestAssured.given().post(api("searchProduct"));
    }

    @Then("the search API reports a missing parameter")
    public void the_search_api_reports_a_missing_parameter() {
        assertEquals(response.jsonPath().getInt("responseCode"), 400,
                "Expected responseCode 400 for a search missing the search_product parameter");
    }

    // ----- verifyLogin with an unknown account (negative) -----
    @When("login is attempted via API with an unknown account")
    public void login_is_attempted_via_api_with_an_unknown_account() {
        response = RestAssured.given()
                .formParam("email", "nobody_" + System.currentTimeMillis() + "@example.com")
                .formParam("password", "whatever")
                .post(api("verifyLogin"));
    }

    @Then("the API reports the user was not found")
    public void the_api_reports_the_user_was_not_found() {
        assertEquals(response.jsonPath().getInt("responseCode"), 404,
                "Expected responseCode 404 for an unknown account");
    }
}
