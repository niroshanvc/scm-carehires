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
import static org.hamcrest.Matchers.is;

public class WorkerDocumentsAndProofActions {

    private final WorkerDocumentsAndProofPage documentsAndProofPage;

    private static final String ENTITY = "worker";
    private static final String YML_FILE = "worker-create";
    private static final String EDIT_YML_FILE = "worker-edit";
    private static final String YML_FILE_SMOKE = "worker-create-smoke";
    private static final String YML_FILE_NON_BRITISH = "scenario-non-British-worker";
    private static final String YML_HEADER = "Documents and Proof";
    private static final String YML_HEADER_BASIC_INFO = "Basic Information";
    private static final String YML_SUB_HEADER_5 = "Passport Visa DBS Information";

    private static final String ADD = "Add";
    private static final String UPDATE = "Update";
    private static final String YML_SUB_HEADER_DOCUMENT = "Document";
    private static final String YML_SUB_HEADER_EXPIRY_DATE = "ExpiryDate";
    private static final String DRIVING_LICENCE = "DrivingLicence";
    private static final String WORKER_DOCUMENTS_PATH = System.getProperty("user.dir") + File.separator + "src"
            + File.separator + "test" + File.separator + "resources" + File.separator + "Upload" + File.separator +
            "Worker" + File.separator;

    private static final Logger logger = LogManager.getLogger(WorkerDocumentsAndProofActions.class);

    private static final GenericUtils genericUtils;

    static {
        try {
            genericUtils = new GenericUtils();
        } catch (BasePage.WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
    }

    private static final WorkerNavigationMenuActions navigationMenu = new WorkerNavigationMenuActions();
    Integer incrementValue;

    public WorkerDocumentsAndProofActions() {
        documentsAndProofPage = new WorkerDocumentsAndProofPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), documentsAndProofPage);
        } catch (BasePage.WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
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

        enterCvInfo(YML_FILE, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.cvFileStatus);

        enterDbsCertificateInfo(YML_FILE, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.dbsCertificateStatus);

        enterPassportDocument(YML_FILE, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.passportDocumentStatus);

        enterNationalInsuranceDocument(YML_FILE, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.nationalInsuranceStatus);

        enterProofOfAddress(YML_FILE, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.proofOfAddressStatus);

        enterDrivingLicence(YML_FILE, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.drivingLicenceStatus);

        BasePage.clickWithJavaScript(documentsAndProofPage.saveButton);
        verifySuccessMessage();
    }

    public void enterDocumentsAndProofDataForSmoke() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<<< Entering Document and Proof Information >>>>>>>>>>>>>>>>>>>>>");
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

        enterCvInfo(YML_FILE_SMOKE, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.cvFileStatus);

        enterDbsCertificateInfo(YML_FILE_SMOKE, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.dbsCertificateStatus);

        enterPassportDocument(YML_FILE_SMOKE, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.passportDocumentStatus);

        enterNationalInsuranceDocument(YML_FILE_SMOKE, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.nationalInsuranceStatus);

        enterProofOfAddress(YML_FILE_SMOKE, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.proofOfAddressStatus);

        enterDrivingLicence(YML_FILE_SMOKE, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.drivingLicenceStatus);

        BasePage.clickWithJavaScript(documentsAndProofPage.saveButton);
        verifySuccessMessage();
    }

    private void enterCvInfo(String ymlFile, String subHeader) {
        String cdvDoc = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader,
                "CV", YML_SUB_HEADER_DOCUMENT);
        String cvFile = WORKER_DOCUMENTS_PATH + cdvDoc;
        BasePage.uploadFile(documentsAndProofPage.cvSelectFile, cvFile);
    }

    private void enterDbsCertificateInfo(String ymlFile, String subHeader) {
        String dbsDoc = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader,
                "DBSCertificate", YML_SUB_HEADER_DOCUMENT);
        String dbsExpiryDate = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                subHeader, "DBSCertificate", YML_SUB_HEADER_EXPIRY_DATE);
        String dbsFile = WORKER_DOCUMENTS_PATH + dbsDoc;
        BasePage.clickWithJavaScript(documentsAndProofPage.dbsCertificateExpiryDate);
        genericUtils.selectDateFromCalendarPopup(dbsExpiryDate);
        BasePage.uploadFile(documentsAndProofPage.dbsCertificateSelectFile, dbsFile);
    }

    private void enterPassportDocument(String ymlFile, String subHeader) {
        String passportDoc = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                subHeader, "Passport", YML_SUB_HEADER_DOCUMENT);
        String passportExpiryDate = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                subHeader, "Passport", YML_SUB_HEADER_EXPIRY_DATE);
        String passportFile = WORKER_DOCUMENTS_PATH + passportDoc;
        BasePage.clickWithJavaScript(documentsAndProofPage.passportDocumentExpiryDate);
        genericUtils.selectDateFromCalendarPopup(passportExpiryDate);
        BasePage.uploadFile(documentsAndProofPage.passportDocumentFile, passportFile);
    }

    private void enterNationalInsuranceDocument(String ymlFile, String subHeader) {
        String nationalInsuranceDoc = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                subHeader, "NationalInsurance", YML_SUB_HEADER_DOCUMENT);
        String nationalInsuranceFile = WORKER_DOCUMENTS_PATH + nationalInsuranceDoc;
        BasePage.uploadFile(documentsAndProofPage.nationalInsuranceSelectFile, nationalInsuranceFile);
    }

    private void enterProofOfAddress(String ymlFile, String subHeader) {
        String proofOfAddressDoc = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                subHeader, "ProofOfAddress", YML_SUB_HEADER_DOCUMENT);
        String proofOfAddressFile = WORKER_DOCUMENTS_PATH + proofOfAddressDoc;
        BasePage.uploadFile(documentsAndProofPage.proofOfAddressSelectFile, proofOfAddressFile);
    }

    private void enterDrivingLicence(String ymlFile, String subHeader) {
        String drivingLicenseDoc = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader,
                DRIVING_LICENCE, YML_SUB_HEADER_DOCUMENT);
        String drivingLicenseExpiryDate = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                subHeader, DRIVING_LICENCE, YML_SUB_HEADER_EXPIRY_DATE);
        String drivingLicenseFile = WORKER_DOCUMENTS_PATH + drivingLicenseDoc;
        BasePage.clickWithJavaScript(documentsAndProofPage.drivingLicenceExpiryDate);
        genericUtils.selectDateFromCalendarPopup(drivingLicenseExpiryDate);
        BasePage.uploadFile(documentsAndProofPage.drivingLicenceSelectFile, drivingLicenseFile);
    }

    private void enterVisaWorkPermitProof(String ymlFile, String subHeader) {
        String doc = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader,
                "VisaWorkPermit", YML_SUB_HEADER_DOCUMENT);
        String expiryDate = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader,
                "VisaWorkPermit", YML_SUB_HEADER_EXPIRY_DATE);
        String file = WORKER_DOCUMENTS_PATH + doc;
        BasePage.clickWithJavaScript(documentsAndProofPage.visaExpiryDate);
        genericUtils.selectDateFromCalendarPopup(expiryDate);
        BasePage.uploadFile(documentsAndProofPage.visaSelectFile, file);
    }

    private void enterCovid19(String ymlFile, String subHeader) {
        String doc = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader,
                "Covid19", YML_SUB_HEADER_DOCUMENT);
        String file = WORKER_DOCUMENTS_PATH + doc;
        BasePage.uploadFile(documentsAndProofPage.covid19SelectFile, file);
    }

    private void enterCosDocument(String ymlFile, String subHeader) {
        String doc = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader,
                "CosDocument", YML_SUB_HEADER_DOCUMENT);
        String expiryDate = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader,
                "CosDocument", YML_SUB_HEADER_EXPIRY_DATE);
        String file = WORKER_DOCUMENTS_PATH + doc;
        BasePage.clickWithJavaScript(documentsAndProofPage.cosDocumentExpiryDate);
        genericUtils.selectDateFromCalendarPopup(expiryDate);
        BasePage.uploadFile(documentsAndProofPage.cosDocumentSelectFile, file);
    }

    private void enterPvgCertificate(String ymlFile, String subHeader) {
        String doc = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader,
                "PvgCertificate", YML_SUB_HEADER_DOCUMENT);
        String file = WORKER_DOCUMENTS_PATH + doc;
        BasePage.uploadFile(documentsAndProofPage.pvgCertificateSelectFile, file);
    }

    private void enterRegulatorySettings(String ymlFile, String subHeader) {
        String doc = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader,
                "RegulatorySettings", YML_SUB_HEADER_DOCUMENT);
        String file = WORKER_DOCUMENTS_PATH + doc;
        BasePage.uploadFile(documentsAndProofPage.regulatorySettingsSelectFile, file);
    }

    private void verifyDocumentUploadedSuccessfully(WebElement fileStatus) {
        BasePage.waitUntilElementAttributeGetChanged(fileStatus, "status", "success",
                60);
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
        assertThat("Documents are not uploaded!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(documentsAndProofPage.successMessage, 20);
    }

    public void updateDocumentsAndProof() {
        navigationMenu.gotoDocumentsPage();
        BasePage.waitUntilPageCompletelyLoaded();
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Document and Proof Information - in Add >>>>>>>>>>>>>>>>>>>>");
        // Retrieve the incremented value
        incrementValue = GlobalVariables.getVariable("worker_incrementValue", Integer.class);
        BasePage.genericWait(3000);
        BasePage.clickWithJavaScript(documentsAndProofPage.updateButton);

        enterCvInfo(EDIT_YML_FILE, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.cvFileStatus);

        enterDbsCertificateInfo(EDIT_YML_FILE, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.dbsCertificateStatus);

        enterPassportDocument(EDIT_YML_FILE, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.passportDocumentStatus);

        enterNationalInsuranceDocument(EDIT_YML_FILE, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.nationalInsuranceStatus);

        enterProofOfAddress(EDIT_YML_FILE, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.proofOfAddressStatus);

        String drivingLicence = DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE,
                YML_HEADER_BASIC_INFO, "Travel Information", UPDATE, DRIVING_LICENCE);
        assert drivingLicence != null;
        if (drivingLicence.equalsIgnoreCase("Yes")) {
            enterDrivingLicence(EDIT_YML_FILE, ADD);
            verifyDocumentUploadedSuccessfully(documentsAndProofPage.drivingLicenceStatus);
        }

        String nationality = DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE,
                YML_HEADER_BASIC_INFO, "Personal Information", UPDATE, "Nationality");
        assert nationality != null;
        if (!nationality.equalsIgnoreCase("British")) {
            enterVisaWorkPermitProof(EDIT_YML_FILE, ADD);
            verifyDocumentUploadedSuccessfully(documentsAndProofPage.visaStatus);
        }

        enterCovid19(EDIT_YML_FILE, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.covid19Status);

        String visaType = DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE,
                YML_HEADER_BASIC_INFO, YML_SUB_HEADER_5, UPDATE, "VisaType");
        assert visaType != null;
        if (visaType.equalsIgnoreCase("Health and care worker visa") || visaType.equalsIgnoreCase(
                "Skilled Worker")) {
            enterCosDocument(EDIT_YML_FILE, ADD);
            verifyDocumentUploadedSuccessfully(documentsAndProofPage.cosDocumentStatus);
        }

        enterPvgCertificate(EDIT_YML_FILE, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.pvgCertificateStatus);

        enterRegulatorySettings(EDIT_YML_FILE, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.regulatorySettingsStatus);

        BasePage.clickWithJavaScript(documentsAndProofPage.saveButton);
        verifySuccessMessage();

        navigationMenu.gotoDocumentsPage();
        BasePage.waitUntilPageCompletelyLoaded();
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Document and Proof Information - in Update >>>>>>>>>>>>>>>>>>>>");
        BasePage.genericWait(4000);
        BasePage.clickWithJavaScript(documentsAndProofPage.updateButton);

        //remove CV and upload a new doc
        removeDocument(documentsAndProofPage.cvFileSDeleteIcon);
        enterCvInfo(EDIT_YML_FILE, UPDATE);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.cvFileStatus);

        //remove  DWB doc and upload a new doc
        removeDocument(documentsAndProofPage.dbsCertificateDeleteIcon);
        enterDbsCertificateInfo(EDIT_YML_FILE, UPDATE);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.dbsCertificateStatus);

        //remove passport doc and upload a new doc
        removeDocument(documentsAndProofPage.passportDocumentDeleteIcon);
        enterPassportDocument(EDIT_YML_FILE, UPDATE);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.passportDocumentStatus);

        //remove national insurance doc and upload a new doc
        removeDocument(documentsAndProofPage.nationalInsuranceDeleteIcon);
        enterNationalInsuranceDocument(EDIT_YML_FILE, UPDATE);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.nationalInsuranceStatus);

        //remove proof of address doc and upload a new doc
        removeDocument(documentsAndProofPage.proofOfAddressDeleteIcon);
        enterProofOfAddress(EDIT_YML_FILE, UPDATE);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.proofOfAddressStatus);

        //remove driving licence doc and upload a new doc
        drivingLicence = DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, YML_HEADER_BASIC_INFO, "Travel Information", UPDATE, DRIVING_LICENCE);
        assert drivingLicence != null;
        if (drivingLicence.equalsIgnoreCase("Yes")) {
            removeDocument(documentsAndProofPage.drivingLicenceDeleteIcon);
            enterDrivingLicence(EDIT_YML_FILE, UPDATE);
            verifyDocumentUploadedSuccessfully(documentsAndProofPage.drivingLicenceStatus);
        }

        //remove visa/ work permit doc and upload a new doc
        nationality = DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, YML_HEADER_BASIC_INFO, "Personal Information", UPDATE, "Nationality");
        assert nationality != null;
        if (!nationality.equalsIgnoreCase("British")) {
            removeDocument(documentsAndProofPage.visaDeleteIcon);
            enterVisaWorkPermitProof(EDIT_YML_FILE, UPDATE);
            verifyDocumentUploadedSuccessfully(documentsAndProofPage.visaStatus);
        }

        //remove covid-19 doc and upload a new doc
        removeDocument(documentsAndProofPage.covid19DeleteIcon);
        enterCovid19(EDIT_YML_FILE, UPDATE);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.covid19Status);

        //remove COS doc and upload a new doc
        visaType = DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, YML_HEADER_BASIC_INFO, YML_SUB_HEADER_5, UPDATE, "VisaType");
        assert visaType != null;
        if (visaType.equalsIgnoreCase("Health and care worker visa") || visaType.equalsIgnoreCase("Skilled Worker")) {
            removeDocument(documentsAndProofPage.cosDocumentDeleteIcon);
            enterCosDocument(EDIT_YML_FILE, UPDATE);
            verifyDocumentUploadedSuccessfully(documentsAndProofPage.cosDocumentStatus);
        }
        //remove PVG doc and upload a new doc
        removeDocument(documentsAndProofPage.pvgCertificateDeleteIcon);
        enterPvgCertificate(EDIT_YML_FILE, UPDATE);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.pvgCertificateStatus);

        //remove regulatory settings doc and upload a new doc
        removeDocument(documentsAndProofPage.regulatorySettingsDeleteIcon);
        enterRegulatorySettings(EDIT_YML_FILE, UPDATE);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.regulatorySettingsStatus);

        BasePage.clickWithJavaScript(documentsAndProofPage.saveButton);
        verifySuccessMessage();
    }

    private void removeDocument(WebElement removeIcon) {
        BasePage.waitUntilElementClickable(removeIcon, 30);
        BasePage.clickWithJavaScript(removeIcon);
        BasePage.waitUntilElementClickable(documentsAndProofPage.deleteButton, 30);
        BasePage.clickWithJavaScript(documentsAndProofPage.deleteButton);
    }

    public void moveToBasicInformationStep() {
        BasePage.genericWait(5000);
        BasePage.waitUntilElementPresent(documentsAndProofPage.basicInformationStep, 60);
        BasePage.clickWithJavaScript(documentsAndProofPage.basicInformationStep);
        BasePage.genericWait(5000);
    }

    public void enterDocumentsAndProofForNonBritishWorker() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<< Entering Document and Proof Information >>>>>>>>>>>>>>>>>>>>>>");
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

        enterCvInfo(YML_FILE_NON_BRITISH, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.cvFileStatus);

        enterDbsCertificateInfo(YML_FILE_NON_BRITISH, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.dbsCertificateStatus);

        enterPassportDocument(YML_FILE_NON_BRITISH, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.passportDocumentStatus);

        enterNationalInsuranceDocument(YML_FILE_NON_BRITISH, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.nationalInsuranceStatus);

        enterProofOfAddress(YML_FILE_NON_BRITISH, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.proofOfAddressStatus);

        enterDrivingLicence(YML_FILE_NON_BRITISH, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.drivingLicenceStatus);

        enterVisaWorkPermitProof(YML_FILE_NON_BRITISH, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.visaStatus);

        enterCosDocument(YML_FILE_NON_BRITISH, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.cosDocumentStatus);

        BasePage.clickWithJavaScript(documentsAndProofPage.saveButton);
        verifySuccessMessage();
    }

    public void uploadDocumentsForNonBritishStudentWorker() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<< Entering Document and Proof Information >>>>>>>>>>>>>>>>>>>>>>>>");
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

        enterCvInfo(YML_FILE_NON_BRITISH, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.cvFileStatus);

        enterDbsCertificateInfo(YML_FILE_NON_BRITISH, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.dbsCertificateStatus);

        enterPassportDocument(YML_FILE_NON_BRITISH, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.passportDocumentStatus);

        enterNationalInsuranceDocument(YML_FILE_NON_BRITISH, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.nationalInsuranceStatus);

        enterProofOfAddress(YML_FILE_NON_BRITISH, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.proofOfAddressStatus);

        enterStudentTimeTable(YML_FILE_NON_BRITISH, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.proofOfAddressStatus);

        enterDrivingLicence(YML_FILE_NON_BRITISH, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.drivingLicenceStatus);

        enterVisaWorkPermitProof(YML_FILE_NON_BRITISH, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.visaStatus);

        BasePage.clickWithJavaScript(documentsAndProofPage.saveButton);
        verifySuccessMessage();
    }

    private void enterStudentTimeTable(String ymlFile, String subHeader) {
        String timeTableDoc = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                subHeader, "StudentTimetable", YML_SUB_HEADER_DOCUMENT);
        String proofOfAddressFile = WORKER_DOCUMENTS_PATH + timeTableDoc;
        BasePage.uploadFile(documentsAndProofPage.studentTimeTableSelectFile, proofOfAddressFile);
    }

    public void uploadDocumentAndProofForNonBritishWorker() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<< Entering Document and Proof Information >>>>>>>>>>>>>>>>>>>>>>>");
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

        enterCvInfo(YML_FILE_NON_BRITISH, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.cvFileStatus);

        enterDbsCertificateInfo(YML_FILE_NON_BRITISH, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.dbsCertificateStatus);

        enterPassportDocument(YML_FILE_NON_BRITISH, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.passportDocumentStatus);

        enterNationalInsuranceDocument(YML_FILE_NON_BRITISH, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.nationalInsuranceStatus);

        enterProofOfAddress(YML_FILE_NON_BRITISH, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.proofOfAddressStatus);

        enterDrivingLicence(YML_FILE_NON_BRITISH, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.drivingLicenceStatus);

        enterVisaWorkPermitProof(YML_FILE_NON_BRITISH, ADD);
        verifyDocumentUploadedSuccessfully(documentsAndProofPage.visaStatus);

        BasePage.clickWithJavaScript(documentsAndProofPage.saveButton);
        verifySuccessMessage();
    }
}
