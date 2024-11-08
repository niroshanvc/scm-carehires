package com.carehires.pages.agency;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BillingProfileManagementPage {

    @FindBy(xpath = "//input[@formcontrolname='invoicePersonName']")
    public WebElement addressBillsInAttentionTo;

    @FindBy(xpath = "//input[@formcontrolname='billingAddress']")
    public WebElement billingAddress;

    @FindBy(id = "costCenterNumber")
    public WebElement costCenter;

    @FindBy(xpath = "//input[@formcontrolname='digitalBillingAddress']")
    public WebElement digitalBillingAddress;

    @FindBy(xpath = "//input[@formcontrolname='carbonCopyBillingAddress']")
    public WebElement ccDigitalBillingAddress;

    @FindBy(xpath = "//input[@formcontrolname='telephoneNumber']")
    public WebElement phoneNumber;

    @FindBy(xpath = "//nb-select[@formcontrolname='billingInterval']/button")
    public WebElement billingCycleDropdown;

    @FindBy(xpath = "//nb-select[@formcontrolname='creditPeriod']/button")
    public WebElement creditTermDropdown;

    @FindBy(xpath = "//input[@formcontrolname='bankName']")
    public WebElement bankName;

    @FindBy(xpath = "//input[@formcontrolname='accountNumber']")
    public WebElement accountNumber;

    @FindBy(xpath = "//input[@formcontrolname='accountName']")
    public WebElement accountName;

    @FindBy(xpath = "//input[@formcontrolname='sortCode']")
    public WebElement sortCode;

    @FindBy(xpath = "//button[contains(text(),'Save')]")
    public WebElement saveButton;

    public static final String BASIC_INFORMATION_SUB_XPATHS = "//h6[text()='Billing Profile Management']/..//nb-icon/*/*/*";

    @FindBy(xpath = "(//h6[text()='Billing Profile Management']/..//nb-icon/*/*/*)[2]")
    public WebElement billingProfileManagementSavedIcon;

    public By getFirstSuccessMessage() {
        return firstSuccessMessage;
    }

    public By getSecondSuccessMessage() {
        return secondSuccessMessage;
    }

    private final By firstSuccessMessage = By.xpath("(//nb-toast//span)[1]");

    private final By secondSuccessMessage = By.xpath("(//nb-toast//span)[2]");

    @FindBy(xpath = "//button[contains(text(),'Update')]")
    public WebElement updateButton;

    @FindBy(xpath = "//button[contains(text(), 'Next')]")
    public WebElement nextButton;
}
