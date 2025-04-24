package com.carehires.pages.providers;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class WorkerStaffPage {

    @FindBy(xpath = "//button[contains(@class, 'inserted') and (contains(@class, 'button'))]")
    public WebElement addNewButton;

    @FindBy(xpath = "//nb-select[@formcontrolname='careHome']/button")
    public WebElement siteDropdown;

    @FindBy(xpath = "//nb-select[@formcontrolname='workerType']/button")
    public WebElement workerTypeDropdown;

    @FindBy(xpath = "//nb-select[@formcontrolname='workerSkills']/button")
    public WebElement skills;

    @FindBy(id = "hourly-rate")
    public WebElement hourlyRate;

    @FindBy(id = "agency-hours")
    public WebElement monthlyAgencyHours;

    @FindBy(id = "agency-spend")
    public WebElement monthlyAgencySpend;

    @FindBy(xpath = "//input[@type='file']")
    public WebElement proofOfDemandDocument;

    @FindBy(xpath = "//button[contains(text(),'Add')]")
    public WebElement addButton;

    @FindBy(xpath = "//button[contains(text(), 'Update')]")
    public WebElement updateButton;

    @FindBy(xpath = "//div[contains(@class, 'table-body-grid')]/div[5]")
    public WebElement monthlySpendInTableGrid;

    @FindBy(xpath = "//button[contains(text(), 'Next')]")
    public WebElement nextButton;

    @FindBy(xpath = "//nb-toast//span")
    public WebElement successMessage;

    @FindBy(xpath = "//nb-icon[@icon='edit-2-outline']")
    public WebElement editDetailsIcon;

    @FindBy(xpath = "//nb-option[contains(@class,'multiple') and (contains(@class, 'selected'))]")
    public List<WebElement> alreadySelectedWorkerSkills;

    public static String getDropdownXpath(String text) {
        return String.format("//nb-option[contains(text(),'%s')]", text);
    }

    @FindBy(xpath = "//nb-option-list/ul/nb-option[1]")
    public WebElement firstOptionInSiteDropdown;
}