package com.carehires.actions.workers;

import com.carehires.common.GlobalVariables;
import com.carehires.pages.worker.WorkerVaccinationAndAllergyInformationPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import com.carehires.utils.GenericUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class WorkerVaccinationAndAllergyInformationActions {

    private final WorkerVaccinationAndAllergyInformationPage vaccinationPage;
    private static final GenericUtils genericUtils = new GenericUtils();
    private static final WorkerNavigationMenuActions navigationMenu = new WorkerNavigationMenuActions();

    private static final String ENTITY = "worker";
    private static final String YML_FILE = "worker-create";
    private static final String EDIT_YML_FILE = "worker-edit";
    private static final String ADD = "Add";
    private static final String UPDATE = "Update";
    private static final String YML_HEADER = "Vaccination and Allergy Information";
    private static final String YML_HEADER_DATASET1 = "Dataset1";
    private static final String YML_HEADER_DATASET2 = "Dataset2";
    
    private static final Logger logger = LogManager.getFormatterLogger(WorkerVaccinationAndAllergyInformationActions.class);
    Integer incrementValue;

    public WorkerVaccinationAndAllergyInformationActions() {
        vaccinationPage = new WorkerVaccinationAndAllergyInformationPage();
        PageFactory.initElements(BasePage.getDriver(), vaccinationPage);
    }

    public void enterDataForVaccinationInformation() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Vaccination and Allergy Information Data >>>>>>>>>>>>>>>>>>>>");

        // Retrieve the incremented value
        incrementValue = GlobalVariables.getVariable("worker_incrementValue", Integer.class);

        // Check for null or default value
        if (incrementValue == null) {
            throw new NullPointerException("Increment value for worker is not set in GlobalVariables.");
        }

        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.clickWithJavaScript(vaccinationPage.addNewButton);
        //enter first set of data
        enterMedicalInfo(YML_FILE, ADD, YML_HEADER_DATASET1);
        // click on the Add button
        BasePage.clickWithJavaScript(vaccinationPage.addButton);
        verifySuccessMessage();

        //enter second set of data
        BasePage.clickWithJavaScript(vaccinationPage.addNewButton);
        enterMedicalInfo(YML_FILE, ADD, YML_HEADER_DATASET2);
        // click on the Add button
        BasePage.clickWithJavaScript(vaccinationPage.addButton);
        verifySuccessMessage();

        //click on Request to complete
        BasePage.clickWithJavaScript(vaccinationPage.requestToCompleteButton);
        verifyRequestToCompleteMessage();

        // click on the save button
        BasePage.clickWithJavaScript(vaccinationPage.updateButton);
        BasePage.waitUntilElementClickable(vaccinationPage.saveButton, 90);
        BasePage.clickWithJavaScript(vaccinationPage.saveButton);
    }

    private void enterMedicalInfo(String ymlFile, String subHeader, String dataset) {
        BasePage.waitUntilElementPresent(vaccinationPage.vaccinationType, 20);

        String vaccinationName = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, dataset, "NameOrType");
        BasePage.clearAndEnterTexts(vaccinationPage.vaccinationType, vaccinationName);

        String vaccinationDate = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, dataset, "VaccinationDate");
        BasePage.clickWithJavaScript(vaccinationPage.vaccinationDate);
        genericUtils.selectDateFromCalendarPopup(vaccinationDate);

        String vaccinationDocument = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, dataset, "VaccinationDocument");
        String absoluteFilePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" +
                File.separator + "resources" + File.separator + "Upload" + File.separator + "Worker" + File.separator + vaccinationDocument;
        BasePage.uploadFile(vaccinationPage.selectFile, absoluteFilePath);

        String isCovidRelated = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, dataset, "IsCovid-19Vaccine");
        assert isCovidRelated != null;
        String text = BasePage.getAttributeValue(vaccinationPage.covid19CheckboxSpan, "class");
        if (isCovidRelated.equalsIgnoreCase("Yes")) {
            if (!text.contains("checked")) {
                BasePage.clickWithJavaScript(vaccinationPage.covid19Checkbox);
            }
        } else {
            if (text.contains("checked")) {
                BasePage.clickWithJavaScript(vaccinationPage.covid19Checkbox);
            }
        }
    }

    private void verifySuccessMessage() {
        BasePage.waitUntilElementPresent(vaccinationPage.successMessage, 90);
        String actualInLowerCase = BasePage.getText(vaccinationPage.successMessage).toLowerCase().trim();
        String expected = "Record created successfully";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Unable to add certificate!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(vaccinationPage.successMessage, 20);
    }

    private void verifyRequestToCompleteMessage() {
        BasePage.waitUntilElementPresent(vaccinationPage.successMessage, 90);
        String actualInLowerCase = BasePage.getText(vaccinationPage.successMessage).toLowerCase().trim();
        String expected = "The email has been sent";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Email sent message is wrong!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(vaccinationPage.successMessage, 20);
    }

    public void updateMedicalInfo() {
        navigationMenu.gotoMedicalPage();
        BasePage.waitUntilPageCompletelyLoaded();
        // Retrieve the incremented value
        incrementValue = GlobalVariables.getVariable("worker_incrementValue", Integer.class);

        //click on Request to complete
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Vaccination and Allergy Information Data - Medical Questionnaire >>>>>>>>>>>>>>>>>>>>");
        BasePage.clickWithJavaScript(vaccinationPage.requestToCompleteButton);
        verifyRequestToCompleteMessage();

        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Vaccination and Allergy Information Data - Adding >>>>>>>>>>>>>>>>>>>>");
        BasePage.clickWithJavaScript(vaccinationPage.addNewButton);
        //enter first set of data
        enterMedicalInfo(EDIT_YML_FILE, ADD, YML_HEADER_DATASET1);
        // click on the Add button
        BasePage.clickWithJavaScript(vaccinationPage.addButton);
        verifySuccessMessage();
        //enter second set of data
        BasePage.clickWithJavaScript(vaccinationPage.addNewButton);
        enterMedicalInfo(EDIT_YML_FILE, ADD, YML_HEADER_DATASET2);
        // click on the Add button
        BasePage.clickWithJavaScript(vaccinationPage.addButton);
        verifySuccessMessage();

        // click on update button
        BasePage.clickWithJavaScript(vaccinationPage.updateButton);
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Vaccination and Allergy Information Data - Updating >>>>>>>>>>>>>>>>>>>>");
        //edit first entry
        clickOnEditIcon(vaccinationPage.editIcon1);
        BasePage.waitUntilElementClickable(vaccinationPage.updateButton, 60);
        enterMedicalInfo(EDIT_YML_FILE, UPDATE, YML_HEADER_DATASET1);
        // click on Update button
        BasePage.clickWithJavaScript(vaccinationPage.updateButton);
        verifyUpdateMessage();

        //edit second entry
        clickOnEditIcon(vaccinationPage.editIcon2);
        BasePage.waitUntilElementClickable(vaccinationPage.updateButton, 60);
        enterMedicalInfo(EDIT_YML_FILE, UPDATE, YML_HEADER_DATASET2);
        // click on Update button
        BasePage.clickWithJavaScript(vaccinationPage.updateButton);
        verifyUpdateMessage();

        // save changes
        BasePage.waitUntilElementClickable(vaccinationPage.saveButton, 30);
        BasePage.clickWithJavaScript(vaccinationPage.saveButton);
    }

    private void clickOnEditIcon(WebElement element) {
        BasePage.waitUntilElementClickable(element, 60);
        BasePage.clickWithJavaScript(element);
    }

    private void verifyUpdateMessage() {
        BasePage.waitUntilElementPresent(vaccinationPage.successMessage, 90);
        String actualInLowerCase = BasePage.getText(vaccinationPage.successMessage).toLowerCase().trim();
        String expected = "Record updated successfully";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Unable to edit emergency contact!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(vaccinationPage.successMessage, 20);
    }
}