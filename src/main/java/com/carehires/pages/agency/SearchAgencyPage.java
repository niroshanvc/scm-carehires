package com.carehires.pages.agency;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SearchAgencyPage {

    @FindBy(xpath = "//input[(@id='business-name') and (not(@formcontrolname))]")
    public WebElement searchByText;

    @FindBy(xpath = "//div[not(contains(@class, 'provider'))]//p/strong")
    public WebElement agencyId;

    @FindBy(xpath = "//nb-select[@placeholder='Filter By']/button")
    public WebElement filterByDropdown;

    @FindBy(xpath = "//nb-option[contains(text(),'Draft Agencies')]")
    public WebElement draftAgenciesOption;
}
