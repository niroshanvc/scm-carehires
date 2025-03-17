package com.carehires.actions.workers;


import com.carehires.common.GlobalVariables;
import com.carehires.pages.worker.WorkerEmergencyInformationPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class WorkerEmergencyInformationActions {

    private final WorkerEmergencyInformationPage emergencyInformationPage;
    private static final WorkerNavigationMenuActions navigationMenu = new WorkerNavigationMenuActions();

    private static final String ENTITY = "worker";
    private static final String YML_FILE = "worker-create";
    private static final String EDIT_YML_FILE = "worker-edit";
    private static final String YML_HEADER = "Emergency Information";
    private static final String ADD = "Add";
    private static final String UPDATE = "Update";
    private static final String YML_HEADER_DATASET1 = "Dataset1";
    private static final String YML_HEADER_DATASET2 = "Dataset2";

    private static final Logger logger = LogManager.getFormatterLogger(WorkerEmergencyInformationActions.class);

    Integer incrementValue;

    public WorkerEmergencyInformationActions() {
        emergencyInformationPage = new WorkerEmergencyInformationPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), emergencyInformationPage);
        } catch (BasePage.WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
    }

    public void enterDataForEmergencyInformation() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Emergency Information Data >>>>>>>>>>>>>>>>>>>>");

        // Retrieve the incremented value
        incrementValue = GlobalVariables.getVariable("worker_incrementValue", Integer.class);

        // Check for null or default value
        if (incrementValue == null) {
            throw new NullPointerException("Increment value for worker is not set in GlobalVariables.");
        }

        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.clickWithJavaScript(emergencyInformationPage.addNewButton);

        //enter first set of data
        enterEmergencyContactInfo(YML_FILE, ADD, YML_HEADER_DATASET1);
        // click on the Add button
        BasePage.clickWithJavaScript(emergencyInformationPage.addButton);
        verifySuccessMessage();

        //enter second set of data
        BasePage.waitUntilElementClickable(emergencyInformationPage.addNewButton, 30);
        BasePage.clickWithJavaScript(emergencyInformationPage.addNewButton);
        enterEmergencyContactInfo(YML_FILE, ADD, YML_HEADER_DATASET2);
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
        assertThat("Unable to add emergency contact!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(emergencyInformationPage.successMessage, 20);
    }

    private void enterEmergencyContactInfo(String ymlFile, String subHeader, String dataset) {
        BasePage.waitUntilElementPresent(emergencyInformationPage.emergencyContactName, 60);

        String contactName = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, dataset, "ContactName");
        BasePage.clearAndEnterTexts(emergencyInformationPage.emergencyContactName, contactName);

        String relationship = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, dataset, "Relationship");
        BasePage.clearAndEnterTexts(emergencyInformationPage.relationship, relationship);

        String primaryNumber = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, dataset, "PrimaryContactNumber");
        BasePage.clearAndEnterTexts(emergencyInformationPage.primaryContactNumber, primaryNumber);

        String secondaryNumber = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, dataset, "SecondaryContactNumber");
        BasePage.clearAndEnterTexts(emergencyInformationPage.secondaryContactNumber, secondaryNumber);
        BasePage.clickTabKey(emergencyInformationPage.secondaryContactNumber);
        BasePage.genericWait(2000);
    }

    public void updateEmergency() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Emergency Information Data - Adding >>>>>>>>>>>>>>>>>>>>");
        navigationMenu.gotoEmergencyPage();
        BasePage.waitUntilPageCompletelyLoaded();
        // Retrieve the incremented value
        incrementValue = GlobalVariables.getVariable("worker_incrementValue", Integer.class);
        BasePage.clickWithJavaScript(emergencyInformationPage.addNewButton);

        //enter first set of data
        enterEmergencyContactInfo(EDIT_YML_FILE, ADD, YML_HEADER_DATASET1);
        // click on the Add button
        BasePage.clickWithJavaScript(emergencyInformationPage.addButton);
        verifySuccessMessage();

        //enter second set of data
        BasePage.waitUntilElementClickable(emergencyInformationPage.addNewButton, 30);
        BasePage.clickWithJavaScript(emergencyInformationPage.addNewButton);
        enterEmergencyContactInfo(EDIT_YML_FILE, ADD, YML_HEADER_DATASET2);
        // click on the Add button
        BasePage.clickWithJavaScript(emergencyInformationPage.addButton);
        verifySuccessMessage();

        BasePage.waitUntilElementClickable(emergencyInformationPage.updateButton, 90);
        BasePage.clickWithJavaScript(emergencyInformationPage.updateButton);

        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Emergency Information Data - Updating >>>>>>>>>>>>>>>>>>>>");
        //edit first entry
        clickOnEditIcon(emergencyInformationPage.editIcon1);
        BasePage.waitUntilElementClickable(emergencyInformationPage.updateButton, 30);
        enterEmergencyContactInfo(EDIT_YML_FILE, UPDATE, YML_HEADER_DATASET1);
        // click on Update button
        BasePage.clickWithJavaScript(emergencyInformationPage.updateButton);
        verifyUpdateMessage();

        //edit second entry
        clickOnEditIcon(emergencyInformationPage.editIcon2);
        BasePage.waitUntilElementClickable(emergencyInformationPage.updateButton, 30);
        enterEmergencyContactInfo(EDIT_YML_FILE, UPDATE, YML_HEADER_DATASET2);
        // click on update button
        BasePage.clickWithJavaScript(emergencyInformationPage.updateButton);
        verifyUpdateMessage();

        // save changes
        BasePage.waitUntilElementClickable(emergencyInformationPage.saveButton, 30);
        BasePage.clickWithJavaScript(emergencyInformationPage.saveButton);
    }

    private void clickOnEditIcon(WebElement element) {
        BasePage.waitUntilElementClickable(element, 60);
        BasePage.clickWithJavaScript(element);
    }

    private void verifyUpdateMessage() {
        BasePage.waitUntilElementPresent(emergencyInformationPage.successMessage, 90);
        String actualInLowerCase = BasePage.getText(emergencyInformationPage.successMessage).toLowerCase().trim();
        String expected = "Record updated successfully";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Unable to edit emergency contact!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(emergencyInformationPage.successMessage, 20);
    }
}