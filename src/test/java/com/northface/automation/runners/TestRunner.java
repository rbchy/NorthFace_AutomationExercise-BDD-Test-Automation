package com.northface.automation.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * TestNG entry point for the Cucumber suite.
 * <p>
 * By extending {@link AbstractTestNGCucumberTests}, TestNG discovers every
 * scenario as a test. The {@link CucumberOptions} below wire the feature
 * files to the step definitions and configure reporting.
 *
 * <h3>Running by tag</h3>
 * <pre>
 *   mvn test                                     # runs the default tags below
 *   mvn test -Dcucumber.filter.tags="@smoke"     # smoke only
 *   mvn test -Dcucumber.filter.tags="@regression"# regression only
 * </pre>
 * A {@code -Dcucumber.filter.tags} value on the command line overrides the
 * {@code tags} attribute here.
 */
@CucumberOptions(
        // Location of the .feature files (classpath = src/test/resources).
        features = "src/test/resources/features",

        // Packages containing @Given/@When/@Then step methods and Hooks.
        glue = {"com.northface.automation.stepdefinitions"},

        // Reports: console + pretty HTML + JSON (for CI dashboards).
        plugin = {
                "pretty",
                "html:target/cucumber-reports/report.html",
                "json:target/cucumber-reports/report.json",
                "timeline:target/cucumber-reports/timeline",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },

        // Default tag filter. Runs smoke OR regression. Override from CLI.
        tags = "@smoke or @regression",

        // Console formatting: cleaner readable output.
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
    // No body needed: the annotation drives everything.
    // (If parallel scenario execution is wanted later, override scenarios()
    //  with @DataProvider(parallel = true).)
}
