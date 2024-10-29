package com.carehires.actions.workers;

import com.carehires.common.GlobalVariables;
import com.carehires.pages.worker.CreateWorkerVaccinationAndAllergyInformationPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import com.carehires.utils.GenericUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CreateWorkerVaccinationAndAllergyInformationActions {

    private final CreateWorkerVaccinationAndAllergyInformationPage vaccinationPage;
    private static final GenericUtils genericUtils = new GenericUtils();

    private static final String ENTITY = "worker";
    private static final String YML_FILE = "worker-create";
    private static final String YML_HEADER = "Vaccination and Allergy Information";
    private static final Logger logger = LogManager.getFormatterLogger(CreateWorkerVaccinationAndAllergyInformationActions.class);

    public CreateWorkerVaccinationAndAllergyInformationActions() {
        vaccinationPage = new CreateWorkerVaccinationAndAllergyInformationPage();
        PageFactory.initElements(BasePage.getDriver(), vaccinationPage);
    }

    public void enterDataForVaccinationInformation() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Vaccination and Allergy Information Data >>>>>>>>>>>>>>>>>>>>");

        // Retrieve the incremented value
        Integer incrementValue = GlobalVariables.getVariable("worker_incrementValue", Integer.class);

        // Check for null or default value
        if (incrementValue == null) {
            throw new NullPointerException("Increment value for worker is not set in GlobalVariables.");
        }

        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.clickWithJavaScript(vaccinationPage.addNewButton);
        BasePage.waitUntilElementPresent(vaccinationPage.vaccinationType, 20);

        String vaccinationName = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "NameOrType");
        BasePage.clearAndEnterTexts(vaccinationPage.vaccinationType, vaccinationName);

        String vaccinationDate = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "VaccinationDate");
        BasePage.clickWithJavaScript(vaccinationPage.vaccinationDate);
        genericUtils.selectDateFromCalendarPopup(vaccinationDate);

        String vaccinationDocument = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "VaccinationDocument");
        String absoluteFilePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" +
                File.separator + "resources" + File.separator + "Upload" + File.separator + "Worker" + File.separator + vaccinationDocument;
        BasePage.uploadFile(vaccinationPage.selectFile, absoluteFilePath);

        String isCovidRelated = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "IsCovid-19Vaccine");
        if (isCovidRelated.equalsIgnoreCase("Yes")) {
            BasePage.clickWithJavaScript(vaccinationPage.covid19Checkbox);
        }

        // click on the Add button
        BasePage.clickWithJavaScript(vaccinationPage.addButton);
        verifySuccessMessage();

        //click on Request to complete
        BasePage.clickWithJavaScript(vaccinationPage.requestToCompleteButton);
        verifyRequestToCompleteMessage();

        // click on the Add button
        BasePage.clickWithJavaScript(vaccinationPage.updateButton);
        BasePage.waitUntilElementClickable(vaccinationPage.saveButton, 90);
        BasePage.clickWithJavaScript(vaccinationPage.saveButton);
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
}