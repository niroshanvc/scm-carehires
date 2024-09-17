package com.carehires.pages.agency;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CreateAgencyStaffPage {

    @FindBy(xpath = "//button[contains(@class, 'inserted') and (contains(@class, 'button'))]")
    public WebElement addNewButton;

    @FindBy(xpath = "//nb-select[@formcontrolname='location']/button")
    public WebElement locationDropdown;

    @FindBy(xpath = "//nb-select[@formcontrolname='workerType']/button")
    public WebElement workerTypeDropdown;

    @FindBy(xpath = "//input[@formcontrolname='quantity']")
    public WebElement numberOfWorkers;

    @FindBy(xpath = "//nb-select[@formcontrolname='workerSkills']/button")
    public WebElement workerSkills;

    @FindBy(xpath = "//input[@formcontrolname='monthlyAvailableHours']")
    public WebElement monthlyHoursAvailable;

    @FindBy(xpath = "//input[@formcontrolname='minChargeRate']")
    public WebElement minChargeHourlyRate;

    @FindBy(xpath = "//input[@name='employeeType']")
    public List<WebElement> employeeType;

    @FindBy(xpath = "//button[contains(text(),'Add')]")
    public WebElement addButton;

    @FindBy(xpath = "//button[contains(text(),'Update')]")
    public WebElement updateButton;

    @FindBy(xpath = "//button[contains(text(), 'Next')]")
    public WebElement nextButton;

    @FindBy(xpath = "(//div[contains(@class, 'common-document-table-body')]//p)[1]")
    public WebElement locationName;
}
