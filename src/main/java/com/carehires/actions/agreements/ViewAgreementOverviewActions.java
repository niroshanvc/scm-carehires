package com.carehires.actions.agreements;

import com.carehires.pages.agreements.ViewAgreementOverviewPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import com.carehires.utils.FileDownloadUtils;
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

import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isIn;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;

public class ViewAgreementOverviewActions {

    ViewAgreementOverviewPage viewAgreementOverviewPage;
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
    private static final String RESOURCE_FOLDER = System.getProperty("user.dir") + File.separator + "src"
            + File.separator
            + "test" + File.separator + "resources";
    private static final String VALUE_ATTRIBUTE = "value";

    private static final Logger logger = LogManager.getLogger(ViewAgreementOverviewActions.class);


    public ViewAgreementOverviewActions() {
        viewAgreementOverviewPage = new ViewAgreementOverviewPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), viewAgreementOverviewPage);
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
            BasePage.waitUntilElementPresent(viewAgreementOverviewPage.signatureStatus, 60);
            actual = BasePage.getText(viewAgreementOverviewPage.signatureStatus).trim().toUpperCase();
        } else if (expected.equalsIgnoreCase("Signed") || expected.equalsIgnoreCase("Active")
            || expected.equalsIgnoreCase("Inactive")) {
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
        String absoluteFilePath = RESOURCE_FOLDER + File.separator + "Upload" + File.separator + "Agreement"
                + File.separator + doc;
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
        logger.info("<<<<<<<<<<<<<<<< Verifying Provider Name displaying in the Attach Agreement popup >>>>>>>>>>>>>");
        BasePage.waitUntilElementPresent(viewAgreementOverviewPage.attachAgreementNote, 30);
        String providerFullText = BasePage.getText(viewAgreementOverviewPage.providerTermsAndConditionsText).trim();
        String[] strArr = providerFullText.split("on behalf of");
        String actual = strArr[1].trim();
        String expected = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER_SIGNATORIES,
                YML_HEADER_PROVIDER, "Name");
        assertThat("Provider name displays incorrectly!", actual, is(expected));
    }

    private void verifyAgencyNameLoadedInAttachAgreementPopup() {
        logger.info("<<<<<<<<<<<<<<<<< Verifying Agency Name displaying in the Attach Agreement popup >>>>>>>>>>>>>>");
        String agencyFullText = BasePage.getText(viewAgreementOverviewPage.agencyTermsAndConditionsText).trim();
        String[] strArr = agencyFullText.split("on behalf of");
        String actual = strArr[1].trim();
        String expected = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER_SIGNATORIES,
                YML_HEADER_AGENCY, "Name");
        assertThat("Agency name displays incorrectly!", actual, is(expected));
    }

    public void clickOnActiveAgreement() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Clicking on Active Agreement button >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementPresent(viewAgreementOverviewPage.activateAgreementButton, 60);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.activateAgreementButton);
    }

    public void verifyContentsInWorkerRates() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying data displaying in the Worker Rates Popup >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementClickable(viewAgreementOverviewPage.workerRatesViewIcon, 60);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.workerRatesViewIcon);

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
        BasePage.waitUntilElementPresent(viewAgreementOverviewPage.workerRatesTableWorkerType, 60);
        return BasePage.getText(viewAgreementOverviewPage.workerRatesTableWorkerType).trim();
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
        String valueWithCurrency = BasePage.getText(viewAgreementOverviewPage.workerRatesTableWorkerHourlyRate).trim();
        return valueWithCurrency.split(" ")[1];
    }

    private String getAgencyHourlyCostWithVat() {
        String valueWithCurrency = BasePage.getText(viewAgreementOverviewPage.workerRatesTableAgencyHourlyCostWithVat)
                .trim();
        return valueWithCurrency.split(" ")[1];
    }

    private String getAgencyHourlyCostWithNoVat() {
        String valueWithCurrency = BasePage.getText(viewAgreementOverviewPage
                .workerRatesTableAgencyHourlyCostWithNoVat).trim();
        return valueWithCurrency.split(" ")[1];
    }

    private String getCareHiresHourlyCost() {
        String valueWithCurrency = BasePage.getText(viewAgreementOverviewPage.workerRatesTableCareHiresHourlyCost)
                .trim();
        return valueWithCurrency.split(" ")[1];
    }

    private String getFinalHourlyRateWithVat() {
        String valueWithCurrency = BasePage.getText(viewAgreementOverviewPage.workerRatesTableFinalHourlyRateWithVat)
                .trim();
        return valueWithCurrency.split(" ")[1];
    }

    private String getFinalHourlyRateWithNoVat() {
        String valueWithCurrency = BasePage.getText(viewAgreementOverviewPage.workerRatesTableFinalHourlyRateWithNoVat)
                .trim();
        return valueWithCurrency.split(" ")[1];
    }

    private String getWorkerTypeFromWorkerRatesPopup() {
        return BasePage.getText(viewAgreementOverviewPage.workerRatePopupWorkerType).trim();
    }

    private List<String> getSkillsFromWorkerRatesPopup() {
        return Arrays.stream(BasePage.getText(viewAgreementOverviewPage.workerRatePopupSkills)
                .split(",")).toList();
    }

    private String getHourlyRateFromWorkerRatesPopup() {
        return BasePage.getAttributeValue(viewAgreementOverviewPage.workerRatePopupHourlyRate,
                VALUE_ATTRIBUTE).trim();
    }

    private String getAgencyMarginFromWorkerRatesPopup() {
        return BasePage.getAttributeValue(viewAgreementOverviewPage.workerRatePopupAgencyMargin,
                VALUE_ATTRIBUTE).trim();
    }

    private String getAgencyVatFromWorkerRatesPopup() {
        return BasePage.getText(viewAgreementOverviewPage.workerRatePopupAgencyVat).trim();
    }

    private String getAgencyCostWithVatFromWorkerRatesPopup() {
        return BasePage.getText(viewAgreementOverviewPage.workerRatePopupAgencyCostWithVat).trim();
    }

    private String getAgencyCostWithNoVatFromWorkerRatesPopup() {
        return BasePage.getText(viewAgreementOverviewPage.workerRatePopupAgencyCostWithNoVat).trim();
    }

    private String getChHourlyMarginFromWorkerRatesPopup() {
        return BasePage.getAttributeValue(viewAgreementOverviewPage.workerRatePopupChHourlyMargin,
                VALUE_ATTRIBUTE).trim();
    }

    private String getChHourlyVatFromWorkerRatesPopup() {
        return BasePage.getText(viewAgreementOverviewPage.workerRatePopupChHourlyVat).trim();
    }

    private String getFinalRateWithVatFromWorkerRatesPopup() {
        return BasePage.getText(viewAgreementOverviewPage.workerRatePopupFinalRateWithVat).trim();
    }

    private String getFinalRateWithNoVatFromWorkerRatesPopup() {
        return BasePage.getText(viewAgreementOverviewPage.workerRatePopupFinalRateWithNoVat).trim();
    }

    public void verifyContentsInSleepInRequest() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying data displaying in Sleep In Rates popup >>>>>>>>>>>>>>>>>>>>");
        BasePage.refreshPage();
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(viewAgreementOverviewPage.sleepInRatesViewIcon, 60);
        BasePage.scrollToWebElement(viewAgreementOverviewPage.sleepInRatesViewIcon);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.sleepInRatesViewIcon);

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
        return BasePage.getText(viewAgreementOverviewPage.sleepInRatesTableWorkerType).trim();
    }

    private String getSleepInRatesTableWorkerHourlyRate() {
        String valueWithCurrency = BasePage.getText(viewAgreementOverviewPage.sleepInRatesTableHourlyChargeRate).trim();
        return valueWithCurrency.split(" ")[1];
    }

    private String getSleepInRatesTableAgencyHourlyCostWithVat() {
        String valueWithCurrency = BasePage.getText(viewAgreementOverviewPage.sleepInRatesTableAgencyHourlyCostWithVat).trim();
        return valueWithCurrency.split(" ")[1];
    }

    private String getSleepInRatesTableAgencyHourlyCostWithNoVat() {
        String valueWithCurrency = BasePage.getText(viewAgreementOverviewPage.sleepInRatesTableAgencyHourlyCostWithNoVat).trim();
        return valueWithCurrency.split(" ")[1];
    }

    private String getSleepInRatesTableFinalHourlyRateWithVat() {
        String valueWithCurrency = BasePage.getText(viewAgreementOverviewPage.sleepInRatesTableFinalHourlyRateWithVat).trim();
        return valueWithCurrency.split(" ")[1];
    }

    private String getSleepInRatesTableFinalHourlyRateWithNoVat() {
        String valueWithCurrency = BasePage.getText(viewAgreementOverviewPage.sleepInRatesTableFinalHourlyRateWithNoVat).trim();
        return valueWithCurrency.split(" ")[1];
    }

    private String getWorkerTypeFromSleepInRatesPopup() {
        return BasePage.getText(viewAgreementOverviewPage.sleepInRatesPopupWorkerType).trim();
    }

    private String getHourlyRateFromSleepInRatesPopup() {
        return BasePage.getAttributeValue(viewAgreementOverviewPage.sleepInRatesPopupHourlyRate, VALUE_ATTRIBUTE).trim();
    }

    private String getAgencyMarginFromSleepInRatesPopup() {
        return BasePage.getAttributeValue(viewAgreementOverviewPage.sleepInRatesPopupAgencyMargin, VALUE_ATTRIBUTE).trim();
    }

    private String getAgencyVatFromSleepInRatesPopup() {
        return BasePage.getText(viewAgreementOverviewPage.sleepInRatesPopupAgencyVat).trim();
    }

    private String getAgencyCostWithVatFromSleepInRatesPopup() {
        return BasePage.getText(viewAgreementOverviewPage.sleepInRatesPopupAgencyCostWithVat).trim();
    }

    private String getAgencyCostWithNoVatFromSleepInRatesPopup() {
        return BasePage.getText(viewAgreementOverviewPage.sleepInRatesPopupAgencyCostWithNoVat).trim();
    }

    private String getChHourlyMarginFromSleepInRatesPopup() {
        return BasePage.getAttributeValue(viewAgreementOverviewPage.sleepInRatesPopupChHourlyMargin, VALUE_ATTRIBUTE).trim();
    }

    private String getChHourlyVatFromSleepInRatesPopup() {
        return BasePage.getText(viewAgreementOverviewPage.sleepInRatesPopupChHourlyVat).trim();
    }

    private String getFinalRateWithVatFromSleepInRatesPopup() {
        return BasePage.getText(viewAgreementOverviewPage.sleepInRatesPopupFinalRateWithVat).trim();
    }

    private String getFinalRateWithNoVatFromSleepInRatesPopup() {
        return BasePage.getText(viewAgreementOverviewPage.sleepInRatesPopupFinalRateWithNoVat).trim();
    }

    public void markAsInactive() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Agreement - Marking as Inactive >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementClickable(viewAgreementOverviewPage.deactivateButton, 60);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.deactivateButton);
        BasePage.waitUntilElementClickable(viewAgreementOverviewPage.deactivateButtonInDeactivateConfirmPopup, 30);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.deactivateButtonInDeactivateConfirmPopup);
        verifyInactiveSuccessMessage();
    }

    private void verifyInactiveSuccessMessage() {
        BasePage.waitUntilElementPresent(viewAgreementOverviewPage.successMessage, 1200);
        String actualInLowerCase = BasePage.getText(viewAgreementOverviewPage.successMessage).toLowerCase().trim();
        String expected = "Agreement marked as inactive";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Unable to mark as inactive!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(viewAgreementOverviewPage.successMessage, 60);
    }

    public void markAsActiveAgain() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Agreement - Marking as Active >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementClickable(viewAgreementOverviewPage.activateAgreementButton, 60);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.activateAgreementButton);
        verifyActivateAgreementSuccessMessage();
    }

    private void verifyActivateAgreementSuccessMessage() {
        BasePage.waitUntilElementPresent(viewAgreementOverviewPage.successMessage, 1200);
        String actualInLowerCase = BasePage.getText(viewAgreementOverviewPage.successMessage).toLowerCase().trim();
        String expected = "Agreement marked as active";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Unable to mark as active!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(viewAgreementOverviewPage.successMessage, 60);
    }

    public void editSite() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Updating site info >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementClickable(viewAgreementOverviewPage.editAgreementButton, 60);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.editAgreementButton);
        BasePage.waitUntilElementClickable(viewAgreementOverviewPage.editSitesButton, 60);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.editSitesButton);

        String attr = BasePage.getAttributeValue(viewAgreementOverviewPage.removingSite, "class");
        if (attr.contains("checked")) {
            BasePage.clickWithJavaScript(viewAgreementOverviewPage.manageSiteAddRemoveCheckbox);
        }
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.applyButton);
        BasePage.waitUntilElementClickable(viewAgreementOverviewPage.saveButton, 60);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.saveButton);

        // Change Summary popup
        BasePage.waitUntilElementDisplayed(viewAgreementOverviewPage.effectiveDateCalendar, 60);

        // saving changes
        savingDataOnChangeSummaryPopup("Edit Site");

        verifySiteUpdateSuccessMessage();
    }

    private void savingDataOnChangeSummaryPopup(String mainHeader) {
        enterEffectiveDate(mainHeader);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.changeSummarySaveButton);
    }

    private void verifySiteUpdateSuccessMessage() {
        BasePage.waitUntilElementPresent(viewAgreementOverviewPage.successMessage, 90);
        String actualInLowerCase = BasePage.getText(viewAgreementOverviewPage.successMessage).toLowerCase().trim();
        String expected = "Record updated successfully";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Agreement not updated!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(viewAgreementOverviewPage.successMessage, 20);
    }

    private void enterEffectiveDate(String mainHeader) {
        String effectiveDate = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_EDIT, mainHeader, "Effective Date");
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.effectiveDateCalendar);
        genericUtils.selectDateFromCalendarPopup(effectiveDate);
    }

    public void removeWorkerRates() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Removing already existing Worker Rates >>>>>>>>>>>>>>>>>>>>");
        // delete existing worker rate
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.editAgreementButton);
        BasePage.waitUntilElementClickable(viewAgreementOverviewPage.workerRatesThreeDots, 60);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.workerRatesThreeDots);
        deleteAlreadySavedRecord();

        // saving effective date
        savingDataOnChangeSummaryPopup(YML_HEADER_WORKER_RATES);
        verifySiteUpdateSuccessMessage();
    }

    public void updateWorkerRates() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Updating Worker Rates >>>>>>>>>>>>>>>>>>>>");
        // add new record
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.editAgreementButton);
        BasePage.waitUntilElementClickable(viewAgreementOverviewPage.workerRatesAddButton, 60);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.workerRatesAddButton);
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

        BasePage.scrollToWebElement(viewAgreementOverviewPage.workerRatesPopupContinueButton);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.workerRatesPopupAddButton);
        BasePage.waitUntilElementDisplayed(viewAgreementOverviewPage.workerRatesPopupViewSpecialRateIcon, 30);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.workerRatesPopupContinueButton);
        applyChanges();

        // saving effective dates
        savingDataOnChangeSummaryPopup(YML_HEADER_WORKER_RATES);
        verifySiteUpdateSuccessMessage();
    }

    private void enterFinalRateVat(String  header, String rateType) {
        String finalRateVat = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_EDIT,
                header, rateType, "Final Rate Vat");
        WebElement element = viewAgreementOverviewPage.finalRateVat(rateType);
        BasePage.clearAndEnterTexts(element, finalRateVat);
        BasePage.clickTabKey(element);
    }

    private void doClickOnEnableRateCheckbox(String rateType) {
        WebElement el = viewAgreementOverviewPage.checkEnableRateCheckbox(rateType);
        String attr = BasePage.getAttributeValue(viewAgreementOverviewPage.enableRateCheckboxSpan(rateType), "class");
        if (!attr.contains("checked")) {
            BasePage.clickWithJavaScript(el);
        }
    }

    private void enterChHourlyMargin(String header) {
        String chHourlyMargin = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_EDIT,
                header, YML_HEADER_NORMAL_RATE, "CH Hourly Margin");
        WebElement element = viewAgreementOverviewPage.chHourlyMarginInput(ViewAgreementOverviewActions.YML_HEADER_NORMAL_RATE);
        BasePage.clearAndEnterTexts(element, chHourlyMargin);
        BasePage.clickTabKey(element);
    }

    private void enterAgencyMargin(String header) {
        String agencyMargin = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_EDIT,
                header, YML_HEADER_NORMAL_RATE, "Agency Margin");
        WebElement element = viewAgreementOverviewPage.agencyMarginInput(ViewAgreementOverviewActions.YML_HEADER_NORMAL_RATE);
        BasePage.clearAndEnterTexts(element, agencyMargin);
    }

    private void enterHourlyRate(String header) {
        String hourlyRate = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_EDIT,
                header, YML_HEADER_NORMAL_RATE, "Hourly Rate");
        WebElement element = viewAgreementOverviewPage.hourlyRateInput(ViewAgreementOverviewActions.YML_HEADER_NORMAL_RATE);
        BasePage.waitUntilElementDisplayed(element, 20);
        BasePage.clearAndEnterTexts(element, hourlyRate);
    }

    private void enterSkills() {
        String[] skills = Objects.requireNonNull(DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_EDIT,
                YML_HEADER_WORKER_RATES, YML_HEADER_NORMAL_RATE, "Skills")).split(",");
        BasePage.genericWait(3000);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.skillsDropdown);
        By locator = By.xpath(viewAgreementOverviewPage.getDropdownOptionXpath(skills[0]));
        BasePage.waitUntilPresenceOfElementLocated(locator, 20);
        for (String skill : skills) {
            BasePage.clickWithJavaScript(viewAgreementOverviewPage.getDropdownOptionXpath(skill));
        }
    }

    private void enterWorkerType(String header) {
        String workerType = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_EDIT,
                header, YML_HEADER_NORMAL_RATE, "Worker Type");
        BasePage.waitUntilElementDisplayed(viewAgreementOverviewPage.workerTypeDropdown, 30);
        BasePage.genericWait(3000);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.workerTypeDropdown);
        By by = By.xpath(viewAgreementOverviewPage.getDropdownOptionXpath(workerType));
        BasePage.waitUntilVisibilityOfElementLocated(by, 20);
        BasePage.scrollToWebElement(viewAgreementOverviewPage.getDropdownOptionXpath(workerType));
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.getDropdownOptionXpath(workerType));
    }

    private void deleteAlreadySavedRecord() {
        BasePage.waitUntilElementClickable(viewAgreementOverviewPage.deleteIcon, 30);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.deleteIcon);
        applyChanges();
    }

    private void applyChanges() {
        BasePage.waitUntilElementDisplayed(viewAgreementOverviewPage.cancelChangesIcon, 30);
        BasePage.waitUntilElementClickable(viewAgreementOverviewPage.saveButton, 30);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.saveButton);
    }

    public void removeCancellationPolicy() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Removing already existing Cancellation Policy >>>>>>>>>>>>>>>>>>>>");

        // delete existing cancellation policy
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.editAgreementButton);
        BasePage.genericWait(2000);
        BasePage.scrollToWebElement(viewAgreementOverviewPage.cancellationPolicyThreeDots);
        BasePage.waitUntilElementClickable(viewAgreementOverviewPage.cancellationPolicyThreeDots, 60);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.cancellationPolicyThreeDots);
        deleteAlreadySavedRecord();

        // saving effective date
        savingDataOnChangeSummaryPopup(YML_HEADER_CANCELLATION_POLICY);
        verifySiteUpdateSuccessMessage();
    }

    public void addNewCancellationPolicy() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Cancellation Policy Info >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementClickable(viewAgreementOverviewPage.editAgreementButton, 60);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.editAgreementButton);
        BasePage.scrollToWebElement(viewAgreementOverviewPage.cancellationPolicyAddButton);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.cancellationPolicyAddButton);

        String beforeJobStart = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_EDIT,
                YML_HEADER_CANCELLATION_POLICY, "Before Job Start");
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.beforeJobStartDropdown);
        By by = By.xpath(viewAgreementOverviewPage.getDropdownOptionXpath(beforeJobStart));
        BasePage.waitUntilVisibilityOfElementLocated(by, 20);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.getDropdownOptionXpath(beforeJobStart));

        String cancellationFeePercentage = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_EDIT,
                YML_HEADER_CANCELLATION_POLICY, "Cancellation fee percentage");
        BasePage.clearAndEnterTexts(viewAgreementOverviewPage.cancellationFeePercentage, cancellationFeePercentage);

        String careHiresSplit = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_EDIT,
                YML_HEADER_CANCELLATION_POLICY, "CareHires split");
        BasePage.clearAndEnterTexts(viewAgreementOverviewPage.careHiresSplit, careHiresSplit);
        BasePage.clickTabKey(viewAgreementOverviewPage.careHiresSplit);
        BasePage.clickTabKey(viewAgreementOverviewPage.agencySplit);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.cancellationPolicyPopupAddButton);

        BasePage.scrollToWebElement(viewAgreementOverviewPage.continueButton);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.continueButton);
        applyChanges();

        // saving effective dates
        savingDataOnChangeSummaryPopup(YML_HEADER_CANCELLATION_POLICY);
        verifySiteUpdateSuccessMessage();
    }

    public void removeSleepInRequest() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Removing already existing Sleep in Request >>>>>>>>>>>>>>>>>>>>");

        // delete existing sleep in request
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.editAgreementButton);
        BasePage.genericWait(2000);
        BasePage.scrollToWebElement(viewAgreementOverviewPage.sleepInRequestThreeDots);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.sleepInRequestThreeDots);
        deleteAlreadySavedRecord();

        // saving effective date
        savingDataOnChangeSummaryPopup(YML_HEADER_SLEEP_IN_REQUEST);
        verifySiteUpdateSuccessMessage();
    }

    public void addSleepInRequest() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Sleep in request Info >>>>>>>>>>>>>>>>>>>>");
        // add new record
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.editAgreementButton);
        BasePage.waitUntilElementClickable(viewAgreementOverviewPage.sleepInRequestAddButton, 60);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.sleepInRequestAddButton);
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

        BasePage.scrollToWebElement(viewAgreementOverviewPage.sleepInRatesPopupContinueButton);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.sleepInRatesPopupAddButton);
        BasePage.waitUntilElementDisplayed(viewAgreementOverviewPage.sleepInRatesPopupViewSpecialRateIcon, 30);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.sleepInRatesPopupContinueButton);
        applyChanges();

        // saving effective dates
        savingDataOnChangeSummaryPopup(YML_HEADER_SLEEP_IN_REQUEST);
        verifySiteUpdateSuccessMessage();
    }

    public void downloadAndDeleteAgreement() {
        // Trigger the download
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Downloading the manually signed agreement >>>>>>>>>>>>>>>>>>>>");
        BasePage.scrollToWebElement(viewAgreementOverviewPage.downloadAgreement);
        FileDownloadUtils.triggerDownloadAndCloseTab(viewAgreementOverviewPage.downloadAgreement);

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
}
