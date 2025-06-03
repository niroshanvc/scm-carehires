package com.carehires.pages.agreements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateAgreementsOverviewPage {
    @FindBy(id = "agency-id")
    public WebElement agencyInput;

    @FindBy(id = "agency-location-id")
    public WebElement agencyLocationDropdown;

    @FindBy(id = "provider-id")
    public WebElement careProviderInput;

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

    @FindBy(xpath = "(//nb-option-list/ul/nb-option/nb-checkbox)[1]")
    public WebElement selectAllSitesCheckbox;

    @FindBy(xpath = "(//nb-option-list/ul/nb-option)[1]")
    public WebElement selectAllSitesCheckboxStatus;
}
