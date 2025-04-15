package com.carehires.actions.jobs;


import com.carehires.pages.jobs.JobPreferencesPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class JobPreferencesActions {

    private final JobPreferencesPage jobPreferencesPage;
    private static final String ENTITY = "job";
    private static final String YML_FILE = "job-create";
    private static final String YML_FILE_WITH_BREAKS = "job-create-with-breaks";
    private static final String YML_FILE_EDIT = "job-post-edit";
    private static final String YML_FILE_SCENARIO1 = "scenario - job post";
    private static final String YML_FILE_SLEEP_IN_SCENARIO1 = "sleep in scenario - job post";
    private static final String YML_FILE_BLOCK_BOOKING = "job-create-block-booking";
    private static final String YML_FILE_MANAGE_TIMESHEET = "manage-timesheet";
    private static final String YML_FILE_CANCELLATION = "job-cancellation";
    private static final String YML_HEADER = "Job Preferences";
    private static final String YML_HEADER1 = "Job Preferences A";
    private static final String YML_HEADER2 = "Job Preferences B";
    private static final String YML_HEADER3 = "Job Preferences C";
    private static final String YML_HEADER_SCENARIO1B = "Job Preferences Scenario1B";
    private static final String YML_HEADER_SCENARIO1C = "Job Preferences Scenario1C";
    private static final String YML_HEADER_SCENARIO1E = "Job Preferences Scenario1E";
    private static final String YML_HEADER_SCENARIO1F = "Job Preferences Scenario1F";
    private static final String YML_HEADER_SCENARIO1G = "Job Preferences Scenario1G";
    private static final String YML_HEADER_EDIT = "Edit Job Preferences";

    private static final Logger logger = LogManager.getLogger(JobPreferencesActions.class);

    public JobPreferencesActions() {
        jobPreferencesPage = new JobPreferencesPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), jobPreferencesPage);
        } catch (BasePage.WebDriverInitializationException e) {
            logger.error("Error while initializing Job Preferences Page elements: {}", e.getMessage());
        }
    }
    public void enterJobPreferences() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Job Preferences >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();

        selectGender(YML_FILE, YML_HEADER);
        selectPreferences(YML_FILE, YML_HEADER, false);
        enableDisableBlockBooking(YML_FILE, YML_HEADER);
        enterJobNotes(YML_FILE, YML_HEADER);
        BasePage.clickWithJavaScript(jobPreferencesPage.continueButton);
    }

    private void enterJobNotes(String ymlFile, String header) {
        String jobNotes = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, header, "Job Notes");
        for (int i = 0; i< Objects.requireNonNull(jobNotes).length(); i++) {
            char c = jobNotes.charAt(i);
            String s = String.valueOf(c);
            BasePage.sendKeys(jobPreferencesPage.notes, s);
        }
    }

    private void enableDisableBlockBooking(String ymlFile, String header) {
        String enableBlockBooking = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, header,
                "Enable Block Booking");
        assert enableBlockBooking != null;
        String currentAttr = BasePage.getAttributeValue(jobPreferencesPage.enableBlockBookingToggle,
                "aria-checked");
        boolean shouldEnable = enableBlockBooking.equalsIgnoreCase("yes");
        boolean isCurrentlyEnabled = currentAttr.equalsIgnoreCase("true");

        if (shouldEnable != isCurrentlyEnabled) {
            BasePage.scrollToWebElement(jobPreferencesPage.continueButton);
            BasePage.clickWithJavaScript(jobPreferencesPage.enableBlockBookingToggle);
            BasePage.genericWait(1000);

            if (shouldEnable) {
                // Retrieve the latest agency increment value
                int agencyIncrementValue = DataConfigurationReader.getCurrentIncrementValue("agency");

                // Read agency name from YAML and replace <agencyIncrement> placeholder
                String agencyTemplate = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, header,
                        "Agency");
                assert agencyTemplate != null;
                String agency = agencyTemplate.replace("<agencyIncrement>", String.valueOf(
                        agencyIncrementValue));
                agency = agency.replace("\"", "").trim();

                BasePage.waitUntilElementPresent(jobPreferencesPage.agencyDropdown, 60);
                BasePage.clickWithJavaScript(jobPreferencesPage.agencyDropdown);
                By by = By.xpath(jobPreferencesPage.getDropdownOptionXpath(agency));
                BasePage.waitUntilVisibilityOfElementLocated(by, 30);
                BasePage.scrollToWebElement(jobPreferencesPage.getDropdownOptionXpath(agency));
                BasePage.clickWithJavaScript(jobPreferencesPage.getDropdownOptionXpath(agency));

                BasePage.clickWithJavaScript(jobPreferencesPage.nameInput);
                BasePage.genericWait(2000);
                if (!jobPreferencesPage.workersList.isEmpty()) {
                    BasePage.waitUntilElementPresent(jobPreferencesPage.workersList.get(0), 30);
                    BasePage.clickWithJavaScript(jobPreferencesPage.workersList.get(0));

                    BasePage.waitUntilElementClickable(jobPreferencesPage.addWorkerButton, 60);
                    BasePage.clickWithJavaScript(jobPreferencesPage.addWorkerButton);
                    BasePage.waitUntilElementClickable(jobPreferencesPage.removeWorkerIcon, 60);
                } else {
                    logger.warn("Workers list is empty, cannot select a worker.");
                }
            }
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

    private Set<String> getPreferredSkillsFromYml(String ymlFile, String header) {
        String[] preferredSkills = Objects.requireNonNull(DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile,
                header, "Preferred Skills")).split(",");
        return Arrays.stream(preferredSkills)
                .map(String::trim)
                .map(String::toLowerCase) // Convert to lowercase for case-insensitive comparison
                .collect(Collectors.toSet());
    }

    private Set<String> getCurrentlySelectedSkills() {
        Set<String> currentlySelectedSkills = new HashSet<>();
        for (WebElement selectedSkills : jobPreferencesPage.selectedSkills) {
            currentlySelectedSkills.add(BasePage.getText(selectedSkills).trim().toLowerCase()); // Convert to lowercase
        }
        logger.info("Currently Selected Skills: {}", currentlySelectedSkills);
        return currentlySelectedSkills;
    }

    private Set<String> getSkillsToDeselect(Set<String> currentlySelectedSkills, Set<String> newPreferredSkills) {
        Set<String> skillsToDeselect = new HashSet<>(currentlySelectedSkills);
        skillsToDeselect.removeAll(newPreferredSkills);
        logger.info("Skills to Deselect: {}", skillsToDeselect);
        return skillsToDeselect;
    }

    private Set<String> getSkillsToSelect(Set<String> currentlySelectedSkills, Set<String> newPreferredSkills) {
        Set<String> skillsToSelect = new HashSet<>(newPreferredSkills);
        skillsToSelect.removeAll(currentlySelectedSkills);
        logger.info("Skills to Select: {}", skillsToSelect);
        return skillsToSelect;
    }

    private void deselectSkills(Set<String> skillsToDeselect) {
        for (WebElement selectedSkills : jobPreferencesPage.selectedSkills) {
            String skillName = BasePage.getText(selectedSkills).trim().toLowerCase(); // Convert to lowercase
            if (skillsToDeselect.contains(skillName)) {
                BasePage.clickWithJavaScript(selectedSkills); // click to deselect
                logger.info("Deselected: {}", skillName);
            }
        }
    }

    private void selectNewSkills(Set<String> skillsToSelect) {
        for (String skillToSelect : skillsToSelect) {
            boolean skillFound = false;
            BasePage.genericWait(3000);
            for (WebElement availableSkill : jobPreferencesPage.preferredSkills) {
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
                        availableSkill = jobPreferencesPage.preferredSkills.stream()
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

    private void verifyFinalSelections(Set<String> newPreferredSkills) {
        BasePage.genericWait(2000); // Wait for selections to complete
        Set<String> finalSelectedSkills = new HashSet<>();
        for (WebElement selectedSkills : jobPreferencesPage.selectedSkills) {
            finalSelectedSkills.add(BasePage.getText(selectedSkills).trim().toLowerCase()); // Convert to lowercase
        }
        logger.info("Final Selected Skills: {}", finalSelectedSkills);

        if (!finalSelectedSkills.equals(newPreferredSkills)) {
            throw new IllegalStateException("Mismatch in skills selection. Expected: " + newPreferredSkills + ", " +
                    "but found: " + finalSelectedSkills);
        }
    }

    private void selectGender(String ymlFile, String header) {
        String gender = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, header, "Gender");
        BasePage.clickWithJavaScript(jobPreferencesPage.genderDropdown);
        By by = By.xpath(jobPreferencesPage.getDropdownOptionXpath(gender));
        BasePage.waitUntilVisibilityOfElementLocated(by, 30);
        BasePage.scrollToWebElement(jobPreferencesPage.getDropdownOptionXpath(gender));
        BasePage.clickWithJavaScript(jobPreferencesPage.getDropdownOptionXpath(gender));
    }

    public void enterJobPreferencesAndEnablingBlockBooking() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Job Preferences and Enable Block Booking >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        selectGender(YML_FILE_WITH_BREAKS, YML_HEADER);
        selectPreferences(YML_FILE_WITH_BREAKS, YML_HEADER, false);
        enableDisableBlockBooking(YML_FILE_WITH_BREAKS, YML_HEADER);
        enterJobNotes(YML_FILE_WITH_BREAKS, YML_HEADER);
        BasePage.clickWithJavaScript(jobPreferencesPage.continueButton);
    }

    public void enterJobPreferencesData() {
        logger.info("<<<<<<<<<<<<<<<<<<<<< Entering Job Preferences Data to test Edit Job Posting >>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        selectGender(YML_FILE_EDIT, YML_HEADER);
        selectPreferences(YML_FILE_EDIT, YML_HEADER, false);
        enableDisableBlockBooking(YML_FILE_EDIT, YML_HEADER);
        enterJobNotes(YML_FILE_EDIT, YML_HEADER);
        BasePage.clickWithJavaScript(jobPreferencesPage.continueButton);
    }

    public void editPreferences() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Edit Job Preferences >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        selectGender(YML_FILE_EDIT, YML_HEADER_EDIT);
        selectPreferences(YML_FILE_EDIT, YML_HEADER_EDIT, true);
        enableDisableBlockBooking(YML_FILE_EDIT, YML_HEADER_EDIT);
        enterJobNotes(YML_FILE_EDIT, YML_HEADER_EDIT);
        BasePage.clickWithJavaScript(jobPreferencesPage.continueButton);
    }

    public void enterJobPreferencesWithJobNoteAndEnablingBlockBooking() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering with Job Note and Enable Block Booking >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        selectGender(YML_FILE_SCENARIO1, YML_HEADER_SCENARIO1B);
        selectPreferences(YML_FILE_SCENARIO1, YML_HEADER_SCENARIO1B, false);
        enableDisableBlockBooking(YML_FILE_SCENARIO1, YML_HEADER_SCENARIO1B);
        enterJobNotes(YML_FILE_SCENARIO1, YML_HEADER_SCENARIO1B);
        BasePage.clickWithJavaScript(jobPreferencesPage.continueButton);
    }

    public void enterWithSkillsAndWithoutJobNoteAndDisablingBlockBooking() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Skills but no Job Note and Block Booking >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        selectGender(YML_FILE_SCENARIO1, YML_HEADER_SCENARIO1B);
        selectPreferences(YML_FILE_SCENARIO1, YML_HEADER_SCENARIO1E, false);
        enableDisableBlockBooking(YML_FILE_SCENARIO1, YML_HEADER_SCENARIO1E);
        BasePage.clickWithJavaScript(jobPreferencesPage.continueButton);
    }

    public void enterWithSkillsAndEnablingBlockBookingButNoNote() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Skills with Block Booking but no Note >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        selectGender(YML_FILE_SCENARIO1, YML_HEADER_SCENARIO1B);
        selectPreferences(YML_FILE_SCENARIO1, YML_HEADER_SCENARIO1E, false);
        enableDisableBlockBooking(YML_FILE_SCENARIO1, YML_HEADER_SCENARIO1B);
        BasePage.clickWithJavaScript(jobPreferencesPage.continueButton);
    }

    public void clickContinueButtonAndRemovingJobNote() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<< Just click on Continue button and remove Job Note >>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.clearTexts(jobPreferencesPage.notes);
        BasePage.clickWithJavaScript(jobPreferencesPage.continueButton);
    }

    public void clickContinueButton() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<< Just click on Continue button >>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.clickWithJavaScript(jobPreferencesPage.continueButton);
    }

    public void noNoteAndDisablingBlockBooking() {
        logger.info("<<<<<<<<<<<<<<<< Entering Job Preferences without Note and disabling Block Booking >>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        selectGender(YML_FILE_SCENARIO1, YML_HEADER);
        enableDisableBlockBooking(YML_FILE_SCENARIO1, YML_HEADER);
        BasePage.clearTextsUsingSendKeys(jobPreferencesPage.notes);
        BasePage.clickWithJavaScript(jobPreferencesPage.continueButton);
    }

    public void clearJobNote() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<< Entering Job Preferences without Note >>>>>>>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        selectGender(YML_FILE_SCENARIO1, YML_HEADER);
        BasePage.clearTextsUsingSendKeys(jobPreferencesPage.notes);
        BasePage.clickWithJavaScript(jobPreferencesPage.continueButton);
    }

    public void customJobNoNoteAndDisablingBlockBooking() {
        logger.info("<<<<<<<<<<<<<<<< Custom Job without Note and disabling Block Booking >>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        selectGender(YML_FILE_SCENARIO1, YML_HEADER_SCENARIO1B);
        enableDisableBlockBooking(YML_FILE_SCENARIO1, YML_HEADER);
        BasePage.clearTextsUsingSendKeys(jobPreferencesPage.notes);
        BasePage.clickWithJavaScript(jobPreferencesPage.continueButton);
    }

    public void selectSkillsWithoutBlockBookingAndNote() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Custom Job with Skills only >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        selectGender(YML_FILE_SLEEP_IN_SCENARIO1, YML_HEADER_SCENARIO1B);
        selectPreferences(YML_FILE_SLEEP_IN_SCENARIO1, YML_HEADER_SCENARIO1B, true);
        enableDisableBlockBooking(YML_FILE_SLEEP_IN_SCENARIO1, YML_HEADER_SCENARIO1B);
        BasePage.waitUntilElementClickable(jobPreferencesPage.continueButton, 30);
        BasePage.clickWithJavaScript(jobPreferencesPage.continueButton);
    }

    public void selectSkillsWithBlockBookingAndNote() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<< Custom Job with Skills and Block Booking >>>>>>>>>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        selectGender(YML_FILE_SLEEP_IN_SCENARIO1, YML_HEADER_SCENARIO1C);
        selectPreferences(YML_FILE_SLEEP_IN_SCENARIO1, YML_HEADER_SCENARIO1C, true);
        enableDisableBlockBooking(YML_FILE_SLEEP_IN_SCENARIO1, YML_HEADER_SCENARIO1C);
        BasePage.waitUntilElementClickable(jobPreferencesPage.continueButton, 30);
        BasePage.clickWithJavaScript(jobPreferencesPage.continueButton);
    }

    public void selectGenderAndClickContinue() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<< Select gender and click on Continue button >>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        selectGender(YML_FILE_SLEEP_IN_SCENARIO1, YML_HEADER_SCENARIO1F);
        BasePage.waitUntilElementClickable(jobPreferencesPage.continueButton, 30);
        BasePage.clickWithJavaScript(jobPreferencesPage.continueButton);
    }

    public void clearJobNoteAndDisableBlockBooking() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<< Clear job note and disabling block booking >>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        selectGender(YML_FILE_SLEEP_IN_SCENARIO1, YML_HEADER_SCENARIO1G);
        enableDisableBlockBooking(YML_FILE_SLEEP_IN_SCENARIO1, YML_HEADER_SCENARIO1G);
        BasePage.clearTextsUsingSendKeys(jobPreferencesPage.notes);
        BasePage.waitUntilElementClickable(jobPreferencesPage.continueButton, 30);
        BasePage.clickWithJavaScript(jobPreferencesPage.continueButton);
    }

    public void clearJobNoteAndEnableBlockBooking() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<< Clear job note and enabling block booking >>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        selectGender(YML_FILE_SLEEP_IN_SCENARIO1, YML_HEADER_SCENARIO1G);
        BasePage.clearTextsUsingSendKeys(jobPreferencesPage.notes);
        BasePage.waitUntilElementClickable(jobPreferencesPage.continueButton, 30);
        BasePage.clickWithJavaScript(jobPreferencesPage.continueButton);
    }

    public void continueWithJobNote() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<< Enter job note and click on Continue button >>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.clearTextsUsingSendKeys(jobPreferencesPage.notes);
        enterJobNotes(YML_FILE_SLEEP_IN_SCENARIO1, YML_HEADER_SCENARIO1E);
        BasePage.waitUntilElementClickable(jobPreferencesPage.continueButton, 30);
        BasePage.clickWithJavaScript(jobPreferencesPage.continueButton);
    }

    public void continueWithEveryOptions() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<< Enter job note, block booking and skills >>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        selectGender(YML_FILE_SLEEP_IN_SCENARIO1, YML_HEADER_SCENARIO1C);
        selectPreferences(YML_FILE_SLEEP_IN_SCENARIO1, YML_HEADER_SCENARIO1C, true);
        enableDisableBlockBooking(YML_FILE_SLEEP_IN_SCENARIO1, YML_HEADER_SCENARIO1C);
        enterJobNotes(YML_FILE_SLEEP_IN_SCENARIO1, YML_HEADER_SCENARIO1C);
        BasePage.waitUntilElementClickable(jobPreferencesPage.continueButton, 30);
        BasePage.clickWithJavaScript(jobPreferencesPage.continueButton);
    }

    public void blockBookingWithSkills() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<< Enter block booking without worker >>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        selectGender(YML_FILE_BLOCK_BOOKING, YML_HEADER);
        selectPreferences(YML_FILE_BLOCK_BOOKING, YML_HEADER, false);
        enableBlockBookingWithoutWorker(YML_FILE_BLOCK_BOOKING, YML_HEADER);
        BasePage.waitUntilElementClickable(jobPreferencesPage.continueButton, 30);
        BasePage.clickWithJavaScript(jobPreferencesPage.continueButton);
    }

    private void enableBlockBookingWithoutWorker(String ymlFile, String header) {
        String enableBlockBooking = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, header,
                "Enable Block Booking");
        assert enableBlockBooking != null;
        String currentAttr = BasePage.getAttributeValue(jobPreferencesPage.enableBlockBookingToggle,
                "aria-checked");
        boolean shouldEnable = enableBlockBooking.equalsIgnoreCase("yes");
        boolean isCurrentlyEnabled = currentAttr.equalsIgnoreCase("true");

        if (shouldEnable != isCurrentlyEnabled) {
            BasePage.scrollToWebElement(jobPreferencesPage.continueButton);
            BasePage.clickWithJavaScript(jobPreferencesPage.enableBlockBookingToggle);
            BasePage.genericWait(1000);

            if (shouldEnable) {
                // Retrieve the latest agency increment value
                int agencyIncrementValue = DataConfigurationReader.getCurrentIncrementValue("agency");

                // Read agency name from YAML and replace <agencyIncrement> placeholder
                String agencyTemplate = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, header,
                        "Agency");
                assert agencyTemplate != null;
                String agency = agencyTemplate.replace("<agencyIncrement>", String.valueOf(
                        agencyIncrementValue));
                agency = agency.replace("\"", "").trim();

                BasePage.waitUntilElementPresent(jobPreferencesPage.agencyDropdown, 60);
                BasePage.clickWithJavaScript(jobPreferencesPage.agencyDropdown);
                By by = By.xpath(jobPreferencesPage.getDropdownOptionXpath(agency));
                BasePage.waitUntilVisibilityOfElementLocated(by, 30);
                BasePage.scrollToWebElement(jobPreferencesPage.getDropdownOptionXpath(agency));
                BasePage.clickWithJavaScript(jobPreferencesPage.getDropdownOptionXpath(agency));
            }
        }
    }

    public void noReasonNoInternalNotes() {
        logger.info("<<<<<<<<<<<<<<< Enter preferences without posting reason and without internal note >>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        selectGender(YML_FILE, YML_HEADER1);
        selectPreferences(YML_FILE, YML_HEADER1, false);
        enableDisableBlockBooking(YML_FILE, YML_HEADER1);
        enterJobNotes(YML_FILE, YML_HEADER1);
        BasePage.clickWithJavaScript(jobPreferencesPage.continueButton);
    }

    public void withReasonAndInternalNotes() {
        logger.info("<<<<<<<<<<<<<<< Enter preferences with posting reason and with internal note >>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        selectGender(YML_FILE, YML_HEADER2);
        selectPreferences(YML_FILE, YML_HEADER2, false);
        enableDisableBlockBooking(YML_FILE, YML_HEADER2);
        enterJobNotes(YML_FILE, YML_HEADER2);
        selectJobPostingReason(YML_FILE, YML_HEADER2);
        enterInternalNotes(YML_FILE, YML_HEADER2);
        BasePage.clickWithJavaScript(jobPreferencesPage.continueButton);
    }

    private void selectJobPostingReason(String ymlFile, String header) {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<< Enter job posting reason >>>>>>>>>>>>>>>>>>>>>>>>>");
        String reason = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, header,
                "Job Posting Reason");
        BasePage.clickWithJavaScript(jobPreferencesPage.jobPostingReasonDropdown);
        By by = By.xpath(jobPreferencesPage.getDropdownOptionXpath(reason));
        BasePage.waitUntilVisibilityOfElementLocated(by, 30);
        BasePage.scrollToWebElement(jobPreferencesPage.getDropdownOptionXpath(reason));
        BasePage.clickWithJavaScript(jobPreferencesPage.getDropdownOptionXpath(reason));
    }

    private void enterInternalNotes(String ymlFile, String header) {
        String internalNotes = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, header,
                "Internal Notes");
        BasePage.clearTexts(jobPreferencesPage.internalNotes);
        for (int i = 0; i < Objects.requireNonNull(internalNotes).length(); i++) {
            char c = internalNotes.charAt(i);
            String s = String.valueOf(c);
            BasePage.sendKeys(jobPreferencesPage.internalNotes, s);
        }
    }

    public void withBothReasonAndInternalNotes() {
        logger.info("<<<<<<<<<<<<<<< Enter preferences with both posting reason and internal note >>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        selectGender(YML_FILE, YML_HEADER3);
        selectPreferences(YML_FILE, YML_HEADER3, false);
        enableDisableBlockBooking(YML_FILE, YML_HEADER3);
        enterJobNotes(YML_FILE, YML_HEADER3);
        selectJobPostingReason(YML_FILE, YML_HEADER3);
        enterInternalNotes(YML_FILE, YML_HEADER3);
        BasePage.clickWithJavaScript(jobPreferencesPage.continueButton);
    }

    public void enterJobPreferencesForSleepIn() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Enter preferences for sleep in job >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        selectGender(YML_FILE_MANAGE_TIMESHEET, YML_HEADER);
        selectPreferences(YML_FILE_MANAGE_TIMESHEET, YML_HEADER, false);
        enableDisableBlockBooking(YML_FILE_MANAGE_TIMESHEET, YML_HEADER);
        enterJobNotes(YML_FILE_MANAGE_TIMESHEET, YML_HEADER);
        BasePage.clickWithJavaScript(jobPreferencesPage.continueButton);
    }

    public void generalJobPreferences() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Job Preferences - General Job >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        selectGender(YML_FILE_MANAGE_TIMESHEET, YML_HEADER2);
        selectPreferences(YML_FILE_MANAGE_TIMESHEET, YML_HEADER2, false);
        enableDisableBlockBooking(YML_FILE_MANAGE_TIMESHEET, YML_HEADER2);
        enterJobNotes(YML_FILE_MANAGE_TIMESHEET, YML_HEADER2);
        BasePage.clickWithJavaScript(jobPreferencesPage.continueButton);
    }

    public void enterJobPreferencesForCancellation() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Job Preferences for Cancellation >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        selectGender(YML_FILE_CANCELLATION, YML_HEADER);
        selectPreferences(YML_FILE_CANCELLATION, YML_HEADER, false);
        enableDisableBlockBooking(YML_FILE_CANCELLATION, YML_HEADER);
        enterJobNotes(YML_FILE_CANCELLATION, YML_HEADER);
        BasePage.clickWithJavaScript(jobPreferencesPage.continueButton);
    }
}
