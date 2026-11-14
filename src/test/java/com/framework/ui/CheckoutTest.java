package com.framework.ui;

import com.framework.base.BaseUiTest;
import com.framework.pages.LoginPage;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class CheckoutTest extends BaseUiTest {

    @Test
    public void userCanCompleteOrder() {
        String message = new LoginPage(driver()).open()
                .loginAs(USERNAME, PASSWORD)
                .addToCart("Sauce Labs Backpack")
                .openCart()
                .checkout()
                .enterDetails("Test", "User", "560001")
                .finish()
                .confirmationMessage();

        assertEquals(message, "Thank you for your order!");
    }
}
