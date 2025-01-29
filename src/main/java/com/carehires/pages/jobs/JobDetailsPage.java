package com.carehires.pages.jobs;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class JobDetailsPage {

    @FindBy(xpath = "//button[contains(@class, 'status-primary')]")
    public WebElement generalJobButton;

    @FindBy(xpath = "//button[contains(@class, 'status-basic')]")
    public WebElement sleepInButton;

    @FindBy(xpath = "//nb-select[@id='care-provider']/button")
    public WebElement careProviderDropdown;

    @FindBy(xpath = "//nb-select[@id='care-home']/button")
    public WebElement siteDropdown;

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
    public WebElement repeatType;

    @FindBy(id = "ends-on")
    public WebElement endsOn;

    @FindBy(xpath = "//p[contains(text(), 'Breaks')]/../../nb-toggle/label/input")
    public WebElement breaksOrIntervals;

    @FindBy(id = "paidDuration")
    public WebElement paidBreaksDuration;

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
}
