package com.sdet.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;

/**
 * Page Object for the SauceDemo Login page.
 * URL: https://www.saucedemo.com
 */
public class LoginPage extends BasePage {

    // Locators
    private final By usernameInput = By.id("user-name");
    private final By passwordInput = By.id("password");
    private final By loginButton   = By.id("login-button");
    private final By errorMessage  = By.cssSelector("[data-test='error']");

    @Step("Navigate to login page")
    public LoginPage open() {
        driver.get("https://www.saucedemo.com");
        waitForVisible(usernameInput);
        return this;
    }

    @Step("Login with username='{username}'")
    public InventoryPage loginAs(String username, String password) {
        type(usernameInput, username);
        type(passwordInput, password);
        click(loginButton);
        return new InventoryPage();
    }

    @Step("Attempt login with invalid credentials")
    public LoginPage loginWithInvalidCredentials(String username, String password) {
        type(usernameInput, username);
        type(passwordInput, password);
        click(loginButton);
        return this;
    }

    @Step("Get error message text")
    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isErrorDisplayed() {
        return isDisplayed(errorMessage);
    }
}
