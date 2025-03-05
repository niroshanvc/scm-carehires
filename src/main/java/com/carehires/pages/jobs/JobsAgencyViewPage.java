package com.carehires.pages.jobs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class JobsAgencyViewPage {

    @FindBy(xpath = "//button[@nbtooltip='Agency View']")
    public WebElement agencyView;

    @FindBy(xpath = "//nb-select[@id='agency-id']/button")
    public WebElement agencyDropdown;

    @FindBy(xpath = "//div[contains(@class, 'calender')]//div[contains(@class, 'active')]/p[2]")
    public WebElement currentDate;

    @FindBy(xpath = "//div[contains(@class, 'calender')]//div[contains(@class, 'active')]/preceding-sibling::div")
    public List<WebElement> previousDateList;

    public String getDropdownOptionXpath(String option) {
        return String.format("//nb-option[contains(text(),'%s')]", option);
    }

    public String getMostRecentJobPost(int mostRecentDate) {
        return String.format("//div[contains(@class, 'open-shift')]/div[%d]//div[contains(@class, 'between')]//img" +
                "[contains(@src, 'Timesheet')]", mostRecentDate);
    }

    public static final By jobDetailsPopupThreeDots = By.xpath("//span[contains(@class, 'menu-wrapper')]" +
            "//p");

    public static final By jobDetailsPopupCancelJob = By.xpath("//div[contains(@class, 'side-menu')]//" +
            "span[contains(text(), 'Cancel Job')]");

    @FindBy(xpath = "//nb-select[@formcontrolname='reason']/button")
    public WebElement reasonDropdownButtonOnCancelJobPopup;

    @FindBy(xpath = "//textarea[@formcontrolname='description']")
    public WebElement descriptionTextAreaOnCancelJobsPopup;

    @FindBy(xpath = "//button[contains(text(), 'Continue')]")
    public WebElement continueButtonOnCancelJobPopup;

    @FindBy(xpath = "//button[text()='Cancel Job']")
    public WebElement cancelJobButton;

    @FindBy(xpath = "//nb-toast//span")
    public WebElement successMessage;
}
