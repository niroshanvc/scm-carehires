package com.carehires.actions.jobs;

import com.carehires.pages.jobs.JobPreferencesPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class JobPreferencesActions {

    private final JobPreferencesPage jobPreferencesPage;
    private static final String ENTITY = "job";
    private static final String YML_FILE = "job-create";
    private static final String YML_FILE_WITH_BREAKS = "job-create-with-breaks";
    private static final String YML_FILE_EDIT = "job-post-edit";
    private static final String YML_HEADER = "Job Preferences";
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
        selectPreferences(YML_FILE, YML_HEADER);
        enableDisableBlockBooking(YML_FILE, YML_HEADER);
        enterJobNotes(YML_FILE, YML_HEADER);
        BasePage.clickWithJavaScript(jobPreferencesPage.continueButton);
    }

    private void enterJobNotes(String ymlFile, String header) {
        String jobNotes = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, header, "Job Notes");
        BasePage.clearAndEnterTexts(jobPreferencesPage.notes, jobNotes);
    }

    private void enableDisableBlockBooking(String ymlFile, String header) {
        String enableBlockBooking = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, header, "Enable Block Booking");
        assert enableBlockBooking != null;
        if (enableBlockBooking.equalsIgnoreCase("yes")) {
            String currentAttr = BasePage.getAttributeValue(jobPreferencesPage.enableBlockBookingToggle, "aria-checked");
            boolean shouldEnable = enableBlockBooking.equalsIgnoreCase("yes");
            boolean isCurrentlyEnabled = currentAttr.equalsIgnoreCase("true");

            if (shouldEnable != isCurrentlyEnabled) {
                BasePage.scrollToWebElement(jobPreferencesPage.continueButton);
                BasePage.clickWithJavaScript(jobPreferencesPage.enableBlockBookingToggle);
                BasePage.genericWait(1000);
                String agency = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, header, "Agency");
                BasePage.waitUntilElementPresent(jobPreferencesPage.agencyDropdown, 60);
                BasePage.clickWithJavaScript(jobPreferencesPage.agencyDropdown);
                By by = By.xpath(jobPreferencesPage.getDropdownOptionXpath(agency));
                BasePage.waitUntilVisibilityOfElementLocated(by, 30);
                BasePage.scrollToWebElement(jobPreferencesPage.getDropdownOptionXpath(agency));
                BasePage.clickWithJavaScript(jobPreferencesPage.getDropdownOptionXpath(agency));

                BasePage.clickWithJavaScript(jobPreferencesPage.nameInput);
                BasePage.genericWait(2000);
                BasePage.waitUntilElementPresent(jobPreferencesPage.workersList.get(0), 30);
                BasePage.clickWithJavaScript(jobPreferencesPage.workersList.get(0));

                BasePage.waitUntilElementClickable(jobPreferencesPage.addWorkerButton, 60);
                BasePage.clickWithJavaScript(jobPreferencesPage.addWorkerButton);
                BasePage.waitUntilElementClickable(jobPreferencesPage.removeWorkerIcon, 60);
            }
        }
    }

    private void selectPreferences(String ymlFile, String header) {
        String[] preferredSkills = Objects.requireNonNull(DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile,
                header, "Preferred Skills")).split(",");

        // Trim spaces and convert to a Set for easy comparison
        Set<String> newPreferredSkills = Arrays.stream(preferredSkills)
                .map(String::trim)
                .collect(Collectors.toSet());

        BasePage.genericWait(3000);

        // Get currently selected skills
        Set<String> currentlySelectedSkills = new HashSet<>();
        /*for (WebElement selectedSkills : jobPreferencesPage.selectedSkills) {

        }*/
        for (String skill : preferredSkills) {
            // Refresh the WebElement list to avoid stale references
            List<WebElement> availableSkills = jobPreferencesPage.preferredSkills;
            for (WebElement el : availableSkills) {
                if (BasePage.getText(el).equalsIgnoreCase(skill)) {
                    BasePage.clickWithJavaScript(el);
                    break;
                }
            }
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
        selectPreferences(YML_FILE_WITH_BREAKS, YML_HEADER);
        enableDisableBlockBooking(YML_FILE_WITH_BREAKS, YML_HEADER);
        enterJobNotes(YML_FILE_WITH_BREAKS, YML_HEADER);
        BasePage.clickWithJavaScript(jobPreferencesPage.continueButton);
    }

    public void enterJobPreferencesData() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Job Preferences Data to test Edit Job Posting >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        selectGender(YML_FILE_EDIT, YML_HEADER);
        selectPreferences(YML_FILE_EDIT, YML_HEADER);
        enableDisableBlockBooking(YML_FILE_EDIT, YML_HEADER);
        enterJobNotes(YML_FILE_EDIT, YML_HEADER);
        BasePage.clickWithJavaScript(jobPreferencesPage.continueButton);
    }

    public void editPreferences() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Edit Job Preferences >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        selectGender(YML_FILE_EDIT, YML_HEADER_EDIT);
        selectPreferences(YML_FILE_EDIT, YML_HEADER_EDIT);
        enableDisableBlockBooking(YML_FILE_EDIT, YML_HEADER_EDIT);
        enterJobNotes(YML_FILE_EDIT, YML_HEADER_EDIT);
        BasePage.clickWithJavaScript(jobPreferencesPage.continueButton);
    }
}
