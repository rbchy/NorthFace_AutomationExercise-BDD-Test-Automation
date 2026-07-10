package com.northface.automation.utils;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Creates, exposes, and disposes the {@link WebDriver} instance.
 * <p>
 * The driver is stored in a {@link ThreadLocal} so the framework stays
 * thread-safe if parallel execution is enabled later. All configuration
 * (browser, headless, timeouts) is read from {@link ConfigReader}.
 */
public final class DriverManager {

    // ThreadLocal keeps one driver per thread -> safe for parallel runs.
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverManager() {
    }

    /** @return the WebDriver for the current thread (creating it if needed). */
    public static WebDriver getDriver() {
        if (DRIVER.get() == null) {
            initDriver();
        }
        return DRIVER.get();
    }

    /** Builds the browser instance based on config.properties. */
    private static void initDriver() {
        String browser = ConfigReader.get("browser").toLowerCase();

        WebDriver driver;
        switch (browser) {
            case "chrome":
                driver = createChromeDriver();
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        // Global timeouts applied to every driver instance.
        driver.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(ConfigReader.getInt("implicit.wait")));
        driver.manage().timeouts()
                .pageLoadTimeout(Duration.ofSeconds(ConfigReader.getInt("page.load.timeout")));
        driver.manage().window().maximize();

        DRIVER.set(driver);
    }

    /** ChromeDriver configuration (binary auto-managed by WebDriverManager). */
    private static WebDriver createChromeDriver() {
        // Downloads / caches the ChromeDriver that matches the installed Chrome.
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        // Reduce automation fingerprint (helps on retail sites with bot walls).
        options.addArguments("--disable-blink-features=AutomationControlled");

        // Blackhole ad/analytics hosts so Google Vignette interstitial ads
        // (a known source of flakiness on demo shops) never render.
        options.addArguments("--host-resolver-rules="
                + "MAP *.googlesyndication.com 127.0.0.1,"
                + "MAP *.doubleclick.net 127.0.0.1,"
                + "MAP *.googleadservices.com 127.0.0.1,"
                + "MAP *.google-analytics.com 127.0.0.1,"
                + "MAP *.adtrafficquality.google 127.0.0.1");

        if (ConfigReader.getBoolean("headless")) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        }

        return new ChromeDriver(options);
    }

    /** @return true if a WebDriver already exists for this thread (no lazy creation). */
    public static boolean hasDriver() {
        return DRIVER.get() != null;
    }

    /** Quits the driver and clears the ThreadLocal so no stale instance lingers. */
    public static void quitDriver() {
        if (DRIVER.get() != null) {
            DRIVER.get().quit();
            DRIVER.remove();
        }
    }
}
