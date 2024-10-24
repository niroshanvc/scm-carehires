package com.carehires.actions.workers;

import com.carehires.pages.providers.ProviderProfilePage;
import com.carehires.pages.worker.ViewWorkerProfilePage;
import com.carehires.utils.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ViewWorkerProfileActions {

    ViewWorkerProfilePage profilePage;

    private static final Logger logger = LogManager.getFormatterLogger(ViewWorkerProfileActions.class);

    public ViewWorkerProfileActions() {
        profilePage = new ViewWorkerProfilePage();
        PageFactory.initElements(BasePage.getDriver(), profilePage);
    }

    private void verifyGeneralComplianceMessage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying General Compliance Message >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementDisplayed(profilePage.viewComplianceButton, 20);
        BasePage.clickWithJavaScript(profilePage.viewComplianceButton);
        verifySuccessMessage();
    }

    public void moveToProfilePage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Moving to Profile Page >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.genericWait(5000);
        BasePage.clickWithJavaScript(profilePage.profileIcon);
    }

    public void verifyProfileStatus(String status) {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying the profile status >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(profilePage.profileStatus, 60);
        String actual = BasePage.getText(profilePage.profileStatus).toLowerCase().trim();
        String expected = status.toLowerCase();
        assertThat("Provider profile is not valid", actual, is(expected));
    }

    private void verifySuccessMessage() {
        BasePage.genericWait(2000);
        BasePage.waitUntilElementPresent(profilePage.successMessage, 30);
        String actualInLowerCase = BasePage.getText(profilePage.successMessage).toLowerCase().trim();
        String expected = "Provider approved successfully";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Provider status updating message is wrong!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(profilePage.successMessage, 20);

    }
}
