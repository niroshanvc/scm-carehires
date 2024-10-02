package com.carehires.actions.agency;

import com.carehires.pages.agency.CreateAgencyBusinessLocationsPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CreateAgencyBusinessLocationsActions {

    CreateAgencyBusinessLocationsPage locationsPage;

    private static final String YML_FILE = "agency-create";
    private static final String YML_HEADER = "Location";
    private static final Logger logger = LogManager.getFormatterLogger(CreateAgencyBusinessLocationsActions.class);

    public CreateAgencyBusinessLocationsActions() {
        locationsPage = new CreateAgencyBusinessLocationsPage();
        PageFactory.initElements(BasePage.getDriver(), locationsPage);
    }

    public void enterLocationDetails() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Business Location Details >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.clickWithJavaScript(locationsPage.addNewButton);

        String location = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "BusinessLocation");
        BasePage.clearAndEnterTexts(locationsPage.businessLocation, location);

        String emailAddress = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "BusinessEmailAddress");
        BasePage.clearAndEnterTexts(locationsPage.businessEmailAddress, emailAddress);

        String city = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "City");
        BasePage.clickWithJavaScript(locationsPage.selectCity);
        BasePage.clickWithJavaScript(getCityXpath(city));
        BasePage.genericWait(1000);

        String jobNotification = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "JobNotificationAddress");
        BasePage.clearAndEnterTexts(locationsPage.jobNotificationAddress, jobNotification);

        String approvalNotification = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "ApprovalNotificationAddress");
        BasePage.clearAndEnterTexts(locationsPage.approvalNotificationAddress, approvalNotification);
        BasePage.genericWait(15000);

        BasePage.clickWithJavaScript(locationsPage.addButton);
        BasePage.genericWait(5000);

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
        String expectedLocationName = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "BusinessLocation");
        assertThat("Business Location is not saved", actualLocationName, is(expectedLocationName));
    }
}
