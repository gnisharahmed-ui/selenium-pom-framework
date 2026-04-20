package com.sdet.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;

/**
 * Page Object for the SauceDemo Cart page.
 */
public class CartPage extends BasePage {

    private final By cartTitle       = By.cssSelector(".title");
    private final By cartItems       = By.cssSelector(".cart_item");
    private final By checkoutButton  = By.id("checkout");
    private final By continueShopBtn = By.id("continue-shopping");

    public CartPage() {
        waitForVisible(cartTitle);
    }

    @Step("Get number of items in cart")
    public int getCartItemCount() {
        return driver.findElements(cartItems).size();
    }

    @Step("Proceed to checkout")
    public CheckoutPage proceedToCheckout() {
        click(checkoutButton);
        return new CheckoutPage();
    }

    @Step("Continue shopping")
    public InventoryPage continueShopping() {
        click(continueShopBtn);
        return new InventoryPage();
    }
}
