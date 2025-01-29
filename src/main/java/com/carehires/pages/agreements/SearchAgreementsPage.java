package com.carehires.pages.agreements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SearchAgreementsPage {

    @FindBy(xpath = "//p[text() = 'Advance Filters']")
    public WebElement advanceFiltersLink;

    @FindBy(xpath = "//nb-select[@id='care-provider' and @placeholder='Select Provider']/button")
    public WebElement filterByProviderDropdown;

    @FindBy(xpath = "//nb-select[@id='care-provider' and @placeholder='Select Carehome']/button")
    public WebElement filterByCarehomeDropdown;

    @FindBy(xpath = "//nb-select[@id='care-provider' and @placeholder='Select Agency']/button")
    public WebElement filterByAgencyDropdown;

    @FindBy(xpath = "//nb-select[@id='care-provider' and @placeholder='Select Agency Location']/button")
    public WebElement filterByAgencyLocationDropdown;

    @FindBy(xpath = "//nb-card-footer//span[text()='Apply']")
    public WebElement applyButton;

    @FindBy(xpath = "(//div[contains(@class, 'search-result-container')]//h6)[1]")
    public WebElement firstSearchedResult;

    public String getDropdownOptionXpath(String option) {
        return String.format("//nb-option[contains(text(),'%s')]", option);
    }
}
