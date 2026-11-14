package com.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class CartPage extends BasePage {

    private final By itemNames = By.cssSelector(".inventory_item_name");
    private final By checkoutButton = By.id("checkout");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public List<String> itemNames() {
        return textsOf(itemNames);
    }

    public CheckoutPage checkout() {
        click(checkoutButton);
        return new CheckoutPage(driver);
    }
}
