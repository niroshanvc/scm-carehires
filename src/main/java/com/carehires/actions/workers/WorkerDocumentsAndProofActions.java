package com.carehires.actions.workers;

import com.carehires.common.GlobalVariables;
import com.carehires.pages.worker.WorkerDocumentsAndProofPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import com.carehires.utils.GenericUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class WorkerDocumentsAndProofActions {

    private final WorkerDocumentsAndProofPage documentsAndProofPage;

    private static final String ENTITY = "worker";
    private static final String YML_FILE = "worker-create";
    private static final String YML_HEADER = "Documents and Proof";
    private static final String YML_SUB_HEADER_DOCUMENT = "Document";
    private static final String YML_SUB_HEADER_EXPIRY_DATE = "ExpiryDate";
    private static final String WORKER_DOCUMENTS_PATH = System.getProperty("user.dir") + File.separator + "src"
            + File.separator + "test" + File.separator + "resources" + File.separator + "Upload" + File.separator + "Worker" + File.separator;

    private static final Logger logger = LogManager.getLogger(WorkerDocumentsAndProofActions.class);

    private static final GenericUtils genericUtils = new GenericUtils();
    private static final WorkerNavigationMenuActions navigationMenu = new WorkerNavigationMenuActions();
    Integer incrementValue;

    public WorkerDocumentsAndProofActions() {
        documentsAndProofPage = new WorkerDocumentsAndProofPage();
        PageFactory.initElements(BasePage.getDriver(), documentsAndProofPage);
    }

    public void enterDocumentsAndProofData() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Document and Proof Information >>>>>>>>>>>>>>>>>>>>");
        // Retrieve the incremented value
        incrementValue = GlobalVariables.getVariable("worker_incrementValue", Integer.class);

        // Check for null or default value
        if (incrementValue == null) {
            throw new NullPointerException("Increment value for worker is not set in GlobalVariables.");
        }

        BasePage.waitUntilPageCompletelyLoaded();

        // verify document upload field is available
        By drivingLicenceTextField = By.xpath(WorkerDocumentsAndProofPage.DRIVING_LICENCE_FIELD_XPATH);
        BasePage.verifyElementIsPresentAfterWait(drivingLicenceTextField, 60);

        // enter cv info
        String cdvDoc = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "CV", YML_SUB_HEADER_DOCUMENT);
        String cvFile = WORKER_DOCUMENTS_PATH + cdvDoc;
        BasePage.uploadFile(documentsAndProofPage.cvSelectFile, cvFile);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.cvFileStatus);

        // enter DBS Certificate info
        String dbsDoc = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "DBSCertificate", YML_SUB_HEADER_DOCUMENT);
        String dbsExpiryDate = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "DBSCertificate", YML_SUB_HEADER_EXPIRY_DATE);
        String dbsFile = WORKER_DOCUMENTS_PATH + dbsDoc;
        BasePage.clickWithJavaScript(documentsAndProofPage.dbsCertificateExpiryDate);
        genericUtils.selectDateFromCalendarPopup(dbsExpiryDate);
        BasePage.uploadFile(documentsAndProofPage.dbsCertificateSelectFile, dbsFile);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.dbsCertificateStatus);

        // enter passport
        String passportDoc = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Passport", YML_SUB_HEADER_DOCUMENT);
        String passportExpiryDate = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Passport", YML_SUB_HEADER_EXPIRY_DATE);
        String passportFile = WORKER_DOCUMENTS_PATH + passportDoc;
        BasePage.clickWithJavaScript(documentsAndProofPage.passportDocumentExpiryDate);
        genericUtils.selectDateFromCalendarPopup(passportExpiryDate);
        BasePage.uploadFile(documentsAndProofPage.passportDocumentFile, passportFile);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.passportDocumentStatus);

        // enter national insurance
        String nationalInsuranceDoc = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "NationalInsurance", YML_SUB_HEADER_DOCUMENT);
        String nationalInsuranceFile = WORKER_DOCUMENTS_PATH + nationalInsuranceDoc;
        BasePage.uploadFile(documentsAndProofPage.nationalInsuranceSelectFile, nationalInsuranceFile);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.nationalInsuranceStatus);

        // enter proof of address
        String proofOfAddressDoc = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "ProofOfAddress", YML_SUB_HEADER_DOCUMENT);
        String proofOfAddressFile = WORKER_DOCUMENTS_PATH + proofOfAddressDoc;
        BasePage.uploadFile(documentsAndProofPage.proofOfAddressSelectFile, proofOfAddressFile);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.proofOfAddressStatus);

        // enter driving license
        String drivingLicenseDoc = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "DrivingLicence", YML_SUB_HEADER_DOCUMENT);
        String drivingLicenseExpiryDate = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "DrivingLicence", YML_SUB_HEADER_EXPIRY_DATE);
        String drivingLicenseFile = WORKER_DOCUMENTS_PATH + drivingLicenseDoc;
        BasePage.clickWithJavaScript(documentsAndProofPage.drivingLicenceExpiryDate);
        genericUtils.selectDateFromCalendarPopup(drivingLicenseExpiryDate);
        BasePage.uploadFile(documentsAndProofPage.drivingLicenceSelectFile, drivingLicenseFile);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.drivingLicenceStatus);

        BasePage.clickWithJavaScript(documentsAndProofPage.saveButton);
        verifySuccessMessage();
    }

    private void verifyDocumentUploadedSuccessfully(WebElement fileStatus) {
        BasePage.waitUntilElementAttributeGetChanged(fileStatus, "status", "success", 60);
        String expectedStatus = "success";
        String actualStatus = BasePage.getAttributeValue(fileStatus, "status");
        assertThat("Document not uploaded", actualStatus, is(expectedStatus));
    }

    // success message: Record created successfully
    private void verifySuccessMessage() {
        BasePage.waitUntilElementPresent(documentsAndProofPage.successMessage, 90);
        String actualInLowerCase = BasePage.getText(documentsAndProofPage.successMessage).toLowerCase().trim();
        String expected = "Record created successfully";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Document and proof success message is wrong!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(documentsAndProofPage.successMessage, 20);
    }

    public void updateDocumentsAndProof() {
        navigationMenu.gotoDocumentsPage();
        BasePage.waitUntilPageCompletelyLoaded();
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Document and Proof Information - in Edit >>>>>>>>>>>>>>>>>>>>");
        // Retrieve the incremented value
        incrementValue = GlobalVariables.getVariable("worker_incrementValue", Integer.class);
        BasePage.genericWait(3000);
        BasePage.clickWithJavaScript(documentsAndProofPage.updateButton);
    }
}
