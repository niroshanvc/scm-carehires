package com.carehires.actions.workers;

import com.carehires.common.GlobalVariables;
import com.carehires.pages.worker.WorkerEducationAndTrainingPage;
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

public class WorkerEducationAndTrainingActions {

    WorkerEducationAndTrainingPage educationAndTrainingPage;
    private final GenericUtils genericUtils;

    {
        try {
            genericUtils = new GenericUtils();
        } catch (BasePage.WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
    }

    private static final WorkerNavigationMenuActions navigationMenu = new WorkerNavigationMenuActions();

    private static final String ENTITY = "worker";
    private static final String YML_FILE = "worker-create";
    private static final String EDIT_YML_FILE = "worker-edit";
    private static final String YML_HEADER = "Education and Training";
    private static final String YML_HEADER_DATASET1 = "Dataset1";
    private static final String YML_HEADER_DATASET2 = "Dataset2";
    private static final String ADD = "Add";
    private static final String UPDATE = "Update";

    private static final Logger logger = LogManager.getFormatterLogger(WorkerEducationAndTrainingActions.class);

    Integer incrementValue;

    public WorkerEducationAndTrainingActions() {
        educationAndTrainingPage = new WorkerEducationAndTrainingPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), educationAndTrainingPage);
        } catch (BasePage.WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
    }

    public void enterDataForEducationAndTraining() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Education and Training Data >>>>>>>>>>>>>>>>>>>>");

        // Retrieve the incremented value
        incrementValue = GlobalVariables.getVariable("worker_incrementValue", Integer.class);

        // Check for null or default value
        if (incrementValue == null) {
            throw new NullPointerException("Increment value for worker is not set in GlobalVariables.");
        }

        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.clickWithJavaScript(educationAndTrainingPage.addButton);

        //enter first set of data
        addEducationAndTrainingEntry(YML_FILE, ADD, YML_HEADER_DATASET1);
        verifySuccessMessage();
        verifyCertificateValidityStatus();

        //enter second set of data
        BasePage.clickWithJavaScript(educationAndTrainingPage.addNewButton);
        addEducationAndTrainingEntry(YML_FILE, ADD, YML_HEADER_DATASET2);
        verifySuccessMessage();
        verifyCertificateValidityStatus();

        //moving to the next section
        BasePage.clickWithJavaScript(educationAndTrainingPage.updateButton);
        markAsCareAcademyIssuedCheckbox1();
        BasePage.waitUntilElementPresent(educationAndTrainingPage.saveButton, 60);
        BasePage.clickWithJavaScript(educationAndTrainingPage.saveButton);

        //move to next section
        verifySaveButtonSuccessMessage();
    }

    private void addEducationAndTrainingEntry(String ymlFile, String subHeader, String dataset) {
        String certificate1 = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, dataset, "Certificate");
        BasePage.clickWithJavaScript(educationAndTrainingPage.certificateDropdown);
        BasePage.waitUntilElementPresent(getDropdownOptionXpath(certificate1), 20);
        BasePage.clickWithJavaScript(getDropdownOptionXpath(certificate1));

        String validUntil1 = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, dataset, "ValidUntil");
        BasePage.clickWithJavaScript(educationAndTrainingPage.validUntilInput);
        genericUtils.selectDateFromCalendarPopup(validUntil1);

        String uploadCertificate1 = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, dataset, "UploadCertificate");
        String absoluteFilePath1 = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                + File.separator + "resources" + File.separator + "Upload" + File.separator + "Worker" + File.separator
                + uploadCertificate1;
        BasePage.uploadFile(educationAndTrainingPage.uploadCertificate, absoluteFilePath1);

        // click on the Add button
        BasePage.clickWithJavaScript(educationAndTrainingPage.addButton);
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

    public void updateEducationAndTraining() {
        navigationMenu.gotoCertificatesPage();
        BasePage.waitUntilPageCompletelyLoaded();
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Education and Training Data - Enter Data>>>>>>>>>>>>>>>>>>>>");
        // Retrieve the incremented value
        incrementValue = GlobalVariables.getVariable("worker_incrementValue", Integer.class);
        BasePage.clickWithJavaScript(educationAndTrainingPage.addButton);

        //enter first set of data
        addEducationAndTrainingEntry(EDIT_YML_FILE, ADD, YML_HEADER_DATASET1);
        verifySuccessMessage();
        verifyCertificateValidityStatus();

        //enter second set of data
        BasePage.clickWithJavaScript(educationAndTrainingPage.addNewButton);
        addEducationAndTrainingEntry(EDIT_YML_FILE, ADD, YML_HEADER_DATASET2);
        verifySuccessMessage();
        verifyCertificateValidityStatus();

        // remove first certificate and upload new one
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Education and Training Data - Update Data>>>>>>>>>>>>>>>>>>>>");
        BasePage.clickWithJavaScript(educationAndTrainingPage.updateButton);
        removeCertificate(educationAndTrainingPage.deleteIcon1);
        BasePage.clickWithJavaScript(educationAndTrainingPage.addNewButton);
        addEducationAndTrainingEntry(EDIT_YML_FILE, UPDATE, YML_HEADER_DATASET1);
        verifySuccessMessage();
        verifyCertificateValidityStatus();

        // remove second certificate and upload new one
        removeCertificate(educationAndTrainingPage.deleteIcon2);
        BasePage.clickWithJavaScript(educationAndTrainingPage.addNewButton);
        addEducationAndTrainingEntry(EDIT_YML_FILE, UPDATE, YML_HEADER_DATASET2);
        verifySuccessMessage();
        verifyCertificateValidityStatus();

        // click on save button
        markAsCareAcademyIssuedCheckbox1();
        BasePage.clickWithJavaScript(educationAndTrainingPage.saveButton);
        verifySaveButtonSuccessMessage();
    }

    // remove uploaded certificate
    private void removeCertificate(WebElement element) {
        BasePage.waitUntilElementClickable(element, 60);
        BasePage.clickWithJavaScript(element);
        BasePage.waitUntilElementClickable(educationAndTrainingPage.deleteButton, 30);
        BasePage.clickWithJavaScript(educationAndTrainingPage.deleteButton);
        verifyCertificateRemoveMessage();
    }

    // success message: Record created successfully
    private void verifyCertificateRemoveMessage() {
        BasePage.waitUntilElementPresent(educationAndTrainingPage.successMessage, 90);
        String actualInLowerCase = BasePage.getText(educationAndTrainingPage.successMessage).toLowerCase().trim();
        String expected = "Removal request submitted";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Certificate not removed!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(educationAndTrainingPage.successMessage, 20);
    }

    private void markAsCareAcademyIssuedCheckbox1() {
        BasePage.clickWithJavaScript(educationAndTrainingPage.caIssuedCheckbox1);
    }
}