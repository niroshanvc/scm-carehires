package com.carehires.actions.settings;

import com.carehires.pages.settings.JobTemplatePage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import com.carehires.utils.GenericUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class JobTemplateActions {

    private final JobTemplatePage jobTemplatePage;
    private final GenericUtils genericUtils;

    private static final Logger logger = LogManager.getLogger(JobTemplateActions.class);

    private static final String ENTITY = "job";
    private static final String YML_FILE = "manage-job-template";
    private static final String YML_HEADER = "Job Preferences";
    private static final String YML_HEADER_TEMPLATE = "New Template";
    private static final String YML_TEMPLATE_SUB_HEADER_TEMPLATE = "Template Details";
    private static final String YML_TEMPLATE_SUB_HEADER_CARE_PROVIDER = "Care Provider / Site and Service Preferences";
    private static final String YML_HEADER_JOB_DURATION = "Job Duration and Recurrence";
    private static final String YML_HEADER_GENDER_SKILLS = "Gender and Skill Preferences";
    private static final String ENABLE_RECURRENCE = "Enable Recurrence";
    private static final String BREAKS_INTERVALS = "Breaks/ Intervals";
    private static final String TOGGLE_ATTRIBUTE_AREA_CHECKED = "aria-checked";

    public JobTemplateActions() {
        jobTemplatePage = new JobTemplatePage();
        try {
            PageFactory.initElements(BasePage.getDriver(), jobTemplatePage);
        } catch (BasePage.WebDriverInitializationException e) {
            logger.error("Error initializing JobTemplatePage: {}", e.getMessage());
        }
        this.genericUtils = GenericUtils.getInstance();
    }

    public void moveToJobTemplatesPage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Moving to job templates page >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementClickable(jobTemplatePage.moveToTemplate, 30);
        BasePage.clickWithJavaScript(jobTemplatePage.moveToTemplate);
    }

    public void searchTemplateByName() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Searching template by entering name >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.genericWait(5000);
        String name = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Template Name");
        BasePage.clearAndEnterTexts(jobTemplatePage.searchText, name);
        BasePage.clickOnEnterKey(jobTemplatePage.searchText);
        waitUntilSearchResultsAreLoaded();
    }

    private void waitUntilSearchResultsAreLoaded() {
        BasePage.genericWait(6000);
        BasePage.waitUntilElementClickable(jobTemplatePage.firstResult, 30);
        BasePage.clickWithJavaScript(jobTemplatePage.firstResult);
    }

    public void doInactiveTemplate(String status) {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Clicking on the inactive menu link >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        if (status.equalsIgnoreCase("inactive")) {
            BasePage.waitUntilElementPresent(jobTemplatePage.topThreeDots, 30);
            BasePage.mouseHoverAndClick(jobTemplatePage.topThreeDots, jobTemplatePage.inactiveMenuLink,
                    JobTemplatePage.INACTIVE_OR_ACTIVE_MENU_LINK);

            BasePage.waitUntilElementClickable(jobTemplatePage.confirmActionPopupYesButton, 30);
            BasePage.clickWithJavaScript(jobTemplatePage.confirmActionPopupYesButton);
            BasePage.genericWait(3000);
            verifyTemplateStatusIsUpdated(status);
        } else if (status.equalsIgnoreCase("active")) {
            BasePage.clickWithJavaScript(jobTemplatePage.templateStatusDropdown);
            BasePage.clickWithJavaScript(jobTemplatePage.getDropdownOptionXpath("Inactive Templates"));
            waitUntilSearchResultsAreLoaded();
            BasePage.waitUntilElementPresent(jobTemplatePage.topThreeDots, 30);
            BasePage.mouseHoverAndClick(jobTemplatePage.topThreeDots, jobTemplatePage.inactiveMenuLink,
                    JobTemplatePage.INACTIVE_OR_ACTIVE_MENU_LINK);
            BasePage.genericWait(3000);
            verifyTemplateStatusIsUpdated(status);
        }
    }

    private void verifyTemplateStatusIsUpdated(String status) {
        BasePage.waitUntilElementPresent(jobTemplatePage.templateStatus, 60);
        String actualStatus = BasePage.getText(jobTemplatePage.templateStatus).toLowerCase().trim();
        assertThat("Template status is not inactive", actualStatus, is(status));
    }

    public void createNewJobTemplate() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Creating new job template >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementClickable(JobTemplatePage.createJobTemplate, 30);
        BasePage.clickWithJavaScript(JobTemplatePage.createJobTemplate);

        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementClickable(JobTemplatePage.createTemplateButton, 30);
        BasePage.clickWithJavaScript(JobTemplatePage.createTemplateButton);

        // enter Template Details from YML file to create a new job template
        BasePage.waitUntilPageCompletelyLoaded();
        enterTemplateDetails(YML_FILE, YML_HEADER_TEMPLATE, YML_TEMPLATE_SUB_HEADER_TEMPLATE);

        selectJobType(YML_FILE, YML_HEADER_TEMPLATE);

        // enter care provider/ site and service preferences from YML file
        enterCareProviderAndServicePreferences(YML_FILE, YML_HEADER_TEMPLATE);

        // enter job duration and recurrence details from YML file
        selectTime(YML_FILE, YML_HEADER_TEMPLATE, "Start Time", jobTemplatePage.startTime, jobTemplatePage.
                        startTimeAreaList, jobTemplatePage.availableStartTimes, jobTemplatePage.
                startTimeSelectionOkButton);
        selectTime(YML_FILE, YML_HEADER_TEMPLATE, "End Time", jobTemplatePage.endTime, jobTemplatePage.
                        endTimeAreaList, jobTemplatePage.availableEndTimes, jobTemplatePage.endTimeSelectionOkButton);
        handleToggleOption(YML_FILE, YML_HEADER_TEMPLATE, ENABLE_RECURRENCE, jobTemplatePage.enableRecurrence);

        // enabling/ selecting breaks and intervals
        handleToggleOption(YML_FILE, YML_HEADER_TEMPLATE, BREAKS_INTERVALS, jobTemplatePage.breaksOrIntervals);

        // enter gender from YML file
        selectGender(YML_FILE, YML_HEADER_TEMPLATE);

        // select preferred skills from YML file
        selectPreferences(YML_FILE, YML_HEADER_TEMPLATE, false);

        // enter job notes from YML file
        enterJobNotes(YML_FILE, YML_HEADER_TEMPLATE);

        // click on save button to save the job template
        BasePage.waitUntilElementClickable(jobTemplatePage.saveButton, 30);
        BasePage.clickWithJavaScript(jobTemplatePage.saveButton);

        // Template posted
        verifyJobPostedSuccessfully();
    }

    private void verifyJobPostedSuccessfully() {
        BasePage.waitUntilElementPresent(jobTemplatePage.successMessage, 60);
        String actualInLowerCase = BasePage.getText(jobTemplatePage.successMessage).toLowerCase().trim();
        String expected = "Template posted";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Create new job template did not work!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(jobTemplatePage.successMessage, 60);
    }

    private void enterJobNotes(String ymlFile, String header) {
        String jobNotes = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, header,
                YML_HEADER_GENDER_SKILLS, "Job Notes");
        BasePage.clearTexts(jobTemplatePage.notes);
        for (int i = 0; i< Objects.requireNonNull(jobNotes).length(); i++) {
            char c = jobNotes.charAt(i);
            String s = String.valueOf(c);
            BasePage.sendKeys(jobTemplatePage.notes, s);
        }
    }

    private void selectPreferences(String ymlFile, String header, boolean isEditMode) {
        Set<String> newPreferredSkills = getPreferredSkillsFromYml(ymlFile, header);
        Set<String> currentlySelectedSkills = getCurrentlySelectedSkills();

        Set<String> skillsToDeselect = getSkillsToDeselect(currentlySelectedSkills, newPreferredSkills);
        Set<String> skillsToSelect = getSkillsToSelect(currentlySelectedSkills, newPreferredSkills);

        if (isEditMode) {
            deselectSkills(skillsToDeselect);
        }

        selectNewSkills(skillsToSelect);

        verifyFinalSelections(newPreferredSkills);
    }

    private void verifyFinalSelections(Set<String> newPreferredSkills) {
        BasePage.genericWait(2000); // Wait for selections to complete
        Set<String> finalSelectedSkills = new HashSet<>();
        for (WebElement selectedSkills : jobTemplatePage.selectedSkills) {
            finalSelectedSkills.add(BasePage.getText(selectedSkills).trim().toLowerCase()); // Convert to lowercase
        }
        logger.info("Final Selected Skills: {}", finalSelectedSkills);

        if (!finalSelectedSkills.equals(newPreferredSkills)) {
            throw new IllegalStateException("Mismatch in skills selection. Expected: " + newPreferredSkills + ", " +
                    "but found: " + finalSelectedSkills);
        }
    }

    private void selectNewSkills(Set<String> skillsToSelect) {
        for (String skillToSelect : skillsToSelect) {
            boolean skillFound = false;
            BasePage.genericWait(3000);
            for (WebElement availableSkill : jobTemplatePage.preferredSkills) {
                String skillText = BasePage.getText(availableSkill).trim().toLowerCase(); // Convert to lowercase
                logger.info("Checking available skill: {}", skillText);
                if (skillText.equals(skillToSelect)) {
                    // Scroll to the element and click
                    BasePage.scrollToWebElement(availableSkill);
                    BasePage.waitUntilElementDisplayed(availableSkill, 30);
                    try {
                        BasePage.clickWithJavaScript(availableSkill);
                        logger.info("Selected: {}", skillText);
                        skillFound = true;
                        break;
                    } catch (StaleElementReferenceException e) {
                        logger.warn("StaleElementReferenceException caught, retrying: {}", skillText);
                        availableSkill = jobTemplatePage.preferredSkills.stream()
                                .filter(skill -> BasePage.getText(skill).trim().toLowerCase().equals(
                                        skillToSelect))
                                .findFirst()
                                .orElseThrow(() -> new RuntimeException("Skill not found after retry: " +
                                        skillToSelect));
                        BasePage.clickWithJavaScript(availableSkill);
                        logger.info("Selected after retry: {}", skillText);
                        skillFound = true;
                        break;
                    }
                }
            }
            if (!skillFound) {
                logger.warn("Skill not found: {}", skillToSelect);
            }
        }
    }

    private void deselectSkills(Set<String> skillsToDeselect) {
        for (WebElement selectedSkills : jobTemplatePage.selectedSkills) {
            String skillName = BasePage.getText(selectedSkills).trim().toLowerCase(); // Convert to lowercase
            if (skillsToDeselect.contains(skillName)) {
                BasePage.clickWithJavaScript(selectedSkills); // click to deselect
                logger.info("Deselected: {}", skillName);
            }
        }
    }

    private Set<String> getSkillsToSelect(Set<String> currentlySelectedSkills, Set<String> newPreferredSkills) {
        Set<String> skillsToSelect = new HashSet<>(newPreferredSkills);
        skillsToSelect.removeAll(currentlySelectedSkills);
        logger.info("Skills to Select: {}", skillsToSelect);
        return skillsToSelect;
    }

    private Set<String> getSkillsToDeselect(Set<String> currentlySelectedSkills, Set<String> newPreferredSkills) {
        Set<String> skillsToDeselect = new HashSet<>(currentlySelectedSkills);
        skillsToDeselect.removeAll(newPreferredSkills);
        logger.info("Skills to Deselect: {}", skillsToDeselect);
        return skillsToDeselect;
    }

    private Set<String> getCurrentlySelectedSkills() {
        Set<String> currentlySelectedSkills = new HashSet<>();
        for (WebElement selectedSkills : jobTemplatePage.selectedSkills) {
            currentlySelectedSkills.add(BasePage.getText(selectedSkills).trim().toLowerCase()); // Convert to lowercase
        }
        logger.info("Currently Selected Skills: {}", currentlySelectedSkills);
        return currentlySelectedSkills;
    }

    private Set<String> getPreferredSkillsFromYml(String ymlFile, String header) {
        String[] preferredSkills = Objects.requireNonNull(DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile,
                header, YML_HEADER_GENDER_SKILLS, "Preferred Skills")).split(",");
        return Arrays.stream(preferredSkills)
                .map(String::trim)
                .map(String::toLowerCase) // Convert to lowercase for case-insensitive comparison
                .collect(Collectors.toSet());
    }

    private void selectGender(String ymlFile, String header) {
        String gender = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, header,
                YML_HEADER_GENDER_SKILLS, "Gender");
        BasePage.waitUntilElementPresent(jobTemplatePage.genderDropdown, 60);
        BasePage.clickWithJavaScript(jobTemplatePage.genderDropdown);
        By by = By.xpath(jobTemplatePage.getDropdownOptionXpath(gender));
        BasePage.waitUntilVisibilityOfElementLocated(by, 30);
        BasePage.scrollToWebElement(jobTemplatePage.getDropdownOptionXpath(gender));
        BasePage.clickWithJavaScript(jobTemplatePage.getDropdownOptionXpath(gender));
    }

    /**
     * Enter care provider/ site and service preference.
     *
     * @param ymlFile     YAML file name.
     * @param toggleKey     toggle button to be enabled or disabled.
     * @param toggleElement   toggle web element.
     */
    private void handleToggleOption(String ymlFile, String header, String toggleKey,
                                    WebElement toggleElement) {
        String toggleValue = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, header,
                YML_HEADER_JOB_DURATION, toggleKey);
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
                    enteringRecurrenceDetails(ymlFile, header);
                } else if (toggleKey.equalsIgnoreCase(BREAKS_INTERVALS)) {
                    // entering breaks details
                    enteringBreaksIntervalsDetails(ymlFile, header);
                }
            } else {
                // If the toggle is already enabled
                if (toggleKey.equalsIgnoreCase(ENABLE_RECURRENCE)) {
                    // entering recurrence details
                    enteringRecurrenceDetails(ymlFile, header);
                } else if (toggleKey.equalsIgnoreCase(BREAKS_INTERVALS)) {
                    // entering breaks details
                    enteringBreaksIntervalsDetails(ymlFile, header);
                }
            }
        } else {
            if (isCurrentlyEnabled) {
                // disabling the toggle
                BasePage.clickWithJavaScript(toggleElement);
            }
        }
    }

    private void enteringBreaksIntervalsDetails(String ymlFile, String header) {
        BasePage.waitUntilElementPresent(jobTemplatePage.paidBreaksDuration, 60);
        selectTime(ymlFile, header, "Paid Breaks Duration", jobTemplatePage.paidBreaksDuration,
                jobTemplatePage.paidBreaksDurationAreaList, jobTemplatePage.availablePaidBreaksDurations,
                jobTemplatePage.paidBreaksDurationOkButton);
        selectTime(ymlFile, header, "Unpaid Breaks Duration", jobTemplatePage.unpaidBreaksDuration,
                jobTemplatePage.unpaidBreaksDurationAreaList, jobTemplatePage.availableUnpaidBreaksDurations,
                jobTemplatePage.unpaidBreaksDurationOkButton);

        String paidBreaksNote = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, header,
                YML_HEADER_JOB_DURATION, "Paid Breaks Note");
        BasePage.clearAndEnterTexts(jobTemplatePage.paidBreaksNote, paidBreaksNote);

        String unpaidBreaksNote = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile,
                header, YML_HEADER_JOB_DURATION, "Unpaid Breaks Note");
        BasePage.clearAndEnterTexts(jobTemplatePage.unpaidBreaksNote, unpaidBreaksNote);
    }

    private void enteringRecurrenceDetails(String ymlFile, String header) {
        BasePage.waitUntilElementPresent(jobTemplatePage.repeatTypeDropdown, 60);
        BasePage.clickWithJavaScript(jobTemplatePage.repeatTypeDropdown);
        String repeatType = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, header,
                YML_HEADER_JOB_DURATION, "Repeat Type");
        BasePage.clickWithJavaScript(jobTemplatePage.getDropdownOptionXpath(repeatType));

        // click on the ends on date field
        BasePage.waitUntilElementClickable(jobTemplatePage.endsOn, 60);
        BasePage.clickWithJavaScript(jobTemplatePage.endsOn);
        String endsOn = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, header,
                YML_HEADER_JOB_DURATION, "Ends On");
        genericUtils.selectDateFromCalendarPopup(endsOn);
    }

    /**
     * Enter care provider/ site and service preference.
     *
     * @param ymlFile     YAML file name.
     *
     */
    private void enterCareProviderAndServicePreferences(String ymlFile, String header) {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<< Entering Care provider info >>>>>>>>>>>>>>>>>>>>>>>>>");
        String site = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, header,
                YML_TEMPLATE_SUB_HEADER_CARE_PROVIDER, "Site");
        BasePage.waitUntilElementClickable(jobTemplatePage.siteDropdown, 60);
        BasePage.clickWithJavaScript(jobTemplatePage.siteDropdown);
        List<WebElement> availableSites = jobTemplatePage.availableOptionList;
        // select correct site from the list
        for (WebElement siteElement : availableSites) {
            if (BasePage.getText(siteElement).equalsIgnoreCase(site)) {
                BasePage.clickWithJavaScript(siteElement);
                break;
            }
        }

        // select correct worker type
        String workerType = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, header,
                YML_TEMPLATE_SUB_HEADER_CARE_PROVIDER, "Worker Type");
        BasePage.waitUntilElementClickable(jobTemplatePage.workerTypeDropdown, 60);
        BasePage.clickWithJavaScript(jobTemplatePage.workerTypeDropdown);
        List<WebElement> availableWorkerTypes = jobTemplatePage.availableOptionList;
        // select correct worker type from the list
        for (WebElement workerTypeElement : availableWorkerTypes) {
            if (BasePage.getText(workerTypeElement).equalsIgnoreCase(workerType)) {
                BasePage.clickWithJavaScript(workerTypeElement);
                break;
            }
        }

        // select number of vacancies
        String numberOfVacancies = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, header,
                YML_TEMPLATE_SUB_HEADER_CARE_PROVIDER, "Number of Vacancies");
        BasePage.clickWithJavaScript(jobTemplatePage.numberOfVacanciesDropdown);
        List<WebElement> availableVacancies = jobTemplatePage.availableOptionList;
        for (WebElement vacancyElement : availableVacancies) {
            if (BasePage.getText(vacancyElement).equalsIgnoreCase(numberOfVacancies)) {
                BasePage.clickWithJavaScript(vacancyElement);
                break;
            }
        }
        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<< Care provider info entered >>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    private void selectTime(String ymlFile, String header, String timeKey, WebElement timeField, WebElement
                                    timeAreaList, List<WebElement> availableTimes, WebElement okButton) {
        String timeValue = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, header,
                YML_HEADER_JOB_DURATION, timeKey);
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

    private void selectJobType(String ymlFile, String header) {
        String jobType = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, header, "Job Type");
        assert jobType != null;
        if (jobType.equalsIgnoreCase("sleep in")) {
            BasePage.clickWithJavaScript(jobTemplatePage.sleepInButton);
        }
    }

    private void enterTemplateDetails(String ymlFile, String header, String subHeader) {
        BasePage.waitUntilElementClickable(jobTemplatePage.templateName, 30);
        String templateName = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, header,
                subHeader, "Template Name");
        BasePage.clearAndEnterTexts(jobTemplatePage.templateName, templateName);

        String templateDescription = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, header,
                subHeader, "Template Description");
        BasePage.clearAndEnterTexts(jobTemplatePage.templateDescription, templateDescription);
    }
}
