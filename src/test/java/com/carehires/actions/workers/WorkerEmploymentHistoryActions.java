package com.carehires.actions.workers;


import com.carehires.common.GlobalVariables;
import com.carehires.pages.worker.WorkerEmploymentHistoryPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import com.carehires.utils.GenericUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class WorkerEmploymentHistoryActions {

    private final WorkerEmploymentHistoryPage employmentHistoryPage;
    private static final GenericUtils genericUtils;

    static {
        try {
            genericUtils = new GenericUtils();
        } catch (BasePage.WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
    }

    private static final WorkerNavigationMenuActions navigationMenu = new WorkerNavigationMenuActions();

    private static final String ENTITY = "worker";
    private static final String YML_FILE = "worker-create";
    private static final String EDIT_YML_FILE = "worker-edit";
    private static final String YML_HEADER = "Employment";
    private static final String YML_SUB_HEADER1 = "Worker History";
    private static final String ADD = "Add";
    private static final String UPDATE = "Update";
    private static final String YML_SUB_HEADER_DATASET1 = "Dataset1";
    private static final String YML_SUB_HEADER_DATASET2 = "Dataset2";
    private static final String YML_SUB_HEADER_DATASET3 = "Dataset3";
    private static final String YML_SUB_HEADER2 = "Reference";
    private static final String COMPANY_NAME = "CompanyName";

    private static final Logger logger = LogManager.getFormatterLogger(WorkerEmploymentHistoryActions.class);
    Integer incrementValue;

    public WorkerEmploymentHistoryActions() {
        employmentHistoryPage = new WorkerEmploymentHistoryPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), employmentHistoryPage);
        } catch (BasePage.WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
    }

    public void enterDataForEmploymentHistory() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Employment Information Data >>>>>>>>>>>>>>>>>>>>");

        // Retrieve the incremented value
        incrementValue = GlobalVariables.getVariable("worker_incrementValue", Integer.class);

        // Check for null or default value
        if (incrementValue == null) {
            throw new NullPointerException("Increment value for worker is not set in GlobalVariables.");
        }

        BasePage.waitUntilPageCompletelyLoaded();

        // add Dataset1
        BasePage.clickWithJavaScript(employmentHistoryPage.workerHistoryAddNewButton);
        enterWorkerHistoryInfo(YML_FILE, ADD, YML_SUB_HEADER_DATASET1);

        //click on Add work history button
        BasePage.clickWithJavaScript(employmentHistoryPage.addWorkHistoryButton);
        verifySuccessMessage();

        // add Dataset2
        BasePage.clickWithJavaScript(employmentHistoryPage.workerHistoryAddNewButton);
        BasePage.waitUntilElementPresent(employmentHistoryPage.employmentTypeDropdown, 20);
        enterWorkerHistoryInfo(YML_FILE, ADD, YML_SUB_HEADER_DATASET2);

        //click on Add work history button
        BasePage.clickWithJavaScript(employmentHistoryPage.addWorkHistoryButton);
        verifySuccessMessage();

        // TBD: verify care sector related experience and total experience values are calculated correctly

        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Reference Information Data >>>>>>>>>>>>>>>>>>>>");
        // add dataset1 - Reference section
        BasePage.scrollToWebElement(employmentHistoryPage.referenceAddNewButton);
        BasePage.clickWithJavaScript(employmentHistoryPage.referenceAddNewButton);
        BasePage.scrollToWebElement(employmentHistoryPage.addReferenceButton);

        enterReferenceInfo(YML_FILE, YML_SUB_HEADER_DATASET1);

        // click on the Add button
        BasePage.clickWithJavaScript(employmentHistoryPage.addReferenceButton);
        verifySuccessMessage();

        // add dataset2 - Reference section
        BasePage.clickWithJavaScript(employmentHistoryPage.referenceAddNewButton);
        enterReferenceInfo(YML_FILE, YML_SUB_HEADER_DATASET2);

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
        BasePage.waitUntilElementPresent(employmentHistoryPage.successPopup, 120);
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

    public void updateEmploymentInfo() {
        navigationMenu.gotoEmploymentPage();
        BasePage.waitUntilPageCompletelyLoaded();

        // Retrieve the incremented value
        incrementValue = GlobalVariables.getVariable("worker_incrementValue", Integer.class);
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Employment Information Data - Adding >>>>>>>>>>>>>>>>>>>>");
        // add Dataset1 - Worker History
        BasePage.clickWithJavaScript(employmentHistoryPage.workerHistoryAddNewButton);
        enterWorkerHistoryInfo(EDIT_YML_FILE, ADD, YML_SUB_HEADER_DATASET1);
        //click on Add work history button
        BasePage.clickWithJavaScript(employmentHistoryPage.addWorkHistoryButton);
        verifySuccessMessage();
        // add Dataset2 - Worker History
        BasePage.clickWithJavaScript(employmentHistoryPage.workerHistoryAddNewButton);
        BasePage.waitUntilElementPresent(employmentHistoryPage.employmentTypeDropdown, 20);
        enterWorkerHistoryInfo(EDIT_YML_FILE, ADD, YML_SUB_HEADER_DATASET2);
        //click on Add work history button
        BasePage.clickWithJavaScript(employmentHistoryPage.addWorkHistoryButton);
        verifySuccessMessage();

        // click on the Update button
        BasePage.waitUntilElementClickable(employmentHistoryPage.updateButton, 90);
        BasePage.clickWithJavaScript(employmentHistoryPage.updateButton);

        // delete worker history
        deleteEntry(employmentHistoryPage.deleteIcon1);
        verifyDeleteSuccessMessage();

        // edit Dataset2 - Worker History
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Employment Information Data - Updating >>>>>>>>>>>>>>>>>>>>");
        BasePage.clickWithJavaScript(employmentHistoryPage.workerHistoryEditIcon1);
        enterWorkerHistoryInfo(EDIT_YML_FILE, UPDATE, YML_SUB_HEADER_DATASET1);
        BasePage.clickWithJavaScript(employmentHistoryPage.updateWorkHistoryButton);
        verifyUpdateSuccessMessage();

        // add Dataset3 - worker history
        BasePage.clickWithJavaScript(employmentHistoryPage.workerHistoryAddNewButton);
        BasePage.waitUntilElementPresent(employmentHistoryPage.employmentTypeDropdown, 20);
        enterWorkerHistoryInfo(EDIT_YML_FILE, ADD, YML_SUB_HEADER_DATASET3);
        BasePage.clickWithJavaScript(employmentHistoryPage.addWorkHistoryButton);
        verifySuccessMessage();

        // closing worker history add new area
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Reference Information Data - Adding >>>>>>>>>>>>>>>>>>>>");
        // add dataset1 - Reference section
        BasePage.clickWithJavaScript(employmentHistoryPage.workerHistoryExpandOrClose);
        BasePage.scrollToWebElement(employmentHistoryPage.referenceAddNewButton);
        BasePage.clickWithJavaScript(employmentHistoryPage.referenceAddNewButton);
        BasePage.scrollToWebElement(employmentHistoryPage.addReferenceButton);
        enterReferenceInfo(EDIT_YML_FILE, YML_SUB_HEADER_DATASET1);
        BasePage.clickWithJavaScript(employmentHistoryPage.addReferenceButton);
        verifySuccessMessage();

        // add dataset2 - Reference section
        BasePage.clickWithJavaScript(employmentHistoryPage.referenceAddNewButton);
        enterReferenceInfo(EDIT_YML_FILE, YML_SUB_HEADER_DATASET2);
        BasePage.clickWithJavaScript(employmentHistoryPage.addReferenceButton);
        verifySuccessMessage();

        // add dataset3 - Reference section
        BasePage.clickWithJavaScript(employmentHistoryPage.referenceAddNewButton);
        enterReferenceInfo(EDIT_YML_FILE, YML_SUB_HEADER_DATASET3);
        BasePage.clickWithJavaScript(employmentHistoryPage.addReferenceButton);
        verifySuccessMessage();

        // delete a reference
        deleteEntry(employmentHistoryPage.referenceDeleteIcon1);
        verifyDeleteSuccessMessage();
        BasePage.clickWithJavaScript(employmentHistoryPage.saveButton);

        //verify employment history data saved successfully
        verifyEmploymentHistorySavedSuccessfully();
        BasePage.waitUntilElementPresent(employmentHistoryPage.successPopupNoLink, 60);
        BasePage.clickWithJavaScript(employmentHistoryPage.successPopupNoLink);
    }

    private void enterWorkerHistoryInfo(String ymlFile, String subHeader, String dataset) {
        BasePage.waitUntilElementPresent(employmentHistoryPage.employmentTypeDropdown, 20);

        String employmentType = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                YML_SUB_HEADER1, subHeader, dataset, "EmploymentType");
        BasePage.genericWait(3000);
        BasePage.clickWithJavaScript(employmentHistoryPage.employmentTypeDropdown);
        BasePage.waitUntilElementClickable(employmentHistoryPage.getDropdownOptionXpath(employmentType),
                20);
        BasePage.clickWithJavaScript(employmentHistoryPage.getDropdownOptionXpath(employmentType));

        String currentlyWorkHere = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                YML_SUB_HEADER1, subHeader, dataset, "IsCurrentlyWorkHere");
        assert currentlyWorkHere != null;
        if (currentlyWorkHere.equalsIgnoreCase("Yes")) {
            BasePage.clickWithJavaScript(employmentHistoryPage.isCurrentlyWorkingHere);
        } else {
            String toDate = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                    YML_SUB_HEADER1, subHeader, dataset, "To");
            BasePage.clickWithJavaScript(employmentHistoryPage.to);
            genericUtils.selectDateFromCalendarPopup(toDate);

            String reasonForLeaving = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                    YML_SUB_HEADER1, subHeader, dataset, "ReasonForLeaving");
            BasePage.clickWithJavaScript(employmentHistoryPage.reasonForLeavingDropdown);
            BasePage.genericWait(1000);
            BasePage.waitUntilElementClickable(employmentHistoryPage.getDropdownOptionXpath(reasonForLeaving),
                    20);
            BasePage.clickWithJavaScript(employmentHistoryPage.getDropdownOptionXpath(reasonForLeaving));
        }

        String fromDate = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                YML_SUB_HEADER1, subHeader, dataset, "From");
        BasePage.clickWithJavaScript(employmentHistoryPage.from);
        genericUtils.selectDateFromCalendarPopup(fromDate);

        String companyName = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                YML_SUB_HEADER1, subHeader, dataset, COMPANY_NAME);
        BasePage.clearAndEnterTexts(employmentHistoryPage.companyName, companyName);

        String designation = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                YML_SUB_HEADER1, subHeader, dataset, "Designation");
        BasePage.clearAndEnterTexts(employmentHistoryPage.designation, designation);

        String isCareSectorRelatedExperience = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile,
                YML_HEADER, YML_SUB_HEADER1, subHeader, dataset, "IsCareSectorRelatedExperience");
        assert isCareSectorRelatedExperience != null;
        if (isCareSectorRelatedExperience.equalsIgnoreCase("Yes")) {
            BasePage.clickWithJavaScript(employmentHistoryPage.isCareRelatedWorkExperience);
        }

        String additionalNote = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                YML_SUB_HEADER1, subHeader, dataset, "AdditionalNote");
        BasePage.clearTexts(employmentHistoryPage.additionalNote);
        BasePage.typeWithStringBuilder(employmentHistoryPage.additionalNote, additionalNote);
    }

    private void enterReferenceInfo(String ymlFile, String dataset) {
        BasePage.waitUntilElementDisplayed(employmentHistoryPage.referenceTypeDropdown, 30);
        String referenceType = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                YML_SUB_HEADER2, ADD, dataset, "ReferenceType");
        BasePage.clickWithJavaScript(employmentHistoryPage.referenceTypeDropdown);
        BasePage.genericWait(1000);
        BasePage.waitUntilElementClickable(employmentHistoryPage.getDropdownOptionXpath(referenceType),
                20);
        BasePage.clickWithJavaScript(employmentHistoryPage.getDropdownOptionXpath(referenceType));

        BasePage.clickWithJavaScript(employmentHistoryPage.selectWorkplaceDropdown);
        String companyName;
        if (dataset.equalsIgnoreCase(YML_SUB_HEADER_DATASET2)) {
            companyName = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, YML_SUB_HEADER1,
                    UPDATE, YML_SUB_HEADER_DATASET1, COMPANY_NAME);
        } else if (dataset.equalsIgnoreCase(YML_SUB_HEADER_DATASET3)) {
            companyName = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, YML_SUB_HEADER1,
                    UPDATE, YML_SUB_HEADER_DATASET2, COMPANY_NAME);
        } else {
            companyName = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, YML_SUB_HEADER1,
                    UPDATE, YML_SUB_HEADER_DATASET1, COMPANY_NAME);
        }

        if (ymlFile.equalsIgnoreCase(YML_FILE) && dataset.equalsIgnoreCase(YML_SUB_HEADER_DATASET1)) {
            companyName = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER1,
                    ADD, YML_SUB_HEADER_DATASET1, COMPANY_NAME);
        } else if (ymlFile.equalsIgnoreCase(YML_FILE) && dataset.equalsIgnoreCase(YML_SUB_HEADER_DATASET2)) {
            companyName = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER1,
                    ADD, YML_SUB_HEADER_DATASET2, COMPANY_NAME);
        }
        BasePage.waitUntilElementClickable(employmentHistoryPage.getDropdownOptionXpath(companyName), 20);
        BasePage.clickWithJavaScript(employmentHistoryPage.getDropdownOptionXpath(companyName));

        String uploadFile = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                YML_SUB_HEADER2, ADD, dataset, "UploadFile");
        String absoluteFilePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                + File.separator + "resources" + File.separator + "Upload" + File.separator + "Worker" + File.separator
                + uploadFile;

        BasePage.genericWait(3000);
        BasePage.waitUntilElementClickable(employmentHistoryPage.uploadFile, 60); // Wait for the element to be present
        BasePage.scrollToWebElement(employmentHistoryPage.uploadFile); // Scroll to make the element visible

        try {
            BasePage.waitUntilElementClickable(employmentHistoryPage.uploadFile, 20); // Wait until clickable
            if (employmentHistoryPage.uploadFile.isDisplayed() && employmentHistoryPage.uploadFile.isEnabled()) {
                BasePage.uploadFile(employmentHistoryPage.uploadFile, absoluteFilePath); // Upload the file
            } else {
                logger.error("Upload file element is not interactable. Check visibility and state.");
            }
        } catch (org.openqa.selenium.TimeoutException e) {
            logger.error("Timeout waiting for upload file element to become clickable. Attempting JavaScript click.");
            BasePage.clickWithJavaScript(employmentHistoryPage.uploadFile); // Fallback to JavaScript click
            BasePage.uploadFile(employmentHistoryPage.uploadFile, absoluteFilePath); // Upload the file
        }


        String referenceNote = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                YML_SUB_HEADER2, ADD, dataset, "ReferenceNote");
        BasePage.typeWithStringBuilder(employmentHistoryPage.referenceNote, referenceNote);
    }

    private void deleteEntry(WebElement deleteIcon) {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Delete worker history - %s >>>>>>>>>>>>>>>>>>>>", deleteIcon);
        BasePage.waitUntilElementClickable(deleteIcon, 30);
        BasePage.clickWithJavaScript(deleteIcon);
    }

    private void verifyDeleteSuccessMessage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying worker history delete successfully >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementPresent(employmentHistoryPage.successMessage, 90);
        String actualInLowerCase = BasePage.getText(employmentHistoryPage.successMessage).toLowerCase().trim();
        String expected = "Record deleted.";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Unable to delete worker history!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(employmentHistoryPage.successMessage, 20);
    }

    private void verifyUpdateSuccessMessage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying worker history edit successfully >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementPresent(employmentHistoryPage.successMessage, 90);
        String actualInLowerCase = BasePage.getText(employmentHistoryPage.successMessage).toLowerCase().trim();
        String expected = "Record updated successfully";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Unable to update worker history!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(employmentHistoryPage.successMessage, 20);
    }

    public void collectWorkerId() {
        getWorkerId();
    }
}