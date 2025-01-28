package com.carehires.pages.agreements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SleepInRatesPage {

    @FindBy(xpath = "//nb-select[@formcontrolname='workerType']/button")
    public WebElement workerTypeDropdown;

    @FindBy(xpath = "//td[contains(@class, 'normal-rate')]/../td[contains(@class, 'hourly-rate') and " +
            "not(contains(@class, 'agency'))]//input")
    public WebElement normalRateHourlyRateInput;

    @FindBy(xpath = "//td[contains(@class, 'normal-rate')]/../td[contains(@class, 'agency-hourly-rate')]//input")
    public WebElement normalRateAgencyMarginInput;

    @FindBy(xpath = "//td[contains(@class, 'normal-rate')]/../td[contains(@class, 'total-vat')]/p")
    public WebElement normalRateAgencyVat;

    @FindBy(xpath = "//td[contains(@class, 'normal-rate')]/../td[@class='vat-column']//div[1]/div[2]")
    public WebElement normalRateAgencyCostVat;

    @FindBy(xpath = "//td[contains(@class, 'normal-rate')]/../td[@class='vat-column']//div[2]/div[2]")
    public WebElement normalRateAgencyCostNoVat;

    @FindBy(xpath = "//td[contains(@class, 'normal-rate')]/../td[contains(@class, 'carehires-margin')]//input")
    public WebElement normalRateChHourlyMarginInput;

    @FindBy(xpath = "//td[contains(@class, 'normal-rate')]/../td[contains(@class,'carehires-vat-column')]/p")
    public WebElement normalRateChHourlyVat;

    @FindBy(xpath = "//td[contains(@class, 'normal-rate')]/../td[contains(@class,'final-charge')]//div[1]/div[2]")
    public WebElement normalRateFinalRateVat;

    @FindBy(xpath = "//td[contains(@class, 'normal-rate')]/../td[contains(@class,'final-charge')]//div[2]/div[2]")
    public WebElement normalRateFinalRateNoVat;

    @FindBy(xpath = "//div[text()='Friday Night Rate']")
    public WebElement fridayNightRate;

    @FindBy(xpath = "//button[contains(text(), 'Add')]")
    public WebElement addButton;

    @FindBy(xpath = "//nb-toast//span")
    public WebElement successMessage;

    @FindBy(xpath = "//button[contains(text(), 'Continue')]")
    public WebElement continueButton;

    @FindBy(xpath = "//table/tbody/tr[1]/td[1]/span[1]")
    public WebElement workerType;

    @FindBy(xpath = "//tr[@class='ng-star-inserted']/td[2]")
    public WebElement hourlyChargeRate;

    @FindBy(xpath = "//tr[@class='ng-star-inserted']/td[3]/div[1]/div[2]")
    public WebElement agencyHourlyMarginWithVat;

    @FindBy(xpath = "//tr[@class='ng-star-inserted']/td[3]/div[2]/div[2]")
    public WebElement agencyHourlyMarginWithNonVat;

    @FindBy(xpath = "//tr[@class='ng-star-inserted']/td[4]")
    public WebElement careHiresHourlyMargin;

    @FindBy(xpath = "//tr[@class='ng-star-inserted']/td[5]/div[1]/div[2]/b")
    public WebElement finallyHourlyRateWithVat;

    @FindBy(xpath = "//tr[@class='ng-star-inserted']/td[5]/div[2]/div[2]/b")
    public WebElement finallyHourlyRateWithNonVat;

    public String getDropdownOptionXpath(String option) {
        return String.format("//nb-option[contains(text(),'%s')]", option);
    }
}
