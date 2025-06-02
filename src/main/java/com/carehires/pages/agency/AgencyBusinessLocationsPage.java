package com.carehires.pages.agency;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AgencyBusinessLocationsPage {

    @FindBy(xpath = "//button[contains(@class, 'inserted') and (contains(@class, 'button'))]")
    public WebElement addNewButton;

    @FindBy(xpath = "//label[text()='Business Location']/../input")
    public WebElement businessLocation;

    @FindBy(xpath = "(//input[@id='email'])[1]")
    public WebElement businessEmailAddress;

    @FindBy(xpath = "//nb-select[@formcontrolname='city']/button")
    public WebElement selectCity;

    @FindBy(xpath = "(//input[@id='email'])[2]")
    public WebElement jobNotificationAddress;

    @FindBy(xpath = "(//input[@id='email'])[3]")
    public WebElement approvalNotificationAddress;

    @FindBy(xpath = "//button[contains(text(),'Add')]")
    public WebElement addButton;

    @FindBy(xpath = "//button[contains(text(),'Update')]")
    public WebElement updateButton;

    @FindBy(xpath = "(//div[contains(@class, 'common-document-table-body')]//p)[1]")
    public WebElement locationName;

    @FindBy(xpath = "//button[contains(text(), 'Next')]")
    public WebElement nextButton;

    @FindBy(xpath = "//nb-toast//span")
    public WebElement successMessage;

    @FindBy(xpath = "//nb-icon[@icon='edit-2-outline']")
    public WebElement editDetailsIcon;

    public String getCityXpath(String city) {
        return String.format("//nb-option[contains(text(),'%s')]", city);
    }
}
