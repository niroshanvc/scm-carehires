package com.carehires.pages.providers;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProviderNotificationManagementPage {

    @FindBy(xpath = "//nb-select[@formcontrolname='siteName']/button")
    public WebElement siteDropdown;

    @FindBy(id = "jobNotificationEmail")
    public WebElement jobNotificationAddress;

    @FindBy(id = "approvalNotificationEmail")
    public WebElement approvalNotificationAddress;

    @FindBy(xpath = "//button[contains(text(), 'Save')]")
    public WebElement saveButton;

    @FindBy(xpath = "//nb-option-list/ul/nb-option[2]")
    public WebElement firstOptionInSiteDropdown;

    public static String getDropdownXpath(String text) {
        return String.format("//nb-option[contains(text(),'%s')]", text);
    }

    @FindBy(xpath = "//span[@class='title subtitle']/../div")
    public WebElement successMessage;
}
