package com.carehires.actions.agency;

import com.carehires.pages.agency.AgencyProfilePage;
import com.carehires.utils.BasePage;
import org.openqa.selenium.support.PageFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AgencyProfileActions {

    AgencyProfilePage agencyProfile;

    public AgencyProfileActions() {
        agencyProfile = new AgencyProfilePage();
        PageFactory.initElements(BasePage.getDriver(), agencyProfile);
    }

    public void updateProfileAsApprove() {
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.clickWithJavaScript(agencyProfile.approveButton);

        //Check if "Go to Settings" button is displayed
        boolean isDisplayed = BasePage.isElementDisplayed(agencyProfile.goToSettingsButton);
        if (isDisplayed) {
            BasePage.clickWithJavaScript(agencyProfile.goToSettingsButton);
        }
    }

    public void moveToProfilePage() {
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.genericWait(5000);
        BasePage.clickWithJavaScript(agencyProfile.profileIcon);
    }

    public void verifyProfileStatus(String status) {
        BasePage.genericWait(5000);
        BasePage.refreshPage();
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(agencyProfile.profileStatus, 60);
        String actual = BasePage.getText(agencyProfile.profileStatus).toLowerCase().trim();
        String expected = status.toLowerCase();
        assertThat("Agent profile is not valid", actual, is(expected));
    }
}
