package com.sdet.tests;

import com.sdet.pages.InventoryPage;
import com.sdet.pages.LoginPage;
import com.sdet.utils.RetryAnalyzer;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Login tests for SauceDemo application.
 * Covers positive, negative, and data-driven scenarios.
 */
@Epic("Authentication")
@Feature("Login")
public class LoginTest extends BaseTest {

    private static final String VALID_PASSWORD = "secret_sauce";

    @Test(description = "Valid credentials should navigate to inventory page",
          retryAnalyzer = RetryAnalyzer.class)
    @Story("Valid Login")
    @Severity(SeverityLevel.BLOCKER)
    public void testValidLogin() {
        InventoryPage inventoryPage = new LoginPage()
                .open()
                .loginAs("standard_user", VALID_PASSWORD);

        Assert.assertEquals(inventoryPage.getPageTitle(), "Products",
                "Page title should be 'Products' after login");
        Assert.assertTrue(inventoryPage.getItemCount() > 0,
                "Inventory should have at least one item");
    }

    @Test(description = "Invalid password should show error message")
    @Story("Invalid Login")
    @Severity(SeverityLevel.CRITICAL)
    public void testInvalidPasswordShowsError() {
        LoginPage loginPage = new LoginPage()
                .open()
                .loginWithInvalidCredentials("standard_user", "wrong_password");

        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should be displayed");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Username and password do not match"),
                "Error message text should indicate invalid credentials");
    }

    @Test(description = "Locked-out user should see specific error")
    @Story("Locked Out User")
    @Severity(SeverityLevel.NORMAL)
    public void testLockedOutUserShowsError() {
        LoginPage loginPage = new LoginPage()
                .open()
                .loginWithInvalidCredentials("locked_out_user", VALID_PASSWORD);

        Assert.assertTrue(loginPage.getErrorMessage().contains("locked out"),
                "Error should indicate account is locked");
    }

    @Test(description = "Empty fields should show validation error")
    @Story("Field Validation")
    @Severity(SeverityLevel.NORMAL)
    public void testEmptyFieldsShowError() {
        LoginPage loginPage = new LoginPage()
                .open()
                .loginWithInvalidCredentials("", "");

        Assert.assertTrue(loginPage.isErrorDisplayed(), "Validation error should show for empty fields");
    }

    @Test(description = "Data-driven login test",
          dataProvider = "validUsersProvider")
    @Story("Valid Login - Data Driven")
    @Severity(SeverityLevel.NORMAL)
    public void testDataDrivenLogin(String username, String expectedTitle) {
        InventoryPage inventoryPage = new LoginPage()
                .open()
                .loginAs(username, VALID_PASSWORD);

        Assert.assertEquals(inventoryPage.getPageTitle(), expectedTitle,
                "Page title mismatch for user: " + username);
    }

    @DataProvider(name = "validUsersProvider", parallel = true)
    public Object[][] provideValidUsers() {
        return new Object[][] {
            {"standard_user",  "Products"},
            {"problem_user",   "Products"},
            {"performance_glitch_user", "Products"},
        };
    }
}
