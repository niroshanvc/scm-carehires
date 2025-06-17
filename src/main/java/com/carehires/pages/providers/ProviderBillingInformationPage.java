package com.carehires.pages.providers;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProviderBillingInformationPage {

    @FindBy(xpath = "(//input[@formcontrolname='invoicePersonName'])[1]")
    public WebElement addressBillsInAttentionTo;

    @FindBy(xpath = "(//input[@formcontrolname='billingAddress'])[1]")
    public WebElement billingAddress;

    @FindBy(xpath = "(//input[@formcontrolname='digitalBillingAddress'])[1]")
    public WebElement digitalBillingAddress;

    @FindBy(xpath = "(//input[@formcontrolname='telephoneNumber'])[1]")
    public WebElement phoneNumber;

    @FindBy(xpath = "(//input[@id='post-code'])[1]")
    public WebElement postalCode;

    @FindBy(xpath = "//nb-select[@formcontrolname='paymentMethod']/button")
    public WebElement paymentMethodDropdown;

    @FindBy(xpath = "(//input[@formcontrolname='costCenterNumber'])[1]")
    public WebElement costCenter;

    @FindBy(xpath = "(//nb-select[@formcontrolname='paymentCycle']/button)[1]")
    public WebElement billingCycleDropdown;

    @FindBy(xpath = "(//nb-select[@formcontrolname='paymentTerm']/button)[1]")
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

    @FindBy(xpath = "//span[text()='Custom Billing Information']/parent::a")
    public WebElement customBillingInformationSection;

    @FindBy(xpath = "(//input[@id='post-code'])[2]")
    public WebElement customBillingPostalCode;

    @FindBy(xpath = "//nb-select[@formcontrolname='careHome']/button")
    public WebElement siteDropdown;

    @FindBy(xpath = "(//input[@formcontrolname='invoicePersonName'])[2]")
    public WebElement customAddressBillsInAttentionTo;

    @FindBy(xpath = "(//input[@formcontrolname='billingAddress'])[2]")
    public WebElement customBillingAddress;

    @FindBy(xpath = "(//input[@formcontrolname='digitalBillingAddress'])[2]")
    public WebElement customDigitalBillingAddress;

    @FindBy(xpath = "(//input[@formcontrolname='costCenterNumber'])[2]")
    public WebElement customCostCenter;

    @FindBy(xpath = "(//input[@formcontrolname='telephoneNumber'])[2]")
    public WebElement customPhoneNumber;

    @FindBy(xpath = "(//nb-select[@formcontrolname='paymentCycle']/button)[2]")
    public WebElement customBillingCycleDropdown;

    @FindBy(xpath = "(//nb-select[@formcontrolname='paymentTerm']/button)[2]")
    public WebElement customCreditTermDropdown;

    @FindBy(xpath = "//nb-checkbox[@formcontrolname='sameAsGeneralBilling']//input")
    public WebElement isSameAsGeneralBillingBankInformationCheckbox;

    @FindBy(xpath = "//nb-checkbox[@formcontrolname='sameAsGeneralBilling']//span[contains(@class, 'custom')]")
    public WebElement isSameAsGeneralBillingBankInformationSpan;

    @FindBy(xpath = "//input[@formcontrolname='accountName']")
    public WebElement accountName;

    @FindBy(xpath = "//input[@formcontrolname='accountNumber']")
    public WebElement accountNumber;

    @FindBy(xpath = "//input[@formcontrolname='sortCode']")
    public WebElement sortCode;

    @FindBy(xpath = "(//button[contains(text(), 'Save')])[2]")
    public WebElement customSaveButton;

    @FindBy(xpath = "//div[@class='main-table']//nb-icon")
    public WebElement customBillingEditIcon;

    public String getDropdownOptionXpath(String city) {
        return String.format("//nb-option[contains(text(),'%s')]", city);
    }

    @FindBy(id = "creditLimitAmount")
    public WebElement creditLimitAmount;
}
