package com.carehires.pages.jobs;

import com.carehires.utils.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class JobDetailsPage {

    @FindBy(xpath = "//nb-button-group[@role='group']/button[2]")
    public WebElement sleepInButton;

    @FindBy(id = "care-provider")
    public WebElement careProviderInput;

    @FindBy(id = "care-home")
    public WebElement siteInput;

    @FindBy(xpath = "//nb-radio-group[@id='break-paid-status']/nb-radio//input")
    public List<WebElement> usingRadioButtons;

    @FindBy(xpath = "//nb-select[@id='worker-type']/button")
    public WebElement workerTypeDropdown;

    @FindBy(xpath = "//nb-select[@id='vacancies']/button")
    public WebElement numberOfVacanciesDropdown;

    @FindBy(id = "start-date")
    public WebElement startDate;

    @FindBy(id = "start-time")
    public WebElement startTime;

    @FindBy(xpath = "//div[@class='picker-body']")
    public WebElement startTimeAreaList;

    @FindBy(xpath = "//nb-card[contains(@class,'timepicker')]/div/nb-list//nb-timepicker-cell/div")
    public List<WebElement> availableStartTimes;

    @FindBy(xpath = "//nb-calendar-actions/button[contains(@class, 'filled')]")
    public WebElement startTimeSelectionOkButton;

    @FindBy(id = "end-time")
    public WebElement endTime;

    @FindBy(xpath = "//div[@class='picker-body']")
    public WebElement endTimeAreaList;

    @FindBy(xpath = "//nb-card[contains(@class,'timepicker')]/div/nb-list//nb-timepicker-cell/div")
    public List<WebElement> availableEndTimes;

    @FindBy(xpath = "//nb-calendar-actions/button[contains(@class, 'filled')]")
    public WebElement endTimeSelectionOkButton;

    @FindBy(xpath = "//label[contains(text(), 'Enable Recurrence')]/../../nb-toggle/label/input")
    public WebElement enableRecurrence;

    @FindBy(xpath = "//nb-select[@id='type-name']/button")
    public WebElement repeatTypeDropdown;

    @FindBy(id = "ends-on")
    public WebElement endsOn;

    @FindBy(xpath = "//p[contains(text(), 'Breaks')]/../../nb-toggle/label/input")
    public WebElement breaksOrIntervals;

    @FindBy(id = "paidDuration")
    public WebElement paidBreaksDuration;

    @FindBy(xpath = "//div[@class='picker-body']")
    public WebElement paidBreaksDurationAreaList;

    @FindBy(xpath = "//div[@class='picker-body']")
    public WebElement unpaidBreaksDurationAreaList;

    @FindBy(xpath = "//nb-card[contains(@class,'timepicker')]/div/nb-list//nb-timepicker-cell/div")
    public List<WebElement> availablePaidBreaksDurations;

    @FindBy(xpath = "//nb-card[contains(@class,'timepicker')]/div/nb-list//nb-timepicker-cell/div")
    public List<WebElement> availableUnpaidBreaksDurations;

    @FindBy(xpath = "//nb-calendar-actions/button[contains(@class, 'filled')]")
    public WebElement paidBreaksDurationOkButton;

    @FindBy(xpath = "//nb-calendar-actions/button[contains(@class, 'filled')]")
    public WebElement unpaidBreaksDurationOkButton;

    @FindBy(id = "unpaidDuration")
    public WebElement unpaidBreaksDuration;

    @FindBy(id = "paidNote")
    public WebElement paidBreaksNote;

    @FindBy(id = "unpaidNote")
    public WebElement unpaidBreaksNote;

    @FindBy(xpath = "//button[contains(text(), 'Continue')]")
    public WebElement continueButton;

    public String getDropdownOptionXpath(String option) {
        return String.format("//nb-option[contains(text(),'%s')]", option);
    }

    @FindBy(xpath = "//nb-card[@class='confirm-card']//button")
    public WebElement pendingActionPopupYesButton;

    @FindBy(xpath = "//span[contains(text(), 'Post using Template')]/preceding-sibling::input")
    public WebElement postUsingTemplateRadioButton;

    @FindBy(xpath = "//nb-select[@formcontrolname='template']/button")
    public WebElement templateDropdownButton;

    public List<WebElement> availableOptionsList() {
        return BasePage.listOfWebElements("//ul/nb-option");
    }

    public String siteXpath() {
        return "//nb-select[@id='care-home']/button";
    }
}
