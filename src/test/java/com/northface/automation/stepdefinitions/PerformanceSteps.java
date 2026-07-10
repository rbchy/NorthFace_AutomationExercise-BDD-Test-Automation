package com.northface.automation.stepdefinitions;

import static org.testng.Assert.assertTrue;

import com.northface.automation.utils.ConfigReader;
import com.northface.automation.utils.DriverManager;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

/**
 * Lightweight performance smoke on the demo shop (wall-clock page load):
 * positive (loads within the configured budget) and negative (load time
 * exceeds an unrealistic 1 ms budget, proving the guard works).
 */
public class PerformanceSteps {

    private long loadTimeMs;

    @Given("the shopper measures the demo shop load time")
    public void the_shopper_measures_the_demo_shop_load_time() {
        long start = System.currentTimeMillis();
        DriverManager.getDriver().get(ConfigReader.get("demo.url"));
        loadTimeMs = System.currentTimeMillis() - start;
        System.out.println("Demo shop measured load time: " + loadTimeMs + " ms");
    }

    // ----- Positive -----
    @Then("the page should load within the performance budget")
    public void the_page_should_load_within_the_performance_budget() {
        long budget = ConfigReader.getInt("perf.budget.ms");
        assertTrue(loadTimeMs <= budget,
                "Page load " + loadTimeMs + " ms exceeded the budget of " + budget + " ms");
    }

    // ----- Negative / guard -----
    @Then("the page load time should exceed {int} ms")
    public void the_page_load_time_should_exceed_ms(int threshold) {
        assertTrue(loadTimeMs > threshold,
                "Load time " + loadTimeMs + " ms did not exceed " + threshold + " ms");
    }
}
