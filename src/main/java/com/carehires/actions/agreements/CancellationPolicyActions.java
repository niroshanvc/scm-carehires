package com.carehires.actions.agreements;

import com.carehires.pages.agreements.CancellationPolicyPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CancellationPolicyActions {

    CancellationPolicyPage cancellationPolicyPage;
    private static final String ENTITY = "agreement";
    private static final String YML_FILE = "agreement-create";
    private static final String YML_HEADER = "Cancellation Policy";
    private String beforeJobStart;
    private String cancellationFeePercentage;
    private String careHiresSplit;

    private static final Logger logger = LogManager.getLogger(CancellationPolicyActions.class);

    public CancellationPolicyActions() {
        cancellationPolicyPage = new CancellationPolicyPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), cancellationPolicyPage);
        } catch (Exception e) {
            logger.error("Error while initializing Cancellation Policy Page elements: {}", e.getMessage());
        }
    }

    public void addCancellationPolicyAndVerifyCalculations() {
        BasePage.waitUntilPageCompletelyLoaded();
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Cancellation Policy Info and Verifying Calculations >>>>>>>>>>>>>>>>>>>>");

        beforeJobStart = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Before Job Start");
        BasePage.clickWithJavaScript(cancellationPolicyPage.beforeJobStartDropdown);
        By by = By.xpath(cancellationPolicyPage.getDropdownOptionXpath(beforeJobStart));
        BasePage.waitUntilVisibilityOfElementLocated(by, 20);
        BasePage.clickWithJavaScript(cancellationPolicyPage.getDropdownOptionXpath(beforeJobStart));

        cancellationFeePercentage = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Cancellation fee percentage");
        BasePage.clearAndEnterTexts(cancellationPolicyPage.cancellationFeePercentage, cancellationFeePercentage);

        careHiresSplit = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "CareHires split");
        BasePage.clearAndEnterTexts(cancellationPolicyPage.careHiresSplit, careHiresSplit);
        BasePage.clickTabKey(cancellationPolicyPage.careHiresSplit);
        BasePage.clickTabKey(cancellationPolicyPage.agencySplit);
        verifyAutoCalculatedAgencySplitValue();
        BasePage.clickWithJavaScript(cancellationPolicyPage.addButton);
        verifyCancellationPolicyAddedSuccessfully();

        BasePage.scrollToWebElement(cancellationPolicyPage.continueButton);
        verifyDataSavedSuccessfully();

        BasePage.clickWithJavaScript(cancellationPolicyPage.continueButton);
    }

    private void verifyDataSavedSuccessfully() {
        verifyBeforeJobStartedValue();
        verifyCancellationFeePercentageValue();
        verifyCareHiresSplitValue();
    }

    private void verifyCareHiresSplitValue() {
        String actualWithPercentage = BasePage.getText(cancellationPolicyPage.careHiresSplitValue).trim();
        String actual = actualWithPercentage.replaceAll("\\D", "");
        String expected = careHiresSplit;
        assertThat("Care Hires split value is not correctly saved!", actual, is(expected));
    }

    private void verifyCancellationFeePercentageValue() {
        String actualWithPercentage = BasePage.getText(cancellationPolicyPage.cancellationFeePercentageValue).trim();
        String actual = actualWithPercentage.replaceAll("\\D", "");
        String expected = cancellationFeePercentage;
        assertThat("Cancellation Fee Percentage value is not correctly saved!", actual, is(expected));
    }

    private void verifyBeforeJobStartedValue() {
        String actual = BasePage.getText(cancellationPolicyPage.beforeJobStartValue).trim();
        String expectedWithSpace = beforeJobStart;
        String[] expectedArr = expectedWithSpace.split(" ");
        String expected = expectedArr[0] + expectedArr[1];
        assertThat("Before job start value is not correctly saved!", actual, startsWith(expected));
    }

    private void verifyCancellationPolicyAddedSuccessfully() {
        BasePage.waitUntilElementPresent(cancellationPolicyPage.successMessage, 30);
        String actualInLowerCase = BasePage.getText(cancellationPolicyPage.successMessage).toLowerCase().trim();
        String expected = "Cancellation policy created successfully";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Cancellation policy is not created!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(cancellationPolicyPage.successMessage, 20);
    }

    private void verifyAutoCalculatedAgencySplitValue() {
        BasePage.genericWait(1000);
        String actualString = BasePage.getAttributeValue(cancellationPolicyPage.agencySplit, "value");
        double actual = Double.parseDouble(actualString);
        double expected = 100 - Double.parseDouble(careHiresSplit);
        assertThat("Agency split is not correctly calculated!", actual, is(expected));
    }

    public void addCancellationPolicy() {
        BasePage.waitUntilPageCompletelyLoaded();
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Cancellation Policy Info >>>>>>>>>>>>>>>>>>>>");

        beforeJobStart = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Before Job Start");
        BasePage.clickWithJavaScript(cancellationPolicyPage.beforeJobStartDropdown);
        By by = By.xpath(cancellationPolicyPage.getDropdownOptionXpath(beforeJobStart));
        BasePage.waitUntilVisibilityOfElementLocated(by, 20);
        BasePage.clickWithJavaScript(cancellationPolicyPage.getDropdownOptionXpath(beforeJobStart));

        cancellationFeePercentage = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Cancellation fee percentage");
        BasePage.clearAndEnterTexts(cancellationPolicyPage.cancellationFeePercentage, cancellationFeePercentage);

        careHiresSplit = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "CareHires split");
        BasePage.clearAndEnterTexts(cancellationPolicyPage.careHiresSplit, careHiresSplit);
        BasePage.clickTabKey(cancellationPolicyPage.careHiresSplit);
        BasePage.clickTabKey(cancellationPolicyPage.agencySplit);
        BasePage.clickWithJavaScript(cancellationPolicyPage.addButton);
        verifyCancellationPolicyAddedSuccessfully();
        BasePage.scrollToWebElement(cancellationPolicyPage.continueButton);
        BasePage.clickWithJavaScript(cancellationPolicyPage.continueButton);
    }
}
