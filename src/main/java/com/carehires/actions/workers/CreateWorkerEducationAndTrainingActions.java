package com.carehires.actions.workers;

import com.carehires.common.GlobalVariables;
import com.carehires.pages.worker.CreateWorkerEducationAndTrainingPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import com.carehires.utils.GenericUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CreateWorkerEducationAndTrainingActions {

    CreateWorkerEducationAndTrainingPage educationAndTrainingPage;
    private final GenericUtils genericUtils = new GenericUtils();

    private static final String ENTITY = "worker";
    private static final String YML_FILE = "worker-create";
    private static final String YML_HEADER = "Education and Training";
    private static final String YML_HEADER_DATESET1 = "Dataset1";
    private static final String YML_HEADER_DATESET2 = "Dataset2";
    private static final Logger logger = LogManager.getFormatterLogger(CreateWorkerEducationAndTrainingActions.class);

    public CreateWorkerEducationAndTrainingActions() {
        educationAndTrainingPage = new CreateWorkerEducationAndTrainingPage();
        PageFactory.initElements(BasePage.getDriver(), educationAndTrainingPage);
    }

    public void enterDataForEducationAndTraining() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Education and Training Data >>>>>>>>>>>>>>>>>>>>");

        // Retrieve the incremented value
        Integer incrementValue = GlobalVariables.getVariable("worker_incrementValue", Integer.class);

        // Check for null or default value
        if (incrementValue == null) {
            throw new NullPointerException("Increment value for worker is not set in GlobalVariables.");
        }

        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.clickWithJavaScript(educationAndTrainingPage.addButton);

        String certificate1 = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_HEADER_DATESET1, "Certificate");
        BasePage.clickWithJavaScript(educationAndTrainingPage.certificateDropdown);
        BasePage.waitUntilElementPresent(getDropdownOptionXpath(certificate1), 20);
        BasePage.clickWithJavaScript(getDropdownOptionXpath(certificate1));

        String validUntil1 = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_HEADER_DATESET1, "ValidUntil");
        BasePage.clickWithJavaScript(educationAndTrainingPage.validUntilInput);
        genericUtils.selectDateFromCalendarPopup(validUntil1);

        String uploadCertificate1 = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_HEADER_DATESET1, "UploadCertificate");
        String absoluteFilePath1 = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                + File.separator + "resources" + File.separator + "Upload" + File.separator + "Worker" + File.separator
                + uploadCertificate1;
        BasePage.uploadFile(educationAndTrainingPage.uploadCertificate, absoluteFilePath1);

        // click on the Add button
        BasePage.clickWithJavaScript(educationAndTrainingPage.addButton);
        verifySuccessMessage();
        verifyCertificateValidityStatus();

        //enter second set of data
        BasePage.clickWithJavaScript(educationAndTrainingPage.addNewButton);
        String certificate2 = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_HEADER_DATESET2, "Certificate");
        BasePage.clickWithJavaScript(educationAndTrainingPage.certificateDropdown);
        BasePage.waitUntilElementPresent(getDropdownOptionXpath(certificate2), 20);
        BasePage.clickWithJavaScript(getDropdownOptionXpath(certificate2));

        String validUntil2 = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_HEADER_DATESET2, "ValidUntil");
        BasePage.clickWithJavaScript(educationAndTrainingPage.validUntilInput);
        genericUtils.selectDateFromCalendarPopup(validUntil2);

        String uploadCertificate2 = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_HEADER_DATESET2, "UploadCertificate");
        String absoluteFilePath2 = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                + File.separator + "resources" + File.separator + "Upload" + File.separator + "Worker" + File.separator
                + uploadCertificate2;
        BasePage.uploadFile(educationAndTrainingPage.uploadCertificate, absoluteFilePath2);

        BasePage.clickWithJavaScript(educationAndTrainingPage.addButton);
        verifySuccessMessage();
        verifyCertificateValidityStatus();

        //moving to the next section
        BasePage.clickWithJavaScript(educationAndTrainingPage.updateButton);
        BasePage.waitUntilElementPresent(educationAndTrainingPage.saveButton, 60);
        BasePage.clickWithJavaScript(educationAndTrainingPage.saveButton);

        //move to next section
        verifySaveButtonSuccessMessage();
    }

    private String getDropdownOptionXpath(String city) {
        return String.format("//nb-option[contains(text(),'%s')]", city);
    }

    private void verifySuccessMessage() {
        BasePage.waitUntilElementPresent(educationAndTrainingPage.successMessage, 90);
        String actualInLowerCase = BasePage.getText(educationAndTrainingPage.successMessage).toLowerCase().trim();
        String expected = "Record created successfully";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Unable to add certificate!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(educationAndTrainingPage.successMessage, 20);
    }

    private void verifyCertificateValidityStatus() {
        String actual = BasePage.getText(educationAndTrainingPage.certificateValidityStatus).trim();
        String expected = "Certificate Valid";
        assertThat("Certificate Validity Status is not loading correctly!", actual, is(expected));
    }

    private void verifySaveButtonSuccessMessage() {
        BasePage.waitUntilElementPresent(educationAndTrainingPage.successMessage, 90);
        String actualInLowerCase = BasePage.getText(educationAndTrainingPage.successMessage).toLowerCase().trim();
        String expected = "Certificates updated successfully.";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Unable to add certificate!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(educationAndTrainingPage.successMessage, 20);
    }
}