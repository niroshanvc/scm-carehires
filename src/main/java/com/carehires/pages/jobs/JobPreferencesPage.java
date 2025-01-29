package com.carehires.pages.jobs;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class JobPreferencesPage {

    @FindBy(xpath = "//nb-select[@id='preferred-gender']/button")
    public WebElement genderDropdown;

    @FindBy(xpath = "//div[@class='skill-container']//button")
    public List<WebElement> preferredSkills;

    @FindBy(xpath = "//nb-toggle[@formcontrolname='enabledBlockBooking']//input")
    public WebElement enableBlockBookingToggle;

    @FindBy(xpath = "//nb-select[@id='agency-id']/button")
    public WebElement agencyDropdown;

    @FindBy(id = "keywordInput")
    public WebElement nameInput;

    @FindBy(xpath = "//nb-option-list[contains(@id, 'nb-autocomplete')]/ul/nb-option/nb-user/div")
    public List<WebElement> workersList;

    @FindBy(xpath = "//button[contains(text(), 'Add worker')]")
    public WebElement addWorkerButton;

    @FindBy(xpath = "//textarea[@formcontrolname='notes']")
    public WebElement notes;

    @FindBy(xpath = "//button[contains(text(), 'Continue')]")
    public WebElement continueButton;

    public String getDropdownOptionXpath(String option) {
        return String.format("//nb-option[contains(text(),'%s')]", option);
    }
}
