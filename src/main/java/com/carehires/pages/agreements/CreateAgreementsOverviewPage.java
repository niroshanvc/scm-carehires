package com.carehires.pages.agreements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateAgreementsOverviewPage {
    @FindBy(id = "agency-id")
    public WebElement agencyDropdown;

    @FindBy(id = "agency-location-id")
    public WebElement agencyLocationDropdown;

    @FindBy(id = "provider-id")
    public WebElement careProviderDropdown;

    public static final String SITE_CHECKBOXES = "//div[contains(@id,'cdk-overlay')]//nb-option/nb-checkbox";

    @FindBy(id = "carehome-id")
    public WebElement siteDropdown;

    @FindBy(xpath = "(//div[contains(@id,'cdk-overlay')]//nb-option)[1]")
    public WebElement site;

    @FindBy(xpath = "//button[contains(text(), 'Continue')]")
    public WebElement continueButton;

    @FindBy(xpath = "//nb-toast//span")
    public WebElement successMessage;

    public String getDropdownOptionXpath(String option) {
        return String.format("//nb-option[contains(text(),'%s')]", option);
    }
}
