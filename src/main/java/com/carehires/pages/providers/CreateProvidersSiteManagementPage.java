package com.carehires.pages.providers;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateProvidersSiteManagementPage {

    @FindBy(xpath = "//button[contains(@class, 'inserted') and (contains(@class, 'button'))]")
    public WebElement addNewButton;

    @FindBy(xpath = "//input[@formcontrolname='careHomeName']")
    public WebElement siteName;

    @FindBy(xpath = "//nb-select[@formcontrolname='careHomeType']")
    public WebElement siteTypeDropdown;

    @FindBy(xpath = "//nb-select[@formcontrolname='siteSpecialismType']")
    public WebElement siteSpecialismMultiSelectDropdown;

    @FindBy(xpath = "//input[@formcontrolname='otherName']")
    public WebElement alsoKnownAs;

    @FindBy(xpath = "//input[@formcontrolname='email']")
    public WebElement siteEmail;

    @FindBy(id = "post-code")
    public WebElement postalCode;

    @FindBy(xpath = "//input[@formcontrolname='bedCount']")
    public WebElement noOfBeds;

    @FindBy(xpath = "//input[@formcontrolname='careHomeCode']")
    public WebElement siteCode;

    @FindBy(id = "registered-office-address")
    public WebElement phoneNumber;

    @FindBy(xpath = "//nb-select[@id='phone-type-select']/button")
    public WebElement selectPhoneDropdown;

    @FindBy(xpath = "//input[@formcontrolname='jobNotificationEmail']")
    public WebElement jobNotificationAddress;

    @FindBy(xpath = "//input[@formcontrolname='approvalNotificationEmail']")
    public WebElement approvalNotificationAddress;

    @FindBy(xpath = "//button[contains(text(),'Add')]")
    public WebElement addButton;

    @FindBy(xpath = "(//div[contains(@class, 'common-document-table-body')]//p)[1]/b")
    public WebElement siteNameAddress;

    @FindBy(xpath = "//button[contains(text(), 'Update')]")
    public WebElement updateButton;

    @FindBy(xpath = "//button[contains(text(), 'Next')]")
    public WebElement nextButton;

    @FindBy(xpath = "//nb-option[contains(text(), 'Out of office')]")
    public WebElement outOfOfficeOption;

    @FindBy(xpath = "//nb-option[contains(text(), 'Other')]")
    public WebElement otherOption;

    @FindBy(xpath = "//nb-option[contains(text(), 'Office2')]")
    public WebElement office2Option;

    @FindBy(xpath = "//nb-option[contains(text(), 'Office1')]")
    public WebElement office1Option;

    @FindBy(xpath = "//nb-option[contains(text(), 'Mobile')]")
    public WebElement mobileOption;
}
