package com.carehires.actions.agreements;

import com.carehires.pages.agreements.ViewAgreementOverviewPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ViewAgreementOverviewActions {

    ViewAgreementOverviewPage viewAgreementOverviewPage;

    private static final String ENTITY = "agreement";
    private static final String YML_FILE = "agreement-create";
    private static final String YML_HEADER = "Mark As Signed";
    private static final String YML_HEADER_SIGNATORIES = "Signatories";
    private static final String YML_HEADER_AGENCY = "Agency";
    private static final String YML_HEADER_PROVIDER = "Provider";
    private static final String ADD = "Add";
    private static final String RESOURCE_FOLDER = System.getProperty("user.dir") + File.separator + "src" + File.separator
            + "test" + File.separator + "resources";
    private static final String VALUE_ATTRIBUTE = "value";

    private static final Logger logger = LogManager.getLogger(ViewAgreementOverviewActions.class);


    public ViewAgreementOverviewActions() {
        viewAgreementOverviewPage = new ViewAgreementOverviewPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), viewAgreementOverviewPage);
        } catch (BasePage.WebDriverInitializationException e) {
            logger.error("Error while initializing Agreement Overview Page elements: {}", e.getMessage());
        }
    }
    public void verifyAgreementPaymentStatusAndSignatureStatus(String paymentStatus, String signatureStatus) {
        verifyPaymentStatus(paymentStatus);
        verifySignatureStatus(signatureStatus);
    }

    public void verifyAgreementSignatureStatus(String signatureStatus) {
        verifySignatureStatus(signatureStatus);
    }

    private void verifySignatureStatus(String expected) {
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.refreshPage();
        BasePage.genericWait(3000);
        BasePage.waitUntilPageCompletelyLoaded();
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying signature status >>>>>>>>>>>>>>>>>>>>");
        String actual = "";
        if (expected.equalsIgnoreCase("PENDING TO SIGN")) {
            BasePage.waitUntilElementPresent(viewAgreementOverviewPage.signatureStatus, 60);
            actual = BasePage.getText(viewAgreementOverviewPage.signatureStatus).trim().toUpperCase();
        } else if (expected.equalsIgnoreCase("SIGNED") || expected.equalsIgnoreCase("ACTIVE")) {
            BasePage.waitUntilElementPresent(viewAgreementOverviewPage.signatureStatusSigned.get(0), 60);
            int size = viewAgreementOverviewPage.signatureStatusSigned.size();
            actual = BasePage.getText(viewAgreementOverviewPage.signatureStatusSigned.get(size-1)).trim().toUpperCase();
        }
        assertThat("Agreement signature status is not correct!", actual, is(expected));
    }

    private void verifyPaymentStatus(String expected) {
        BasePage.waitUntilPageCompletelyLoaded();
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying payment status >>>>>>>>>>>>>>>>>>>>");
        String actual = BasePage.getText(viewAgreementOverviewPage.paymentAuthorizationStatus).trim().toUpperCase();
        assertThat("Agreement payment status is not correct!", actual, is(expected));
    }

    public void clickOnMarkAsSigned() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Clicking on Mark as Signed button >>>>>>>>>>>>>>>>>>>>");
        BasePage.scrollToWebElement(viewAgreementOverviewPage.markAsSignedButton);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.markAsSignedButton);
        verifyProviderNameLoadedInAttachAgreementPopup();
        verifyAgencyNameLoadedInAttachAgreementPopup();
        enterDataForAttachAgreementPopup();
    }

    private void enterDataForAttachAgreementPopup() {
        String doc = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Agreement");
        String absoluteFilePath = RESOURCE_FOLDER + File.separator + "Upload" + File.separator + "Agreement" + File.separator + doc;
        BasePage.uploadFile(viewAgreementOverviewPage.uploadFile, absoluteFilePath);
        waitUntilDocumentUploaded();

        String note = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Note");
        BasePage.typeWithStringBuilder(viewAgreementOverviewPage.attachAgreementNote, note);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.providerTermsAndConditionsCheckbox);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.agencyTermsAndConditionsCheckbox);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.saveButton);

        verifySuccessMessage();
    }

    private void verifySuccessMessage() {
        BasePage.waitUntilElementPresent(viewAgreementOverviewPage.successMessage, 1200);
        String actualInLowerCase = BasePage.getText(viewAgreementOverviewPage.successMessage).toLowerCase().trim();
        String expected = "Contract signed successfully";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Contract signed success message is wrong!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(viewAgreementOverviewPage.successMessage, 60);
    }

    private void waitUntilDocumentUploaded() {
        BasePage.waitUntilElementDisplayed(viewAgreementOverviewPage.previewSubContractDocument, 60);
        BasePage.mouseHoverOverElement(viewAgreementOverviewPage.previewSubContractDocument);
        BasePage.waitUntilElementDisplayed(viewAgreementOverviewPage.removeAttachment, 60);
    }

    private void verifyProviderNameLoadedInAttachAgreementPopup() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying Provider Name displaying in the Attach Agreement popup >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementPresent(viewAgreementOverviewPage.attachAgreementNote, 30);
        String providerFullText = BasePage.getText(viewAgreementOverviewPage.providerTermsAndConditionsText).trim();
        String[] strArr = providerFullText.split("on behalf of");
        String actual = strArr[1].trim();
        String expected = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER_SIGNATORIES, YML_HEADER_PROVIDER, "Name");
        assertThat("Provider name displays incorrectly!", actual, is(expected));
    }

    private void verifyAgencyNameLoadedInAttachAgreementPopup() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying Agency Name displaying in the Attach Agreement popup >>>>>>>>>>>>>>>>>>>>");
        String agencyFullText = BasePage.getText(viewAgreementOverviewPage.agencyTermsAndConditionsText).trim();
        String[] strArr = agencyFullText.split("on behalf of");
        String actual = strArr[1].trim();
        String expected = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER_SIGNATORIES, YML_HEADER_AGENCY, "Name");
        assertThat("Agency name displays incorrectly!", actual, is(expected));
    }

    public void clickOnActiveAgreement() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Clicking on Active Agreement button >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementPresent(viewAgreementOverviewPage.activateAgreementButton, 60);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.activateAgreementButton);
        verifyMarkAsActiveSuccessMessage();
    }

    private void verifyMarkAsActiveSuccessMessage() {

    }

    public void verifyContentsInWorkerRates() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying data displaying in the View Worker Rates Table >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementClickable(viewAgreementOverviewPage.workerRatesViewIcon, 60);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.workerRatesViewIcon);

        //verify worker type
        String expected = getWorkerType();
        String actual = getWorkerTypeFromWorkerRatesPopup();
        assertThat("Worker types are not equal", actual, is(expected));
    }

    private String getWorkerType() {
        String value = BasePage.getText(viewAgreementOverviewPage.workerRatesTableWorkerType).trim();
        return value;
    }

    private List<String> getSkills() {
        List<WebElement> elements = viewAgreementOverviewPage.workerRatesTableWorkerSkill;
        List<String> skills = new ArrayList<>();

        for (WebElement e : elements) {
            skills.add(e.getText().trim());
        }
        return skills;
    }

    private String getWorkerHourlyRate() {
        String value = BasePage.getText(viewAgreementOverviewPage.workerRatesTableWorkerHourlyRate).trim();
        return value;
    }

    private String getAgencyHourlyCostWithVat() {
        String value = BasePage.getText(viewAgreementOverviewPage.workerRatesTableAgencyHourlyCostWithVat).trim();
        return value;
    }

    private String getAgencyHourlyCostWithNoVat() {
        String value = BasePage.getText(viewAgreementOverviewPage.workerRatesTableAgencyHourlyCostWithNoVat).trim();
        return value;
    }

    private String getCareHiresHourlyCost() {
        String value = BasePage.getText(viewAgreementOverviewPage.workerRatesTableCareHiresHourlyCost).trim();
        return value;
    }

    private String getFinalHourlyRateWithVat() {
        String value = BasePage.getText(viewAgreementOverviewPage.workerRatesTableFinalHourlyRateWithVat).trim();
        return value;
    }

    private String getFinalHourlyRateWithNoVat() {
        String value = BasePage.getText(viewAgreementOverviewPage.workerRatesTableFinalHourlyRateWithNoVat).trim();
        return value;
    }

    private String getWorkerTypeFromWorkerRatesPopup() {
        String value = BasePage.getText(viewAgreementOverviewPage.workerRatePopupWorkerType).trim();
        return value;
    }

    private List<String> getSkillsFromWorkerRatesPopup() {
        List<String> skills = Arrays.stream(BasePage.getText(viewAgreementOverviewPage.workerRatePopupSkills).split(",")).toList();
        return skills;
    }

    private String getHourlyRateFromWorkerRatesPopup() {
        String value = BasePage.getAttributeValue(viewAgreementOverviewPage.workerRatePopupHourlyRate, VALUE_ATTRIBUTE).trim();
        return value;
    }

    private String getAgencyMarginFromWorkerRatesPopup() {
        String value = BasePage.getAttributeValue(viewAgreementOverviewPage.workerRatePopupAgencyMargin, VALUE_ATTRIBUTE).trim();
        return value;
    }

    private String getAgencyVatFromWorkerRatesPopup() {
        String value = BasePage.getText(viewAgreementOverviewPage.workerRatePopupAgencyVat).trim();
        return value;
    }

    private String getAgencyCostWithVatFromWorkerRatesPopup() {
        String value = BasePage.getText(viewAgreementOverviewPage.workerRatePopupAgencyCostWithVat).trim();
        return value;
    }

    private String getAgencyCostWithNoVatFromWorkerRatesPopup() {
        String value = BasePage.getText(viewAgreementOverviewPage.workerRatePopupAgencyCostWithNoVat).trim();
        return value;
    }

    private String getChHourlyMarginFromWorkerRatesPopup() {
        String value = BasePage.getAttributeValue(viewAgreementOverviewPage.workerRatePopupChHourlyMargin, VALUE_ATTRIBUTE).trim();
        return value;
    }

    private String getChHourlyVatFromWorkerRatesPopup() {
        String value = BasePage.getText(viewAgreementOverviewPage.workerRatePopupChHourlyVat).trim();
        return value;
    }

    private String getFinalRateWithVatFromWorkerRatesPopup() {
        String value = BasePage.getText(viewAgreementOverviewPage.workerRatePopupFinalRateWithVat).trim();
        return value;
    }

    private String getFinalRatetWithNoVatFromWorkerRatesPopup() {
        String value = BasePage.getText(viewAgreementOverviewPage.workerRatePopupFinalRateWithNoVat).trim();
        return value;
    }
}
