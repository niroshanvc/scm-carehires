package com.carehires.actions.jobs;


import com.carehires.pages.jobs.JobSummaryPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class JobSummaryActions {

    private final JobSummaryPage jobSummaryPage;

    private static final Logger logger = LogManager.getLogger(JobSummaryActions.class);

    private static final String ENTITY = "job";
    private static final String YML_FILE = "manage-job-template";
    private static final String YML_HEADER = "Job Preferences";


    public JobSummaryActions() {
        jobSummaryPage = new JobSummaryPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), jobSummaryPage);
        } catch (BasePage.WebDriverInitializationException e) {
            logger.error("Error while initializing Job Summary Page elements: {}", e.getMessage());
        }
    }

    public void enterJobSummary() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Posting a Job >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementClickable(jobSummaryPage.postJobButton, 60);
        BasePage.clickWithJavaScript(jobSummaryPage.postJobButton);

        BasePage.waitUntilElementClickable(jobSummaryPage.savePopupNoButton, 60);
        BasePage.clickWithJavaScript(jobSummaryPage.savePopupNoButton);
        verifyJobPostedSuccessfully();
    }

    private void verifyJobPostedSuccessfully() {
        BasePage.waitUntilElementPresent(jobSummaryPage.successMessage, 60);
        String actualInLowerCase = BasePage.getText(jobSummaryPage.successMessage).toLowerCase().trim();
        String expected = "Posted new job";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Job post did not work!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(jobSummaryPage.successMessage, 60);
    }

    public void clickOnEditButton() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Clicking on Job Posting Edit icon >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementClickable(jobSummaryPage.editIcon, 30);
        BasePage.scrollToWebElement(jobSummaryPage.editIcon);
        BasePage.clickWithJavaScript(jobSummaryPage.editIcon);
    }

    public void enterJobSummaryFromTemplate() {
        logger.info("<<<<<<<<<<<<<<<<<<<<< Posting a Job without directs to Template saving  popup>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementClickable(jobSummaryPage.postJobButton, 60);
        BasePage.clickWithJavaScript(jobSummaryPage.postJobButton);
        verifyJobPostedSuccessfully();
    }

    public void createTemplateWithNewJobPost() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Create new template with new job post >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementClickable(jobSummaryPage.postJobButton, 60);
        BasePage.clickWithJavaScript(jobSummaryPage.postJobButton);
        BasePage.waitUntilElementClickable(jobSummaryPage.savePopupYesButton, 60);
        BasePage.clickWithJavaScript(jobSummaryPage.savePopupYesButton);
        createNewTemplate();
        BasePage.waitUntilElementDisappeared(jobSummaryPage.successMessageTwo, 60);
        BasePage.genericWait(10000);
    }

    private void createNewTemplate() {
        BasePage.waitUntilElementClickable(jobSummaryPage.templateNameInput, 60);
        String name = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Template Name");
        String description = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER,
                "Template Description");
        BasePage.clearAndEnterTexts(jobSummaryPage.templateNameInput, name);
        BasePage.clearAndEnterTexts(jobSummaryPage.templateDescriptionTextarea, description);
        BasePage.waitUntilElementClickable(jobSummaryPage.templateDetailsPopupSaveButton, 60);
        BasePage.clickWithJavaScript(jobSummaryPage.templateDetailsPopupSaveButton);
    }
}
