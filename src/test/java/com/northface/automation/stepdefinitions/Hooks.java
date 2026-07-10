package com.northface.automation.stepdefinitions;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.northface.automation.utils.DriverManager;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;

/**
 * Cucumber lifecycle hooks.
 * <p>
 * A fresh browser is created lazily on first use (see {@link DriverManager}).
 * After each scenario we attach a screenshot on failure, dump the live page
 * HTML to target/dom-dumps (for locator debugging), and always quit the
 * driver so scenarios stay independent.
 */
public class Hooks {

    /**
     * Runs after every scenario: on failure captures a screenshot and a full
     * page-source dump, then always closes the browser.
     *
     * @param scenario the just-executed scenario (provides pass/fail state)
     */
    @After
    public void tearDown(Scenario scenario) {
        try {
            // Only capture browser artifacts if a browser was actually used
            // (API-only scenarios never create a driver).
            if (scenario.isFailed() && DriverManager.hasDriver()) {
                WebDriver driver = DriverManager.getDriver();

                // 1) Screenshot -> attached to the Cucumber HTML report.
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", scenario.getName());

                // 2) Full page source -> file, so exact locators can be inspected.
                dumpPageSource(driver, scenario.getName());
            }
        } catch (Exception e) {
            System.err.println("Could not capture failure artifacts: " + e.getMessage());
        } finally {
            DriverManager.quitDriver();
        }
    }

    /** Writes the current DOM (page source) to target/dom-dumps/<scenario>.html. */
    private void dumpPageSource(WebDriver driver, String scenarioName) {
        try {
            Path dir = Paths.get("target", "dom-dumps");
            Files.createDirectories(dir);
            String safeName = scenarioName.replaceAll("[^a-zA-Z0-9-_]", "_");
            Path file = dir.resolve(safeName + ".html");
            Files.write(file, driver.getPageSource().getBytes(StandardCharsets.UTF_8));
            System.out.println("DOM dump written to: " + file.toAbsolutePath());
        } catch (Exception e) {
            System.err.println("Could not write DOM dump: " + e.getMessage());
        }
    }
}
