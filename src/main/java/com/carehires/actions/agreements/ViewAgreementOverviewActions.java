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

import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isIn;
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
        String valueWithCurrency = BasePage.getText(viewAgreementOverviewPage.workerRatesTableWorkerHourlyRate).trim();
        String value = valueWithCurrency.split(" ")[1];
        return value;
    }

    private String getAgencyHourlyCostWithVat() {
        String valueWithCurrency = BasePage.getText(viewAgreementOverviewPage.workerRatesTableAgencyHourlyCostWithVat)
                .trim();
        String value = valueWithCurrency.split(" ")[1];
        return value;
    }

    private String getAgencyHourlyCostWithNoVat() {
        String valueWithCurrency = BasePage.getText(viewAgreementOverviewPage
                .workerRatesTableAgencyHourlyCostWithNoVat).trim();
        String value = valueWithCurrency.split(" ")[1];
        return value;
    }

    private String getCareHiresHourlyCost() {
        String valueWithCurrency = BasePage.getText(viewAgreementOverviewPage.workerRatesTableCareHiresHourlyCost)
                .trim();
        String value = valueWithCurrency.split(" ")[1];
        return value;
    }

    private String getFinalHourlyRateWithVat() {
        String valueWithCurrency = BasePage.getText(viewAgreementOverviewPage.workerRatesTableFinalHourlyRateWithVat)
                .trim();
        String value = valueWithCurrency.split(" ")[1];
        return value;
    }

    private String getFinalHourlyRateWithNoVat() {
        String valueWithCurrency = BasePage.getText(viewAgreementOverviewPage.workerRatesTableFinalHourlyRateWithNoVat)
                .trim();
        String value = valueWithCurrency.split(" ")[1];
        return value;
    }

    private String getWorkerTypeFromWorkerRatesPopup() {
        String value = BasePage.getText(viewAgreementOverviewPage.workerRatePopupWorkerType).trim();
        return value;
    }

    private List<String> getSkillsFromWorkerRatesPopup() {
        List<String> skills = Arrays.stream(BasePage.getText(viewAgreementOverviewPage.workerRatePopupSkills)
                .split(",")).toList();
        return skills;
    }

    private String getHourlyRateFromWorkerRatesPopup() {
        String value = BasePage.getAttributeValue(viewAgreementOverviewPage.workerRatePopupHourlyRate,
                VALUE_ATTRIBUTE).trim();
        return value;
    }

    private String getAgencyMarginFromWorkerRatesPopup() {
        String value = BasePage.getAttributeValue(viewAgreementOverviewPage.workerRatePopupAgencyMargin,
                VALUE_ATTRIBUTE).trim();
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
        String value = BasePage.getAttributeValue(viewAgreementOverviewPage.workerRatePopupChHourlyMargin,
                VALUE_ATTRIBUTE).trim();
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

    private String getFinalRateWithNoVatFromWorkerRatesPopup() {
        String value = BasePage.getText(viewAgreementOverviewPage.workerRatePopupFinalRateWithNoVat).trim();
        return value;
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
        assert getChHourlyVatFromSleepInRatesPopup() != null;
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
        String value = BasePage.getText(viewAgreementOverviewPage.sleepInRatesTableWorkerType).trim();
        return value;
    }

    private String getSleepInRatesTableWorkerHourlyRate() {
        String valueWithCurrency = BasePage.getText(viewAgreementOverviewPage.sleepInRatesTableHourlyChargeRate).trim();
        String value = valueWithCurrency.split(" ")[1];
        return value;
    }

    private String getSleepInRatesTableAgencyHourlyCostWithVat() {
        String valueWithCurrency = BasePage.getText(viewAgreementOverviewPage.sleepInRatesTableAgencyHourlyCostWithVat).trim();
        String value = valueWithCurrency.split(" ")[1];
        return value;
    }

    private String getSleepInRatesTableAgencyHourlyCostWithNoVat() {
        String valueWithCurrency = BasePage.getText(viewAgreementOverviewPage.sleepInRatesTableAgencyHourlyCostWithNoVat).trim();
        String value = valueWithCurrency.split(" ")[1];
        return value;
    }

    private String getSleepInRatesTableCareHiresHourlyCost() {
        String valueWithCurrency = BasePage.getText(viewAgreementOverviewPage.sleepInRatesTableCareHiresHourlyCost).trim();
        String value = valueWithCurrency.split(" ")[1];
        return value;
    }

    private String getSleepInRatesTableFinalHourlyRateWithVat() {
        String valueWithCurrency = BasePage.getText(viewAgreementOverviewPage.sleepInRatesTableFinalHourlyRateWithVat).trim();
        String value = valueWithCurrency.split(" ")[1];
        return value;
    }

    private String getSleepInRatesTableFinalHourlyRateWithNoVat() {
        String valueWithCurrency = BasePage.getText(viewAgreementOverviewPage.sleepInRatesTableFinalHourlyRateWithNoVat).trim();
        String value = valueWithCurrency.split(" ")[1];
        return value;
    }

    private String getWorkerTypeFromSleepInRatesPopup() {
        String value = BasePage.getText(viewAgreementOverviewPage.sleepInRatesPopupWorkerType).trim();
        return value;
    }

    private String getHourlyRateFromSleepInRatesPopup() {
        String value = BasePage.getAttributeValue(viewAgreementOverviewPage.sleepInRatesPopupHourlyRate, VALUE_ATTRIBUTE).trim();
        return value;
    }

    private String getAgencyMarginFromSleepInRatesPopup() {
        String value = BasePage.getAttributeValue(viewAgreementOverviewPage.sleepInRatesPopupAgencyMargin, VALUE_ATTRIBUTE).trim();
        return value;
    }

    private String getAgencyVatFromSleepInRatesPopup() {
        String value = BasePage.getText(viewAgreementOverviewPage.sleepInRatesPopupAgencyVat).trim();
        return value;
    }

    private String getAgencyCostWithVatFromSleepInRatesPopup() {
        String value = BasePage.getText(viewAgreementOverviewPage.sleepInRatesPopupAgencyCostWithVat).trim();
        return value;
    }

    private String getAgencyCostWithNoVatFromSleepInRatesPopup() {
        String value = BasePage.getText(viewAgreementOverviewPage.sleepInRatesPopupAgencyCostWithNoVat).trim();
        return value;
    }

    private String getChHourlyMarginFromSleepInRatesPopup() {
        String value = BasePage.getAttributeValue(viewAgreementOverviewPage.sleepInRatesPopupChHourlyMargin, VALUE_ATTRIBUTE).trim();
        return value;
    }

    private String getChHourlyVatFromSleepInRatesPopup() {
        String value = BasePage.getText(viewAgreementOverviewPage.sleepInRatesPopupChHourlyVat).trim();
        return value;
    }

    private String getFinalRateWithVatFromSleepInRatesPopup() {
        String value = BasePage.getText(viewAgreementOverviewPage.sleepInRatesPopupFinalRateWithVat).trim();
        return value;
    }

    private String getFinalRateWithNoVatFromSleepInRatesPopup() {
        String value = BasePage.getText(viewAgreementOverviewPage.sleepInRatesPopupFinalRateWithNoVat).trim();
        return value;
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
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Agreement - Marking as Active Again >>>>>>>>>>>>>>>>>>>>");
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
}
