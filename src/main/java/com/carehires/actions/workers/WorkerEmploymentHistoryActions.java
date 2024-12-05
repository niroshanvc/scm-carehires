package com.carehires.actions.workers;

import com.carehires.common.GlobalVariables;
import com.carehires.pages.worker.WorkerEmploymentHistoryPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import com.carehires.utils.GenericUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class WorkerEmploymentHistoryActions {

    private final WorkerEmploymentHistoryPage employmentHistoryPage;
    private static final GenericUtils genericUtils = new GenericUtils();

    private static final String ENTITY = "worker";
    private static final String YML_FILE = "worker-create";
    private static final String YML_HEADER = "Employment";
    private static final String YML_SUB_HEADER1 = "Worker History";
    private static final String YML_SUB_HEADER_DATASET1 = "Dataset1";
    private static final String YML_SUB_HEADER_DATASET2 = "Dataset2";
    private static final String YML_SUB_HEADER2 = "Reference";
    private static final Logger logger = LogManager.getFormatterLogger(WorkerEmploymentHistoryActions.class);

    public WorkerEmploymentHistoryActions() {
        employmentHistoryPage = new WorkerEmploymentHistoryPage();
        PageFactory.initElements(BasePage.getDriver(), employmentHistoryPage);
    }

    public void enterDataForEmploymentHistory() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Employment Information Data >>>>>>>>>>>>>>>>>>>>");

        // Retrieve the incremented value
        Integer incrementValue = GlobalVariables.getVariable("worker_incrementValue", Integer.class);

        // Check for null or default value
        if (incrementValue == null) {
            throw new NullPointerException("Increment value for worker is not set in GlobalVariables.");
        }

        BasePage.waitUntilPageCompletelyLoaded();

        // add Dataset1
        BasePage.clickWithJavaScript(employmentHistoryPage.workerHistoryAddNewButton);
        BasePage.waitUntilElementPresent(employmentHistoryPage.employmentTypeDropdown, 20);

        String employmentType = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER1, YML_SUB_HEADER_DATASET1, "EmploymentType");
        BasePage.clickWithJavaScript(employmentHistoryPage.employmentTypeDropdown);
        BasePage.genericWait(1000);
        BasePage.waitUntilElementClickable(getDropdownOptionXpath(employmentType), 20);
        BasePage.clickWithJavaScript(getDropdownOptionXpath(employmentType));

        String currentlyWorkHere = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER1, YML_SUB_HEADER_DATASET1, "IsCurrentlyWorkHere");
        if (currentlyWorkHere.equalsIgnoreCase("Yes")) {
            BasePage.clickWithJavaScript(employmentHistoryPage.isCurrentlyWorkingHere);
        } else {
            String toDate = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER1, YML_SUB_HEADER_DATASET1, "To");
            BasePage.clickWithJavaScript(employmentHistoryPage.to);
            genericUtils.selectDateFromCalendarPopup(toDate);

            String reasonForLeaving = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER1, YML_SUB_HEADER_DATASET1, "ReasonForLeaving");
            BasePage.clickWithJavaScript(employmentHistoryPage.reasonForLeavingDropdown);
            BasePage.genericWait(1000);
            BasePage.waitUntilElementClickable(getDropdownOptionXpath(reasonForLeaving), 20);
            BasePage.clickWithJavaScript(getDropdownOptionXpath(reasonForLeaving));
        }

        String fromDate = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER1, YML_SUB_HEADER_DATASET1, "From");
        BasePage.clickWithJavaScript(employmentHistoryPage.from);
        genericUtils.selectDateFromCalendarPopup(fromDate);

        String companyName = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER1, YML_SUB_HEADER_DATASET1, "CompanyName");
        BasePage.clearAndEnterTexts(employmentHistoryPage.companyName, companyName);

        String designation = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER1, YML_SUB_HEADER_DATASET1, "Designation");
        BasePage.clearAndEnterTexts(employmentHistoryPage.designation, designation);

        String isCareSectorRelatedExperience = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER1, YML_SUB_HEADER_DATASET1, "IsCareSectorRelatedExperience");
        if (isCareSectorRelatedExperience.equalsIgnoreCase("Yes")) {
            BasePage.clickWithJavaScript(employmentHistoryPage.isCareRelatedWorkExperience);
        }

        String additionalNote = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER1, YML_SUB_HEADER_DATASET1, "AdditionalNote");
        BasePage.typeWithStringBuilder(employmentHistoryPage.additionalNote, additionalNote);

        //click on Add work history button
        BasePage.clickWithJavaScript(employmentHistoryPage.addWorkHistoryButton);
        verifySuccessMessage();

        // add Dataset2
        BasePage.waitUntilElementPresent(employmentHistoryPage.employmentTypeDropdown, 20);

        employmentType = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER1, YML_SUB_HEADER_DATASET2, "EmploymentType");
        BasePage.clickWithJavaScript(employmentHistoryPage.employmentTypeDropdown);
        BasePage.genericWait(1000);
        BasePage.waitUntilElementPresent(getDropdownOptionXpath(employmentType), 20);
        BasePage.clickWithJavaScript(getDropdownOptionXpath(employmentType));

        currentlyWorkHere = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER1, YML_SUB_HEADER_DATASET2, "IsCurrentlyWorkHere");
        if (currentlyWorkHere.equalsIgnoreCase("Yes")) {
            BasePage.clickWithJavaScript(employmentHistoryPage.isCurrentlyWorkingHere);
        } else {
            String toDate = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER1, YML_SUB_HEADER_DATASET2, "To");
            BasePage.clickWithJavaScript(employmentHistoryPage.to);
            genericUtils.selectDateFromCalendarPopup(toDate);

            String reasonForLeaving = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER1, YML_SUB_HEADER_DATASET2, "ReasonForLeaving");
            BasePage.clickWithJavaScript(employmentHistoryPage.reasonForLeavingDropdown);
            BasePage.genericWait(1000);
            BasePage.waitUntilElementClickable(getDropdownOptionXpath(reasonForLeaving), 20);
            BasePage.clickWithJavaScript(getDropdownOptionXpath(reasonForLeaving));
        }

        fromDate = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER1, YML_SUB_HEADER_DATASET2, "From");
        BasePage.clickWithJavaScript(employmentHistoryPage.from);
        genericUtils.selectDateFromCalendarPopup(fromDate);

        companyName = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER1, YML_SUB_HEADER_DATASET2, "CompanyName");
        BasePage.clearAndEnterTexts(employmentHistoryPage.companyName, companyName);

        designation = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER1, YML_SUB_HEADER_DATASET2, "Designation");
        BasePage.clearAndEnterTexts(employmentHistoryPage.designation, designation);

        isCareSectorRelatedExperience = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER1, YML_SUB_HEADER_DATASET2, "IsCareSectorRelatedExperience");
        if (isCareSectorRelatedExperience.equalsIgnoreCase("Yes")) {
            BasePage.clickWithJavaScript(employmentHistoryPage.isCareRelatedWorkExperience);
        }

        additionalNote = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER1, YML_SUB_HEADER_DATASET2, "AdditionalNote");
        BasePage.typeWithStringBuilder(employmentHistoryPage.additionalNote, additionalNote);

        //click on Add work history button
        BasePage.clickWithJavaScript(employmentHistoryPage.addWorkHistoryButton);
        verifySuccessMessage();

        // TBD: verify care sector related experience and total experience values are calculated correctly

        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Reference Information Data >>>>>>>>>>>>>>>>>>>>");
        // add dataset1 - Reference section
        BasePage.scrollToWebElement(employmentHistoryPage.referenceAddNewButton);
        BasePage.clickWithJavaScript(employmentHistoryPage.referenceAddNewButton);

        BasePage.waitUntilElementDisplayed(employmentHistoryPage.referenceTypeDropdown, 30);
        String referenceType = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER2, YML_SUB_HEADER_DATASET1, "ReferenceType");
        BasePage.clickWithJavaScript(employmentHistoryPage.referenceTypeDropdown);
        BasePage.genericWait(1000);
        BasePage.waitUntilElementClickable(getDropdownOptionXpath(referenceType), 20);
        BasePage.clickWithJavaScript(getDropdownOptionXpath(referenceType));

        BasePage.clickWithJavaScript(employmentHistoryPage.selectWorkplaceDropdown);
        BasePage.waitUntilElementClickable(getDropdownOptionXpath(companyName), 20);
        BasePage.clickWithJavaScript(getDropdownOptionXpath(companyName));

        String uploadFile = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER2, YML_SUB_HEADER_DATASET1, "UploadFile");
        String absoluteFilePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                + File.separator + "resources" + File.separator + "Upload" + File.separator + "Worker" + File.separator
                + uploadFile;
        BasePage.uploadFile(employmentHistoryPage.uploadFile, absoluteFilePath);

        String referenceNote = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER2, YML_SUB_HEADER_DATASET1, "ReferenceNote");
        BasePage.typeWithStringBuilder(employmentHistoryPage.referenceNote, referenceNote);

        // click on the Add button
        BasePage.clickWithJavaScript(employmentHistoryPage.addReferenceButton);
        verifySuccessMessage();

        // add dataset2 - Reference section
        BasePage.waitUntilElementDisplayed(employmentHistoryPage.referenceTypeDropdown, 30);
        referenceType = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER2, YML_SUB_HEADER_DATASET2, "ReferenceType");
        BasePage.clickWithJavaScript(employmentHistoryPage.referenceTypeDropdown);
        BasePage.genericWait(1000);
        BasePage.waitUntilElementClickable(getDropdownOptionXpath(referenceType), 20);
        BasePage.clickWithJavaScript(getDropdownOptionXpath(referenceType));

        BasePage.clickWithJavaScript(employmentHistoryPage.selectWorkplaceDropdown);
        BasePage.waitUntilElementClickable(getDropdownOptionXpath(companyName), 20);
        BasePage.clickWithJavaScript(getDropdownOptionXpath(companyName));

        uploadFile = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER2, YML_SUB_HEADER_DATASET2, "UploadFile");
        absoluteFilePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                + File.separator + "resources" + File.separator + "Upload" + File.separator + "Worker" + File.separator
                + uploadFile;
        BasePage.uploadFile(employmentHistoryPage.uploadFile, absoluteFilePath);

        referenceNote = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER2, YML_SUB_HEADER_DATASET2, "ReferenceNote");
        BasePage.typeWithStringBuilder(employmentHistoryPage.referenceNote, referenceNote);

        // click on the Add button
        BasePage.clickWithJavaScript(employmentHistoryPage.addReferenceButton);
        verifySuccessMessage();

        // click on the Update button
        BasePage.waitUntilElementClickable(employmentHistoryPage.updateButton, 90);
        BasePage.clickWithJavaScript(employmentHistoryPage.updateButton);
        BasePage.waitUntilElementClickable(employmentHistoryPage.saveButton, 90);
        BasePage.clickWithJavaScript(employmentHistoryPage.saveButton);

        //verify employment history data saved successfully
        verifyEmploymentHistorySavedSuccessfully();
        BasePage.waitUntilElementPresent(employmentHistoryPage.successPopupNoLink, 30);
        BasePage.clickWithJavaScript(employmentHistoryPage.successPopupNoLink);
        getWorkerId();
    }

    private String getDropdownOptionXpath(String city) {
        return String.format("//nb-option[contains(text(),'%s')]", city);
    }

    private void verifySuccessMessage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying data saving success message >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementPresent(employmentHistoryPage.successMessage, 90);
        String actualInLowerCase = BasePage.getText(employmentHistoryPage.successMessage).toLowerCase().trim();
        String expected = "Record created successfully";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Unable to add certificate!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(employmentHistoryPage.successMessage, 20);
    }

    // verify care sector related experience calculation
    private void verifyCareSectorRelatedExperience() {
        String actual = BasePage.getText(employmentHistoryPage.careSectorRelatedExperience).trim();
        String expected = "Yes";
        assertThat("Care sector related experience is not calculated correctly!", actual, is(expected));
    }

    // calculate work experience
    private String calculateExperience(String startDateStr, String endDateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        LocalDate startDate = LocalDate.parse(startDateStr, formatter);

        LocalDate endDate;
        if (endDateStr.equalsIgnoreCase("current")) {
            endDate = LocalDate.now(); // Use current date if endDateStr is "current"
        } else {
            endDate = LocalDate.parse(endDateStr, formatter);
        }

        // Calculate the difference between start and end date
        Period period = Period.between(startDate, endDate);
        int years = period.getYears();
        int months = period.getMonths();

        return String.format("%dY %dM", years, months);
    }

    private void verifyEmploymentHistorySavedSuccessfully() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying employment history saved successfully >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementPresent(employmentHistoryPage.successPopup, 60);
        String actual = BasePage.getText(employmentHistoryPage.successPopupTitle).trim();
        String expected = "Successful!";
        assertThat("Employment history is not saved successfully!", actual, is(expected));
    }

    // get auto generated worker id and save it on the memory
    private void getWorkerId() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Reading auto generated worker id >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementPresent(employmentHistoryPage.workerId, 30);
        String headerText = BasePage.getText(employmentHistoryPage.workerId).trim();
        String workerId = headerText.split("\n")[0];
        GlobalVariables.setVariable("workerId", workerId);
    }
}