package com.carehires.pages.providers;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProviderBillingInformationPage {

    @FindBy(xpath = "//input[@formcontrolname='invoicePersonName']")
    public WebElement addressBillsInAttentionTo;

    @FindBy(xpath = "//input[@formcontrolname='billingAddress']")
    public WebElement billingAddress;

    @FindBy(xpath = "//input[@formcontrolname='digitalBillingAddress']")
    public WebElement digitalBillingAddress;

    @FindBy(xpath = "//input[@formcontrolname='telephoneNumber']")
    public WebElement phoneNumber;

    @FindBy(xpath = "//nb-select[@formcontrolname='paymentMethod']/button")
    public WebElement paymentMethodDropdown;

    @FindBy(xpath = "//input[@formcontrolname='creditLimitAmount']")
    public WebElement creditLimitAmount;

    @FindBy(xpath = "//nb-select[@formcontrolname='paymentCycle']/button")
    public WebElement billingCycleDropdown;

    @FindBy(xpath = "//nb-select[@formcontrolname='paymentTerm']/button")
    public WebElement creditTermDropdown;

    @FindBy(xpath = "//input[@formcontrolname='firstName']")
    public WebElement firstName;

    @FindBy(xpath = "//input[@formcontrolname='lastName']")
    public WebElement lastName;

    @FindBy(xpath = "//input[@formcontrolname='email']")
    public WebElement emailAddress;

    @FindBy(xpath = "//input[@formcontrolname='phoneNumber']")
    public WebElement phoneNumberBillingContact;

    @FindBy(xpath = "(//button[contains(text(), 'Save')])[1]")
    public WebElement saveButton;

    @FindBy(xpath = "//nb-toast//span")
    public WebElement successMessage;

    @FindBy(xpath = "//button[contains(text(), 'Continue')]")
    public WebElement continueButton;
}
