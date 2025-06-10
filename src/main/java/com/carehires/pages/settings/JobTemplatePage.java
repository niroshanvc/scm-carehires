package com.carehires.pages.settings;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class JobTemplatePage {

    @FindBy(xpath = "//img[contains(@src, 'add')]/../p//img")
    public WebElement moveToTemplate;

    @FindBy(id = "business-name")
    public WebElement searchText;

    @FindBy(xpath = "//div[@class='result-list']/div")
    public WebElement firstResult;

    @FindBy(xpath = "//p[contains(@class, 'text-icon') and contains(text(), '…')]")
    public WebElement topThreeDots;

    @FindBy(xpath = "//div[contains(@class, 'side')]/ul[@class='expanded-nav']/li[1]")
    public WebElement inactiveMenuLink;

    public static final By INACTIVE_OR_ACTIVE_MENU_LINK = By.xpath("//div[contains(@class," +
            " 'side')]/ul[@class='expanded-nav']/li[1]");

    @FindBy(xpath = "//nb-card-footer//button")
    public WebElement confirmActionPopupYesButton;

    @FindBy(xpath = "//div[contains(@class, 'basic-profile-container')]//div[@class='text']")
    public WebElement templateStatus;

    @FindBy(xpath = "//nb-select[not(@id)]/button")
    public WebElement templateStatusDropdown;

    public String getDropdownOptionXpath(String templateStatus) {
        return String.format("//nb-option[contains(text(),'%s')]", templateStatus);
    }

    public static final By createJobTemplate = By.xpath("//p[contains(text(), 'Create')]");

    public static final By manageImportAndExport = By.xpath("//p[contains(text(), 'Manage')]");

    public static final By createTemplateButton = By.xpath("//button[contains(text(), " +
            "'Create Template')]");

    @FindBy(xpath = "//input[@formcontrolname='name']")
    public WebElement templateName;

    @FindBy(xpath = "//textarea[@formcontrolname='description']")
    public WebElement templateDescription;

    @FindBy(xpath = "//nb-button-group[@role='group']/button[1]")
    public WebElement generalJobButton;

    @FindBy(xpath = "//nb-button-group[@role='group']/button[2]")
    public WebElement sleepInButton;

    @FindBy(xpath = "//nb-select[@id='care-home']/button")
    public WebElement siteDropdown;

    @FindBy(xpath = "//nb-option-list/ul/nb-option[not(@hidden)]")
    public List<WebElement> availableOptionList;

    @FindBy(xpath = "//nb-select[@id='vacancies']/button")
    public WebElement numberOfVacanciesDropdown;

    @FindBy(xpath = "//nb-select[@id='worker-type']/button")
    public WebElement workerTypeDropdown;

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

    @FindBy(xpath = "//nb-select[@id='preferred-gender']/button")
    public WebElement genderDropdown;

    @FindBy(xpath = "//div[@class='skill-container']//button")
    public List<WebElement> preferredSkills;

    @FindBy(xpath = "//div[@class='skill-container']//button[@aria-pressed='true']")
    public List<WebElement> selectedSkills;

    @FindBy(xpath = "//textarea[@formcontrolname='notes']")
    public WebElement notes;

    @FindBy(xpath = "//button[contains(text(), 'Save')]")
    public WebElement saveButton;

    @FindBy(xpath = "//nb-toast//span")
    public WebElement successMessage;
}
