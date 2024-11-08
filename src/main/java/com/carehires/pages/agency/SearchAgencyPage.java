package com.carehires.pages.agency;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SearchAgencyPage {

    @FindBy(xpath = "//input[(@id='business-name') and (not(@formcontrolname))]")
    public WebElement searchByText;

    @FindBy(xpath = "//nb-select[@placeholder='Filter By']/button")
    public WebElement filterByDropdown;

    @FindBy(xpath = "//nb-option[contains(text(),'Draft Agencies')]")
    public WebElement draftAgenciesOption;

    @FindBy(xpath = "//div[@class= 'result' or contains(@class, 'result-active')][1]//p[contains(@class, 'id')]")
    public WebElement firstAgencyId;

    @FindBy(xpath = "//div[contains(@class, 'result-list')]/div//p[contains(@class, 'id')]")
    public List<WebElement> agencyIds;
}
