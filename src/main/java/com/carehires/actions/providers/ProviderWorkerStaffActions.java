package com.carehires.actions.providers;

import com.carehires.common.GlobalVariables;
import com.carehires.pages.providers.WorkerStaffPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.text.DecimalFormat;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ProviderWorkerStaffActions {

    WorkerStaffPage workerStaffPage;
    private static final ProviderNavigationMenuActions navigationMenu = new ProviderNavigationMenuActions();

    private static final String ENTITY = "provider";
    private static final String YML_FILE = "provider-create";
    private static final String EDIT_YML_FILE = "provider-edit";
    private static final String YML_HEADER = "Worker Staff Management";
    private static final String YML_HEADER_SITE_MANAGEMENT_HEADER = "Site Management";
    private static final String ADD = "Add";
    private static final String UPDATE = "Update";
    private static final Logger logger = LogManager.getFormatterLogger(ProviderWorkerStaffActions.class);
    Integer incrementValue;

    public ProviderWorkerStaffActions() {
        workerStaffPage = new WorkerStaffPage();
        PageFactory.initElements(BasePage.getDriver(), workerStaffPage);
    }

    public void addingWorkerStaffData() {
        // Retrieve the incremented value
        incrementValue = GlobalVariables.getVariable("provider_incrementValue", Integer.class);

        BasePage.waitUntilPageCompletelyLoaded();
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering staff information >>>>>>>>>>>>>>>>>>>>");
        BasePage.clickWithJavaScript(workerStaffPage.addNewButton);

        enterWorkerStaffManagementData(YML_FILE, ADD);
    }

    private String getDropdownXpath(String text) {
        return String.format("//nb-option[contains(text(),'%s')]", text);
    }

    public void verifyMonthlyAgencySpendValue() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying Monthly Agency Spend Value >>>>>>>>>>>>>>>>>>>>");
        String expectMonthlyAgencySpendValue = getExpectedMonthlySpendValue();
        String actual = BasePage.getAttributeValue(workerStaffPage.monthlyAgencySpend, "value").trim();
        assertThat("Monthly agency spend is not correctly calculate", actual, is(expectMonthlyAgencySpendValue));
    }

    public void clickingOnAddButton() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Worker Staff Information >>>>>>>>>>>>>>>>>>>>");
        uploadProofOfDemandDocument();
        BasePage.clickWithJavaScript(workerStaffPage.addButton);
        verifySuccessMessage();
    }

    private void uploadProofOfDemandDocument() {
        String proofOfDemandDocument = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE,  YML_HEADER, "ProofOfDemandDocument");
        String absoluteFilePathVatRegDoc = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                + File.separator + "resources" + File.separator + "Upload" + File.separator + "Provider" + File.separator + proofOfDemandDocument;
        BasePage.uploadFile(workerStaffPage.proofOfDemandDocument, absoluteFilePathVatRegDoc);
    }

    public void verifyMonthlySpendDisplayInTableGrid() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying Monthly Agency Spend Value displaying in the table grid >>>>>>>>>>>>>>>>>>>>");
        String expectedValue = getExpectedMonthlySpendValue();
        String actual = BasePage.getText(workerStaffPage.monthlySpendInTableGrid).trim();
        assertThat("Monthly agency spend is not correctly displaying.", actual, is(expectedValue));
    }

    private String getExpectedMonthlySpendValue() {
        String hourlyRate = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "HourlyRate");
        String monthlyAgencyHours = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "MonthlyAgencyHours");
        double hourlyRateInt = Double.parseDouble(hourlyRate);
        double monthlyAgencyHoursInt = Double.parseDouble(monthlyAgencyHours);
        double monthlyAgencySpendValue = hourlyRateInt * monthlyAgencyHoursInt;
        BasePage.clickTabKey(workerStaffPage.updateButton);
        BasePage.genericWait(500);
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(monthlyAgencySpendValue);
    }

    public void moveToUserManagementPage() {
        BasePage.clickWithJavaScript(workerStaffPage.updateButton);
        BasePage.waitUntilElementPresent(workerStaffPage.nextButton, 60);
        BasePage.clickWithJavaScript(workerStaffPage.nextButton);
    }

    private void verifySuccessMessage() {
        BasePage.waitUntilElementPresent(workerStaffPage.successMessage, 30);
        String actualInLowerCase = BasePage.getText(workerStaffPage.successMessage).toLowerCase().trim();
        String expected = "Record created successfully";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Worker staff information success message is wrong!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(workerStaffPage.successMessage, 20);
    }

    public void updateStaffData() {
        navigationMenu.gotoStaffPage();
        BasePage.waitUntilPageCompletelyLoaded();
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Worker Staff Management - In Edit >>>>>>>>>>>>>>>>>>>>");
        // Retrieve the incremented value
        incrementValue = GlobalVariables.getVariable("provider_incrementValue", Integer.class);

        // add new worker staff
        BasePage.clickWithJavaScript(workerStaffPage.addNewButton);
        enterWorkerStaffManagementData(EDIT_YML_FILE, ADD);
        clickingOnAddButton();
        BasePage.clickWithJavaScript(workerStaffPage.updateButton);
        BasePage.waitUntilElementClickable(workerStaffPage.nextButton, 60);

        // edit already added worker staff
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Updating Worker Staff Management Information - In Edit >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementDisplayed(workerStaffPage.editDetailsIcon, 30);
        BasePage.clickWithJavaScript(workerStaffPage.editDetailsIcon);
        enterWorkerStaffManagementData(EDIT_YML_FILE, UPDATE);
        clickingOnAddButton();
        uploadProofOfDemandDocument();
        BasePage.clickWithJavaScript(workerStaffPage.updateButton);
        verifyUpdateSuccessMessage();
        BasePage.waitUntilElementClickable(workerStaffPage.nextButton, 60);
        BasePage.clickWithJavaScript(workerStaffPage.nextButton);
    }

    private void enterWorkerStaffManagementData(String ymlFile, String subHeader) {
        //select site
        String site = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER_SITE_MANAGEMENT_HEADER, subHeader, "SiteName");
        BasePage.waitUntilElementClickable(workerStaffPage.siteDropdown, 20);
        BasePage.clickWithJavaScript(workerStaffPage.siteDropdown);
        BasePage.genericWait(1000);
        BasePage.clickWithJavaScript(getDropdownXpath(site));

        //select worker type
        BasePage.clickWithJavaScript(workerStaffPage.workerTypeDropdown);
        String workerType = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "WorkerType");
        BasePage.waitUntilElementClickable(getDropdownXpath(workerType), 20);
        BasePage.clickWithJavaScript(getDropdownXpath(workerType));

        //select skill(s)
        String[] skills = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "Skills").split(",");
        BasePage.waitUntilElementClickable(workerStaffPage.skills, 20);
        BasePage.clickWithJavaScript(workerStaffPage.skills);
        BasePage.genericWait(1500);
        for (String skill : skills) {
            BasePage.clickWithJavaScript(getDropdownXpath(skill));
        }

        //enter hourly rate
        String hourlyRate = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "HourlyRate");
        BasePage.clickWithJavaScript(workerStaffPage.hourlyRate);
        BasePage.typeWithStringBuilder(workerStaffPage.hourlyRate, hourlyRate);

        //enter monthly agency hours
        String monthlyAgencyHours = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, subHeader, "MonthlyAgencyHours");
        BasePage.typeWithStringBuilder(workerStaffPage.monthlyAgencyHours, monthlyAgencyHours);
    }

    private void verifyUpdateSuccessMessage() {
        BasePage.waitUntilElementPresent(workerStaffPage.successMessage, 30);
        String actualInLowerCase = BasePage.getText(workerStaffPage.successMessage).toLowerCase().trim();
        String expected = "Record updated successfully";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Worker staff update success message is wrong!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(workerStaffPage.successMessage, 20);
    }
}