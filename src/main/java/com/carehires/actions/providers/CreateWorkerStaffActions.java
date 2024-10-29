package com.carehires.actions.providers;

import com.carehires.pages.providers.CreateWorkerStaffPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.text.DecimalFormat;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CreateWorkerStaffActions {

    CreateWorkerStaffPage createWorkerStaffPage;

    private static final String ENTITY = "provider";
    private static final String YML_FILE = "provider-create";
    private static final String YML_HEADER = "WorkerStaffManagement";
    private static final String YML_HEADER_SITE_MANAGEMENT_HEADER = "SiteManagement";
    private static final Logger logger = LogManager.getFormatterLogger(CreateWorkerStaffActions.class);

    public CreateWorkerStaffActions() {
        createWorkerStaffPage = new CreateWorkerStaffPage();
        PageFactory.initElements(BasePage.getDriver(), createWorkerStaffPage);
    }

    public void enterWorkerStaffData() {
        BasePage.waitUntilPageCompletelyLoaded();
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering staff information >>>>>>>>>>>>>>>>>>>>");
        BasePage.clickWithJavaScript(createWorkerStaffPage.addNewButton);

        //select site
        String site = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER_SITE_MANAGEMENT_HEADER, "SiteName");
        BasePage.waitUntilElementClickable(createWorkerStaffPage.siteDropdown, 20);
        BasePage.clickWithJavaScript(createWorkerStaffPage.siteDropdown);
        BasePage.genericWait(1000);
        BasePage.clickWithJavaScript(getDropdownXpath(site));

        //select worker type
        BasePage.clickWithJavaScript(createWorkerStaffPage.workerTypeDropdown);
        String workerType = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "WorkerType");
        BasePage.waitUntilElementClickable(getDropdownXpath(workerType), 20);
        BasePage.clickWithJavaScript(getDropdownXpath(workerType));

        //select skill(s)
        String[] skills = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Skills").split(",");
        BasePage.waitUntilElementClickable(createWorkerStaffPage.skills, 20);
        BasePage.clickWithJavaScript(createWorkerStaffPage.skills);
        BasePage.genericWait(1500);
        for (String skill : skills) {
            BasePage.clickWithJavaScript(getDropdownXpath(skill));
        }

        //enter hourly rate
        String hourlyRate = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "HourlyRate");
        BasePage.clickWithJavaScript(createWorkerStaffPage.hourlyRate);
        BasePage.typeWithStringBuilder(createWorkerStaffPage.hourlyRate, hourlyRate);

        //enter monthly agency hours
        String monthlyAgencyHours = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "MonthlyAgencyHours");
        BasePage.typeWithStringBuilder(createWorkerStaffPage.monthlyAgencyHours, monthlyAgencyHours);
    }

    private String getDropdownXpath(String text) {
        return String.format("//nb-option[contains(text(),'%s')]", text);
    }

    public void verifyMonthlyAgencySpendValue() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying Monthly Agency Spend Value >>>>>>>>>>>>>>>>>>>>");
        String expectMonthlyAgencySpendValue = getExpectedMonthlySpendValue();
        String actual = BasePage.getAttributeValue(createWorkerStaffPage.monthlyAgencySpend, "value").trim();
        assertThat("Monthly agency spend is not correctly calculate", actual, is(expectMonthlyAgencySpendValue));
    }

    public void addWorkerStaff() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Worker Staff Information >>>>>>>>>>>>>>>>>>>>");
        String proofOfDemandDocument = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE,  YML_HEADER, "ProofOfDemandDocument");
        String absoluteFilePathVatRegDoc = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                + File.separator + "resources" + File.separator + "Upload" + File.separator + "Provider" + File.separator + proofOfDemandDocument;
        BasePage.uploadFile(createWorkerStaffPage.proofOfDemandDocument, absoluteFilePathVatRegDoc);
        BasePage.clickWithJavaScript(createWorkerStaffPage.addButton);
        verifySuccessMessage();
    }

    public void verifyMonthlySpendDisplayInTableGrid() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying Monthly Agency Spend Value displaying in the table grid >>>>>>>>>>>>>>>>>>>>");
        String expectedValue = getExpectedMonthlySpendValue();
        String actual = BasePage.getText(createWorkerStaffPage.monthlySpendInTableGrid).trim();
        assertThat("Monthly agency spend is not correctly displaying.", actual, is(expectedValue));
    }

    private String getExpectedMonthlySpendValue() {
        String hourlyRate = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "HourlyRate");
        String monthlyAgencyHours = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "MonthlyAgencyHours");
        double hourlyRateInt = Double.parseDouble(hourlyRate);
        double monthlyAgencyHoursInt = Double.parseDouble(monthlyAgencyHours);
        double monthlyAgencySpendValue = hourlyRateInt * monthlyAgencyHoursInt;
        BasePage.clickTabKey(createWorkerStaffPage.updateButton);
        BasePage.genericWait(500);
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(monthlyAgencySpendValue);
    }

    public void moveToUserManagementPage() {
        BasePage.clickWithJavaScript(createWorkerStaffPage.updateButton);
        BasePage.waitUntilElementPresent(createWorkerStaffPage.nextButton, 60);
        BasePage.clickWithJavaScript(createWorkerStaffPage.nextButton);
    }

    private void verifySuccessMessage() {
        BasePage.waitUntilElementPresent(createWorkerStaffPage.successMessage, 30);
        String actualInLowerCase = BasePage.getText(createWorkerStaffPage.successMessage).toLowerCase().trim();
        String expected = "Record created successfully";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Worker staff information success message is wrong!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(createWorkerStaffPage.successMessage, 20);
    }
}