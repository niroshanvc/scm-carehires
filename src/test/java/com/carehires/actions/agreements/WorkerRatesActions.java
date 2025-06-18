package com.carehires.actions.agreements;


import com.carehires.pages.agreements.WorkerRatesPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;

public class WorkerRatesActions {

    WorkerRatesPage workerRatesPage;

    private static final String ENTITY = "agreement";
    private static final String YML_FILE = "agreement-create";
    private static final String YML_FILE_SMOKE = "agreement-create-smoke";
    private static final String SKILLS = "With Skills";
    private static final String YML_HEADER = "Worker Rates";
    private static final String NORMAL_RATE = "Normal Rate";
    private static final String SPECIAL_HOLIDAY_RATE = "Special Holiday Rate";
    private static final String BANK_HOLIDAY_RATE = "Bank Holiday Rate";
    private static final String FRIDAY_NIGHT_RATE = "Friday Night Rate";
    private static final String FINAL_RATE_WITH_VAT = "Final Rate with Vat";
    private String hourlyRate;
    private String agencyMargin;
    private String chHourlyMargin;
    private String workerType;

    private static final Logger logger = LogManager.getLogger(WorkerRatesActions.class);

    public WorkerRatesActions() {
        workerRatesPage = new WorkerRatesPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), workerRatesPage);
        } catch (Exception e) {
            logger.error("Error while initializing Worker Rates Page elements: {}", e.getMessage());
        }
    }

    public void enterWorkerRatesVerifyCalculations() {
        logger.info("<<<<<<<<<<<<<<<<<<< Entering Worker Rates Info and Verifying Calculations >>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();

        enterWorkerType();
        enterSkills();

        expandSubSection(workerRatesPage.rateBreakdownTableHeader, workerRatesPage.rateBreakdownTableHeaderExpandIcon);

        hourlyRate = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, SKILLS, YML_HEADER,
                NORMAL_RATE, "Hourly Rate");
        BasePage.waitUntilElementDisplayed(workerRatesPage.hourlyRateInput, 20);
        BasePage.clearAndEnterTexts(workerRatesPage.hourlyRateInput, hourlyRate);

        agencyMargin = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, SKILLS, YML_HEADER,
                NORMAL_RATE, "Agency Margin");
        BasePage.clearAndEnterTexts(workerRatesPage.agencyMarginInput, agencyMargin);

        chHourlyMargin = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, SKILLS, YML_HEADER,
                NORMAL_RATE, "CH Hourly Margin");
        BasePage.clearAndEnterTexts(workerRatesPage.chHourlyMarginInput, chHourlyMargin);
        BasePage.clickTabKey(workerRatesPage.chHourlyMarginInput);

        verifyNormalRateAgencyVat();
        verifyNormalRateAgencyCostWithVatAmount();
        verifyNormalRateAgencyCostWithNoVatAmount();
        verifyNormalRateChHourlyVat();
        verifyNormalRateFinalRateWithVatAmount();
        verifyNormalRateFinalRateWithNoVatAmount();

        BasePage.scrollToWebElement(workerRatesPage.addButton);
        BasePage.clickWithJavaScript(workerRatesPage.addButton);
        verifyWorkerRateIsAddedSuccessfully();
        BasePage.scrollToWebElement(workerRatesPage.continueButton);
        verifyDataLoadedInCurrentWorkerRatesList();
        BasePage.clickWithJavaScript(workerRatesPage.continueButton);
    }

    private void enterSkills() {
        String[] skills = Objects.requireNonNull(DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE,
                SKILLS, YML_HEADER, "Skills")).split(",");
        BasePage.genericWait(3000);
        BasePage.clickWithJavaScript(workerRatesPage.skillsDropdown);
        By locator = By.xpath(workerRatesPage.getDropdownOptionXpath(skills[0]));
        BasePage.waitUntilPresenceOfElementLocated(locator, 20);
        for (String skill : skills) {
            BasePage.clickWithJavaScript(workerRatesPage.getDropdownOptionXpath(skill));
        }
    }

    private void enterWorkerType() {
        workerType = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, SKILLS, YML_HEADER,
                "Worker Type");
        BasePage.waitUntilElementDisplayed(workerRatesPage.workerTypeDropdown, 30);
        BasePage.clickWithJavaScript(workerRatesPage.workerTypeDropdown);
        By by = By.xpath(workerRatesPage.getDropdownOptionXpath(workerType));
        BasePage.waitUntilVisibilityOfElementLocated(by, 20);
        BasePage.scrollToWebElement(workerRatesPage.getDropdownOptionXpath(workerType));
        BasePage.clickWithJavaScript(workerRatesPage.getDropdownOptionXpath(workerType));
    }

    private void expandSubSection(WebElement headerText, WebElement headerIcon) {
        if(BasePage.getAttributeValue(headerText, "class").equalsIgnoreCase("collapsed")) {
            BasePage.clickWithJavaScript(headerIcon);
        }
    }

    private void verifyNormalRateAgencyCostWithNoVatAmount() {
        double expected = calculateSumOfHourlyRateAndAgencyMargin();
        String expectedString = roundToTwoDecimalPlaces(expected);
        String actual = BasePage.getText(workerRatesPage.normalRateAgencyCostNoVatAmount).trim();
        assertThat("Normal Rate Agency Cost With No VAT Amount is incorrect", actual, is(expectedString));
    }

    private void verifyNormalRateAgencyCostWithVatAmount() {
        double expected = calculateSumOfHourlyRateAndAgencyMargin() + calculateExpectedAgencyVat();
        String expectedString = roundToTwoDecimalPlaces(expected);
        String actual = BasePage.getText(workerRatesPage.normalRateAgencyCostVatAmount).trim();
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

    private void verifyNormalRateAgencyVat() {
        double expected = calculateExpectedAgencyVat();
        String expectedString = roundToTwoDecimalPlaces(expected);
        String actual = getNormalRateAgencyVatAmount();
        assertThat("Normal Rate Agency VAT Amount is incorrect", actual, is(expectedString));
    }

    private String getNormalRateAgencyVatAmount() {
        return BasePage.getText(workerRatesPage.normalRateAgencyVatAmount).trim();
    }

    private String getNormalRateChHourlyVatAmount() {
        return BasePage.getText(workerRatesPage.normalRateChHourlyVatAmount).trim();
    }

    private void verifyNormalRateChHourlyVat() {
        double expected = calculateExpectedChHourlyVat();
        String expectedString = roundToTwoDecimalPlaces(expected);
        String actual = getNormalRateChHourlyVatAmount();
        assertThat("Normal Rate CH Hourly VAT Amount is incorrect", actual, is(expectedString));
    }

    private double calculateExpectedChHourlyVat() {
        return Double.parseDouble(chHourlyMargin) * 0.2;
    }

    private void verifyNormalRateFinalRateWithVatAmount() {
        double expected = calculateSumOfHourlyRateAndAgencyMargin() + Double.parseDouble(
                getNormalRateAgencyVatAmount()) + Double.parseDouble(chHourlyMargin) + calculateExpectedChHourlyVat();
        String expectedString = roundToTwoDecimalPlaces(expected);
        String actual = BasePage.getText(workerRatesPage.normalRateFinalRateVatAmount).trim();
        assertThat("Normal Rate Final Rate With VAT Amount is incorrect", actual, is(expectedString));
    }

    private void verifyNormalRateFinalRateWithNoVatAmount() {
        double expected = calculateSumOfHourlyRateAndAgencyMargin() + Double.parseDouble(chHourlyMargin)
                + calculateExpectedChHourlyVat();
        String expectedString = roundToTwoDecimalPlaces(expected);
        String actual = BasePage.getText(workerRatesPage.normalRateFinalRateNoVatAmount).trim();
        assertThat("Normal Rate Final Rate Without VAT Amount is incorrect", actual, is(expectedString));
    }

    private String roundToTwoDecimalPlaces(Double d) {
        BigDecimal bd = BigDecimal.valueOf(d).setScale(2, RoundingMode.HALF_UP);
        double value = bd.doubleValue();
        return String.format("%.2f", value);
    }

    private void verifyWorkerRateIsAddedSuccessfully() {
        BasePage.waitUntilElementPresent(workerRatesPage.successMessage, 30);
        String actualInLowerCase = BasePage.getText(workerRatesPage.successMessage).toLowerCase().trim();
        String expected = "Worker rate created successfully";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Worker rate is not created!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(workerRatesPage.successMessage, 20);
    }

    private void verifyDataLoadedInCurrentWorkerRatesList() {
        BasePage.genericWait(1500);
        verifySavedWorkerType();
        verifySavedSkills();
        verifySavedWorkerRate();
        verifyAgencyChargeWithVat();
        verifyAgencyChargeWithoutVat();
        verifyCareHiresCharge();
        verifyFinalHourlyRateWithVat();
        verifyFinalHourlyRateWithoutVat();
    }

    private void verifyFinalHourlyRateWithoutVat() {
        String actualWithCurrency = BasePage.getText(workerRatesPage.finalHourlyRateWithNonVat).trim();
        String[] str = actualWithCurrency.split(" ");
        String actual = str[1];
        double expectedWithMoreDecimalPlaces = calculateSumOfHourlyRateAndAgencyMargin() +
                ((Double.parseDouble(chHourlyMargin)) * ( 1 + 0.2));
        String expected = roundToTwoDecimalPlaces(expectedWithMoreDecimalPlaces);
        assertThat("Final Hourly Rate with Non VAT is not correctly displayed!", actual, is(expected));
    }

    private void verifyFinalHourlyRateWithVat() {
        String actualWithCurrency = BasePage.getText(workerRatesPage.finalHourlyRateWithVat).trim();
        String[] str = actualWithCurrency.split(" ");
        String actual = str[1];
        double expectedWithMoreDecimalPlaces = calculateSumOfHourlyRateAndAgencyMargin() + calculateExpectedAgencyVat()
                + ((Double.parseDouble(chHourlyMargin)) * ( 1 + 0.2));
        String expected = roundToTwoDecimalPlaces(expectedWithMoreDecimalPlaces);
        assertThat("Final Hourly Rate with VAT is not correctly displayed!", actual, is(expected));
    }

    private void verifyCareHiresCharge() {
        String actualWithCurrency = BasePage.getText(workerRatesPage.careHiresCharge).trim();
        String[] str = actualWithCurrency.split(" ");
        String actual = str[1];
        double expected = ((Double.parseDouble(chHourlyMargin)) * ( 1 + 0.2));
        String expectedString = roundToTwoDecimalPlaces(expected);
        assertThat("CareHires Charge is not correctly displayed!", actual, is(expectedString));
    }

    private void verifyAgencyChargeWithoutVat() {
        String actualWithCurrency = BasePage.getText(workerRatesPage.agencyChargeWithNonVat).trim();
        String[] str = actualWithCurrency.split(" ");
        String actual = str[1];
        double expectedWithMoreDecimalPlaces = calculateSumOfHourlyRateAndAgencyMargin();
        String expected = roundToTwoDecimalPlaces(expectedWithMoreDecimalPlaces);
        assertThat("Agency charge with Non VAT is not correctly displayed!", actual, is(expected));
    }

    private void verifyAgencyChargeWithVat() {
        String actualWithCurrency = BasePage.getText(workerRatesPage.agencyChargeWithVat).trim();
        String[] str = actualWithCurrency.split(" ");
        String actual = str[1];
        double expectedWithMoreDecimalPlaces = calculateSumOfHourlyRateAndAgencyMargin() + calculateExpectedAgencyVat();
        String expected = roundToTwoDecimalPlaces(expectedWithMoreDecimalPlaces);
        assertThat("Agency charge with VAT is not correctly displayed!", actual, is(expected));
    }

    private void verifySavedWorkerRate() {
        String actualWithCurrency = BasePage.getText(workerRatesPage.hourlyRate).trim();
        String[] str = actualWithCurrency.split(" ");
        String actual = str[1].replaceAll("[^\\d.]", "");
        String expected = hourlyRate;
        assertThat("Worker rate is not correctly displayed!", actual, is(expected));
    }

    private void verifySavedSkills() {
        List<String> expected = Arrays.asList(Objects.requireNonNull(DataConfigurationReader.readDataFromYmlFile(
                ENTITY, YML_FILE, SKILLS, YML_HEADER, "Skills")).split(","));
        List<String> actual = workerRatesPage.skills.stream().map(WebElement::getText).map(String::trim).
                collect(Collectors.toList());

        // Using Hamcrest to verify both lists contain the same elements
        assertThat("Saved Skills are not correctly displayed!", actual, containsInAnyOrder(expected.toArray()));
    }

    private void verifySavedWorkerType() {
        BasePage.waitUntilElementPresent(workerRatesPage.workerType, 30);
        String actual = BasePage.getText(workerRatesPage.workerType).trim().toLowerCase();
        String expected = workerType.toLowerCase();
        assertThat("Worker type is not correctly displayed!", actual, is(expected));
    }

    private void enableRate(String rateType) {
        BasePage.clickWithJavaScript(workerRatesPage.getRateTypeCheckbox(rateType));
    }

    private void fillFinalRateVat(String rateType, String value) {
        BasePage.waitUntilElementClickable(workerRatesPage.getFinalRateWithVatInput(rateType), 30);
        BasePage.sendKeys(workerRatesPage.finalRateVat(rateType), value);
    }

    public void enterWorkerRates() {
        BasePage.waitUntilPageCompletelyLoaded();
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Worker Rates Info >>>>>>>>>>>>>>>>>>>>");

        enterWorkerType();
        enterSkills();

        expandSubSection(workerRatesPage.rateBreakdownTableHeader, workerRatesPage.rateBreakdownTableHeaderExpandIcon);

        hourlyRate = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_SMOKE, SKILLS, YML_HEADER,
                NORMAL_RATE, "Hourly Rate");
        BasePage.waitUntilElementDisplayed(workerRatesPage.hourlyRateInput, 20);
        BasePage.clearAndEnterTexts(workerRatesPage.hourlyRateInput, hourlyRate);

        agencyMargin = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_SMOKE, SKILLS, YML_HEADER,
                NORMAL_RATE, "Agency Margin");
        BasePage.clearAndEnterTexts(workerRatesPage.agencyMarginInput, agencyMargin);

        chHourlyMargin = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_SMOKE, SKILLS, YML_HEADER,
                NORMAL_RATE, "CH Hourly Margin");
        BasePage.clearAndEnterTexts(workerRatesPage.chHourlyMarginInput, chHourlyMargin);
        BasePage.clickTabKey(workerRatesPage.chHourlyMarginInput);

        String specialHolidayVat = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_SMOKE, SKILLS, YML_HEADER,
                SPECIAL_HOLIDAY_RATE, FINAL_RATE_WITH_VAT);
        enableRate(SPECIAL_HOLIDAY_RATE);
        fillFinalRateVat(SPECIAL_HOLIDAY_RATE, specialHolidayVat);

        String bankHolidayVat = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_SMOKE, SKILLS, YML_HEADER,
                BANK_HOLIDAY_RATE, FINAL_RATE_WITH_VAT);
        enableRate(BANK_HOLIDAY_RATE);
        fillFinalRateVat(BANK_HOLIDAY_RATE, bankHolidayVat);

        String fridayNightVat = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE_SMOKE, SKILLS, YML_HEADER,
                FRIDAY_NIGHT_RATE, FINAL_RATE_WITH_VAT);
        enableRate(FRIDAY_NIGHT_RATE);
        fillFinalRateVat(FRIDAY_NIGHT_RATE, fridayNightVat);

        BasePage.scrollToWebElement(workerRatesPage.addButton);
        BasePage.clickWithJavaScript(workerRatesPage.addButton);
        verifyWorkerRateIsAddedSuccessfully();
        BasePage.scrollToWebElement(workerRatesPage.continueButton);
        verifyDataLoadedInCurrentWorkerRatesList();
        BasePage.clickWithJavaScript(workerRatesPage.continueButton);
    }

    public void withoutSkills() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Worker Rates without Skills >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();

    }
}
