package com.framework.base;

import com.framework.config.ConfigReader;
import com.framework.driver.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseUiTest {

    protected static final String USERNAME = ConfigReader.get("ui.username");
    protected static final String PASSWORD = ConfigReader.get("ui.password");

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        DriverManager.initDriver();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverManager.quitDriver();
    }

    protected WebDriver driver() {
        return DriverManager.getDriver();
    }
}
