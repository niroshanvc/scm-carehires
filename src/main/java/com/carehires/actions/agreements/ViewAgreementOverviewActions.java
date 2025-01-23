package com.carehires.actions.agreements;

import com.carehires.pages.agreements.ViewAgreementOverviewPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
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
    private static final String YML_FILE_PROVIDER = "provider-create";
    private static final String YML_HEADER = "Mark As Signed";
    private static final String YML_HEADER_WORKER_RATES = "Worker Rates";
    private static final String YML_HEADER_NORMAL_RATE = "Normal Rate";
    private static final String YML_HEADER_SPECIAL_HOLIDAY_RATE = "Special Holiday Rate";
    private static final String YML_HEADER_SIGNATORIES = "Signatories";
    private static final String YML_HEADER_AGENCY = "Agency";
    private static final String YML_HEADER_PROVIDER = "Provider";
    private static final String ADD = "Add";
    private static final String RESOURCE_FOLDER = System.getProperty("user.dir") + File.separator + "src"
            + File.separator
            + "test" + File.separator + "resources";
    private static final String VALUE_ATTRIBUTE = "value";

    String siteToBeRemoved;

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
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying Provider Name displaying in the Attach Agreement popup >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementPresent(viewAgreementOverviewPage.attachAgreementNote, 30);
        String providerFullText = BasePage.getText(viewAgreementOverviewPage.providerTermsAndConditionsText).trim();
        String[] strArr = providerFullText.split("on behalf of");
        String actual = strArr[1].trim();
        String expected = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER_SIGNATORIES,
                YML_HEADER_PROVIDER, "Name");
        assertThat("Provider name displays incorrectly!", actual, is(expected));
    }

    private void verifyAgencyNameLoadedInAttachAgreementPopup() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying Agency Name displaying in the Attach Agreement popup >>>>>>>>>>>>>>>>>>>>");
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
        verifyMarkAsActiveSuccessMessage();
    }

    private void verifyMarkAsActiveSuccessMessage() {

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

    private String getSleepInRatesTableCareHiresHourlyCost() {
        String valueWithCurrency = BasePage.getText(viewAgreementOverviewPage.sleepInRatesTableCareHiresHourlyCost).trim();
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
        BasePage.waitUntilElementClickable(viewAgreementOverviewPage.inactiveButton, 60);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.inactiveButton);
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
        BasePage.waitUntilElementClickable(viewAgreementOverviewPage.editAgreementButton, 60);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.editAgreementButton);
        BasePage.waitUntilElementClickable(viewAgreementOverviewPage.editSitesButton, 60);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.editSitesButton);

        siteToBeRemoved = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_PROVIDER, "Site Management", ADD, "Dataset2", "SiteName");
        WebElement removeSite = viewAgreementOverviewPage.manageSiteAddRemoveCheckbox(siteToBeRemoved);
        WebElement attributeChecking = viewAgreementOverviewPage.checkboxCheckedVerification(siteToBeRemoved);
        String attr = BasePage.getAttributeValue(attributeChecking, "class");
        if (!attr.contains("checked")) {
            BasePage.clickWithJavaScript(removeSite);
        }
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.applyButton);
        BasePage.waitUntilElementClickable(viewAgreementOverviewPage.saveButton, 60);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.saveButton);

        // Change Summary popup
        BasePage.waitUntilElementDisplayed(viewAgreementOverviewPage.effectiveDateCalendar, 60);
        verifyDataLoadedInRemovedSitesArea();

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

    private void verifyDataLoadedInRemovedSitesArea() {
        String actual = getDataFromRemovedSitesAreaInChangeSummaryPopup();
        String expected = siteToBeRemoved;
        assertThat("Removed site is not correctly displayed!", actual, is(expected));
    }

    private String getDataFromRemovedSitesAreaInChangeSummaryPopup() {
        String text = BasePage.getText(viewAgreementOverviewPage.removedSiteInChangeSummaryPopup);
        return text;
    }

    public void updateWorkerRates() {
        // delete existing worker rate
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.editAgreementButton);
        BasePage.waitUntilElementClickable(viewAgreementOverviewPage.workerRatesThreeDots, 60);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.workerRatesThreeDots);
        deleteAlreadySavedWorkerRates();

        // saving changes
        savingDataOnChangeSummaryPopup(YML_HEADER_WORKER_RATES);
        verifySiteUpdateSuccessMessage();

        // add new record
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.editAgreementButton);
        BasePage.waitUntilElementClickable(viewAgreementOverviewPage.workerRatesAddButton, 60);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.workerRatesAddButton);
        enterWorkerType();
        enterSkills();

        // enter normal rate data
        enterHourlyRate(YML_HEADER_NORMAL_RATE);
        enterAgencyMargin(YML_HEADER_NORMAL_RATE);
        enterChHourlyMargin(YML_HEADER_NORMAL_RATE);

        // enter special holiday rate
        doClickOnEnableRateCheckbox(YML_HEADER_SPECIAL_HOLIDAY_RATE);
        enterHourlyRate(YML_HEADER_SPECIAL_HOLIDAY_RATE);
        enterAgencyMargin(YML_HEADER_SPECIAL_HOLIDAY_RATE);
        enterChHourlyMargin(YML_HEADER_SPECIAL_HOLIDAY_RATE);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.workerRatesPopupAddButton);
    }

    private void doClickOnEnableRateCheckbox(String rateType) {
        WebElement el = viewAgreementOverviewPage.checkEnableRateCheckbox(rateType);
        String attr = BasePage.getAttributeValue(viewAgreementOverviewPage.enableRateCheckboxSpan(rateType), "class");
        if (!attr.contains("checked")) {
            BasePage.clickWithJavaScript(el);
        }
    }

    private void enterChHourlyMargin(String rateType) {
        String chHourlyMargin = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_EDIT,
                YML_HEADER_WORKER_RATES, YML_HEADER_NORMAL_RATE, "CH Hourly Margin");
        WebElement element = viewAgreementOverviewPage.chHourlyMarginInput(rateType);
        BasePage.clearAndEnterTexts(element, chHourlyMargin);
        BasePage.clickTabKey(element);
    }

    private void enterAgencyMargin(String rateType) {
        String agencyMargin = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_EDIT,
                YML_HEADER_WORKER_RATES, YML_HEADER_NORMAL_RATE, "Agency Margin");
        WebElement element = viewAgreementOverviewPage.agencyMarginInput(rateType);
        BasePage.clearAndEnterTexts(element, agencyMargin);
    }

    private void enterHourlyRate(String rateType) {
        String hourlyRate = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_EDIT,
                YML_HEADER_WORKER_RATES, YML_HEADER_NORMAL_RATE, "Hourly Rate");
        WebElement element = viewAgreementOverviewPage.hourlyRateInput(rateType);
        BasePage.waitUntilElementDisplayed(element, 20);
        BasePage.clearAndEnterTexts(element, hourlyRate);
    }

    private void enterSkills() {
        String[] skills = Objects.requireNonNull(DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_EDIT,
                YML_HEADER_WORKER_RATES, YML_HEADER_NORMAL_RATE, "Skills")).split(",");
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.skillsDropdown);
        By locator = By.xpath(getDropdownOptionXpath(skills[0]));
        BasePage.waitUntilPresenceOfElementLocated(locator, 20);
        for (String skill : skills) {
            BasePage.clickWithJavaScript(getDropdownOptionXpath(skill));
        }
    }

    private void enterWorkerType() {
        String workerType = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_EDIT,
                YML_HEADER_WORKER_RATES, YML_HEADER_NORMAL_RATE, "Worker Type");
        BasePage.waitUntilElementDisplayed(viewAgreementOverviewPage.workerTypeDropdown, 30);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.workerTypeDropdown);
        By by = By.xpath(getDropdownOptionXpath(workerType));
        BasePage.waitUntilVisibilityOfElementLocated(by, 20);
        BasePage.scrollToWebElement(getDropdownOptionXpath(workerType));
        BasePage.clickWithJavaScript(getDropdownOptionXpath(workerType));
    }

    private String getDropdownOptionXpath(String option) {
        return String.format("//nb-option[contains(text(),'%s')]", option);
    }

    private void deleteAlreadySavedWorkerRates() {
        BasePage.waitUntilElementClickable(viewAgreementOverviewPage.deleteIcon, 30);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.deleteIcon);
        BasePage.waitUntilElementDisplayed(viewAgreementOverviewPage.cancelChangesIcon, 30);
        BasePage.clickWithJavaScript(viewAgreementOverviewPage.saveButton);
    }
}
