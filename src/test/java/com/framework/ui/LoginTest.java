package com.framework.ui;

import com.framework.base.BaseUiTest;
import com.framework.pages.InventoryPage;
import com.framework.pages.LoginPage;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class LoginTest extends BaseUiTest {

    @Test
    public void validUserCanLogin() {
        InventoryPage inventory = new LoginPage(driver()).open().loginAs(USERNAME, PASSWORD);

        assertEquals(inventory.pageTitle(), "Products");
        assertEquals(inventory.productCount(), 6);
    }

    @Test
    public void lockedUserSeesError() {
        String error = new LoginPage(driver()).open()
                .loginExpectingFailure("locked_out_user", PASSWORD)
                .errorMessage();

        assertTrue(error.contains("locked out"));
    }
}
