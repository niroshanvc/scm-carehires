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
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class WorkerRatesActions {

    WorkerRatesPage workerRatesPage;

    private static final String ENTITY = "agreement";
    private static final String YML_FILE = "agreement-create";
    private static final String YML_HEADER = "Worker Rates";
    private static final String ADD = "Add";
    private static final String EDIT_YML_FILE = "agreement-edit";
    String hourlyRate;
    String agencyMargin;
    String chHourlyMargin;

    private static final Logger logger = LogManager.getLogger(WorkerRatesActions.class);

    public WorkerRatesActions() {
        workerRatesPage = new WorkerRatesPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), workerRatesPage);
        } catch (Exception e) {
            logger.error("Error while initializing Worker Rates Page elements: {}", e.getMessage());
        }
    }

    public void enterWorkerRates() {
        BasePage.waitUntilPageCompletelyLoaded();
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Worker Rates Info >>>>>>>>>>>>>>>>>>>>");

        String workerType = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Worker Type");
        BasePage.clickWithJavaScript(workerRatesPage.workerTypeDropdown);
        By by = By.xpath(getDropdownOptionXpath(workerType));
        BasePage.waitUntilVisibilityOfElementLocated(by, 20);
        BasePage.scrollToWebElement(getDropdownOptionXpath(workerType));
        BasePage.clickWithJavaScript(getDropdownOptionXpath(workerType));

        String[] skills = Objects.requireNonNull(DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Skills")).split(",");
        BasePage.clickWithJavaScript(workerRatesPage.skillsDropdown);
        By locator = By.xpath(getDropdownOptionXpath(skills[0]));
        BasePage.waitUntilPresenceOfElementLocated(locator, 20);
        for (String skill : skills) {
            BasePage.clickWithJavaScript(getDropdownOptionXpath(skill));
        }
        expandSubSection(workerRatesPage.rateBreakdownTableHeader, workerRatesPage.rateBreakdownTableHeaderExpandIcon);

        hourlyRate = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Hourly Rate");
        BasePage.waitUntilElementDisplayed(workerRatesPage.hourlyRateInput, 20);
        BasePage.clearAndEnterTexts(workerRatesPage.hourlyRateInput, hourlyRate);

        agencyMargin = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Agency Margin");
        BasePage.clearAndEnterTexts(workerRatesPage.agencyMarginInput, agencyMargin);

        chHourlyMargin = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "CH Hourly Margin");
        BasePage.clearAndEnterTexts(workerRatesPage.chHourlyMarginInput, chHourlyMargin);
        BasePage.clickTabKey(workerRatesPage.chHourlyMarginInput);

        verifyNormalRateAgencyVat();
        verifyNormalRateAgencyCostWithVatAmount();
        verifyNormalRateAgencyCostWithNoVatAmount();
        verifyNormalRateChHourlyVat();
        verifyNormalRateFinalRateWithVatAmount();
        verifyNormalRateFinalRateWithNoVatAmount();
        BasePage.clickWithJavaScript(workerRatesPage.addButton);
    }

    private String getDropdownOptionXpath(String option) {
        return String.format("//nb-option[contains(text(),'%s')]", option);
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
}
