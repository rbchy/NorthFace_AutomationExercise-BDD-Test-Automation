package com.northface.automation.stepdefinitions;

import io.cucumber.java.en.When;

/**
 * Steps for moving between primary catalogue categories.
 */
public class NavigationSteps {

    private final TestContext context;

    public NavigationSteps(TestContext context) {
        this.context = context;
    }

    @When("the user opens the {string} category")
    public void the_user_opens_the_category(String category) {
        switch (category.toLowerCase()) {
            case "mens":
            case "men":
            case "men's":
                context.getHomePage().goToMens();
                break;
            case "womens":
            case "women":
            case "women's":
                context.getHomePage().goToWomens();
                break;
            default:
                throw new IllegalArgumentException("Unknown category: " + category);
        }
    }
}
