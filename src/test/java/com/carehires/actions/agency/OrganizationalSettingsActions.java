package com.carehires.actions.agency;


import com.carehires.pages.agency.OrganizationalSettingsPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class OrganizationalSettingsActions {

    OrganizationalSettingsPage settingsPage;

    private static final String ENTITY = "agency";
    private static final String YML_FILE = "agency-create";
    private static final String YML_HEADER = "Settings";
    private static final Logger logger = LogManager.getFormatterLogger(OrganizationalSettingsActions.class);

    public OrganizationalSettingsActions() {
        settingsPage = new OrganizationalSettingsPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), settingsPage);
        } catch (BasePage.WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveSettings() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Saving Organizational Settings >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementClickable(settingsPage.updateButton, 90);

        BasePage.clickWithJavaScript(settingsPage.updateButton);
        BasePage.waitUntilElementPresent(settingsPage.saveButton, 20);

        String timeFormat = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "TimeFormat");
        BasePage.clickWithJavaScript(settingsPage.timeFormatDropdown);
        BasePage.waitUntilElementClickable(settingsPage.getDropdownOptionXpath(timeFormat), 10);
        assert timeFormat != null;
        if (timeFormat.equals("12 Hours")) {
            BasePage.clickWithJavaScript(settingsPage.getDropdownOptionXpath(timeFormat));
        } else {
            BasePage.clickWithJavaScript(settingsPage.getDropdownOptionXpath("24 Hours"));
        }

        String hubspot = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "HubSpotLink");
        BasePage.typeWithStringBuilder(settingsPage.hubSpotLink, hubspot);

        String salesPerson = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "SalesProcessCompletedBy");
        BasePage.clickWithJavaScript(settingsPage.salesProcessCompletedByDropdown);
        BasePage.clickWithJavaScript(settingsPage.getDropdownOptionXpath(salesPerson));

        String onboard = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "OnboardedBDM");
        BasePage.clickWithJavaScript(settingsPage.onboardedBdmDropdown);
        BasePage.clickWithJavaScript(settingsPage.getDropdownOptionXpath(onboard));

        BasePage.clickWithJavaScript(settingsPage.saveButton);
        verifySuccessMessage();
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
