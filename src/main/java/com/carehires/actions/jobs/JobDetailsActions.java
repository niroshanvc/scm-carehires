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
    private static final String YML_FILE_WITH_BREAKS = "job-create-with-breaks";
    private static final String YML_HEADER = "Job Details";
    private static final String YML_HEADER_SUB1 = "Care Provider / Site and Service Preferences";
    private static final String YML_HEADER_SUB2 = "Job Duration and Recurrence";
    private static final String TOGGLE_ATTRIBUTE_AREA_CHECKED = "aria-checked";

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

        enterCareProviderAndServicePreferences(YML_FILE);
        BasePage.scrollToWebElement(jobDetailsPage.continueButton);
        enterJobDurationAndRecurrence(YML_FILE);

        BasePage.genericWait(5000);
        BasePage.clickWithJavaScript(jobDetailsPage.continueButton);
    }

    /**
     * Enter job duration and recurrence.
     *
     * @param ymlFile     YAML file name.
     *
     */
    private void enterJobDurationAndRecurrence(String ymlFile) {
        selectDateOnCalendar(ymlFile, jobDetailsPage.startDate, "Start Date");
        selectTime(ymlFile, "Start Time", jobDetailsPage.startTime, jobDetailsPage.startTimeAreaList,
                jobDetailsPage.availableStartTimes, jobDetailsPage.startTimeSelectionOkButton);
        selectTime(ymlFile, "End Time", jobDetailsPage.endTime, jobDetailsPage.endTimeAreaList,
                jobDetailsPage.availableEndTimes, jobDetailsPage.endTimeSelectionOkButton);

        BasePage.scrollToWebElement(jobDetailsPage.enableRecurrence);
        handleToggleOption(ymlFile, "Enable Recurrence", jobDetailsPage.enableRecurrence);
        handleToggleOption(ymlFile, "Breaks/ Intervals", jobDetailsPage.breaksOrIntervals);
    }

    /**
     * Enter care provider/ site and service preference.
     *
     * @param ymlFile     YAML file name.
     *
     */
    private void enterCareProviderAndServicePreferences(String ymlFile) {
        selectJobType(ymlFile);
        BasePage.genericWait(5000);
        selectDropdownOption(ymlFile, "Care Provider", jobDetailsPage.careProviderDropdown);
        BasePage.genericWait(500);
        selectDropdownOption(ymlFile, "Site", jobDetailsPage.siteDropdown);
        selectRadioButton(ymlFile, jobDetailsPage.usingRadioButtons);
        BasePage.genericWait(2000);
        selectDropdownOption(ymlFile, "Worker Type", jobDetailsPage.workerTypeDropdown);
        selectDropdownOption(ymlFile, "Number of Vacancies", jobDetailsPage.numberOfVacanciesDropdown);
    }

    private void selectJobType(String ymlFile) {
        String jobType = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, "Job Type");
        assert jobType != null;
        if (jobType.equalsIgnoreCase("sleep in")) {
            BasePage.clickWithJavaScript(jobDetailsPage.sleepInButton);
        }
    }

    /**
     * select an option from dropdown.
     *
     * @param ymlFile     YAML file name.
     * @param optionKey     dropdown option to be selected.
     * @param dropdown     dropdown element.
     */
    private void selectDropdownOption(String ymlFile, String optionKey, WebElement dropdown) {
        String optionValue = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, YML_HEADER_SUB1,
                optionKey);
        BasePage.clickWithJavaScript(dropdown);
        By by = By.xpath(jobDetailsPage.getDropdownOptionXpath(optionValue));
        BasePage.waitUntilVisibilityOfElementLocated(by, 30);
        BasePage.scrollToWebElement(jobDetailsPage.getDropdownOptionXpath(optionValue));
        BasePage.clickWithJavaScript(jobDetailsPage.getDropdownOptionXpath(optionValue));
    }

    private void selectRadioButton(String ymlFile, List<WebElement> radioButtons) {
        String value = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, YML_HEADER_SUB1,
                "Using");
        assert value != null;
        int index = value.equalsIgnoreCase("Post custom job") ? 0 : 1;
        BasePage.clickWithJavaScript(radioButtons.get(index));
    }

    /**
     * Select a date from calendar.
     *
     * @param ymlFile     YAML file name.
     * @param element     calendar web element.
     * @param date        date to be selected.
     *
     */
    private void selectDateOnCalendar(String ymlFile, WebElement element, String date) {
        String startDate = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                YML_HEADER_SUB2, date);
        BasePage.clickWithJavaScript(element);
        genericUtils.selectDateFromCalendarPopup(startDate);
    }

    /**
     * Enter care provider/ site and service preference.
     *
     * @param ymlFile     YAML file name.
     * @param timeKey     time to be selected.
     * @param timeField   web element to be entered.
     * @param timeAreaList  time displaying list web element
     * @param availableTimes  available time in the list
     * @param okButton    Ok button web element
     */
    private void selectTime(String ymlFile, String timeKey, WebElement timeField, WebElement timeAreaList,
                            List<WebElement> availableTimes, WebElement okButton) {
        String timeValue = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, YML_HEADER_SUB2,
                timeKey);
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

    /**
     * Enter care provider/ site and service preference.
     *
     * @param ymlFile     YAML file name.
     * @param toggleKey     toggle button to be enabled or disabled.
     * @param toggleElement   toggle web element.
     */
    private void handleToggleOption(String ymlFile, String toggleKey, WebElement toggleElement) {
        String toggleValue = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, YML_HEADER_SUB2,
                toggleKey);
        assert toggleValue != null;
        String currentAttr = BasePage.getAttributeValue(toggleElement, TOGGLE_ATTRIBUTE_AREA_CHECKED);
        boolean shouldEnable = toggleValue.equalsIgnoreCase("yes");
        boolean isCurrentlyEnabled = currentAttr.equalsIgnoreCase("true");

        if (toggleKey.equalsIgnoreCase("Enable Recurrence") && shouldEnable != isCurrentlyEnabled) {
            BasePage.clickWithJavaScript(toggleElement);
            BasePage.waitUntilElementPresent(jobDetailsPage.repeatTypeDropdown, 60);
            BasePage.clickWithJavaScript(jobDetailsPage.repeatTypeDropdown);
            String repeatType = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                    YML_HEADER_SUB2, "Repeat Type");
            BasePage.clickWithJavaScript(jobDetailsPage.getDropdownOptionXpath(repeatType));
            selectDateOnCalendar(ymlFile, jobDetailsPage.endsOn, "Ends On");
        } else if (toggleKey.equalsIgnoreCase("Breaks/ Intervals") && shouldEnable != isCurrentlyEnabled) {
            BasePage.clickWithJavaScript(toggleElement);
            BasePage.waitUntilElementPresent(jobDetailsPage.paidBreaksDuration, 60);
            selectTime(ymlFile, "Paid Breaks Duration", jobDetailsPage.paidBreaksDuration,
                    jobDetailsPage.paidBreaksDurationAreaList, jobDetailsPage.availablePaidBreaksDurations,
                    jobDetailsPage.paidBreaksDurationOkButton);
            selectTime(ymlFile, "Unpaid Breaks Duration", jobDetailsPage.unpaidBreaksDuration,
                    jobDetailsPage.unpaidBreaksDurationAreaList, jobDetailsPage.availableUnpaidBreaksDurations,
                    jobDetailsPage.unpaidBreaksDurationOkButton);

            String paidBreaksNote = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                    YML_HEADER_SUB2, "Paid Breaks Note");
            BasePage.clearAndEnterTexts(jobDetailsPage.paidBreaksNote, paidBreaksNote);

            String unpaidBreaksNote = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                    YML_HEADER_SUB2, "Unpaid Breaks Note");
            BasePage.clearAndEnterTexts(jobDetailsPage.unpaidBreaksNote, unpaidBreaksNote);
        }
    }

    public void enterJobDetailsWithBreaks() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Job Details With Enabling Breaks >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        enterCareProviderAndServicePreferences(YML_FILE_WITH_BREAKS);
        BasePage.scrollToWebElement(jobDetailsPage.continueButton);
        enterJobDurationAndRecurrence(YML_FILE_WITH_BREAKS);
        BasePage.genericWait(5000);
        BasePage.clickWithJavaScript(jobDetailsPage.continueButton);
    }
}
