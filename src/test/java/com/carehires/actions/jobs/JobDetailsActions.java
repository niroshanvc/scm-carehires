package com.carehires.actions.jobs;


import com.carehires.pages.jobs.JobDetailsPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import com.carehires.utils.GenericUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class JobDetailsActions {

    private final JobDetailsPage jobDetailsPage;
    private final GenericUtils genericUtils;

    private static final Logger logger = LogManager.getLogger(JobDetailsActions.class);

    private static final String ENTITY = "job";
    private static final String YML_FILE = "job-create";
    private static final String YML_FILE_SCENARIO1 = "scenario - job post";
    private static final String YML_FILE_SLEEP_IN_SCENARIO1 = "sleep in scenario - job post";
    private static final String YML_FILE_MANAGE_TIMESHEET = "manage-timesheet";
    private static final String YML_FILE_EDIT = "job-post-edit";
    private static final String YML_FILE_WITH_BREAKS = "job-create-with-breaks";
    private static final String YML_FILE_BLOCK_BOOKING = "job-create-block-booking";
    private static final String YML_FILE_CANCELLATION = "job-cancellation";
    private static final String YML_FILE_MANAGE_TEMPLATE = "manage-job-template";
    private static final String YML_FILE_PROVIDER_USER = "provider user - job-post";
    private static final String YML_FILE_NON_BRITISH = "scenario-non-British-worker";
    private static final String YML_HEADER = "Job Details";
    private static final String YML_HEADER1 = "Job Details A";
    private static final String YML_HEADER2 = "Job Details B";
    private static final String YML_HEADER3 = "Job Details C";
    private static final String YML_HEADER4 = "Job Details D";
    private static final String YML_HEADER_SCENARIO1A = "Job Details Scenario1A";
    private static final String YML_HEADER_SCENARIO1B = "Job Details Scenario1B";
    private static final String YML_HEADER_SCENARIO1C = "Job Details Scenario1C";
    private static final String YML_HEADER_SCENARIO1D = "Job Details Scenario1D";
    private static final String YML_HEADER_SCENARIO1F = "Job Details Scenario1F";
    private static final String YML_HEADER_SCENARIO1G = "Job Details Scenario1G";
    private static final String YML_HEADER_SCENARIO_E2E = "Job Details ScenarioE2E";
    private static final String NORMAL_DAY = "Normal Day";
    private static final String ENABLE_RECURRENCE = "Enable Recurrence";
    private static final String BREAKS_INTERVALS = "Breaks/ Intervals";
    private static final String YML_HEADER_EDIT = "Edit Job Details";
    private static final String YML_HEADER_PROVIDER = "Care Provider / Site and Service Preferences";
    private static final String YML_HEADER_JOB_DURATION = "Job Duration and Recurrence";
    private static final String YML_HEADER_SLEEP_IN = "Sleep In duration and Recurrence";
    private static final String TOGGLE_ATTRIBUTE_AREA_CHECKED = "aria-checked";

    public JobDetailsActions() {
        jobDetailsPage = new JobDetailsPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), jobDetailsPage);
        } catch (BasePage.WebDriverInitializationException e) {
            logger.error("Error while initializing Job Details Page elements: {}", e.getMessage());
        }
        this.genericUtils = GenericUtils.getInstance();
    }

    public void enterJobDetails() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Enter Job Details >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        closePendingActionPopup();
        enterCareProviderAndServicePreferences(YML_FILE, YML_HEADER);
        BasePage.scrollToWebElement(jobDetailsPage.continueButton);
        enterJobDurationRecurrenceAndBreaksWithoutEndsOn(YML_FILE, YML_HEADER);

        BasePage.genericWait(5000);
        BasePage.clickWithJavaScript(jobDetailsPage.continueButton);
    }

    public void enteringJobDetails() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Job Details >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        closePendingActionPopup();
        enterCareProviderAndServicePreferences(YML_FILE, YML_HEADER);
        BasePage.scrollToWebElement(jobDetailsPage.continueButton);
        enterJobDurationRecurrenceAndBreaksWithoutEndsOn(YML_FILE, YML_HEADER);

        BasePage.genericWait(5000);
        BasePage.clickWithJavaScript(jobDetailsPage.continueButton);
    }

    /**
     * Enter job duration, recurrence and breaks.
     *
     * @param ymlFile     YAML file name.
     *
     */
    private void enterJobDurationRecurrenceAndBreaksWithoutEndsOn(String ymlFile, String header) {
        enterJobDurationOnly(ymlFile, header, NORMAL_DAY);
        BasePage.scrollToBottomOfPage();
        handleToggleOption(ymlFile, header, NORMAL_DAY, ENABLE_RECURRENCE, jobDetailsPage.enableRecurrence);
        handleToggleOption(ymlFile, header, NORMAL_DAY, BREAKS_INTERVALS, jobDetailsPage.breaksOrIntervals);
    }

    private void enterJobDurationOnly(String ymlFile, String header, String dayType) {
        selectDateOnCalendar(dayType, ymlFile, header, jobDetailsPage.startDate, "Start Date");
        selectTime(ymlFile, header, "Start Time", jobDetailsPage.startTime, jobDetailsPage.startTimeAreaList,
                jobDetailsPage.availableStartTimes, jobDetailsPage.startTimeSelectionOkButton);
        selectTime(ymlFile, header, "End Time", jobDetailsPage.endTime, jobDetailsPage.endTimeAreaList,
                jobDetailsPage.availableEndTimes, jobDetailsPage.endTimeSelectionOkButton);
    }

    /**
     * Enter care provider/ site and service preference.
     *
     * @param ymlFile     YAML file name.
     *
     */
    private void enterCareProviderAndServicePreferences(String ymlFile, String header) {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<< Entering Care provider info >>>>>>>>>>>>>>>>>>>>>>>>>");
        selectJobType(ymlFile, header);
        BasePage.genericWait(5000);

        // Retrieve the latest provider increment value
        int providerIncrementValue = DataConfigurationReader.getCurrentIncrementValue("provider");

        BasePage.clickWithJavaScript(jobDetailsPage.careProviderInput);

        // Read care provider name from YAML and replace <providerIncrement> placeholder
        String providerTemplate = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, header,
                YML_HEADER_PROVIDER, "Care Provider");
        assert providerTemplate != null;
        String careProvider = providerTemplate.replace("<providerIncrement>", String.valueOf(
                providerIncrementValue));
        careProvider = careProvider.replace("\"", "").trim();
        BasePage.clearAndEnterTexts(jobDetailsPage.careProviderInput, careProvider);
        BasePage.genericWait(3000);
        By by = By.xpath(jobDetailsPage.getDropdownOptionXpath(careProvider));
        BasePage.waitUntilVisibilityOfElementLocated(by, 60);
        BasePage.scrollToWebElement(jobDetailsPage.getDropdownOptionXpath(careProvider));
        BasePage.clickWithJavaScript(jobDetailsPage.getDropdownOptionXpath(careProvider));

        BasePage.genericWait(500);
        String siteTemplates = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, header,
                YML_HEADER_PROVIDER, "Site");
        assert siteTemplates != null;
        String site = siteTemplates.replace("<providerIncrement>", String.valueOf(providerIncrementValue));
        site = site.replace("\"", "").trim();
        BasePage.genericWait(500);
        BasePage.waitUntilElementClickable(jobDetailsPage.siteInput, 60);
        BasePage.clickWithJavaScript(jobDetailsPage.siteInput);
        BasePage.clearAndEnterTexts(jobDetailsPage.siteInput, site);
        BasePage.genericWait(500);
        by = By.xpath(jobDetailsPage.getDropdownOptionXpath(site));
        BasePage.waitUntilVisibilityOfElementLocated(by, 30);
        BasePage.scrollToWebElement(jobDetailsPage.getDropdownOptionXpath(site));
        BasePage.clickWithJavaScript(jobDetailsPage.getDropdownOptionXpath(site));

        selectRadioButton(ymlFile, header, jobDetailsPage.usingRadioButtons);
        BasePage.genericWait(2000);
        selectDropdownOption(ymlFile, header, "Worker Type", jobDetailsPage.workerTypeDropdown);
        selectDropdownOption(ymlFile, header , "Number of Vacancies", jobDetailsPage.
                numberOfVacanciesDropdown);
    }

    private void selectJobType(String ymlFile, String header) {
        String jobType = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, header, "Job Type");
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
    private void selectDropdownOption(String ymlFile, String header, String optionKey, WebElement dropdown) {
        String optionValue = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, header, YML_HEADER_PROVIDER,
                optionKey);
        BasePage.clickWithJavaScript(dropdown);
        BasePage.genericWait(1000);
        By by = By.xpath(jobDetailsPage.getDropdownOptionXpath(optionValue));
        BasePage.waitUntilVisibilityOfElementLocated(by, 30);
        BasePage.scrollToWebElement(jobDetailsPage.getDropdownOptionXpath(optionValue));
        BasePage.clickWithJavaScript(jobDetailsPage.getDropdownOptionXpath(optionValue));
    }

    private void selectRadioButton(String ymlFile, String header, List<WebElement> radioButtons) {
        String value = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, header, YML_HEADER_PROVIDER,
                "Using");
        assert value != null;
        int index = value.equalsIgnoreCase("Post custom job") ? 0 : 1;
        BasePage.clickWithJavaScript(radioButtons.get(index));
    }

    /**
     * Select a date from calendar.
     *
     * @param dayType     Normal or Bank or Special.
     * @param ymlFile     YAML file name.
     * @param header      header name.
     * @param element     calendar web element.
     * @param date        date to be selected.
     *
     */
    private void selectDateOnCalendar(String dayType, String ymlFile, String header, WebElement element, String date) {
        String startDate = "";
        if (dayType.contains("Normal")) {
            startDate = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, header, YML_HEADER_JOB_DURATION,
                    NORMAL_DAY, date);
        } else if (dayType.contains("Special")) {
            startDate = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, header,
                    YML_HEADER_JOB_DURATION, "Special Holiday", date);
        } else if (dayType.contains("Bank")) {
            startDate = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, header,
                    YML_HEADER_JOB_DURATION, "Bank Holiday", date);
        }
        BasePage.clickWithJavaScript(element);
        genericUtils.selectDateFromCalendarPopup(startDate);
    }

    /**
     * Select a date from calendar for Sleep in jobs.
     *
     * @param ymlFile     YAML file name.
     * @param header      header name.
     *
     */
    private void selectDateOnCalendarForSleepIn(String ymlFile, String header) {
        String startDate = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, header,
                YML_HEADER_SLEEP_IN, NORMAL_DAY, "Start Date");
        BasePage.clickWithJavaScript(jobDetailsPage.startDate);
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
    private void selectTime(String ymlFile, String header, String timeKey, WebElement timeField, WebElement timeAreaList
            , List<WebElement> availableTimes, WebElement okButton) {
        String timeValue = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, header, YML_HEADER_JOB_DURATION,
                NORMAL_DAY, timeKey);
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
    private void handleToggleOption(String ymlFile, String header, String subHeader, String toggleKey,
                                    WebElement toggleElement) {
        String toggleValue = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, header,
                YML_HEADER_JOB_DURATION, subHeader, toggleKey);
        assert toggleValue != null;

        String currentAttr = BasePage.getAttributeValue(toggleElement, TOGGLE_ATTRIBUTE_AREA_CHECKED);
        boolean shouldEnable = toggleValue.equalsIgnoreCase("yes");
        boolean isCurrentlyEnabled = currentAttr.equalsIgnoreCase("true");

        // If the current state does not match the expected state, click the toggle
        if (shouldEnable) {
            if (!isCurrentlyEnabled) {
                // enabling the toggle
                BasePage.clickWithJavaScript(toggleElement);

                if (toggleKey.equalsIgnoreCase(ENABLE_RECURRENCE)) {
                    // entering recurrence details
                    enteringRecurrenceDetails(ymlFile, header, subHeader);
                } else if (toggleKey.equalsIgnoreCase(BREAKS_INTERVALS)) {
                    // entering breaks details
                    enteringBreaksIntervalsDetails(ymlFile, header, subHeader);
                }
            } else {
                // If the toggle is already enabled
                if (toggleKey.equalsIgnoreCase(ENABLE_RECURRENCE)) {
                    // entering recurrence details
                    enteringRecurrenceDetails(ymlFile, header, subHeader);
                } else if (toggleKey.equalsIgnoreCase(BREAKS_INTERVALS)) {
                    // entering breaks details
                    enteringBreaksIntervalsDetails(ymlFile, header, subHeader);
                }
            }
        } else {
            if (isCurrentlyEnabled) {
                // disabling the toggle
                BasePage.clickWithJavaScript(toggleElement);
            }
        }
    }

    private void enteringBreaksIntervalsDetails(String ymlFile, String header, String subHeader) {
        BasePage.waitUntilElementPresent(jobDetailsPage.paidBreaksDuration, 60);
        selectTime(ymlFile, header, "Paid Breaks Duration", jobDetailsPage.paidBreaksDuration,
                jobDetailsPage.paidBreaksDurationAreaList, jobDetailsPage.availablePaidBreaksDurations,
                jobDetailsPage.paidBreaksDurationOkButton);
        selectTime(ymlFile, header, "Unpaid Breaks Duration", jobDetailsPage.unpaidBreaksDuration,
                jobDetailsPage.unpaidBreaksDurationAreaList, jobDetailsPage.availableUnpaidBreaksDurations,
                jobDetailsPage.unpaidBreaksDurationOkButton);

        String paidBreaksNote = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, header,
                YML_HEADER_JOB_DURATION, subHeader, "Paid Breaks Note");
        BasePage.clearAndEnterTexts(jobDetailsPage.paidBreaksNote, paidBreaksNote);

        String unpaidBreaksNote = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile,
                header, YML_HEADER_JOB_DURATION, subHeader, "Unpaid Breaks Note");
        BasePage.clearAndEnterTexts(jobDetailsPage.unpaidBreaksNote, unpaidBreaksNote);
    }

    private void enteringRecurrenceDetails(String ymlFile, String header, String subHeader) {
        BasePage.waitUntilElementPresent(jobDetailsPage.repeatTypeDropdown, 60);
        BasePage.clickWithJavaScript(jobDetailsPage.repeatTypeDropdown);
        String repeatType = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, header,
                YML_HEADER_JOB_DURATION, subHeader, "Repeat Type");
        BasePage.clickWithJavaScript(jobDetailsPage.getDropdownOptionXpath(repeatType));
        selectDateOnCalendar(subHeader ,ymlFile, header, jobDetailsPage.endsOn, "Ends On");
    }

    public void enterJobDetailsWithBreaks() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Job Details With Enabling Breaks >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        closePendingActionPopup();
        enterCareProviderAndServicePreferences(YML_FILE_WITH_BREAKS, YML_HEADER);
        BasePage.scrollToWebElement(jobDetailsPage.continueButton);
        enterJobDurationRecurrenceAndBreaksWithoutEndsOn(YML_FILE_WITH_BREAKS, YML_HEADER);
        BasePage.genericWait(5000);
        BasePage.clickWithJavaScript(jobDetailsPage.continueButton);
    }

    private void closePendingActionPopup() {
        try {
            BasePage.waitUntilElementPresent(jobDetailsPage.pendingActionPopupYesButton, 10);
            BasePage.clickWithJavaScript(jobDetailsPage.pendingActionPopupYesButton);
        } catch (NoSuchElementException e) {
            logger.info("<<<<<<<<<<<<<<<<<<<<<<< Pending Action popup is not there >>>>>>>>>>>>>>>>>>>>");
        }
    }

    public void enterJobDetailsInData() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Job Details to test Edit Job >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        closePendingActionPopup();
        enterCareProviderAndServicePreferences(YML_FILE_EDIT, YML_HEADER);
        BasePage.scrollToWebElement(jobDetailsPage.continueButton);
        enterJobDurationRecurrenceAndBreaksWithoutEndsOn(YML_FILE_EDIT, YML_HEADER);
        BasePage.genericWait(5000);
        BasePage.clickWithJavaScript(jobDetailsPage.continueButton);
    }

    public void editInfo() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Edit Job Details >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        enterCareProviderAndServicePreferences(YML_FILE_EDIT, YML_HEADER_EDIT);
        BasePage.scrollToWebElement(jobDetailsPage.continueButton);
        enterJobDurationRecurrenceAndBreaksWithoutEndsOn(YML_FILE_EDIT, YML_HEADER_EDIT);
        BasePage.genericWait(5000);
        BasePage.clickWithJavaScript(jobDetailsPage.continueButton);
    }

    public void enterJobDetailsForEnd2End() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Job Details for End 2 End Testing >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        closePendingActionPopup();
        enterCareProviderAndServicePreferences(YML_FILE_SCENARIO1, YML_HEADER_SCENARIO_E2E);
        BasePage.scrollToWebElement(jobDetailsPage.continueButton);
        enterJobDurationRecurrenceForSpecialHoliday();
        BasePage.genericWait(5000);
        BasePage.clickWithJavaScript(jobDetailsPage.continueButton);
    }

    private void enterJobDurationRecurrenceForSpecialHoliday() {
        selectDateOnCalendar("Special Holiday", YML_FILE, YML_HEADER, jobDetailsPage.startDate,
                "Start Date");
        selectTime(YML_FILE, YML_HEADER, "Start Time", jobDetailsPage.startTime, jobDetailsPage.
                startTimeAreaList, jobDetailsPage.availableStartTimes, jobDetailsPage.startTimeSelectionOkButton);
        selectTime(YML_FILE, YML_HEADER, "End Time", jobDetailsPage.endTime, jobDetailsPage.endTimeAreaList,
                jobDetailsPage.availableEndTimes, jobDetailsPage.endTimeSelectionOkButton);

        BasePage.scrollToWebElement(jobDetailsPage.enableRecurrence);
        handleToggleOption(YML_FILE, YML_HEADER, "Special Holiday", ENABLE_RECURRENCE, jobDetailsPage.
                enableRecurrence);
        handleToggleOption(YML_FILE, YML_HEADER, "Special Holiday", BREAKS_INTERVALS, jobDetailsPage.
                breaksOrIntervals);
    }

    public void enterCareProviderDetailsWithPostCustomJob() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Care Provider by Selecting Custom Job >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        closePendingActionPopup();
        enterCareProviderAndServicePreferences(YML_FILE_SCENARIO1, YML_HEADER_SCENARIO1A);

    }

    public void enterJobDurationWithoutRecurrenceAndBreaks() {
        enterJobDurationRecurrenceAndClickContinue();
    }

    private void enterJobDurationRecurrenceAndClickContinue() {
        enterJobDurationRecurrenceAndBreaksWithoutEndsOn(YML_FILE_SCENARIO1, YML_HEADER_SCENARIO1A);
        BasePage.waitUntilElementClickable(jobDetailsPage.continueButton, 20);
        BasePage.clickWithJavaScript(jobDetailsPage.continueButton);
    }

    public void enterJobDetailsWithRecurrenceBreaks() {
        logger.info("<<<<<<<<<<<<<<<<<<<<< Entering Job Details with Recurrence and Breaks >>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        closePendingActionPopup();
        enterCareProviderAndServicePreferences(YML_FILE_SCENARIO1, YML_HEADER_SCENARIO1B);
        enterJobDurationRecurrenceAndBreaksWithoutEndsOn(YML_FILE_SCENARIO1, YML_HEADER_SCENARIO1B);
        BasePage.waitUntilElementClickable(jobDetailsPage.continueButton, 20);
        BasePage.clickWithJavaScript(jobDetailsPage.continueButton);
    }

    public void enterJobDetailsWithRecurrenceWithoutBreaks() {
        logger.info("<<<<<<<<<<<<<<<<<<<<< Entering Job Details with Recurrence and no Breaks >>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        closePendingActionPopup();
        enterCareProviderAndServicePreferences(YML_FILE_SCENARIO1, YML_HEADER_SCENARIO1C);
        enterJobDurationRecurrenceAndBreaksWithoutEndsOn(YML_FILE_SCENARIO1, YML_HEADER_SCENARIO1C);
        BasePage.waitUntilElementClickable(jobDetailsPage.continueButton, 20);
        BasePage.clickWithJavaScript(jobDetailsPage.continueButton);
    }

    public void enterJobDurationWithProviderAndDuration() {
        logger.info("<<<<<<<<<<<<<<<<<<<<< Entering Job Details with Recurrence and with Breaks >>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        closePendingActionPopup();
        enterCareProviderAndServicePreferences(YML_FILE_SCENARIO1, YML_HEADER_SCENARIO1D);
        enterJobDurationRecurrenceAndBreaksWithoutEndsOn(YML_FILE_SCENARIO1, YML_HEADER_SCENARIO1D);
        BasePage.waitUntilElementClickable(jobDetailsPage.continueButton, 20);
        BasePage.clickWithJavaScript(jobDetailsPage.continueButton);
    }

    public void selectTemplate(String templateName) {
        logger.info("<<<<<<<<<<<<<<<<<<<<< Selecting template as {} >>>>>>>>>>>>>>>>>>", templateName);
        BasePage.waitUntilPageCompletelyLoaded();
        closePendingActionPopup();
        selectPostUsingTemplateRadioButton();
        BasePage.waitUntilElementClickable(jobDetailsPage.templateDropdownButton, 60);
        BasePage.clickWithJavaScript(jobDetailsPage.templateDropdownButton);
        BasePage.genericWait(3000);
        By by = By.xpath(jobDetailsPage.getDropdownOptionXpath(templateName));
        BasePage.waitUntilVisibilityOfElementLocated(by, 30);
        BasePage.scrollToWebElement(jobDetailsPage.getDropdownOptionXpath(templateName));
        BasePage.clickWithJavaScript(jobDetailsPage.getDropdownOptionXpath(templateName));

        String header = "";

        if (templateName.contains("001")) {
            header = YML_HEADER_SCENARIO1A;
        } else if (templateName.contains("002")) {
            header = YML_HEADER_SCENARIO1B;
        }

        waitUntilDesiredSiteLoaded(header);
    }

    // wait until desired site is selected
    private void waitUntilDesiredSiteLoaded(String header) {
        int providerIncrementValue = DataConfigurationReader.getCurrentIncrementValue("provider");
        String siteTemplates = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_SCENARIO1, header,
                YML_HEADER_PROVIDER, "Site");
        assert siteTemplates != null;
        String site = siteTemplates.replace("<providerIncrement>", String.valueOf(providerIncrementValue));
        site = site.replace("\"", "").trim();
        BasePage.waitUntilValueLoadedInDropdown(jobDetailsPage.siteInput, site, 60);
    }


    private void selectPostUsingTemplateRadioButton() {
        BasePage.waitUntilElementClickable(jobDetailsPage.postUsingTemplateRadioButton, 60);
        BasePage.clickWithJavaScript(jobDetailsPage.postUsingTemplateRadioButton);
    }

    public void enterJobDurationByDisablingRecurrenceAndBreaks() {
        enterJobDurationOnly(YML_FILE_SCENARIO1, YML_HEADER_SCENARIO1A, NORMAL_DAY);
        handleToggleOption(YML_FILE_SCENARIO1, YML_HEADER_SCENARIO1A, NORMAL_DAY, ENABLE_RECURRENCE, jobDetailsPage.
                enableRecurrence);
        handleToggleOption(YML_FILE_SCENARIO1, YML_HEADER_SCENARIO1A, NORMAL_DAY, BREAKS_INTERVALS, jobDetailsPage.
                breaksOrIntervals);
        BasePage.clickWithJavaScript(jobDetailsPage.continueButton);
    }

    public void enterJobDurationOnlyWithRecurrenceAndWithBreaks() {
        logger.info("<<<<<<<<<<<<<<<<<<<<< Entering Job Details Only with Recurrence and Breaks >>>>>>>>>>>>>>>>>>");
        enterJobDurationRecurrenceAndBreaksWithoutEndsOn(YML_FILE_SCENARIO1, YML_HEADER_SCENARIO1B);
        BasePage.waitUntilElementClickable(jobDetailsPage.continueButton, 20);
        BasePage.clickWithJavaScript(jobDetailsPage.continueButton);
    }

    public void enterJobDurationAndEnablingRecurrenceOnly() {
        logger.info("<<<<<<<<<<<<<<<< Entering Job Details by enabling Recurrence and disabling Breaks >>>>>>>>>>>>>");
        enterJobDurationRecurrenceAndBreaksWithoutEndsOn(YML_FILE_SCENARIO1, YML_HEADER_SCENARIO1C);
        BasePage.waitUntilElementClickable(jobDetailsPage.continueButton, 20);
        BasePage.clickWithJavaScript(jobDetailsPage.continueButton);
    }

    public void enterSleepInJobDetails() {
        logger.info("<<<<<<<<<<<<<<<<<<<<< Entering Job Details for Sleep In jobs >>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        closePendingActionPopup();
        enterCareProviderAndServicePreferences(YML_FILE_SLEEP_IN_SCENARIO1, YML_HEADER_SCENARIO1A);
        enterSleepInDurationAndRecurrence(YML_FILE_SLEEP_IN_SCENARIO1, YML_HEADER_SCENARIO1A);
        BasePage.waitUntilElementClickable(jobDetailsPage.continueButton, 20);
        BasePage.clickWithJavaScript(jobDetailsPage.continueButton);
    }

    private void enterSleepInDurationAndRecurrence(String ymlFile, String header) {
        logger.info("<<<<<<<<<<<<<<<<<<<<< Entering Job Details - Sleep In Duration and Recurrence >>>>>>>>>>>>>>>>>>");
        selectDateOnCalendarForSleepIn(ymlFile, header);
        selectTimeForSleepIn(ymlFile, header, "Start Time", jobDetailsPage.startTime, jobDetailsPage.
                startTimeAreaList, jobDetailsPage.availableStartTimes, jobDetailsPage.startTimeSelectionOkButton);
        selectTimeForSleepIn(ymlFile, header, "End Time", jobDetailsPage.endTime, jobDetailsPage.
                endTimeAreaList, jobDetailsPage.availableEndTimes, jobDetailsPage.endTimeSelectionOkButton);
    }

    /**
     * Enter sleep in start and end times.
     *
     * @param ymlFile     YAML file name.
     * @param timeKey     time to be selected.
     * @param timeField   web element to be entered.
     * @param timeAreaList  time displaying list web element
     * @param availableTimes  available time in the list
     * @param okButton    Ok button web element
     */
    private void selectTimeForSleepIn(String ymlFile, String header, String timeKey, WebElement timeField, WebElement
                                              timeAreaList, List<WebElement> availableTimes, WebElement okButton) {
        String timeValue = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, header, YML_HEADER_SLEEP_IN,
                NORMAL_DAY, timeKey);
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

    public void sleepInJobWithMultipleVacancies() {
        logger.info("<<<<<<<<<<<<<<<<< Entering Job Details for Sleep In jobs with Multiple Vacancies >>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        closePendingActionPopup();
        enterCareProviderAndServicePreferences(YML_FILE_SLEEP_IN_SCENARIO1, YML_HEADER_SCENARIO1B);
        enterSleepInDurationAndRecurrence(YML_FILE_SLEEP_IN_SCENARIO1, YML_HEADER_SCENARIO1B);
        BasePage.waitUntilElementClickable(jobDetailsPage.continueButton, 20);
        BasePage.clickWithJavaScript(jobDetailsPage.continueButton);
    }

    public void selectSleepInTemplate(String templateName) {
        logger.info("<<<<<<<<<<<<<<<<<<<< Selecting sleep in template as {} >>>>>>>>>>>>>>>>>", templateName);
        BasePage.waitUntilPageCompletelyLoaded();
        closePendingActionPopup();
        selectPostUsingTemplateRadioButton();
        BasePage.waitUntilElementClickable(jobDetailsPage.templateDropdownButton, 60);
        BasePage.clickWithJavaScript(jobDetailsPage.templateDropdownButton);
        BasePage.genericWait(3000);
        By by = By.xpath(jobDetailsPage.getDropdownOptionXpath(templateName));
        BasePage.waitUntilVisibilityOfElementLocated(by, 30);
        BasePage.scrollToWebElement(jobDetailsPage.getDropdownOptionXpath(templateName));
        BasePage.clickWithJavaScript(jobDetailsPage.getDropdownOptionXpath(templateName));

        String header = "";

        if (templateName.contains("001")) {
            header = YML_HEADER_SCENARIO1A;
        } else if (templateName.contains("002")) {
            header = YML_HEADER_SCENARIO1B;
        } else if(templateName.contains("003")) {
            header = YML_HEADER_SCENARIO1F;
        } else if(templateName.contains("004")) {
            header = YML_HEADER_SCENARIO1G;
        }

        waitUntilDesiredSiteLoadedInSleepIn(header);
    }

    // wait until desired site is selected
    private void waitUntilDesiredSiteLoadedInSleepIn(String header) {
        int providerIncrementValue = DataConfigurationReader.getCurrentIncrementValue("provider");
        String siteTemplates = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_SLEEP_IN_SCENARIO1,
                header, YML_HEADER_PROVIDER, "Site");
        assert siteTemplates != null;
        String site = siteTemplates.replace("<providerIncrement>", String.valueOf(providerIncrementValue));
        site = site.replace("\"", "").trim();
        BasePage.waitUntilValueLoadedInDropdown(jobDetailsPage.siteInput, site, 60);
    }

    public void enterSleepInDurationAndRecurrence() {
        enterSleepInDurationAndRecurrence(YML_FILE_SLEEP_IN_SCENARIO1, YML_HEADER_SCENARIO1F);
        BasePage.waitUntilElementClickable(jobDetailsPage.continueButton, 20);
        BasePage.clickWithJavaScript(jobDetailsPage.continueButton);
    }

    public void enterSleepInDurationWithMoreThanOneVacancy() {
        logger.info("<<<<<<<<<<<<<<<<< Multiple Vacancies with Sleep In Duration and Recurrence >>>>>>>>>>>>>>");
        selectDropdownOption(YML_FILE_SLEEP_IN_SCENARIO1, YML_HEADER_SCENARIO1G , "Number of Vacancies",
                jobDetailsPage.numberOfVacanciesDropdown);
        enterSleepInDurationAndRecurrence(YML_FILE_SLEEP_IN_SCENARIO1, YML_HEADER_SCENARIO1G);
        BasePage.waitUntilElementClickable(jobDetailsPage.continueButton, 20);
        BasePage.clickWithJavaScript(jobDetailsPage.continueButton);
    }

    public void disablingRecurrenceAndBreaks() {
        logger.info("<<<<<<<<<<<<<<<<< Enter job details by disabling recurrence and breaks >>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        closePendingActionPopup();
        enterCareProviderAndServicePreferences(YML_FILE_BLOCK_BOOKING, YML_HEADER);
        BasePage.scrollToWebElement(jobDetailsPage.continueButton);
        enterJobDurationRecurrenceAndBreaksWithoutEndsOn(YML_FILE_BLOCK_BOOKING, YML_HEADER);
        BasePage.genericWait(5000);
        BasePage.clickWithJavaScript(jobDetailsPage.continueButton);
    }

    public void enterGeneralJobDetails() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<< Enter general job details >>>>>>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        closePendingActionPopup();
        enterCareProviderAndServicePreferences(YML_FILE, YML_HEADER1);
        BasePage.scrollToWebElement(jobDetailsPage.continueButton);
        enterJobDurationRecurrenceAndBreaksWithoutEndsOn(YML_FILE, YML_HEADER1);
        BasePage.genericWait(5000);
        BasePage.clickWithJavaScript(jobDetailsPage.continueButton);
    }

    public void jobTypeSleepInAndCustomJob() {
        logger.info("<<<<<<<<<<<<<<<<< Entering Job Details for Sleep In - Manage Timesheet - Approve >>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        closePendingActionPopup();
        enterCareProviderAndServicePreferences(YML_FILE_MANAGE_TIMESHEET, YML_HEADER);
        enterSleepInDurationAndRecurrence(YML_FILE_MANAGE_TIMESHEET, YML_HEADER);
        BasePage.waitUntilElementClickable(jobDetailsPage.continueButton, 20);
        BasePage.clickWithJavaScript(jobDetailsPage.continueButton);
    }

    public void enterJobDetailsForReSubmit() {
        logger.info("<<<<<<<<<<<<<<<<< Entering Job Details for Sleep In - Manage Timesheet - ReSubmit >>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        closePendingActionPopup();
        enterCareProviderAndServicePreferences(YML_FILE_MANAGE_TIMESHEET, YML_HEADER1);
        enterSleepInDurationAndRecurrence(YML_FILE_MANAGE_TIMESHEET, YML_HEADER1);
        BasePage.waitUntilElementClickable(jobDetailsPage.continueButton, 20);
        BasePage.clickWithJavaScript(jobDetailsPage.continueButton);
    }

    public void generalJobAndCustomJob() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Entering Job Details for General Job >>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        closePendingActionPopup();
        enterCareProviderAndServicePreferences(YML_FILE_MANAGE_TIMESHEET, YML_HEADER2);
        BasePage.scrollToWebElement(jobDetailsPage.continueButton);
        enterJobDurationRecurrenceAndBreaksWithoutEndsOn(YML_FILE_MANAGE_TIMESHEET, YML_HEADER2);

        BasePage.genericWait(5000);
        BasePage.clickWithJavaScript(jobDetailsPage.continueButton);
    }

    public void enterJobDetailsForManageTimesheet() {
        logger.info("<<<<<<<<<<<<<<<<<<< Entering Job Details for General Job - Manage Timesheet >>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        closePendingActionPopup();
        enterCareProviderAndServicePreferences(YML_FILE_MANAGE_TIMESHEET, YML_HEADER3);
        BasePage.scrollToWebElement(jobDetailsPage.continueButton);
        enterJobDurationRecurrenceAndBreaksWithoutEndsOn(YML_FILE_MANAGE_TIMESHEET, YML_HEADER3);

        BasePage.genericWait(5000);
        BasePage.clickWithJavaScript(jobDetailsPage.continueButton);
    }

    public void enterJobDetailsForCancellation() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Enter Job Details for Cancellation >>>>>>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        closePendingActionPopup();
        enterCareProviderAndServicePreferences(YML_FILE_CANCELLATION, YML_HEADER);
        BasePage.scrollToWebElement(jobDetailsPage.continueButton);
        enterJobDurationRecurrenceAndBreaksWithoutEndsOn(YML_FILE_CANCELLATION, YML_HEADER);
        BasePage.genericWait(5000);
        BasePage.clickWithJavaScript(jobDetailsPage.continueButton);
    }

    public void enterOverDueJobDetailsForCancellation() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Enter OverDue Job Details for Cancellation >>>>>>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        closePendingActionPopup();
        enterCareProviderAndServicePreferences(YML_FILE_CANCELLATION, YML_HEADER1);
        BasePage.scrollToWebElement(jobDetailsPage.continueButton);
        enterJobDurationRecurrenceAndBreaksWithoutEndsOn(YML_FILE_CANCELLATION, YML_HEADER1);
        BasePage.genericWait(5000);
        BasePage.clickWithJavaScript(jobDetailsPage.continueButton);
    }

    public void enterJobDetailsToManageTemplate() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Job Details to manage job template >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        closePendingActionPopup();
        enterCareProviderAndServicePreferences(YML_FILE_MANAGE_TEMPLATE, YML_HEADER);
        BasePage.scrollToWebElement(jobDetailsPage.continueButton);
        enterJobDurationRecurrenceAndBreaksWithoutEndsOn(YML_FILE_MANAGE_TEMPLATE, YML_HEADER);
        BasePage.genericWait(5000);
        BasePage.clickWithJavaScript(jobDetailsPage.continueButton);
    }

    public void providerUserEntersJobDetails() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Enter Job Details by Provider User >>>>>>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        closePendingActionPopup();
        enterCareProviderDetailsByProviderUser(YML_FILE_PROVIDER_USER, YML_HEADER);
        BasePage.scrollToWebElement(jobDetailsPage.continueButton);

        // enter job duration and recurrence
        enterJobDurationRecurrenceAndBreaksWithoutEndsOn(YML_FILE_PROVIDER_USER, YML_HEADER);

        BasePage.genericWait(5000);
        BasePage.clickWithJavaScript(jobDetailsPage.continueButton);
    }

    private void enterCareProviderDetailsByProviderUser(String ymlFile, String header) {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<< Entering Care provider info >>>>>>>>>>>>>>>>>>>>>>>>>>");
        selectJobType(ymlFile, header);
        BasePage.genericWait(5000);

        // select site
        selectFirstOptionFromDropdown(jobDetailsPage.siteInput);

        // select post custom job or post using template
        selectRadioButton(ymlFile, header, jobDetailsPage.usingRadioButtons);
        BasePage.genericWait(2000);

        // select worker type
        selectDropdownOption(ymlFile, header, "Worker Type", jobDetailsPage.workerTypeDropdown);

        // select number of vacancies
        selectDropdownOption(ymlFile, header, "Number of Vacancies", jobDetailsPage.numberOfVacanciesDropdown);
    }

    private void selectFirstOptionFromDropdown(WebElement input) {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<< Selecting first option from dropdown >>>>>>>>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementClickable(input, 30);
        BasePage.clearAndEnterTexts(input, "aaa");
        BasePage.genericWait(500);
        BasePage.clearTexts(input);
        BasePage.genericWait(500);
        BasePage.doubleClick(input);
        BasePage.genericWait(3000);
        List<WebElement> options = jobDetailsPage.availableOptionsList();
        BasePage.waitUntilElementPresent(options.get(0), 30);
        BasePage.scrollToWebElement(options.get(0));
        BasePage.clickWithJavaScript(options.get(0));
    }

    public void providerUserEntersJobDetailsWithBreaks() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<< Enter Job Details by Provider User >>>>>>>>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        closePendingActionPopup();
        enterCareProviderDetailsByProviderUser(YML_FILE_PROVIDER_USER, YML_HEADER1);
        BasePage.scrollToWebElement(jobDetailsPage.continueButton);

        // enter job duration and recurrence
        enterJobDurationRecurrenceAndBreaksWithoutEndsOn(YML_FILE_PROVIDER_USER, YML_HEADER1);

        BasePage.genericWait(5000);
        BasePage.clickWithJavaScript(jobDetailsPage.continueButton);
    }

    public void providerEnterJobDetailsWithoutRecurrenceAndBreaks() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<< Enter Job Details by Provider User >>>>>>>>>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.scrollToWebElement(jobDetailsPage.continueButton);

        // enter job duration and recurrence
        enterJobDurationRecurrenceAndBreaksWithoutEndsOn(YML_FILE_PROVIDER_USER, YML_HEADER2);

        BasePage.genericWait(5000);
        BasePage.clickWithJavaScript(jobDetailsPage.continueButton);
    }

    public void providerUserSelectTemplate(String templateName) {
        logger.info("<<<<<<<<<<<<<<<<<<<<<< Selecting template as {} >>>>>>>>>>>>>>>>>>>", templateName);
        BasePage.waitUntilPageCompletelyLoaded();
        closePendingActionPopup();
        selectPostUsingTemplateRadioButton();
        BasePage.waitUntilElementClickable(jobDetailsPage.templateDropdownButton, 60);
        BasePage.clickWithJavaScript(jobDetailsPage.templateDropdownButton);
        BasePage.genericWait(3000);
        By by = By.xpath(jobDetailsPage.getDropdownOptionXpath(templateName));
        BasePage.waitUntilVisibilityOfElementLocated(by, 30);
        BasePage.scrollToWebElement(jobDetailsPage.getDropdownOptionXpath(templateName));
        BasePage.clickWithJavaScript(jobDetailsPage.getDropdownOptionXpath(templateName));

        // wait until desired site is selected
        By siteLocator = By.xpath(jobDetailsPage.siteXpath());
        BasePage.waitForDropdownTextChange(siteLocator, 30);
    }

    public void providerUserSelectJobTypeAsSleepInAndProceedWithCustomJob() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Job Details for Sleep In jobs >>>>>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        closePendingActionPopup();
        enterCareProviderDetailsByProviderUser(YML_FILE_PROVIDER_USER, YML_HEADER3);
        BasePage.scrollToWebElement(jobDetailsPage.continueButton);
        enterSleepInDurationAndRecurrence(YML_FILE_PROVIDER_USER, YML_HEADER3);
        BasePage.waitUntilElementClickable(jobDetailsPage.continueButton, 20);
        BasePage.clickWithJavaScript(jobDetailsPage.continueButton);
    }

    public void providerUserEntersSleepInDurationAndRecurrence() {
        enterSleepInDurationAndRecurrence(YML_FILE_PROVIDER_USER, YML_HEADER4);
        BasePage.waitUntilElementClickable(jobDetailsPage.continueButton, 20);
        BasePage.clickWithJavaScript(jobDetailsPage.continueButton);
    }

    public void enterJobDetailsAndDisablingRecurrenceAndBreaks() {
        logger.info("<<<<<<<<<<<<<<<<<<< Enter job details by disabling recurrence and breaks >>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        closePendingActionPopup();
        enterCareProviderAndServicePreferences(YML_FILE_NON_BRITISH, YML_HEADER);
        BasePage.scrollToWebElement(jobDetailsPage.continueButton);
        enterJobDurationRecurrenceAndBreaksWithoutEndsOn(YML_FILE_NON_BRITISH, YML_HEADER);
        BasePage.genericWait(5000);
        BasePage.clickWithJavaScript(jobDetailsPage.continueButton);
    }
}
