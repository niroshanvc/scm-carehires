package com.carehires.actions.agreements;


import com.carehires.pages.agreements.ViewAgreementOverviewPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import com.carehires.utils.FileDownloadUtils;
import com.carehires.utils.FileWriterUtils;
import com.carehires.utils.GenericUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.isIn;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class ViewAgreementOverviewActions {

    ViewAgreementOverviewPage agreementOverviewPage;
    private static final GenericUtils genericUtils;

    static {
        try {
            genericUtils = new GenericUtils();
        } catch (BasePage.WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String ENTITY = "agreement";
    private static final String YML_FILE = "agreement-create";
    private static final String YML_FILE_EDIT = "agreement-edit";
    private static final String SKILLS = "With Skills";
    private static final String YML_HEADER = "Mark As Signed";
    private static final String YML_HEADER_WORKER_RATES = "Worker Rates";
    private static final String YML_HEADER_NORMAL_RATE = "Normal Rate";
    private static final String YML_HEADER_CANCELLATION_POLICY = "Cancellation Policy";
    private static final String YML_HEADER_SLEEP_IN_REQUEST = "Sleep In Request";
    private static final String YML_HEADER_SPECIAL_HOLIDAY_RATE = "Special Holiday Rate";
    private static final String YML_HEADER_BANK_HOLIDAY_RATE = "Bank Holiday Rate";
    private static final String YML_HEADER_SIGNATORIES = "Signatories";
    private static final String YML_HEADER_AGENCY = "Agency";
    private static final String YML_HEADER_PROVIDER = "Provider";
    private static final String TEST_RESOURCE_FOLDER = System.getProperty("user.dir") + File.separator + "src"
            + File.separator + "test" + File.separator + "resources";
    private static final String VALUE_ATTRIBUTE = "value";

    private static final Logger logger = LogManager.getLogger(ViewAgreementOverviewActions.class);


    public ViewAgreementOverviewActions() {
        agreementOverviewPage = new ViewAgreementOverviewPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), agreementOverviewPage);
        } catch (BasePage.WebDriverInitializationException e) {
            logger.error("Error while initializing Agreement View Page elements: {}", e.getMessage());
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
            BasePage.waitUntilElementPresent(agreementOverviewPage.signatureStatus, 60);
            actual = BasePage.getText(agreementOverviewPage.signatureStatus).trim().toUpperCase();
        } else if (expected.equalsIgnoreCase("Signed") || expected.equalsIgnoreCase("Active")
            || expected.equalsIgnoreCase("Inactive")) {
                BasePage.waitUntilElementPresent(agreementOverviewPage.signatureStatusSigned.get(0), 60);
                int size = agreementOverviewPage.signatureStatusSigned.size();
                actual = BasePage.getText(agreementOverviewPage.signatureStatusSigned.get(size-1)).trim().toUpperCase();
        }
        assertThat("Agreement signature status is not correct!", actual, is(expected));
    }

    private String getFirstAgreementId() {
        return BasePage.getText(agreementOverviewPage.firstId).trim();
    }

    private void verifyPaymentStatus(String expected) {
        BasePage.waitUntilPageCompletelyLoaded();
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying payment status >>>>>>>>>>>>>>>>>>>>");
        String actual = BasePage.getText(agreementOverviewPage.paymentAuthorizationStatus).trim().toUpperCase();
        assertThat("Agreement payment status is not correct!", actual, is(expected));
    }

    public void clickOnMarkAsSignedAndDoVerifications() {
        logger.info("<<<<<<<<<<<<<<<<<<<< Clicking on Mark as Signed button and Doing Verifications >>>>>>>>>>>>>>>>>");
        BasePage.scrollToWebElement(agreementOverviewPage.markAsSignedButton);
        BasePage.clickWithJavaScript(agreementOverviewPage.markAsSignedButton);
        verifyProviderNameLoadedInAttachAgreementPopup();
        verifyAgencyNameLoadedInAttachAgreementPopup();
        enterDataForAttachAgreementPopup();
    }

    private void enterDataForAttachAgreementPopup() {
        String doc = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, SKILLS, YML_HEADER, "Agreement");
        String absoluteFilePath = TEST_RESOURCE_FOLDER + File.separator + "Upload" + File.separator + "Agreement"
                + File.separator + doc;
        BasePage.uploadFile(agreementOverviewPage.uploadFile, absoluteFilePath);
        waitUntilDocumentUploaded();

        String note = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, SKILLS, YML_HEADER, "Note");
        BasePage.typeWithStringBuilder(agreementOverviewPage.attachAgreementNote, note);
        BasePage.clickWithJavaScript(agreementOverviewPage.providerTermsAndConditionsCheckbox);
        BasePage.clickWithJavaScript(agreementOverviewPage.agencyTermsAndConditionsCheckbox);
        BasePage.clickWithJavaScript(agreementOverviewPage.saveButton);

        verifySuccessMessage();
    }

    private void verifySuccessMessage() {
        BasePage.waitUntilElementPresent(agreementOverviewPage.successMessage, 1200);
        String actualInLowerCase = BasePage.getText(agreementOverviewPage.successMessage).toLowerCase().trim();
        String expected = "Contract signed successfully";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Contract signed success message is wrong!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(agreementOverviewPage.successMessage, 60);
    }

    private void waitUntilDocumentUploaded() {
        BasePage.waitUntilElementDisplayed(agreementOverviewPage.previewSubContractDocument, 60);
        BasePage.mouseHoverOverElement(agreementOverviewPage.previewSubContractDocument);
        BasePage.waitUntilElementDisplayed(agreementOverviewPage.removeAttachment, 60);
    }

    private void verifyProviderNameLoadedInAttachAgreementPopup() {
        logger.info("<<<<<<<<<<<<<<<< Verifying Provider Name displaying in the Attach Agreement popup >>>>>>>>>>>>>");
        BasePage.waitUntilElementPresent(agreementOverviewPage.attachAgreementNote, 30);
        String providerFullText = BasePage.getText(agreementOverviewPage.providerTermsAndConditionsText).trim();
        String[] strArr = providerFullText.split("on behalf of");
        String actual = strArr[1].trim();
        String expected = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, SKILLS, YML_HEADER_SIGNATORIES,
                YML_HEADER_PROVIDER, "Name");
        assertThat("Provider name displays incorrectly!", actual, is(expected));
    }

    private void verifyAgencyNameLoadedInAttachAgreementPopup() {
        logger.info("<<<<<<<<<<<<<<<<< Verifying Agency Name displaying in the Attach Agreement popup >>>>>>>>>>>>>>");
        String agencyFullText = BasePage.getText(agreementOverviewPage.agencyTermsAndConditionsText).trim();
        String[] strArr = agencyFullText.split("on behalf of");
        String actual = strArr[1].trim();
        String expected = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, SKILLS, YML_HEADER_SIGNATORIES,
                YML_HEADER_AGENCY, "Name");
        assertThat("Agency name displays incorrectly!", actual, is(expected));
    }

    public void clickOnActiveAgreement() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Clicking on Active Agreement button >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementPresent(agreementOverviewPage.activateAgreementButton, 60);
        BasePage.clickWithJavaScript(agreementOverviewPage.activateAgreementButton);
    }

    public void verifyContentsInWorkerRates() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying data displaying in the Worker Rates Popup >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementClickable(agreementOverviewPage.workerRatesViewIcon, 60);
        BasePage.clickWithJavaScript(agreementOverviewPage.workerRatesViewIcon);

        // verify worker type
        String expectedWorkerType = getWorkerType();
        BasePage.genericWait(5000);
        String actualWorkerType = getWorkerTypeFromWorkerRatesPopup();
        assertThat("Worker types are not equal", actualWorkerType, is(expectedWorkerType));

        // verify skills
        List<String> expectedSkills = getSkills().stream().map(String::trim).toList();
        List<String> actualSkills = getSkillsFromWorkerRatesPopup().stream().map(String::trim).toList();
        // verifying size first for better debugging
        assertThat("The number of skills does not match!", actualSkills.size(), is(expectedSkills.size()));
        // Check that all expected skills are in the actual skills list
        assertThat(actualSkills, hasItems(expectedSkills.toArray(new String[0])));
        // Check that every item in the actual list is expected
        assertThat(actualSkills, everyItem(isIn(expectedSkills)));

        // verify agency hourly rate
        double expectedWorkerHourlyRate = Double.parseDouble(getWorkerHourlyRate());
        double actualWorkerMinorRate = Double.parseDouble(getHourlyRateFromWorkerRatesPopup());
        assertThat("Worker Hourly Rate does not match!", actualWorkerMinorRate, is(expectedWorkerHourlyRate));

        // verify agency margin
        double expectedAgencyMargin = Double.parseDouble(getAgencyHourlyCostWithNoVat()) - Double
                .parseDouble(getWorkerHourlyRate());
        double actualAgencyMargin = Double.parseDouble(getAgencyMarginFromWorkerRatesPopup());
        expectedAgencyMargin = Double.parseDouble(String.format("%.2f", expectedAgencyMargin));
        actualAgencyMargin = Double.parseDouble(String.format("%.2f", actualAgencyMargin));
        assertThat("Agency Margin does not match!", actualAgencyMargin, is(expectedAgencyMargin));

        // verify agency vat
        double expectedVat = Double.parseDouble(getAgencyHourlyCostWithVat()) - Double.parseDouble(
                getAgencyHourlyCostWithNoVat());
        double actualVat = Double.parseDouble(getAgencyVatFromWorkerRatesPopup());
        expectedVat = Double.parseDouble(String.format("%.2f", expectedVat));
        actualVat = Double.parseDouble(String.format("%.2f", actualVat));
        assertThat("Agency VAT does not match!", actualVat, is(expectedVat));

        // verify agency hourly cost with vat
        double expectedHourlyCostWithVat = Double.parseDouble(getAgencyHourlyCostWithVat());
        double actualHourlyCostWithVat = Double.parseDouble(getAgencyCostWithVatFromWorkerRatesPopup());
        assertThat("Agency Hourly Cost With VAT does not match!", actualHourlyCostWithVat,
                is(expectedHourlyCostWithVat));

        // verify agency hourly cost with no vat
        double expectedHourlyCostWithNoVat = Double.parseDouble(getAgencyHourlyCostWithNoVat());
        double actualHourlyCostWithNoVat = Double.parseDouble(getAgencyCostWithNoVatFromWorkerRatesPopup());
        assertThat("Agency Hourly Cost With No VAT does not match!", actualHourlyCostWithNoVat,
                is(expectedHourlyCostWithNoVat));

        // verify final rate with vat
        double expectedFinalRateWithVat = Double.parseDouble(getFinalHourlyRateWithVat());
        double actualFinalRateWithVat = Double.parseDouble(getFinalRateWithVatFromWorkerRatesPopup());
        assertThat("Final Rate With VAT does not match!", actualFinalRateWithVat, is(expectedFinalRateWithVat));

        // verify final rate with no vat
        double expectedFinalRateWithNoVat = Double.parseDouble(getFinalHourlyRateWithNoVat());
        double actualFinalRateWithNoVat = Double.parseDouble(getFinalRateWithNoVatFromWorkerRatesPopup());
        assertThat("Final Rate With No VAT does not match!", actualFinalRateWithNoVat,
                is(expectedFinalRateWithNoVat));

        // verify care hires hourly cost
        double exp = Double.parseDouble(getCareHiresHourlyCost());
        double act = Double.parseDouble(getChHourlyMarginFromWorkerRatesPopup()) + Double
                .parseDouble(getChHourlyVatFromWorkerRatesPopup());
        assertThat("Care hires hourly cost does not match!", act, is(exp));
    }

    private String getWorkerType() {
        BasePage.waitUntilElementPresent(agreementOverviewPage.workerRatesTableWorkerType, 60);
        return BasePage.getText(agreementOverviewPage.workerRatesTableWorkerType).trim();
    }

    private List<String> getSkills() {
        List<WebElement> elements = agreementOverviewPage.workerRatesTableWorkerSkill;
        List<String> skills = new ArrayList<>();

        for (WebElement e : elements) {
            skills.add(e.getText().trim());
        }
        return skills;
    }

    private String getWorkerHourlyRate() {
        String valueWithCurrency = BasePage.getText(agreementOverviewPage.workerRatesTableWorkerHourlyRate).trim();
        return valueWithCurrency.split(" ")[1];
    }

    private String getAgencyHourlyCostWithVat() {
        String valueWithCurrency = BasePage.getText(agreementOverviewPage.workerRatesTableAgencyHourlyCostWithVat)
                .trim();
        return valueWithCurrency.split(" ")[1];
    }

    private String getAgencyHourlyCostWithNoVat() {
        String valueWithCurrency = BasePage.getText(agreementOverviewPage.workerRatesTableAgencyHourlyCostWithNoVat)
                .trim();
        return valueWithCurrency.split(" ")[1];
    }

    private String getCareHiresHourlyCost() {
        String valueWithCurrency = BasePage.getText(agreementOverviewPage.workerRatesTableCareHiresHourlyCost).trim();
        return valueWithCurrency.split(" ")[1];
    }

    private String getFinalHourlyRateWithVat() {
        String valueWithCurrency = BasePage.getText(agreementOverviewPage.workerRatesTableFinalHourlyRateWithVat)
                .trim();
        return valueWithCurrency.split(" ")[1];
    }

    private String getFinalHourlyRateWithNoVat() {
        String valueWithCurrency = BasePage.getText(agreementOverviewPage.workerRatesTableFinalHourlyRateWithNoVat)
                .trim();
        return valueWithCurrency.split(" ")[1];
    }

    private String getWorkerTypeFromWorkerRatesPopup() {
        return BasePage.getText(agreementOverviewPage.workerRatePopupWorkerType).trim();
    }

    private List<String> getSkillsFromWorkerRatesPopup() {
        return Arrays.stream(BasePage.getText(agreementOverviewPage.workerRatePopupSkills)
                .split(",")).toList();
    }

    private String getHourlyRateFromWorkerRatesPopup() {
        return BasePage.getAttributeValue(agreementOverviewPage.workerRatePopupHourlyRate, VALUE_ATTRIBUTE).trim();
    }

    private String getAgencyMarginFromWorkerRatesPopup() {
        return BasePage.getAttributeValue(agreementOverviewPage.workerRatePopupAgencyMargin,
                VALUE_ATTRIBUTE).trim();
    }

    private String getAgencyVatFromWorkerRatesPopup() {
        return BasePage.getText(agreementOverviewPage.workerRatePopupAgencyVat).trim();
    }

    private String getAgencyCostWithVatFromWorkerRatesPopup() {
        return BasePage.getText(agreementOverviewPage.workerRatePopupAgencyCostWithVat).trim();
    }

    private String getAgencyCostWithNoVatFromWorkerRatesPopup() {
        return BasePage.getText(agreementOverviewPage.workerRatePopupAgencyCostWithNoVat).trim();
    }

    private String getChHourlyMarginFromWorkerRatesPopup() {
        return BasePage.getAttributeValue(agreementOverviewPage.workerRatePopupChHourlyMargin,
                VALUE_ATTRIBUTE).trim();
    }

    private String getChHourlyVatFromWorkerRatesPopup() {
        return BasePage.getText(agreementOverviewPage.workerRatePopupChHourlyVat).trim();
    }

    private String getFinalRateWithVatFromWorkerRatesPopup() {
        return BasePage.getText(agreementOverviewPage.workerRatePopupFinalRateWithVat).trim();
    }

    private String getFinalRateWithNoVatFromWorkerRatesPopup() {
        return BasePage.getText(agreementOverviewPage.workerRatePopupFinalRateWithNoVat).trim();
    }

    public void verifyContentsInSleepInRequest() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying data displaying in Sleep In Rates popup >>>>>>>>>>>>>>>>>>>>");
        BasePage.refreshPage();
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(agreementOverviewPage.sleepInRatesViewIcon, 60);
        BasePage.scrollToWebElement(agreementOverviewPage.sleepInRatesViewIcon);
        BasePage.clickWithJavaScript(agreementOverviewPage.sleepInRatesViewIcon);

        // verify worker type
        String expectedWorkerType = getSleepInRatesTableWorkerType();
        BasePage.genericWait(5000);
        String actualWorkerType = getWorkerTypeFromSleepInRatesPopup();
        assertThat("Worker types are not equal", actualWorkerType, is(expectedWorkerType));

        // verify hourly rate
        double expectedWorkerHourlyRate = Double.parseDouble(getSleepInRatesTableWorkerHourlyRate());
        double actualWorkerMinorRate = Double.parseDouble(getHourlyRateFromSleepInRatesPopup());
        assertThat("Hourly Rate does not match!", actualWorkerMinorRate, is(expectedWorkerHourlyRate));

        // verify agency margin
        double expectedAgencyMargin = Double.parseDouble(getSleepInRatesTableAgencyHourlyCostWithNoVat()) -
                Double.parseDouble(getSleepInRatesTableWorkerHourlyRate());
        double actualAgencyMargin = Double.parseDouble(getAgencyMarginFromSleepInRatesPopup());
        expectedAgencyMargin = Double.parseDouble(String.format("%.2f", expectedAgencyMargin));
        actualAgencyMargin = Double.parseDouble(String.format("%.2f", actualAgencyMargin));
        assertThat("Agency Margin does not match!", actualAgencyMargin, is(expectedAgencyMargin));

        // verify agency vat
        double expectedVat = Double.parseDouble(getSleepInRatesTableAgencyHourlyCostWithVat()) -
                Double.parseDouble(getSleepInRatesTableAgencyHourlyCostWithNoVat());
        double actualVat = Double.parseDouble(getAgencyVatFromSleepInRatesPopup());
        expectedVat = Double.parseDouble(String.format("%.2f", expectedVat));
        actualVat = Double.parseDouble(String.format("%.2f", actualVat));
        assertThat("Agency VAT does not match!", actualVat, is(expectedVat));

        // verify agency cost with vat
        double expectedHourlyCostWithVat = Double.parseDouble(getSleepInRatesTableAgencyHourlyCostWithVat());
        double actualHourlyCostWithVat = Double.parseDouble(getAgencyCostWithVatFromSleepInRatesPopup());
        assertThat("Agency Hourly Cost With VAT does not match!", actualHourlyCostWithVat,
                is(expectedHourlyCostWithVat));

        // verify agency cost with no vat
        double expectedHourlyCostWithNoVat = Double.parseDouble(getSleepInRatesTableAgencyHourlyCostWithNoVat());
        double actualHourlyCostWithNoVat = Double.parseDouble(getAgencyCostWithNoVatFromSleepInRatesPopup());
        assertThat("Agency Hourly Cost With No VAT does not match!", actualHourlyCostWithNoVat,
                is(expectedHourlyCostWithNoVat));

        // verify ch hourly vat
        double expectedChHourlyVat = Double.parseDouble(getFinalRateWithNoVatFromSleepInRatesPopup()) -
                Double.parseDouble(getAgencyCostWithNoVatFromSleepInRatesPopup()) -
                Double.parseDouble(getChHourlyMarginFromSleepInRatesPopup());
        double actualChHourlyVat = Double.parseDouble(getChHourlyVatFromSleepInRatesPopup());
        expectedChHourlyVat = Double.parseDouble(String.format("%.2f", expectedChHourlyVat));
        actualChHourlyVat = Double.parseDouble(String.format("%.2f", actualChHourlyVat));
        assertThat("CH Hourly Vat does not match!", actualChHourlyVat, is(expectedChHourlyVat));

        // verify final rate with vat
        double expectedFinalRateWithVat = Double.parseDouble(getSleepInRatesTableFinalHourlyRateWithVat());
        double actualFinalRateWithVat = Double.parseDouble(getFinalRateWithVatFromSleepInRatesPopup());
        assertThat("Final Rate With VAT does not match!", actualFinalRateWithVat, is(expectedFinalRateWithVat));

        // verify final rate with no vat
        double expectedFinalRateWithNoVat = Double.parseDouble(getSleepInRatesTableFinalHourlyRateWithNoVat());
        double actualFinalRateWithNoVat = Double.parseDouble(getFinalRateWithNoVatFromSleepInRatesPopup());
        assertThat("Final Rate With No VAT does not match!", actualFinalRateWithNoVat,
                is(expectedFinalRateWithNoVat));
    }

    private String getSleepInRatesTableWorkerType() {
        return BasePage.getText(agreementOverviewPage.sleepInRatesTableWorkerType).trim();
    }

    private String getSleepInRatesTableWorkerHourlyRate() {
        String valueWithCurrency = BasePage.getText(agreementOverviewPage.sleepInRatesTableHourlyChargeRate).trim();
        return valueWithCurrency.split(" ")[1];
    }

    private String getSleepInRatesTableAgencyHourlyCostWithVat() {
        String valueWithCurrency = BasePage.getText(agreementOverviewPage.sleepInRatesTableAgencyHourlyCostWithVat)
                .trim();
        return valueWithCurrency.split(" ")[1];
    }

    private String getSleepInRatesTableAgencyHourlyCostWithNoVat() {
        String valueWithCurrency = BasePage.getText(agreementOverviewPage.sleepInRatesTableAgencyHourlyCostWithNoVat)
                .trim();
        return valueWithCurrency.split(" ")[1];
    }

    private String getSleepInRatesTableFinalHourlyRateWithVat() {
        String valueWithCurrency = BasePage.getText(agreementOverviewPage.sleepInRatesTableFinalHourlyRateWithVat)
                .trim();
        return valueWithCurrency.split(" ")[1];
    }

    private String getSleepInRatesTableFinalHourlyRateWithNoVat() {
        String valueWithCurrency = BasePage.getText(agreementOverviewPage.sleepInRatesTableFinalHourlyRateWithNoVat)
                .trim();
        return valueWithCurrency.split(" ")[1];
    }

    private String getWorkerTypeFromSleepInRatesPopup() {
        return BasePage.getText(agreementOverviewPage.sleepInRatesPopupWorkerType).trim();
    }

    private String getHourlyRateFromSleepInRatesPopup() {
        return BasePage.getAttributeValue(agreementOverviewPage.sleepInRatesPopupHourlyRate, VALUE_ATTRIBUTE).trim();
    }

    private String getAgencyMarginFromSleepInRatesPopup() {
        return BasePage.getAttributeValue(agreementOverviewPage.sleepInRatesPopupAgencyMargin, VALUE_ATTRIBUTE).trim();
    }

    private String getAgencyVatFromSleepInRatesPopup() {
        return BasePage.getText(agreementOverviewPage.sleepInRatesPopupAgencyVat).trim();
    }

    private String getAgencyCostWithVatFromSleepInRatesPopup() {
        return BasePage.getText(agreementOverviewPage.sleepInRatesPopupAgencyCostWithVat).trim();
    }

    private String getAgencyCostWithNoVatFromSleepInRatesPopup() {
        return BasePage.getText(agreementOverviewPage.sleepInRatesPopupAgencyCostWithNoVat).trim();
    }

    private String getChHourlyMarginFromSleepInRatesPopup() {
        return BasePage.getAttributeValue(agreementOverviewPage.sleepInRatesPopupChHourlyMargin, VALUE_ATTRIBUTE).trim();
    }

    private String getChHourlyVatFromSleepInRatesPopup() {
        return BasePage.getText(agreementOverviewPage.sleepInRatesPopupChHourlyVat).trim();
    }

    private String getFinalRateWithVatFromSleepInRatesPopup() {
        return BasePage.getText(agreementOverviewPage.sleepInRatesPopupFinalRateWithVat).trim();
    }

    private String getFinalRateWithNoVatFromSleepInRatesPopup() {
        return BasePage.getText(agreementOverviewPage.sleepInRatesPopupFinalRateWithNoVat).trim();
    }

    public void markAsInactive() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Agreement - Marking as Inactive >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementClickable(agreementOverviewPage.deactivateButton, 60);
        BasePage.clickWithJavaScript(agreementOverviewPage.deactivateButton);
        BasePage.waitUntilElementClickable(agreementOverviewPage.deactivateButtonInDeactivateConfirmPopup, 30);
        BasePage.clickWithJavaScript(agreementOverviewPage.deactivateButtonInDeactivateConfirmPopup);
        verifyInactiveSuccessMessage();
    }

    private void verifyInactiveSuccessMessage() {
        BasePage.waitUntilElementPresent(agreementOverviewPage.successMessage, 1200);
        String actualInLowerCase = BasePage.getText(agreementOverviewPage.successMessage).toLowerCase().trim();
        String expected = "Agreement marked as inactive";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Unable to mark as inactive!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(agreementOverviewPage.successMessage, 60);
    }

    public void markAsActiveAgain() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Agreement - Marking as Active >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementClickable(agreementOverviewPage.activateAgreementButton, 60);
        BasePage.clickWithJavaScript(agreementOverviewPage.activateAgreementButton);
        verifyActivateAgreementSuccessMessage();
    }

    private void verifyActivateAgreementSuccessMessage() {
        BasePage.waitUntilElementPresent(agreementOverviewPage.successMessage, 1200);
        String actualInLowerCase = BasePage.getText(agreementOverviewPage.successMessage).toLowerCase().trim();
        String expected = "Agreement marked as active";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Unable to mark as active!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(agreementOverviewPage.successMessage, 60);
    }

    public void editSite() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Updating site info >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementClickable(agreementOverviewPage.editAgreementButton, 60);
        BasePage.clickWithJavaScript(agreementOverviewPage.editAgreementButton);
        BasePage.waitUntilElementClickable(agreementOverviewPage.editSitesButton, 60);
        BasePage.clickWithJavaScript(agreementOverviewPage.editSitesButton);

        String attr = BasePage.getAttributeValue(agreementOverviewPage.removingSite, "class");
        if (attr.contains("checked")) {
            BasePage.clickWithJavaScript(agreementOverviewPage.manageSiteAddRemoveCheckbox);
        }
        BasePage.clickWithJavaScript(agreementOverviewPage.applyButton);
        BasePage.waitUntilElementClickable(agreementOverviewPage.saveButton, 60);
        BasePage.clickWithJavaScript(agreementOverviewPage.saveButton);

        // Change Summary popup
        BasePage.waitUntilElementDisplayed(agreementOverviewPage.effectiveDateCalendar, 60);

        // saving changes
        savingDataOnChangeSummaryPopup("Edit Site");

        verifySiteUpdateSuccessMessage();
    }

    private void savingDataOnChangeSummaryPopup(String mainHeader) {
        enterEffectiveDate(mainHeader);
        BasePage.clickWithJavaScript(agreementOverviewPage.changeSummarySaveButton);
    }

    private void verifySiteUpdateSuccessMessage() {
        BasePage.waitUntilElementPresent(agreementOverviewPage.successMessage, 90);
        String actualInLowerCase = BasePage.getText(agreementOverviewPage.successMessage).toLowerCase().trim();
        String expected = "Record updated successfully";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Agreement not updated!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(agreementOverviewPage.successMessage, 20);
    }

    private void enterEffectiveDate(String mainHeader) {
        String effectiveDate = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_EDIT, mainHeader,
                "Effective Date");
        BasePage.clickWithJavaScript(agreementOverviewPage.effectiveDateCalendar);
        genericUtils.selectDateFromCalendarPopup(effectiveDate);
    }

    public void removeWorkerRates() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Removing already existing Worker Rates >>>>>>>>>>>>>>>>>>>>");
        // delete existing worker rate
        BasePage.clickWithJavaScript(agreementOverviewPage.editAgreementButton);
        BasePage.waitUntilElementClickable(agreementOverviewPage.workerRatesThreeDots, 60);
        BasePage.clickWithJavaScript(agreementOverviewPage.workerRatesThreeDots);
        deleteAlreadySavedRecord();

        // saving effective date
        savingDataOnChangeSummaryPopup(YML_HEADER_WORKER_RATES);
        verifySiteUpdateSuccessMessage();
    }

    public void updateWorkerRates() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Updating Worker Rates >>>>>>>>>>>>>>>>>>>>");
        // add new record
        BasePage.clickWithJavaScript(agreementOverviewPage.editAgreementButton);
        BasePage.waitUntilElementClickable(agreementOverviewPage.workerRatesAddButton, 60);
        BasePage.clickWithJavaScript(agreementOverviewPage.workerRatesAddButton);
        enterWorkerType(YML_HEADER_WORKER_RATES);
        enterSkills();

        // enter normal rate data
        enterHourlyRate(YML_HEADER_WORKER_RATES);
        enterAgencyMargin(YML_HEADER_WORKER_RATES);
        enterChHourlyMargin(YML_HEADER_WORKER_RATES);

        // enter special holiday rate
        doClickOnEnableRateCheckbox(YML_HEADER_SPECIAL_HOLIDAY_RATE);
        enterFinalRateVat(YML_HEADER_WORKER_RATES, YML_HEADER_SPECIAL_HOLIDAY_RATE);

        // enter bank holiday rate
        doClickOnEnableRateCheckbox(YML_HEADER_BANK_HOLIDAY_RATE);
        enterFinalRateVat(YML_HEADER_WORKER_RATES, YML_HEADER_BANK_HOLIDAY_RATE);

        BasePage.scrollToWebElement(agreementOverviewPage.workerRatesPopupContinueButton);
        BasePage.clickWithJavaScript(agreementOverviewPage.workerRatesPopupAddButton);
        BasePage.waitUntilElementDisplayed(agreementOverviewPage.workerRatesPopupViewSpecialRateIcon, 30);
        BasePage.clickWithJavaScript(agreementOverviewPage.workerRatesPopupContinueButton);
        applyChanges();

        // saving effective dates
        savingDataOnChangeSummaryPopup(YML_HEADER_WORKER_RATES);
        verifySiteUpdateSuccessMessage();
    }

    private void enterFinalRateVat(String  header, String rateType) {
        String finalRateVat = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_EDIT,
                header, rateType, "Final Rate Vat");
        WebElement element = agreementOverviewPage.finalRateVat(rateType);
        BasePage.clearAndEnterTexts(element, finalRateVat);
        BasePage.clickTabKey(element);
    }

    private void doClickOnEnableRateCheckbox(String rateType) {
        WebElement el = agreementOverviewPage.checkEnableRateCheckbox(rateType);
        String attr = BasePage.getAttributeValue(agreementOverviewPage.enableRateCheckboxSpan(rateType),
                "class");
        if (!attr.contains("checked")) {
            BasePage.clickWithJavaScript(el);
        }
    }

    private void enterChHourlyMargin(String header) {
        String chHourlyMargin = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_EDIT,
                header, YML_HEADER_NORMAL_RATE, "CH Hourly Margin");
        WebElement element = agreementOverviewPage.chHourlyMarginInput(ViewAgreementOverviewActions
                .YML_HEADER_NORMAL_RATE);
        BasePage.clearAndEnterTexts(element, chHourlyMargin);
        BasePage.clickTabKey(element);
    }

    private void enterAgencyMargin(String header) {
        String agencyMargin = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_EDIT,
                header, YML_HEADER_NORMAL_RATE, "Agency Margin");
        WebElement element = agreementOverviewPage.agencyMarginInput(ViewAgreementOverviewActions
                .YML_HEADER_NORMAL_RATE);
        BasePage.clearAndEnterTexts(element, agencyMargin);
    }

    private void enterHourlyRate(String header) {
        String hourlyRate = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_EDIT,
                header, YML_HEADER_NORMAL_RATE, "Hourly Rate");
        WebElement element = agreementOverviewPage.hourlyRateInput(ViewAgreementOverviewActions.YML_HEADER_NORMAL_RATE);
        BasePage.waitUntilElementDisplayed(element, 20);
        BasePage.clearAndEnterTexts(element, hourlyRate);
    }

    private void enterSkills() {
        String[] skills = Objects.requireNonNull(DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_EDIT,
                YML_HEADER_WORKER_RATES, YML_HEADER_NORMAL_RATE, "Skills")).split(",");
        BasePage.genericWait(3000);
        BasePage.clickWithJavaScript(agreementOverviewPage.skillsDropdown);
        By locator = By.xpath(agreementOverviewPage.getDropdownOptionXpath(skills[0]));
        BasePage.waitUntilPresenceOfElementLocated(locator, 20);
        for (String skill : skills) {
            BasePage.clickWithJavaScript(agreementOverviewPage.getDropdownOptionXpath(skill));
        }
    }

    private void enterWorkerType(String header) {
        String workerType = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_EDIT,
                header, YML_HEADER_NORMAL_RATE, "Worker Type");
        BasePage.waitUntilElementDisplayed(agreementOverviewPage.workerTypeDropdown, 30);
        BasePage.genericWait(3000);
        BasePage.clickWithJavaScript(agreementOverviewPage.workerTypeDropdown);
        By by = By.xpath(agreementOverviewPage.getDropdownOptionXpath(workerType));
        BasePage.waitUntilVisibilityOfElementLocated(by, 20);
        BasePage.scrollToWebElement(agreementOverviewPage.getDropdownOptionXpath(workerType));
        BasePage.clickWithJavaScript(agreementOverviewPage.getDropdownOptionXpath(workerType));
    }

    private void deleteAlreadySavedRecord() {
        BasePage.waitUntilElementClickable(agreementOverviewPage.deleteIcon, 30);
        BasePage.clickWithJavaScript(agreementOverviewPage.deleteIcon);
        applyChanges();
    }

    private void applyChanges() {
        BasePage.waitUntilElementDisplayed(agreementOverviewPage.cancelChangesIcon, 30);
        BasePage.waitUntilElementClickable(agreementOverviewPage.saveButton, 30);
        BasePage.clickWithJavaScript(agreementOverviewPage.saveButton);
    }

    public void removeCancellationPolicy() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Removing already existing Cancellation Policy >>>>>>>>>>>>>>>>>>>>");

        // delete existing cancellation policy
        BasePage.clickWithJavaScript(agreementOverviewPage.editAgreementButton);
        BasePage.genericWait(2000);
        BasePage.scrollToWebElement(agreementOverviewPage.cancellationPolicyThreeDots);
        BasePage.waitUntilElementClickable(agreementOverviewPage.cancellationPolicyThreeDots, 60);
        BasePage.clickWithJavaScript(agreementOverviewPage.cancellationPolicyThreeDots);
        deleteAlreadySavedRecord();

        // saving effective date
        savingDataOnChangeSummaryPopup(YML_HEADER_CANCELLATION_POLICY);
        verifySiteUpdateSuccessMessage();
    }

    public void addNewCancellationPolicy() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Cancellation Policy Info >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementClickable(agreementOverviewPage.editAgreementButton, 60);
        BasePage.clickWithJavaScript(agreementOverviewPage.editAgreementButton);
        BasePage.scrollToWebElement(agreementOverviewPage.cancellationPolicyAddButton);
        BasePage.clickWithJavaScript(agreementOverviewPage.cancellationPolicyAddButton);

        String beforeJobStart = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_EDIT,
                YML_HEADER_CANCELLATION_POLICY, "Before Job Start");
        BasePage.clickWithJavaScript(agreementOverviewPage.beforeJobStartDropdown);
        By by = By.xpath(agreementOverviewPage.getDropdownOptionXpath(beforeJobStart));
        BasePage.waitUntilVisibilityOfElementLocated(by, 20);
        BasePage.clickWithJavaScript(agreementOverviewPage.getDropdownOptionXpath(beforeJobStart));

        String cancellationFeePercentage = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_EDIT,
                YML_HEADER_CANCELLATION_POLICY, "Cancellation fee percentage");
        BasePage.clearAndEnterTexts(agreementOverviewPage.cancellationFeePercentage, cancellationFeePercentage);

        String careHiresSplit = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_EDIT,
                YML_HEADER_CANCELLATION_POLICY, "CareHires split");
        BasePage.clearAndEnterTexts(agreementOverviewPage.careHiresSplit, careHiresSplit);
        BasePage.clickTabKey(agreementOverviewPage.careHiresSplit);
        BasePage.clickTabKey(agreementOverviewPage.agencySplit);
        BasePage.clickWithJavaScript(agreementOverviewPage.cancellationPolicyPopupAddButton);

        BasePage.scrollToWebElement(agreementOverviewPage.continueButton);
        BasePage.clickWithJavaScript(agreementOverviewPage.continueButton);
        applyChanges();

        // saving effective dates
        savingDataOnChangeSummaryPopup(YML_HEADER_CANCELLATION_POLICY);
        verifySiteUpdateSuccessMessage();
    }

    public void removeSleepInRequest() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Removing already existing Sleep in Request >>>>>>>>>>>>>>>>>>>>");

        // delete existing sleep in request
        BasePage.clickWithJavaScript(agreementOverviewPage.editAgreementButton);
        BasePage.genericWait(2000);
        BasePage.scrollToWebElement(agreementOverviewPage.sleepInRequestThreeDots);
        BasePage.clickWithJavaScript(agreementOverviewPage.sleepInRequestThreeDots);
        deleteAlreadySavedRecord();

        // saving effective date
        savingDataOnChangeSummaryPopup(YML_HEADER_SLEEP_IN_REQUEST);
        verifySiteUpdateSuccessMessage();
    }

    public void addSleepInRequest() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Sleep in request Info >>>>>>>>>>>>>>>>>>>>");
        // add new record
        BasePage.clickWithJavaScript(agreementOverviewPage.editAgreementButton);
        BasePage.waitUntilElementClickable(agreementOverviewPage.sleepInRequestAddButton, 60);
        BasePage.clickWithJavaScript(agreementOverviewPage.sleepInRequestAddButton);
        enterWorkerType(YML_HEADER_SLEEP_IN_REQUEST);

        // enter normal rate data
        enterHourlyRate(YML_HEADER_SLEEP_IN_REQUEST);
        enterAgencyMargin(YML_HEADER_SLEEP_IN_REQUEST);
        enterChHourlyMargin(YML_HEADER_SLEEP_IN_REQUEST);

        // enter special holiday rate
        doClickOnEnableRateCheckbox(YML_HEADER_SPECIAL_HOLIDAY_RATE);
        enterFinalRateVat(YML_HEADER_SLEEP_IN_REQUEST, YML_HEADER_SPECIAL_HOLIDAY_RATE);

        // enter bank holiday rate
        doClickOnEnableRateCheckbox(YML_HEADER_BANK_HOLIDAY_RATE);
        enterFinalRateVat(YML_HEADER_SLEEP_IN_REQUEST, YML_HEADER_BANK_HOLIDAY_RATE);

        BasePage.scrollToWebElement(agreementOverviewPage.sleepInRatesPopupContinueButton);
        BasePage.clickWithJavaScript(agreementOverviewPage.sleepInRatesPopupAddButton);
        BasePage.waitUntilElementDisplayed(agreementOverviewPage.sleepInRatesPopupViewSpecialRateIcon, 30);
        BasePage.clickWithJavaScript(agreementOverviewPage.sleepInRatesPopupContinueButton);
        applyChanges();

        // saving effective dates
        savingDataOnChangeSummaryPopup(YML_HEADER_SLEEP_IN_REQUEST);
        verifySiteUpdateSuccessMessage();
    }

    public void downloadAndDeleteAgreement() {
        // Trigger the download
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Downloading the manually signed agreement >>>>>>>>>>>>>>>>>>>>");
        BasePage.scrollToWebElement(agreementOverviewPage.downloadAgreement);
        FileDownloadUtils.triggerDownloadAndCloseTab(agreementOverviewPage.downloadAgreement);

        // Get the downloaded file name
        BasePage.genericWait(5000);
        String downloadedFileName = FileDownloadUtils.getLatestDownloadedFileName();
        if (downloadedFileName == null) {
            logger.error("Download failed! No file detected.");
        }
        // Ensure the fileName is not null or empty
        assertThat("File name should not be null", downloadedFileName, is(notNullValue()));
        assertThat("File name should not be empty", downloadedFileName, not(isEmptyOrNullString()));

        // Delete the after verification
        boolean isDeleted = FileDownloadUtils.deleteLatestDownloadedFile();
        assertThat("Downloaded file is not deleted", isDeleted, is(true));
    }

    public void writeDownWorkerRates(String rateType) {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Writing worker rates into a text file >>>>>>>>>>>>>>>>>>>>");
        // array to store worker rates
        String[] workerRates = new String[4];

        String fileName1 = TEST_RESOURCE_FOLDER + File.separator + "agreement-special-rates.csv";
        String fileName2 = TEST_RESOURCE_FOLDER + File.separator + "agreement-bank-rates.csv";
        BasePage.waitUntilElementPresent(agreementOverviewPage.getAgencyCostVat(rateType), 30);
        workerRates[0] = BasePage.getText(BasePage.getElement(agreementOverviewPage.getAgencyCostNoVat(rateType)));
        workerRates[1] = BasePage.getText(BasePage.getElement(agreementOverviewPage.getChHourlyMargin(rateType)));
        workerRates[2] = BasePage.getText(BasePage.getElement(agreementOverviewPage.getChHourlyVat(rateType)));
        workerRates[3] = BasePage.getText(BasePage.getElement(agreementOverviewPage.getFinalRateNoVat(rateType)));
        if (rateType.contains("Special")) {
            FileWriterUtils.clearContent(fileName1);
            FileWriterUtils.writeWorkerRatesToAFile(fileName1, workerRates);
        } else if (rateType.contains("Bank")) {
            FileWriterUtils.clearContent(fileName2);
            FileWriterUtils.writeWorkerRatesToAFile(fileName2, workerRates);
        }
    }

    private void searchAgreementByAgreementId() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Searching an agreement by using agreement id >>>>>>>>>>>>>>>>>>>>");
        String text = getFirstAgreementId();
        BasePage.sendKeys(agreementOverviewPage.searchInput, text);
        BasePage.clickOnEnterKey(agreementOverviewPage.searchInput);
        BasePage.waitUntilElementPresent(agreementOverviewPage.firstSearchedResult, 60);
    }

    public void openWorkerRatesPopup() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Open relevant worker rates popup >>>>>>>>>>>>>>>>>>>>");
        searchAgreementByAgreementId();
        BasePage.waitUntilElementClickable(agreementOverviewPage.workerRatesViewIcon, 60);
        BasePage.clickWithJavaScript(agreementOverviewPage.workerRatesViewIcon);
    }

    public void clickOnMarkAsSigned() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<< Clicking on Mark as Signed button >>>>>>>>>>>>>>>>>>>>>>");
        BasePage.scrollToWebElement(agreementOverviewPage.markAsSignedButton);
        BasePage.clickWithJavaScript(agreementOverviewPage.markAsSignedButton);
        enterDataForAttachAgreementPopup();
    }

    public void closeWorkerRatesPopup() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Close worker rates popup >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementClickable(agreementOverviewPage.workerRatesCloseIcon, 30);
        BasePage.clickWithJavaScript(agreementOverviewPage.workerRatesCloseIcon);
    }
}
