package com.framework.pages;

import com.framework.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private final By username = By.id("user-name");
    private final By password = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By error = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage open() {
        driver.get(ConfigReader.get("ui.base.url"));
        return this;
    }

    public InventoryPage loginAs(String user, String pass) {
        type(username, user);
        type(password, pass);
        click(loginButton);
        return new InventoryPage(driver);
    }

    public LoginPage loginExpectingFailure(String user, String pass) {
        type(username, user);
        type(password, pass);
        click(loginButton);
        return this;
    }

    public String errorMessage() {
        return textOf(error);
    }
}
