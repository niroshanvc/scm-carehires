package com.carehires.actions.agency;


import com.carehires.common.GlobalVariables;
import com.carehires.pages.agency.AgencyProfilePage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class AgencyProfileActions {

    AgencyProfilePage agencyProfile;

    private static final String ENTITY = "agency";
    private static final String EDIT_YML_FILE = "agency-edit";
    private static final String YML_HEADER = "Basic Info";
    private static final String UPDATE = "Update";
    private static final Logger logger = LogManager.getFormatterLogger(AgencyProfileActions.class);

    private static final AgencyNavigationMenuActions navigationMenu = new AgencyNavigationMenuActions();

    public AgencyProfileActions() {
        agencyProfile = new AgencyProfilePage();
        try {
            PageFactory.initElements(BasePage.getDriver(), agencyProfile);
        } catch (BasePage.WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
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
        BasePage.waitUntilElementPresent(agencyProfile.successMessage, 60);
        String actualInLowerCase = BasePage.getText(agencyProfile.successMessage).toLowerCase().trim();
        String expected = "Agency approved successfully";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Branding theme missing message is wrong!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(agencyProfile.successMessage, 20);
    }

    private void clickOnUpdateProfileLink() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Clicking on the update profile link >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(agencyProfile.topThreeDots, 30);
        BasePage.mouseHoverAndClick(agencyProfile.topThreeDots, agencyProfile.updateProfileLink,
                AgencyProfilePage.updateProfileLinkChildElement);
        BasePage.waitUntilElementClickable(agencyProfile.saveButton, 30);
    }

    public void editProfile() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Editing the profile >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        clickOnUpdateProfileLink();

        // update existing business registration number
        String businessRegistrationNumber = DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, YML_HEADER, UPDATE, "BusinessRegistrationNumber");
        BasePage.clearAndEnterTexts(agencyProfile.businessRegistrationNumber, businessRegistrationNumber);

        // update existing phone number
        String updatingNumber = DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, YML_HEADER, UPDATE, "PhoneNumber");
        BasePage.clearAndEnterTexts(agencyProfile.phoneNumberInput, updatingNumber);

        BasePage.genericWait(5000);
        BasePage.clickWithJavaScript(agencyProfile.saveButton);
        verifySuccessMessage();
    }

    private void verifySuccessMessage() {
        BasePage.waitUntilElementPresent(agencyProfile.successMessage, 90);
        String actualInLowerCase = BasePage.getText(agencyProfile.successMessage).toLowerCase().trim();
        String expected = "Profile updated successfully.";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Profile updated success message is wrong!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(agencyProfile.successMessage, 20);
    }

    public void getsAgencyId() {
        navigateToProfilePage();
        getAgencyId();
    }

    private void navigateToProfilePage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Navigating to Agency Profile >>>>>>>>>>>>>>>>>>>>");
        navigationMenu.gotoProfilePage();
        BasePage.genericWait(3000);
    }

    // get auto generated agency id and save it on the memory
    private void getAgencyId() {
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(agencyProfile.agencyId, 30);
        String headerText = BasePage.getText(agencyProfile.agencyId).trim();
        String agencyId = headerText.split("\n")[0];
        GlobalVariables.setVariable("agencyId", agencyId);
    }
}
