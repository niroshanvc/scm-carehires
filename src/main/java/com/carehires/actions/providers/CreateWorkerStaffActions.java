package com.carehires.actions.providers;

import com.carehires.pages.providers.CreateWorkerStaffPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import java.text.DecimalFormat;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CreateWorkerStaffActions {

    CreateWorkerStaffPage createWorkerStaffPage;

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
        String site = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER_SITE_MANAGEMENT_HEADER, "SiteName");
        BasePage.waitUntilElementClickable(createWorkerStaffPage.siteDropdown, 20);
        BasePage.clickWithJavaScript(createWorkerStaffPage.siteDropdown);
        BasePage.genericWait(1000);
        BasePage.clickWithJavaScript(getDropdownXpath(site));

        //select worker type
        BasePage.clickWithJavaScript(createWorkerStaffPage.workerTypeDropdown);
        String workerType = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "WorkerType");
        BasePage.waitUntilElementClickable(getDropdownXpath(workerType), 20);
        BasePage.clickWithJavaScript(getDropdownXpath(workerType));

        //select skill(s)
        String[] skills = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "Skills").split(",");
        BasePage.waitUntilElementClickable(createWorkerStaffPage.skills, 20);
        BasePage.clickWithJavaScript(createWorkerStaffPage.skills);
        BasePage.genericWait(1500);
        for (String skill : skills) {
            BasePage.clickWithJavaScript(getDropdownXpath(skill));
        }

        //enter hourly rate
        String hourlyRate = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "HourlyRate");
        BasePage.clickWithJavaScript(createWorkerStaffPage.hourlyRate);
        BasePage.typeWithStringBuilder(createWorkerStaffPage.hourlyRate, hourlyRate);

        //enter monthly agency hours
        String monthlyAgencyHours = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "MonthlyAgencyHours");
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
        String proofOfDemandDocument = DataConfigurationReader.readDataFromYmlFile(YML_FILE,  YML_HEADER, "ProofOfDemandDocument");
        String absoluteFilePathVatRegDoc = System.getProperty("user.dir") + "\\src\\test\\resources\\Upload\\Provider\\" + proofOfDemandDocument;
        BasePage.uploadFile(createWorkerStaffPage.proofOfDemandDocument, absoluteFilePathVatRegDoc);
        BasePage.clickWithJavaScript(createWorkerStaffPage.addButton);
        isWorkerStaffAdded();
    }

    //verify worker is added
    private void isWorkerStaffAdded() {
        String expectedWorkerType = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "WorkerType");
        BasePage.genericWait(10000);
        BasePage.waitUntilElementPresent(createWorkerStaffPage.addedWorkerType, 90);
        String fullText = BasePage.getText(createWorkerStaffPage.addedWorkerType).trim();
        String actualWorkerType = fullText.split("\n")[0];

        assertThat("Worker staff is not added", actualWorkerType, is(expectedWorkerType));
    }

    public void verifyMonthlySpendDisplayInTableGrid() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying Monthly Agency Spend Value displaying in the table grid >>>>>>>>>>>>>>>>>>>>");
        String expectedValue = getExpectedMonthlySpendValue();
        String actual = BasePage.getText(createWorkerStaffPage.monthlySpendInTableGrid).trim();
        assertThat("Monthly agency spend is not correctly displaying.", actual, is(expectedValue));
    }

    private String getExpectedMonthlySpendValue() {
        String hourlyRate = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "HourlyRate");
        String monthlyAgencyHours = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "MonthlyAgencyHours");
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
}