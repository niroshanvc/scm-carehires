package com.carehires.pages.agreements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PoliciesForTheProvisionsOfServicePage {

    @FindBy(xpath = "//p[text()='Weekly']/ancestor::div[contains(@class, 'payment-card')]")
    public WebElement weeklyBillingCycle;

    @FindBy(xpath = "//p[contains(text(),'7 Days')]/ancestor::div[contains(@class, 'payment-card')]")
    public WebElement sevenDaysCreditPeriod;

    @FindBy(xpath = "//p[contains(text(),'14 Days')]/ancestor::div[contains(@class, 'payment-card')]")
    public WebElement fourteenDaysCreditPeriod;

    @FindBy(xpath = "//p[contains(text(),'21 Days')]/ancestor::div[contains(@class, 'payment-card')]")
    public WebElement twentyOneDaysCreditPeriod;

    @FindBy(xpath = "//p[contains(text(),'28 Days')]/ancestor::div[contains(@class, 'payment-card')]")
    public WebElement twentyEightDaysCreditPeriod;

    @FindBy(xpath = "//p[contains(text(),'30 Days')]/ancestor::div[contains(@class, 'payment-card')]")
    public WebElement thirtyDaysCreditPeriod;

    @FindBy(xpath = "//nb-checkbox[@status='basic']//input")
    public WebElement timesheetApprovalCheckbox;

    @FindBy(xpath = "//button[contains(text(), 'Continue')]")
    public WebElement continueButton;
}
