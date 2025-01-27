package com.carehires.actions.agreements;

import com.carehires.pages.agreements.SleepInRatesPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SleepInRatesActions {

    SleepInRatesPage sleepInRatesPage;
    private static final String ENTITY = "agreement";
    private static final String YML_FILE = "agreement-create";
    private static final String YML_HEADER = "Sleep In Rates";
    private String workerType;
    private String hourlyRate;
    private String agencyMargin;
    private String chHourlyMargin;

    private static final Logger logger = LogManager.getLogger(SleepInRatesActions.class);

    public SleepInRatesActions() {
        sleepInRatesPage = new SleepInRatesPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), sleepInRatesPage);
        } catch (Exception e) {
            logger.error("Error while initializing Sleep In Rates Page elements: {}", e.getMessage());
        }
    }

    public void addSleepInRates() {
        BasePage.waitUntilPageCompletelyLoaded();
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Sleep in Rates Info >>>>>>>>>>>>>>>>>>>>");

        workerType = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Worker Type");
        BasePage.clickWithJavaScript(sleepInRatesPage.workerTypeDropdown);
        By by = By.xpath(sleepInRatesPage.getDropdownOptionXpath(workerType));
        BasePage.waitUntilVisibilityOfElementLocated(by, 20);
        BasePage.scrollToWebElement(sleepInRatesPage.getDropdownOptionXpath(workerType));
        BasePage.clickWithJavaScript(sleepInRatesPage.getDropdownOptionXpath(workerType));

        BasePage.scrollToWebElement(sleepInRatesPage.fridayNightRate);
        hourlyRate = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Hourly Rate");
        BasePage.waitUntilElementDisplayed(sleepInRatesPage.normalRateHourlyRateInput, 20);
        BasePage.clearAndEnterTexts(sleepInRatesPage.normalRateHourlyRateInput, hourlyRate);

        agencyMargin = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Agency Margin");
        BasePage.clearAndEnterTexts(sleepInRatesPage.normalRateAgencyMarginInput, agencyMargin);

        chHourlyMargin = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "CH Hourly Margin");
        BasePage.clearAndEnterTexts(sleepInRatesPage.normalRateChHourlyMarginInput, chHourlyMargin);
        BasePage.clickTabKey(sleepInRatesPage.normalRateChHourlyMarginInput);

        verifyNormalRateAgencyVat();
        verifyNormalRateAgencyCostWithVatAmount();
        verifyNormalRateAgencyCostWithNoVatAmount();
        verifyNormalRateChHourlyVat();
        verifyNormalRateFinalRateWithVatAmount();
        verifyNormalRateFinalRateWithNoVatAmount();

        BasePage.scrollToWebElement(sleepInRatesPage.addButton);
        BasePage.clickWithJavaScript(sleepInRatesPage.addButton);
        verifyWorkerRateIsAddedSuccessfully();

        BasePage.scrollToWebElement(sleepInRatesPage.continueButton);
        verifyDataLoadedInCurrentWorkerRatesList();
        BasePage.clickWithJavaScript(sleepInRatesPage.continueButton);
    }

    private void verifyDataLoadedInCurrentWorkerRatesList() {
        BasePage.genericWait(1500);
        verifySavedWorkerType();
        verifySavedHourlyChargeRate();
        verifyAgencyHourlyMarginWithVat();
        verifyAgencyHourlyMarginWithoutVat();
        verifyCareHiresHourlyMargin();
        verifyFinallyHourlyRateWithVat();
        verifyFinallyHourlyRateWithoutVat();
    }

    private void verifyFinallyHourlyRateWithoutVat() {
        String actualWithCurrency = BasePage.getText(sleepInRatesPage.finallyHourlyRateWithNonVat).trim();
        String[] str = actualWithCurrency.split(" ");
        String actual = str[1];
        double expectedWithMoreDecimalPlaces = calculateSumOfHourlyRateAndAgencyMargin() +
                ((Double.parseDouble(chHourlyMargin)) * ( 1 + 0.2));
        String expected = roundToTwoDecimalPlaces(expectedWithMoreDecimalPlaces);
        assertThat("Finally Hourly Rate with Non VAT is not correctly displayed!", actual, is(expected));
    }

    private void verifyFinallyHourlyRateWithVat() {
        String actualWithCurrency = BasePage.getText(sleepInRatesPage.finallyHourlyRateWithVat).trim();
        String[] str = actualWithCurrency.split(" ");
        String actual = str[1];
        double expectedWithMoreDecimalPlaces = calculateSumOfHourlyRateAndAgencyMargin() + calculateExpectedAgencyVat()
                + ((Double.parseDouble(chHourlyMargin)) * ( 1 + 0.2));
        String expected = roundToTwoDecimalPlaces(expectedWithMoreDecimalPlaces);
        assertThat("Finally Hourly Rate with VAT is not correctly displayed!", actual, is(expected));
    }

    private void verifyCareHiresHourlyMargin() {
        String actualWithCurrency = BasePage.getText(sleepInRatesPage.careHiresHourlyMargin).trim();
        String[] str = actualWithCurrency.split(" ");
        String actual = str[1];
        double expected = ((Double.parseDouble(chHourlyMargin)) * ( 1 + 0.2));
        String expectedString = roundToTwoDecimalPlaces(expected);
        assertThat("CareHires Hourly Margin is not correctly displayed!", actual, is(expectedString));
    }

    private void verifyAgencyHourlyMarginWithoutVat() {
        String actualWithCurrency = BasePage.getText(sleepInRatesPage.agencyHourlyMarginWithNonVat).trim();
        String[] str = actualWithCurrency.split(" ");
        String actual = str[1];
        double expectedWithMoreDecimalPlaces = calculateSumOfHourlyRateAndAgencyMargin();
        String expected = roundToTwoDecimalPlaces(expectedWithMoreDecimalPlaces);
        assertThat("Agency hourly margin with Non VAT is not correctly displayed!", actual, is(expected));
    }

    private void verifyAgencyHourlyMarginWithVat() {
        String actualWithCurrency = BasePage.getText(sleepInRatesPage.agencyHourlyMarginWithVat).trim();
        String[] str = actualWithCurrency.split(" ");
        String actual = str[1];
        double expectedWithMoreDecimalPlaces = calculateSumOfHourlyRateAndAgencyMargin() + calculateExpectedAgencyVat();
        String expected = roundToTwoDecimalPlaces(expectedWithMoreDecimalPlaces);
        assertThat("Agency hourly margin with VAT is not correctly displayed!", actual, is(expected));
    }


    private void verifySavedHourlyChargeRate() {
        String actualWithCurrency = BasePage.getText(sleepInRatesPage.hourlyChargeRate).trim();
        String[] str = actualWithCurrency.split(" ");
        String actual = str[1];
        String expected = hourlyRate;
        assertThat("Hourly charge rate is not correctly displayed!", actual, is(expected));
    }

    private void verifySavedWorkerType() {
        BasePage.waitUntilElementPresent(sleepInRatesPage.workerType, 30);
        String actual = BasePage.getText(sleepInRatesPage.workerType).trim().toLowerCase();
        String expected = workerType.toLowerCase();
        assertThat("Worker type is not correctly displayed!", actual, is(expected));
    }

    private void verifyWorkerRateIsAddedSuccessfully() {
        BasePage.waitUntilElementPresent(sleepInRatesPage.successMessage, 30);
        String actualInLowerCase = BasePage.getText(sleepInRatesPage.successMessage).toLowerCase().trim();
        String expected = "Record created successfully";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Worker rate is not created!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(sleepInRatesPage.successMessage, 20);
    }

    private void verifyNormalRateFinalRateWithNoVatAmount() {
        double expected = calculateSumOfHourlyRateAndAgencyMargin() + Double.parseDouble(chHourlyMargin)
                + calculateExpectedChHourlyVat();
        String expectedString = roundToTwoDecimalPlaces(expected);
        String actual = BasePage.getText(sleepInRatesPage.normalRateFinalRateNoVat).trim();
        assertThat("Normal Rate Final Rate Without VAT Amount is incorrect", actual, is(expectedString));
    }

    private void verifyNormalRateFinalRateWithVatAmount() {
        double expected = calculateSumOfHourlyRateAndAgencyMargin() + Double.parseDouble(
                getNormalRateAgencyVatAmount()) + Double.parseDouble(chHourlyMargin) + calculateExpectedChHourlyVat();
        String expectedString = roundToTwoDecimalPlaces(expected);
        String actual = BasePage.getText(sleepInRatesPage.normalRateFinalRateVat).trim();
        assertThat("Normal Rate Final Rate With VAT Amount is incorrect", actual, is(expectedString));
    }

    private double calculateExpectedChHourlyVat() {
        return Double.parseDouble(chHourlyMargin) * 0.2;
    }

    private void verifyNormalRateChHourlyVat() {
        double expected = calculateExpectedChHourlyVat();
        String expectedString = roundToTwoDecimalPlaces(expected);
        String actual = getNormalRateChHourlyVatAmount();
        assertThat("Normal Rate CH Hourly VAT Amount is incorrect", actual, is(expectedString));
    }

    private void verifyNormalRateAgencyCostWithNoVatAmount() {
        double expected = calculateSumOfHourlyRateAndAgencyMargin();
        String expectedString = roundToTwoDecimalPlaces(expected);
        String actual = BasePage.getText(sleepInRatesPage.normalRateAgencyCostNoVat).trim();
        assertThat("Normal Rate Agency Cost With No VAT Amount is incorrect", actual, is(expectedString));
    }

    private void verifyNormalRateAgencyCostWithVatAmount() {
        double expected = calculateSumOfHourlyRateAndAgencyMargin() + calculateExpectedAgencyVat();
        String expectedString = roundToTwoDecimalPlaces(expected);
        String actual = BasePage.getText(sleepInRatesPage.normalRateAgencyCostVat).trim();
        assertThat("Normal Rate Agency Cost With VAT Amount is incorrect", actual, is(expectedString));
    }

    private double calculateSumOfHourlyRateAndAgencyMargin() {
        double hourlyRateD = Double.parseDouble(hourlyRate);
        double agencyMarginD = Double.parseDouble(agencyMargin);

        return hourlyRateD + agencyMarginD;
    }

    private double calculateExpectedAgencyVat() {
        return calculateSumOfHourlyRateAndAgencyMargin() * 0.2;
    }

    private String roundToTwoDecimalPlaces(Double d) {
        BigDecimal bd = BigDecimal.valueOf(d).setScale(2, RoundingMode.HALF_UP);
        double value = bd.doubleValue();
        return String.format("%.2f", value);
    }

    private String getNormalRateChHourlyVatAmount() {
        return BasePage.getText(sleepInRatesPage.normalRateChHourlyVat).trim();
    }


    private String getNormalRateAgencyVatAmount() {
        return BasePage.getText(sleepInRatesPage.normalRateAgencyVat).trim();
    }

    private void verifyNormalRateAgencyVat() {
        double expected = calculateExpectedAgencyVat();
        String expectedString = roundToTwoDecimalPlaces(expected);
        String actual = getNormalRateAgencyVatAmount();
        assertThat("Normal Rate Agency VAT Amount is incorrect", actual, is(expectedString));
    }
}
