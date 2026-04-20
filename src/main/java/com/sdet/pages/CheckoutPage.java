package com.sdet.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;

/**
 * Page Object for the SauceDemo Checkout pages.
 */
public class CheckoutPage extends BasePage {

    private final By firstNameInput   = By.id("first-name");
    private final By lastNameInput    = By.id("last-name");
    private final By postalCodeInput  = By.id("postal-code");
    private final By continueButton   = By.id("continue");
    private final By finishButton     = By.id("finish");
    private final By confirmationMsg  = By.cssSelector(".complete-header");
    private final By errorMessage     = By.cssSelector("[data-test='error']");

    public CheckoutPage() {
        waitForVisible(firstNameInput);
    }

    @Step("Fill checkout info: {firstName} {lastName}, zip {postalCode}")
    public CheckoutPage fillInfo(String firstName, String lastName, String postalCode) {
        type(firstNameInput, firstName);
        type(lastNameInput, lastName);
        type(postalCodeInput, postalCode);
        return this;
    }

    @Step("Click Continue")
    public CheckoutPage clickContinue() {
        click(continueButton);
        return this;
    }

    @Step("Click Finish to complete order")
    public CheckoutPage clickFinish() {
        click(finishButton);
        return this;
    }

    @Step("Get order confirmation message")
    public String getConfirmationMessage() {
        return getText(confirmationMsg);
    }

    @Step("Get error message")
    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isOrderConfirmed() {
        return isDisplayed(confirmationMsg);
    }
}
