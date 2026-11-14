package com.framework.ui;

import com.framework.base.BaseUiTest;
import com.framework.pages.CartPage;
import com.framework.pages.InventoryPage;
import com.framework.pages.LoginPage;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

public class CartTest extends BaseUiTest {

    @Test
    public void addedProductsShowUpInCart() {
        InventoryPage inventory = new LoginPage(driver()).open().loginAs(USERNAME, PASSWORD);
        inventory.addToCart("Sauce Labs Backpack").addToCart("Sauce Labs Bike Light");

        assertEquals(inventory.cartCount(), "2");

        CartPage cart = inventory.openCart();
        assertEquals(cart.itemNames(), List.of("Sauce Labs Backpack", "Sauce Labs Bike Light"));
    }
}
