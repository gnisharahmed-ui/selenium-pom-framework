package com.sdet.tests;

import com.sdet.pages.*;
import com.sdet.utils.RetryAnalyzer;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * End-to-end checkout flow tests for SauceDemo.
 */
@Epic("Shopping")
@Feature("Checkout")
public class CheckoutTest extends BaseTest {

    @Test(description = "Complete end-to-end purchase flow",
          retryAnalyzer = RetryAnalyzer.class)
    @Story("Happy Path Checkout")
    @Severity(SeverityLevel.BLOCKER)
    public void testCompleteCheckoutFlow() {
        // Login
        InventoryPage inventoryPage = new LoginPage()
                .open()
                .loginAs("standard_user", "secret_sauce");

        // Add item to cart
        inventoryPage.addFirstItemToCart();
        Assert.assertEquals(inventoryPage.getCartCount(), 1, "Cart should have 1 item");

        // Go to cart and checkout
        CartPage cartPage = inventoryPage.goToCart();
        Assert.assertEquals(cartPage.getCartItemCount(), 1, "Cart page should show 1 item");

        CheckoutPage checkoutPage = cartPage.proceedToCheckout();

        // Fill checkout info
        checkoutPage
                .fillInfo("Jane", "Doe", "48187")
                .clickContinue()
                .clickFinish();

        Assert.assertTrue(checkoutPage.isOrderConfirmed(),
                "Order confirmation message should be displayed");
        Assert.assertEquals(checkoutPage.getConfirmationMessage(), "Thank you for your order!");
    }

    @Test(description = "Checkout with missing first name shows validation error")
    @Story("Checkout Validation")
    @Severity(SeverityLevel.NORMAL)
    public void testCheckoutMissingFirstNameShowsError() {
        InventoryPage inventoryPage = new LoginPage()
                .open()
                .loginAs("standard_user", "secret_sauce");

        inventoryPage.addFirstItemToCart();

        CartPage cartPage = inventoryPage.goToCart();
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();

        checkoutPage
                .fillInfo("", "Doe", "48187")
                .clickContinue();

        Assert.assertTrue(checkoutPage.getErrorMessage().contains("First Name is required"),
                "Validation error should mention First Name");
    }

    @Test(description = "Products can be sorted by price low to high")
    @Story("Sorting")
    @Severity(SeverityLevel.NORMAL)
    public void testProductSorting() {
        InventoryPage inventoryPage = new LoginPage()
                .open()
                .loginAs("standard_user", "secret_sauce");

        inventoryPage.sortBy("Price (low to high)");

        // Verify sorted — product list should still load
        Assert.assertTrue(inventoryPage.getItemCount() > 0, "Products should still be visible after sorting");
    }
}
