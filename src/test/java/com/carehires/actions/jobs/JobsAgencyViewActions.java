package com.carehires.actions.jobs;

import com.carehires.pages.jobs.JobsAgencyViewPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class JobsAgencyViewActions {

    private final JobsAgencyViewPage agencyView;

    private static final Logger logger = LogManager.getLogger(JobsAgencyViewActions.class);

    private static final String ENTITY = "job";
    private static final String YML_FILE = "job-view";
    private static final String YML_FILE_CREATE = "job-create";
    private static final String YML_HEADER_JOB_PREFERENCES = "Job Preferences";
    private static final String YML_HEADER_JOB_CANCEL = "Cancel Job";

    public JobsAgencyViewActions() {
        agencyView = new JobsAgencyViewPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), agencyView);
        } catch (BasePage.WebDriverInitializationException e) {
            logger.error("Error while initializing Job's Agency View Page elements: {}", e.getMessage());
        }
    }

    public void moveToAgencyView() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<< Moving to Agency View section >>>>>>>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementClickable(agencyView.agencyView, 60);
        BasePage.clickWithJavaScript(agencyView.agencyView);
        BasePage.waitUntilElementClickable(agencyView.agencyDropdown, 60);
        BasePage.clickWithJavaScript(agencyView.agencyDropdown);

        String agency = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_CREATE,
                YML_HEADER_JOB_PREFERENCES, "Agency");
        By by = By.xpath(agencyView.getDropdownOptionXpath(agency));
        BasePage.waitUntilVisibilityOfElementLocated(by, 30);
        BasePage.scrollToWebElement(agencyView.getDropdownOptionXpath(agency));
        BasePage.clickWithJavaScript(agencyView.getDropdownOptionXpath(agency));
        BasePage.clickTabKey(agencyView.agencyDropdown);
    }

    public void viewJobPost() {
        List<WebElement> previousDates = agencyView.previousDateList;
        int mostRecentDay = previousDates.size();
        By by = By.xpath(agencyView.getMostRecentJobPost(mostRecentDay));
        BasePage.clickWithJavaScript(BasePage.findListOfWebElements(by).get(0));
    }

    public void cancelJob() {
        clickOnCancelJob();
        String reason = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER_JOB_CANCEL,
                "Reason");
        String description = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER_JOB_CANCEL,
                "Description");
        confirmingCancelJob(reason, description);
        verifyCancelJobFunctionality();
    }

    private void verifyCancelJobFunctionality() {
        logger.info("<<<<<<<<<<<<<<<<<<<< Verifying Cancel Job functionality on Job Detail popup >>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementPresent(agencyView.successMessage, 30);
        String actualInLowerCase = BasePage.getText(agencyView.successMessage).toLowerCase().trim();
        String expected = "Cancelled succesfully";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Job cancellation is not working!", actualInLowerCase, containsString(
                expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(agencyView.successMessage, 20);
    }

    private void confirmingCancelJob(String reason, String description) {
        logger.info("<<<<<<<<<<<<<<<<<<<< Confirming Cancel Job on Cancel Job popup >>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementClickable(agencyView.reasonDropdownButtonOnCancelJobPopup, 30);
        BasePage.clickWithJavaScript(agencyView.reasonDropdownButtonOnCancelJobPopup);
        BasePage.genericWait(1000);
        By by = By.xpath(agencyView.getDropdownOptionXpath(reason));
        BasePage.waitUntilVisibilityOfElementLocated(by, 30);
        BasePage.scrollToWebElement(agencyView.getDropdownOptionXpath(reason));
        BasePage.clickWithJavaScript(agencyView.getDropdownOptionXpath(reason));

        if (reason.equalsIgnoreCase("Other")) {
            BasePage.waitUntilElementDisplayed(agencyView.descriptionTextAreaOnCancelJobsPopup, 30);
            BasePage.clearAndEnterTexts(agencyView.descriptionTextAreaOnCancelJobsPopup, description);
        }

        BasePage.waitUntilElementClickable(agencyView.continueButtonOnCancelJobPopup, 30);
        BasePage.clickWithJavaScript(agencyView.continueButtonOnCancelJobPopup);

        BasePage.waitUntilElementClickable(agencyView.cancelJobButton, 30);
        BasePage.clickWithJavaScript(agencyView.cancelJobButton);
    }

    private void clickOnCancelJob() {
        logger.info("<<<<<<<<<<<<<<<< Move to three dots and Click on Cancel Job >>>>>>>>>>>>>");
        BasePage.waitUntilElementClickable(JobsAgencyViewPage.jobDetailsPopupThreeDots, 30);
        BasePage.mouseHoverAndClick(JobsAgencyViewPage.jobDetailsPopupThreeDots, JobsAgencyViewPage.
                jobDetailsPopupCancelJob);
    }
}
