package com.carehires.actions.agency;

import com.carehires.pages.agency.AgencyProfilePage;
import com.carehires.utils.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AgencyProfileActions {

    AgencyProfilePage agencyProfile;

    private static final Logger logger = LogManager.getFormatterLogger(AgencyProfileActions.class);

    public AgencyProfileActions() {
        agencyProfile = new AgencyProfilePage();
        PageFactory.initElements(BasePage.getDriver(), agencyProfile);
    }

    public void updateProfileAsProfileComplete() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Updating the profile as Profile Complete >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.clickWithJavaScript(agencyProfile.approveButton);

        waitUntilCreatingPaymentProfilePopupGetDisappeared();
        verifyProfileApproveMessage();
    }

    public void moveToProfilePage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Moving to Profile Page >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.genericWait(5000);
        BasePage.clickWithJavaScript(agencyProfile.profileIcon);
    }

    public void verifyProfileStatus(String status) {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying the profile status >>>>>>>>>>>>>>>>>>>>");
        BasePage.refreshPage();
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(agencyProfile.profileStatus, 60);
        String actual = BasePage.getText(agencyProfile.profileStatus).toLowerCase().trim();
        String expected = status.toLowerCase();
        assertThat("Agent profile status is not valid", actual, is(expected));
    }

    private void waitUntilCreatingPaymentProfilePopupGetDisappeared() {
        BasePage.waitUntilElementDisplayed(agencyProfile.popupText, 90);
        String expectedText = "Creating payment profile for organization.....";
        //Successfully updated settings!

        String actualText = BasePage.getText(agencyProfile.popupText);
        assertThat("Creating payment profile popup message is not correct", actualText, is(expectedText));
    }

    public void updateProfileAsApprove() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Updating the profile as approve >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.clickWithJavaScript(agencyProfile.approveButton);
        verifyProfileApprovedSuccessMessage();
    }

    private void verifyProfileApprovedSuccessMessage() {
        BasePage.waitUntilElementPresent(agencyProfile.successMessage, 30);
        String actualInLowerCase = BasePage.getText(agencyProfile.successMessage).toLowerCase().trim();
        String expected = "Agency approved successfully";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Agency approved success message is wrong!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(agencyProfile.successMessage, 20);
    }

    private void verifyProfileApproveMessage() {
        BasePage.waitUntilElementPresent(agencyProfile.successMessage, 30);
        String actualInLowerCase = BasePage.getText(agencyProfile.successMessage).toLowerCase().trim();
        String expected = "Agency approved successfully";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Branding theme missing message is wrong!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(agencyProfile.successMessage, 20);
    }
}
