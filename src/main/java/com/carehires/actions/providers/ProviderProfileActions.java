package com.carehires.actions.providers;

import com.carehires.common.GlobalVariables;
import com.carehires.pages.providers.ProviderProfilePage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ProviderProfileActions {

    ProviderProfilePage profilePage;

    private static final Logger logger = LogManager.getFormatterLogger(ProviderProfileActions.class);

    public ProviderProfileActions() {
        profilePage = new ProviderProfilePage();
        try {
            PageFactory.initElements(BasePage.getDriver(), profilePage);
        } catch (BasePage.WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateProfileAsApprove() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Updating the profile as Approve >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementDisplayed(profilePage.approveButton, 20);
        BasePage.clickWithJavaScript(profilePage.approveButton);
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
        int providerIncrementValue = DataConfigurationReader.getCurrentIncrementValue("provider");
        GlobalVariables.storeIncrementedValue("provider", providerIncrementValue);
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
