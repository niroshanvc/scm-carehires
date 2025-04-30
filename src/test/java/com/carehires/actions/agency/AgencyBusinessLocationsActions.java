package com.carehires.actions.agency;


import com.carehires.common.GlobalVariables;
import com.carehires.pages.agency.AgencyBusinessLocationsPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AgencyBusinessLocationsActions {

    private final AgencyBusinessLocationsPage locationsPage;
    private static final AgencyNavigationMenuActions navigationMenu = new AgencyNavigationMenuActions();

    private static final String ENTITY = "agency";
    private static final String YML_FILE = "agency-create";
    private static final String EDIT_YML_FILE = "agency-edit";
    private static final String YML_HEADER = "Agency Business Location";
    private static final String ADD = "Add";
    private static final String UPDATE = "Update";
    private static final String BUSINESS_LOCATION = "BusinessLocation";
    private static final Logger logger = LogManager.getFormatterLogger(AgencyBusinessLocationsActions.class);

    public AgencyBusinessLocationsActions() {
        locationsPage = new AgencyBusinessLocationsPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), locationsPage);
        } catch (BasePage.WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
    }

    public void addLocationDetails() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Business Location Details >>>>>>>>>>>>>>>>>>>>");

        // Retrieve the incremented value
        Integer incrementValue = GlobalVariables.getVariable("agency_incrementValue", Integer.class);

        // Log the retrieved value
        logger.info("Retrieved agency increment value in BusinessLocation: %s", incrementValue);

        // Check for null or default value
        if (incrementValue == null) {
            throw new NullPointerException("Increment value for agency is not set in GlobalVariables.");
        }

        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.clickWithJavaScript(locationsPage.addNewButton);

        String location = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, ADD,
                BUSINESS_LOCATION);
        BasePage.clearAndEnterTexts(locationsPage.businessLocation, location);

        String emailAddress = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER,
                ADD, "BusinessEmailAddress");
        BasePage.clearAndEnterTexts(locationsPage.businessEmailAddress, emailAddress);

        String city = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, ADD, "City");
        BasePage.clickWithJavaScript(locationsPage.selectCity);
        BasePage.clickWithJavaScript(getCityXpath(city));

        BasePage.genericWait(15000);

        BasePage.clickWithJavaScript(locationsPage.addButton);
        verifySuccessMessage();

        isBusinessLocationSaved();

        BasePage.clickWithJavaScript(locationsPage.updateButton);
        BasePage.waitUntilElementClickable(locationsPage.nextButton, 90);
        BasePage.clickWithJavaScript(locationsPage.nextButton);
    }

    private String getCityXpath(String city) {
        return String.format("//nb-option[contains(text(),'%s')]", city);
    }

    private void isBusinessLocationSaved() {
        BasePage.waitUntilElementPresent(locationsPage.locationName, 60);
        String actualLocationName = BasePage.getText(locationsPage.locationName);
        String expectedLocationName = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER,
                ADD, BUSINESS_LOCATION);
        assertThat("Business Location is not saved", actualLocationName, is(expectedLocationName));
    }

    private void verifySuccessMessage() {
        BasePage.waitUntilElementPresent(locationsPage.successMessage, 90);
        String actualInLowerCase = BasePage.getText(locationsPage.successMessage).toLowerCase().trim();
        String expected = "Record created successfully.";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Location information saved success message is wrong!", actualInLowerCase, is(
                expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(locationsPage.successMessage, 20);
    }

    private void enterLocationsData(String headers) {
        BasePage.waitUntilElementDisplayed(locationsPage.businessLocation, 30);
        String location = DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, YML_HEADER, headers,
                BUSINESS_LOCATION);
        BasePage.clearAndEnterTexts(locationsPage.businessLocation, location);

        String emailAddress = DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE,
                YML_HEADER, headers, "BusinessEmailAddress");
        BasePage.clearAndEnterTexts(locationsPage.businessEmailAddress, emailAddress);

        String city = DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, YML_HEADER, headers,
                "City");
        BasePage.clickWithJavaScript(locationsPage.selectCity);
        BasePage.clickWithJavaScript(getCityXpath(city));

        BasePage.genericWait(10000);
    }

    public void editLocationsData() {
        navigateToLocationsPage();
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Add new Location >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementClickable(locationsPage.addNewButton, 30);
        BasePage.clickWithJavaScript(locationsPage.addNewButton);

        enterLocationsData(ADD);
        BasePage.clickWithJavaScript(locationsPage.addButton);
        verifySuccessMessage();

        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Edit a Location >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementDisplayed(locationsPage.editDetailsIcon, 30);
        BasePage.clickWithJavaScript(locationsPage.editDetailsIcon);
        enterLocationsData(UPDATE);
        BasePage.clickWithJavaScript(locationsPage.updateButton);
        verifyLocationUpdateSuccessMessage();
        BasePage.waitUntilElementClickable(locationsPage.nextButton, 90);
        BasePage.clickWithJavaScript(locationsPage.nextButton);
    }

    private void navigateToLocationsPage() {
        navigationMenu.gotoLocationsPage();
        BasePage.genericWait(3000);
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Navigating to Agency Business Locations >>>>>>>>>>>>>>>>>>>>");
        if (BasePage.isElementDisplayed(locationsPage.updateButton)) {
            BasePage.clickWithJavaScript(locationsPage.updateButton);
        }
        BasePage.waitUntilElementPresent(locationsPage.nextButton, 30);
        BasePage.genericWait(2000);
    }

    private void verifyLocationUpdateSuccessMessage() {
        BasePage.waitUntilElementPresent(locationsPage.successMessage, 90);
        String actualInLowerCase = BasePage.getText(locationsPage.successMessage).toLowerCase().trim();
        String expected = "Record updated successfully.";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Location information update success message is wrong!", actualInLowerCase, is(
                expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(locationsPage.successMessage, 20);
    }
}
