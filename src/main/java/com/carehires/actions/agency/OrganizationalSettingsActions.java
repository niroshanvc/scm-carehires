package com.carehires.actions.agency;

import com.carehires.pages.agency.OrganizationalSettingsPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class OrganizationalSettingsActions {

    OrganizationalSettingsPage settingsPage;

    private static final String ENTITY = "agency";
    private static final String YML_FILE = "agency-create";
    private static final String YML_HEADER = "Settings";
    private static final Logger logger = LogManager.getFormatterLogger(OrganizationalSettingsActions.class);

    public OrganizationalSettingsActions() {
        settingsPage = new OrganizationalSettingsPage();
        PageFactory.initElements(BasePage.getDriver(), settingsPage);
    }

    public void saveSettings() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Saving Organizational Settings >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementClickable(settingsPage.updateButton, 90);

        BasePage.clickWithJavaScript(settingsPage.updateButton);
        BasePage.waitUntilElementPresent(settingsPage.saveButton, 20);

        String timeFormat = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "TimeFormat");
        BasePage.clickWithJavaScript(settingsPage.timeFormatDropdown);
        BasePage.waitUntilElementClickable(getDropdownOptionXpath(timeFormat), 10);
        if (timeFormat.equals("12 Hours")) {
            BasePage.clickWithJavaScript(getDropdownOptionXpath(timeFormat));
        } else {
            BasePage.clickWithJavaScript(getDropdownOptionXpath("24 Hours"));
        }

        String hubspot = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "HubSpotLink");
        BasePage.typeWithStringBuilder(settingsPage.hubSpotLink, hubspot);

        String salesPerson = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "SalesProcessCompletedBy");
        BasePage.clickWithJavaScript(settingsPage.salesProcessCompletedByDropdown);
        BasePage.clickWithJavaScript(getDropdownOptionXpath(salesPerson));

        String onboard = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "OnboardedBDM");
        BasePage.clickWithJavaScript(settingsPage.onboardedBdmDropdown);
        BasePage.clickWithJavaScript(getDropdownOptionXpath(onboard));

        BasePage.clickWithJavaScript(settingsPage.saveButton);
        verifySuccessMessage();
    }

    private String getDropdownOptionXpath(String city) {
        return String.format("//nb-option[contains(text(),'%s')]", city);
    }

    private void verifySuccessMessage() {
        BasePage.waitUntilElementPresent(settingsPage.successMessage, 90);
        String actualInLowerCase = BasePage.getText(settingsPage.successMessage).toLowerCase().trim();
        String expected = "Successfully updated settings!";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Organizational setting updated success message is wrong!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(settingsPage.successMessage, 20);
    }
}
