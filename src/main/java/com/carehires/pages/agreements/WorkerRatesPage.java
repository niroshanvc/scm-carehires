package com.carehires.pages.agreements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WorkerRatesPage {

    @FindBy(xpath = "//nb-select[@formcontrolname='workerType']/button")
    public WebElement workerTypeDropdown;

    @FindBy(xpath = "//nb-select[@formcontrolname='workerSkills']/button")
    public WebElement skillsDropdown;

    @FindBy(xpath = "//nb-accordion-item-header[contains(text(),'Rate breakdown table')]/ancestor::nb-accordion-item")
    public WebElement rateBreakdownTableHeader;

    @FindBy(xpath = "//nb-accordion-item-header[contains(text(),'Rate breakdown table')]/nb-icon")
    public WebElement rateBreakdownTableHeaderExpandIcon;

    @FindBy(xpath = "//input[@formcontrolname='workerHourlyRate']")
    public WebElement hourlyRateInput;

    @FindBy(xpath = "//input[@formcontrolname='agencyHourlyMargin']")
    public WebElement agencyMarginInput;

    @FindBy(xpath = "//input[@formcontrolname='carehiresMarginRate']")
    public WebElement chHourlyMarginInput;

    @FindBy(xpath = "//button[contains(text(), 'Add')]")
    public WebElement addButton;

    @FindBy(xpath = "//td[@class='vat-column']/span/div[1]/div[2]")
    public WebElement normalRateAgencyCostVatAmount;

    @FindBy(xpath = "//td[@class='vat-column']/span/div[2]/div[2]")
    public WebElement normalRateAgencyCostNoVatAmount;

    @FindBy(xpath = "//td[contains(@class,'final-charge')]/span/div[1]/div[2]")
    public WebElement normalRateFinalRateVatAmount;

    @FindBy(xpath = "//td[contains(@class,'final-charge')]/span/div[2]/div[2]")
    public WebElement normalRateFinalRateNoVatAmount;

    @FindBy(xpath = "//td[contains(@class,'total-vat')]/p")
    public WebElement normalRateAgencyVatAmount;

    @FindBy(xpath = "//td[contains(@class,'carehires-vat')]/p")
    public WebElement normalRateChHourlyVatAmount;

    @FindBy(xpath = "//nb-toast//span")
    public WebElement successMessage;

    @FindBy(xpath = "//button[contains(text(), 'Continue')]")
    public WebElement continueButton;
}
