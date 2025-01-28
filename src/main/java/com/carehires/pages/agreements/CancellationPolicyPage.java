package com.carehires.pages.agreements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CancellationPolicyPage {

    @FindBy(xpath = "//nb-select[@formcontrolname='noOfHours']/button")
    public WebElement beforeJobStartDropdown;

    @FindBy(xpath = "//input[@formcontrolname='cancellatonFeePercentage']")
    public WebElement cancellationFeePercentage;

    @FindBy(xpath = "//input[@formcontrolname='careHiresSplit']")
    public WebElement careHiresSplit;

    @FindBy(xpath = "//input[@formcontrolname='agencySplit']")
    public WebElement agencySplit;

    @FindBy(xpath = "//button[contains(text(), 'Add')]")
    public WebElement addButton;

    @FindBy(xpath = "//nb-toast//span")
    public WebElement successMessage;

    @FindBy(xpath = "//button[contains(text(), 'Continue')]")
    public WebElement continueButton;

    @FindBy(xpath = "//table[@class='table ch-table']/tbody/tr/td[2]")
    public WebElement beforeJobStartValue;

    @FindBy(xpath = "//table[@class='table ch-table']/tbody/tr/td[3]")
    public WebElement cancellationFeePercentageValue;

    @FindBy(xpath = "//table[@class='table ch-table']/tbody/tr/td[4]")
    public WebElement careHiresSplitValue;

    public String getDropdownOptionXpath(String option) {
        return String.format("//nb-option[contains(text(),'%s')]", option);
    }
}
