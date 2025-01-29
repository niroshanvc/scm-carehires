package com.carehires.pages.agreements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

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

    @FindBy(xpath = "//p[@class='ch-sub-heading']/../table/tbody/tr[1]/td[1]/span[1]")
    public WebElement workerType;

    @FindBy(xpath = "//p[@class='ch-sub-heading']/../table/tbody/tr[1]/td[1]/span[@class]")
    public List<WebElement> skills;

    @FindBy(xpath = "//p[@class='ch-sub-heading']/../table/tbody/tr[1]/td[2]")
    public WebElement hourlyRate;

    @FindBy(xpath = "//p[@class='ch-sub-heading']/../table/tbody/tr[1]/td[3]/div[1]/div[2]")
    public WebElement agencyChargeWithVat;

    @FindBy(xpath = "//p[@class='ch-sub-heading']/../table/tbody/tr[1]/td[3]/div[2]/div[2]")
    public WebElement agencyChargeWithNonVat;

    @FindBy(xpath = "//p[@class='ch-sub-heading']/../table/tbody/tr[1]/td[4]")
    public WebElement careHiresCharge;

    @FindBy(xpath = "//p[@class='ch-sub-heading']/../table/tbody/tr[1]/td[5]/div[1]/div[2]/b")
    public WebElement finalHourlyRateWithVat;

    @FindBy(xpath = "//p[@class='ch-sub-heading']/../table/tbody/tr[1]/td[5]/div[2]/div[2]/b")
    public WebElement finalHourlyRateWithNonVat;

    @FindBy(xpath = "//button[contains(text(), 'Continue')]")
    public WebElement continueButton;

    public String getDropdownOptionXpath(String option) {
        return String.format("//nb-option[contains(text(),'%s')]", option);
    }
}
