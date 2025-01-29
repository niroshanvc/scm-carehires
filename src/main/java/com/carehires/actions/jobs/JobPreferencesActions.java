package com.carehires.actions.jobs;

import com.carehires.pages.jobs.JobPreferencesPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.Objects;

public class JobPreferencesActions {

    private final JobPreferencesPage jobPreferencesPage;
    private static final String ENTITY = "job";
    private static final String YML_FILE = "job-create";
    private static final String YML_HEADER = "Job Preferences";

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

        String gender = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Gender");
        BasePage.clickWithJavaScript(jobPreferencesPage.genderDropdown);
        By by = By.xpath(jobPreferencesPage.getDropdownOptionXpath(gender));
        BasePage.waitUntilVisibilityOfElementLocated(by, 30);
        BasePage.scrollToWebElement(jobPreferencesPage.getDropdownOptionXpath(gender));
        BasePage.clickWithJavaScript(jobPreferencesPage.getDropdownOptionXpath(gender));

        String[] preferredSkills = Objects.requireNonNull(DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE,
                YML_HEADER, "Preferred Skills")).split(",");

        BasePage.genericWait(3000);
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

        String enableBlockBooking = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Enable Block Booking");
        assert enableBlockBooking != null;
        if (enableBlockBooking.equalsIgnoreCase("yes")) {
            String currentAttr = BasePage.getAttributeValue(jobPreferencesPage.enableBlockBookingToggle, "aria-checked");
            boolean shouldEnable = enableBlockBooking.equalsIgnoreCase("yes");
            boolean isCurrentlyEnabled = currentAttr.equalsIgnoreCase("false");

            if (shouldEnable != isCurrentlyEnabled) {
                BasePage.clickWithJavaScript(jobPreferencesPage.enableBlockBookingToggle);
                String agency = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Agency");
                BasePage.waitUntilElementPresent(jobPreferencesPage.agencyDropdown, 60);
                by = By.xpath(jobPreferencesPage.getDropdownOptionXpath(agency));
                BasePage.waitUntilVisibilityOfElementLocated(by, 30);
                BasePage.scrollToWebElement(jobPreferencesPage.getDropdownOptionXpath(agency));
                BasePage.clickWithJavaScript(jobPreferencesPage.getDropdownOptionXpath(agency));

                BasePage.clickWithJavaScript(jobPreferencesPage.nameInput);
                BasePage.waitUntilElementPresent(jobPreferencesPage.workersList.get(0), 30);
                BasePage.clickWithJavaScript(jobPreferencesPage.workersList.get(0));

                BasePage.waitUntilElementClickable(jobPreferencesPage.addWorkerButton, 60);
                BasePage.clickWithJavaScript(jobPreferencesPage.addWorkerButton);
            }
        }
        String jobNotes = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Job Notes");
        BasePage.clearAndEnterTexts(jobPreferencesPage.notes, jobNotes);

        BasePage.clickWithJavaScript(jobPreferencesPage.continueButton);
    }
}
