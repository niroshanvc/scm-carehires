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

    public void updateProfileAsApprove() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Updating the profile as Approve >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.clickWithJavaScript(agencyProfile.approveButton);

        //Check if "Go to Settings" button is displayed
        boolean isDisplayed = BasePage.isElementDisplayed(agencyProfile.goToSettingsButton);
        if (isDisplayed) {
            BasePage.clickWithJavaScript(agencyProfile.goToSettingsButton);
        }
    }

    public void moveToProfilePage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Moving to Profile Page >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.genericWait(5000);
        BasePage.clickWithJavaScript(agencyProfile.profileIcon);
    }

    public void verifyProfileStatus(String status) {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying the profile status >>>>>>>>>>>>>>>>>>>>");
        BasePage.genericWait(5000);
        BasePage.refreshPage();
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(agencyProfile.profileStatus, 60);
        String actual = BasePage.getText(agencyProfile.profileStatus).toLowerCase().trim();
        String expected = status.toLowerCase();
        assertThat("Agent profile is not valid", actual, is(expected));
    }
}
