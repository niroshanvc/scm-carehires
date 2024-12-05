package com.carehires.actions.workers;

import com.carehires.common.GlobalVariables;
import com.carehires.pages.worker.WorkerEmergencyInformationPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class WorkerEmergencyInformationActions {

    private final WorkerEmergencyInformationPage emergencyInformationPage;

    private static final String ENTITY = "worker";
    private static final String YML_FILE = "worker-create";
    private static final String YML_HEADER = "Emergency Information";
    private static final Logger logger = LogManager.getFormatterLogger(WorkerEmergencyInformationActions.class);

    public WorkerEmergencyInformationActions() {
        emergencyInformationPage = new WorkerEmergencyInformationPage();
        PageFactory.initElements(BasePage.getDriver(), emergencyInformationPage);
    }

    public void enterDataForEmergencyInformation() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Emergency Information Data >>>>>>>>>>>>>>>>>>>>");

        // Retrieve the incremented value
        Integer incrementValue = GlobalVariables.getVariable("worker_incrementValue", Integer.class);

        // Check for null or default value
        if (incrementValue == null) {
            throw new NullPointerException("Increment value for worker is not set in GlobalVariables.");
        }

        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.clickWithJavaScript(emergencyInformationPage.addNewButton);
        BasePage.waitUntilElementPresent(emergencyInformationPage.emergencyContactName, 20);

        String contactName = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "ContactName");
        BasePage.clearAndEnterTexts(emergencyInformationPage.emergencyContactName, contactName);

        String relationship = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Relationship");
        BasePage.clearAndEnterTexts(emergencyInformationPage.relationship, relationship);

        String primaryNumber = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "PrimaryContactNumber");
        BasePage.clearAndEnterTexts(emergencyInformationPage.primaryContactNumber, primaryNumber);

        String secondaryNumber = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "SecondaryContactNumber");
        BasePage.clearAndEnterTexts(emergencyInformationPage.secondaryContactNumber, secondaryNumber);
        BasePage.clickTabKey(emergencyInformationPage.secondaryContactNumber);
        BasePage.genericWait(2000);

        // click on the Add button
        BasePage.clickWithJavaScript(emergencyInformationPage.addButton);
        verifySuccessMessage();

        // click on the Update button
        BasePage.waitUntilElementClickable(emergencyInformationPage.updateButton, 90);
        BasePage.clickWithJavaScript(emergencyInformationPage.updateButton);
        BasePage.waitUntilElementClickable(emergencyInformationPage.saveButton, 90);
        BasePage.clickWithJavaScript(emergencyInformationPage.saveButton);
    }

    private void verifySuccessMessage() {
        BasePage.waitUntilElementPresent(emergencyInformationPage.successMessage, 90);
        String actualInLowerCase = BasePage.getText(emergencyInformationPage.successMessage).toLowerCase().trim();
        String expected = "Record created successfully";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Unable to add certificate!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(emergencyInformationPage.successMessage, 20);
    }
}