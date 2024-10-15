package com.carehires.pages.agency;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OrganizationalSettingsPage {

    @FindBy(xpath = "//nb-select[@id='timeFormat']/button")
    public WebElement timeFormatDropdown;

    @FindBy(id = "hubSpotLink")
    public WebElement hubSpotLink;

    @FindBy(xpath = "//nb-select[@name='selectedBrandingTheme']/button")
    public WebElement invoiceBrandingThemeDropdown;

    @FindBy(xpath = "//nb-select[@name='selectedSPCUser']/button")
    public WebElement salesProcessCompletedByDropdown;

    @FindBy(xpath = "//nb-select[@name='selectedBDMUser']/button")
    public WebElement onboardedBdmDropdown;

    @FindBy(xpath = "//button[contains(text(), 'Update')]")
    public WebElement updateButton;

    @FindBy(xpath = "//button[contains(text(), 'Save')]")
    public WebElement saveButton;

    @FindBy(xpath = "//nb-toast//span")
    public WebElement successMessage;
}
