package com.carehires.actions.workers;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.carehires.pages.worker.ViewWorkerProfilePage;
import com.carehires.utils.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ViewWorkerProfileActions {

    ViewWorkerProfilePage profilePage;

    private static final Logger logger = LogManager.getFormatterLogger(ViewWorkerProfileActions.class);

    public ViewWorkerProfileActions() {
        profilePage = new ViewWorkerProfilePage();
        try {
            PageFactory.initElements(BasePage.getDriver(), profilePage);
        } catch (BasePage.WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
    }

    public void verifyProfileStatus(String status) {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying the profile status as %s >>>>>>>>>>>>>>>>>>>>", status);
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(profilePage.profileStatus, 60);
        String actual = BasePage.getText(profilePage.profileStatus).toLowerCase().trim();
        String expected = status.toLowerCase();
        assertThat("Provider profile is not valid", actual, is(expected));
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("extentReports.html");
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        ExtentTest test = extent.createTest("Worker Profile Test", "Testing worker profile status");
        test.log(Status.INFO, "Verified worker profile status");
        extent.flush();
    }

    public void acceptAllCompliance() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Accepting all compliance >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        openViewComplianceWidget();
        acceptCompliance();
        verifyComplianceSavedSuccessMessage();
        verifyGeneralComplianceMessageAfterAcceptingTheCompliance();
    }

    private void openViewComplianceWidget() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying General Compliance Message >>>>>>>>>>>>>>>>>>>>");
        BasePage.refreshPage();
        BasePage.waitUntilPageCompletelyLoaded();
        // open the View Compliance widget
        BasePage.waitUntilElementClickable(profilePage.viewComplianceButton, 20);
        BasePage.clickWithJavaScript(profilePage.viewComplianceButton);
        BasePage.waitUntilElementDisplayed(profilePage.workerComplianceOverviewWidget, 20);
    }

    private void acceptCompliance() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Selecting all compliance checkboxes >>>>>>>>>>>>>>>>>>>>");
        BasePage.genericWait(3000);
        List<WebElement> checkboxes = profilePage.complianceCheckboxes;
        BasePage.waitUntilElementPresent((profilePage.complianceCheckboxes.get(0)), 40);

        for (WebElement checkbox : checkboxes) {
            BasePage.clickWithJavaScript(checkbox);
        }
        BasePage.clickWithJavaScript(profilePage.complianceSaveButton);
        BasePage.genericWait(1500);
        if (BasePage.isElementDisplayed(profilePage.complianceSaveButton)) {
            BasePage.clickWithJavaScript(profilePage.complianceSaveButton);
        }
    }

    private void verifyComplianceSavedSuccessMessage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying Compliance Saved Success Message >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementPresent(profilePage.successMessage, 30);
        String actualInLowerCase = BasePage.getText(profilePage.successMessage).toLowerCase().trim();
        String expected = "Compliance has been saved successfully";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Compliance Saved Success Message is wrong!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(profilePage.successMessage, 30);
    }

    private void verifyGeneralComplianceMessageAfterAcceptingTheCompliance() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying General Compliance Message After Accepting The Compliance >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.clickWithJavaScript(profilePage.viewComplianceButton);
        BasePage.waitUntilElementDisplayed(profilePage.workerComplianceOverviewWidget, 20);
        BasePage.genericWait(3000);
        String actual = BasePage.getText(profilePage.generalComplianceStatusMessage).toLowerCase().trim();
        String[] arr = actual.split(" ");
        String firstNumber = arr[0].trim();
        String secondNumber = arr[3].trim();
        assertThat("All mandatory items are checked!", firstNumber, is(secondNumber));
    }

    public void updateWorkerProfileAsSubmittedForReview() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Updating the profile status as Submitted for Review >>>>>>>>>>>>>>>>>>>>");
        BasePage.clickWithJavaScript(profilePage.submitForReviewButton);
        verifySubmittedForReviewSuccessMessage();
    }

    private void verifySubmittedForReviewSuccessMessage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying Submitted For Review Success Message >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementPresent(profilePage.successMessage, 30);
        String actualInLowerCase = BasePage.getText(profilePage.successMessage).toLowerCase().trim();
        String expected = "Profile submitted for review";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Submitted For Review Success Message is wrong!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(profilePage.successMessage, 30);
    }

    public void approveProfile() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Updating the profile status as Approve >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementClickable(profilePage.reviewProfileButton, 30);
        BasePage.clickWithJavaScript(profilePage.reviewProfileButton);
        BasePage.waitUntilElementDisplayed(profilePage.workerComplianceOverviewWidget, 20);
        BasePage.waitUntilElementClickable(profilePage.approveButton, 20);
        BasePage.clickWithJavaScript(profilePage.approveButton);
        verifyProfileApproveSuccessMessage();
    }

    private void verifyProfileApproveSuccessMessage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying Profile Approve Success Message >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementPresent(profilePage.successMessage, 30);
        String actualInLowerCase = BasePage.getText(profilePage.successMessage).toLowerCase().trim();
        String expected = "Profile approved successfully";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Profile approved Success Message is wrong!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(profilePage.successMessage, 30);
    }
}
