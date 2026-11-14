package com.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InventoryPage extends BasePage {

    private final By title = By.cssSelector(".title");
    private final By items = By.cssSelector(".inventory_item");
    private final By cartBadge = By.cssSelector(".shopping_cart_badge");
    private final By cartLink = By.cssSelector(".shopping_cart_link");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public String pageTitle() {
        return textOf(title);
    }

    public int productCount() {
        visible(items);
        return driver.findElements(items).size();
    }

    public InventoryPage addToCart(String productName) {
        click(By.id("add-to-cart-" + slug(productName)));
        return this;
    }

    public String cartCount() {
        return textOf(cartBadge);
    }

    public CartPage openCart() {
        click(cartLink);
        return new CartPage(driver);
    }

    private String slug(String productName) {
        return productName.toLowerCase().replace(' ', '-');
    }
}
