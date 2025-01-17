package com.carehires.actions.jobs;

import com.carehires.pages.jobs.JobDetailsPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import com.carehires.utils.GenericUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class JobDetailsActions {

    private final JobDetailsPage jobDetailsPage;
    private static final GenericUtils genericUtils;

    static {
        try {
            genericUtils = new GenericUtils();
        } catch (BasePage.WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String ENTITY = "job";
    private static final String YML_FILE = "job-create";
    private static final String YML_HEADER = "Job Details";
    private static final String YML_HEADER_SUB1 = "Care Provider / Site and Service Preferences";
    private static final String YML_HEADER_SUB2 = "Job duration and Recurrence";
    private static final String ADD = "Add";
    private static final String TOGGLE_ATTRIBUTE_AREA_CHECKED = "aria-checked";
    private static final String ATTRIBUTE_VALUE_FALSE = "false";

    private static final Logger logger = LogManager.getLogger(JobDetailsActions.class);

    public JobDetailsActions() {
        jobDetailsPage = new JobDetailsPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), jobDetailsPage);
        } catch (BasePage.WebDriverInitializationException e) {
            logger.error("Error while initializing Job Details Page elements: {}", e.getMessage());
        }
    }

    public void enterJobDetails() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Job Details >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();

        selectJobType();
        selectDropdownOption("Care Provider", jobDetailsPage.careProviderDropdown);
        selectDropdownOption("Site", jobDetailsPage.siteDropdown);
        selectRadioButton(jobDetailsPage.usingRadioButtons);
        selectDropdownOption("Worker Type", jobDetailsPage.workerTypeDropdown);
        selectDropdownOption("Number of Vacancies", jobDetailsPage.numberOfVacanciesDropdown);

        selectStartDate();
        selectTime("Start Time", jobDetailsPage.startTime, jobDetailsPage.startTimeAreaList, jobDetailsPage.availableStartTimes, jobDetailsPage.startTimeSelectionOkButton);
        selectTime("End Time", jobDetailsPage.endTime, jobDetailsPage.endTimeAreaList, jobDetailsPage.availableEndTimes, jobDetailsPage.endTimeSelectionOkButton);

        handleToggleOption("Enable Recurrence", jobDetailsPage.enableRecurrence);
        handleToggleOption("Breaks/ Intervals", jobDetailsPage.breaksOrIntervals);

        BasePage.genericWait(5000);
        BasePage.clickWithJavaScript(jobDetailsPage.continueButton);
    }

    private void selectJobType() {
        String jobType = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Job Type");
        assert jobType != null;
        if (jobType.equalsIgnoreCase("sleep in")) {
            BasePage.clickWithJavaScript(jobDetailsPage.sleepInButton);
        }
    }

    private void selectDropdownOption(String optionKey, WebElement dropdown) {
        String optionValue = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_HEADER_SUB1, optionKey);
        BasePage.clickWithJavaScript(dropdown);
        By by = By.xpath(getDropdownOptionXpath(optionValue));
        BasePage.waitUntilVisibilityOfElementLocated(by, 30);
        BasePage.scrollToWebElement(getDropdownOptionXpath(optionValue));
        BasePage.clickWithJavaScript(getDropdownOptionXpath(optionValue));
    }

    private void selectRadioButton(List<WebElement> radioButtons) {
        String value = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_HEADER_SUB1, "Using");
        assert value != null;
        int index = value.equalsIgnoreCase("Post custom job") ? 0 : 1;
        BasePage.clickWithJavaScript(radioButtons.get(index));
    }

    private void selectStartDate() {
        String startDate = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_HEADER_SUB2, "Start Date");
        BasePage.clickWithJavaScript(jobDetailsPage.startDate);
        genericUtils.selectDateFromCalendarPopup(startDate);
    }

    private void selectTime(String timeKey, WebElement timeField, WebElement timeAreaList, List<WebElement> availableTimes, WebElement okButton) {
        String timeValue = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_HEADER_SUB2, timeKey);
        BasePage.clickWithJavaScript(timeField);
        BasePage.waitUntilElementPresent(timeAreaList, 60);
        BasePage.waitUntil(() -> !availableTimes.isEmpty(), 60);

        for (WebElement timeOption : availableTimes) {
            BasePage.scrollToWebElement(timeOption);
            BasePage.waitUntilElementClickable(timeOption, 10);
            if (BasePage.getTextWithoutWait(timeOption).equalsIgnoreCase(timeValue)) {
                BasePage.clickWithJavaScript(timeOption);
                break;
            }
        }
        BasePage.clickWithJavaScript(okButton);
    }

    private void handleToggleOption(String toggleKey, WebElement toggleElement) {
        String toggleValue = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_HEADER_SUB2, toggleKey);
        assert toggleValue != null;
        String currentAttr = BasePage.getAttributeValue(toggleElement, TOGGLE_ATTRIBUTE_AREA_CHECKED);
        boolean shouldEnable = toggleValue.equalsIgnoreCase("yes");
        boolean isCurrentlyEnabled = currentAttr.equalsIgnoreCase(ATTRIBUTE_VALUE_FALSE);

        if (shouldEnable != isCurrentlyEnabled) {
            BasePage.clickWithJavaScript(toggleElement);
        }
    }

    private String getDropdownOptionXpath(String option) {
        return String.format("//nb-option[contains(text(),'%s')]", option);
    }
}
