package com.sdet.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Page Object for the SauceDemo Inventory (products) page.
 */
public class InventoryPage extends BasePage {

    private final By pageTitle       = By.cssSelector(".title");
    private final By inventoryItems  = By.cssSelector(".inventory_item");
    private final By itemNames       = By.cssSelector(".inventory_item_name");
    private final By itemPrices      = By.cssSelector(".inventory_item_price");
    private final By sortDropdown    = By.cssSelector("[data-test='product_sort_container']");
    private final By cartBadge       = By.cssSelector(".shopping_cart_badge");
    private final By cartLink        = By.cssSelector(".shopping_cart_link");
    private final By addToCartBtns   = By.cssSelector("[data-test^='add-to-cart']");
    private final By menuButton      = By.id("react-burger-menu-btn");
    private final By logoutLink      = By.id("logout_sidebar_link");

    public InventoryPage() {
        waitForVisible(pageTitle);
    }

    @Step("Get page title")
    public String getPageTitle() {
        return getText(pageTitle);
    }

    @Step("Get count of inventory items")
    public int getItemCount() {
        return driver.findElements(inventoryItems).size();
    }

    @Step("Get all product names")
    public List<String> getProductNames() {
        return driver.findElements(itemNames)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    @Step("Add first item to cart")
    public InventoryPage addFirstItemToCart() {
        List<WebElement> buttons = driver.findElements(addToCartBtns);
        if (!buttons.isEmpty()) {
            buttons.get(0).click();
        }
        return this;
    }

    @Step("Sort products by: {option}")
    public InventoryPage sortBy(String option) {
        selectByVisibleText(sortDropdown, option);
        return this;
    }

    @Step("Get cart item count")
    public int getCartCount() {
        if (!isDisplayed(cartBadge)) return 0;
        return Integer.parseInt(getText(cartBadge));
    }

    @Step("Go to cart")
    public CartPage goToCart() {
        click(cartLink);
        return new CartPage();
    }

    @Step("Logout")
    public LoginPage logout() {
        click(menuButton);
        waitForVisible(logoutLink);
        click(logoutLink);
        return new LoginPage();
    }
}
