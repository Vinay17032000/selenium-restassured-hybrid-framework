package com.framework.driver;

import com.framework.config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.Map;

public final class DriverManager {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverManager() {
    }

    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    public static void initDriver() {
        boolean headless = ConfigReader.getBoolean("headless");
        DRIVER.set(switch (ConfigReader.get("browser").toLowerCase()) {
            case "chrome" -> chrome(headless);
            case "firefox" -> firefox(headless);
            case "edge" -> edge(headless);
            default -> throw new IllegalArgumentException("Unsupported browser: " + ConfigReader.get("browser"));
        });
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }

    private static WebDriver chrome(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1920,1080", "--disable-notifications", "--incognito");
        options.setExperimentalOption("prefs", Map.of(
                "credentials_enable_service", false,
                "profile.password_manager_enabled", false,
                "profile.password_manager_leak_detection", false));
        if (headless) {
            options.addArguments("--headless=new");
        }
        return new ChromeDriver(options);
    }

    private static WebDriver firefox(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();
        if (headless) {
            options.addArguments("-headless");
        }
        return new FirefoxDriver(options);
    }

    private static WebDriver edge(boolean headless) {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--window-size=1920,1080");
        if (headless) {
            options.addArguments("--headless=new");
        }
        return new EdgeDriver(options);
    }
}
