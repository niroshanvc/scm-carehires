package com.carehires.pages.providers;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProviderSettingsPage {

    @FindBy(xpath = "//img[contains(@src, 'settings')]")
    public WebElement imgSettings;

    @FindBy(xpath = "//button[contains(text(), 'Update')]")
    public WebElement updateButton;

    @FindBy(xpath = "//button[text()= 'Edit Settings']")
    public WebElement editSettingsButton;

    public String getSiteNameVisibleCheckboxXpath(String siteName) {
        return String.format("(//td[contains(text(), '%s')]/..//nb-checkbox//input)[1]", siteName);
    }

    public String getSiteNameMandatoryCheckboxXpath(String siteName) {
        return String.format("(//td[contains(text(), '%s')]/..//nb-checkbox//input)[2]", siteName);
    }

    public String getSiteNameVisibleCheckboxSpan(String siteName) {
        return String.format("(//td[contains(text(), '%s')]/..//nb-checkbox//input/../span[1])[1]", siteName);
    }

    public String getSiteNameMandatoryCheckboxSpan(String siteName) {
        return String.format("(//td[contains(text(), '%s')]/..//nb-checkbox//input/../span[1])[2]", siteName);
    }

    @FindBy(xpath = "//button[text()= 'Save']")
    public WebElement siteNamePopupSaveButton;

    @FindBy(xpath = "//nb-toast//span")
    public WebElement successMessage;

    @FindBy(xpath = "//div[contains(@class, 'footer')]//button[contains(text(), 'Save')]")
    public WebElement settingsSaveButton;

    @FindBy(xpath = "//nb-select[@id='timeFormat']/button")
    public WebElement timeFormat;

    public String getDropdownOptionXpath(String option) {
        return String.format("//nb-option[contains(text(),'%s')]", option);
    }
}
