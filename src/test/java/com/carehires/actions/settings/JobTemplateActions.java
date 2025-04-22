package com.carehires.actions.settings;

import com.carehires.pages.settings.JobTemplatePage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class JobTemplateActions {

    private final JobTemplatePage jobTemplatePage;

    private static final Logger logger = LogManager.getLogger(JobTemplateActions.class);

    private static final String ENTITY = "job";
    private static final String YML_FILE = "manage-job-template";
    private static final String YML_HEADER = "Job Preferences";


    public JobTemplateActions() {
        jobTemplatePage = new JobTemplatePage();
        try {
            PageFactory.initElements(BasePage.getDriver(), jobTemplatePage);
        } catch (BasePage.WebDriverInitializationException e) {
            logger.error("Error initializing JobTemplatePage: {}", e.getMessage());
        }
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
}
