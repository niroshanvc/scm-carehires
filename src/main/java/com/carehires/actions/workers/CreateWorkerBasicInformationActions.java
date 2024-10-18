package com.carehires.actions.workers;

import com.carehires.common.GlobalVariables;
import com.carehires.pages.worker.CreateWorkerBasicInformationPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import com.carehires.utils.GenericUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CreateWorkerBasicInformationActions {

    CreateWorkerBasicInformationPage basicInfo;
    GenericUtils genericUtils = new GenericUtils();

    private static final String ENTITY = "worker";
    private static final String YML_FILE = "worker-create";
    private static final String YML_AGENCY_FILE = "agency-create";
    private static final String YML_HEADER = "BasicInformation";
    private static final String YML_SUB_HEADER_2 = "PersonalInformation";
    private static final String YML_SUB_HEADER_3 = "ResidentialAddressInformation";
    private static final String YML_SUB_HEADER_4 = "EmploymentInformation";
    private static final String YML_SUB_HEADER_5 = "PassportVisaDbsInformation";

    private static final String VALUE_TEXT = "value";

    private static final Logger logger = LogManager.getLogger(CreateWorkerBasicInformationActions.class);

    public CreateWorkerBasicInformationActions() {
        basicInfo = new CreateWorkerBasicInformationPage();
        PageFactory.initElements(BasePage.getDriver(), basicInfo);
    }

    public void enterWorkerBasicInformationData() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Creating a worker >>>>>>>>>>>>>>>>>>>>");
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Basic Information >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();

        // Retrieve the current increment value for the worker (from the file)
        int incrementValue = DataConfigurationReader.getCurrentIncrementValueForWorkers(ENTITY);

        BasePage.clickWithJavaScript(basicInfo.firstName);

        String agency = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "AgencyInformation", "Agency");
        BasePage.clickWithJavaScript(basicInfo.agencyDropdown);
        By by = By.xpath(getDropdownOptionXpath(agency));
        BasePage.waitUntilVisibilityOfElementLocated(by, 20);
        BasePage.scrollToWebElement(getDropdownOptionXpath(agency));
        BasePage.clickWithJavaScript(getDropdownOptionXpath(agency));

        BasePage.genericWait(2000);
        String agencyLocation = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_AGENCY_FILE, "Location", "BusinessLocation");
        BasePage.clickWithJavaScript(basicInfo.agencyLocationDropdown);
        BasePage.waitUntilElementClickable(getDropdownOptionXpath(agencyLocation), 30);
        selectAllAgencyLocations();

        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Personal Information >>>>>>>>>>>>>>>>>>>>");
        //upload a logo
        String workerImage = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER_2, "WorkerLogo");
        String absoluteFilePath = System.getProperty("user.dir") + "\\src\\test\\resources\\Upload\\Worker\\" + workerImage;
        BasePage.clickWithJavaScript(basicInfo.uploadLogo);
        BasePage.uploadFile(basicInfo.uploadLogoInput, absoluteFilePath);
        BasePage.waitUntilElementDisplayed(basicInfo.imageSaveButton, 60);
        BasePage.clickWithJavaScript(basicInfo.imageSaveButton);

        String firstName = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER_2, "FirstName");
        BasePage.typeWithStringBuilder(basicInfo.firstName, firstName);

        String lastName = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER_2, "LastName");
        BasePage.typeWithStringBuilder(basicInfo.lastName, lastName);

        String alsoKnownAs = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER_2, "AlsoKnownAs");
        BasePage.typeWithStringBuilder(basicInfo.alsoKnownAs, alsoKnownAs);

        String workerEmail = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER_2, "WorkerEmailAddress");
        BasePage.typeWithStringBuilder(basicInfo.email, workerEmail);

        BasePage.scrollToWebElement(basicInfo.passportInformationHeader);

        genericUtils.fillPhoneNumber(ENTITY, YML_FILE, basicInfo.phoneNumberInput, YML_HEADER, YML_SUB_HEADER_2, "PhoneNumberType");
        genericUtils.fillPhoneNumber(ENTITY, YML_FILE, basicInfo.phoneNumberInput, YML_HEADER, YML_SUB_HEADER_2, "PhoneNumber");

        String dateOfBirth = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER_2, "DateOfBirth");
        BasePage.typeWithStringBuilder(basicInfo.dateOfBirth, dateOfBirth);

        String gender = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER_2, "Gender");
        if (gender.equalsIgnoreCase("Male")) {
            BasePage.clickWithJavaScript((basicInfo.genderRadioButton).get(0));
        } else if (gender.equalsIgnoreCase("Female")) {
            BasePage.clickWithJavaScript((basicInfo.genderRadioButton).get(1));
        } else {
            BasePage.clickWithJavaScript((basicInfo.genderRadioButton).get(2));
        }

        String nationality = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER_2, "Nationality");
        BasePage.clickWithJavaScript(basicInfo.nationalityDropdown);
        BasePage.clickWithJavaScript(getDropdownOptionXpath(nationality));

        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Residential Address Information >>>>>>>>>>>>>>>>>>>>");
        expandSubSection(basicInfo.residentialAddressInformationHeader, basicInfo.residentialAddressInformationHeaderExpandIcon);
        String country = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER_3, "Country");
        BasePage.clickWithJavaScript(basicInfo.countryDropdown);
        BasePage.clickWithJavaScript(getDropdownOptionXpath(country));

        //enter postcode and select a valid address
        String postcode = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER_3, "PostCode");
        BasePage.clickWithJavaScript(basicInfo.postcode);
        genericUtils.fillAddress(basicInfo.postcode, postcode);

        //scroll down to employment information
        BasePage.scrollToWebElement(basicInfo.employmentInformationHeader);

        String fromDate = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER_3, "From");
        BasePage.clearAndEnterTexts(basicInfo.livingFrom, fromDate);
        BasePage.clickTabKey(basicInfo.livingFrom);

        String currentlyLivingThisAddress = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER_3, "CurrentlyLivingInThisAddress");
        if(currentlyLivingThisAddress.equalsIgnoreCase("Yes")) {
            BasePage.clickWithJavaScript(basicInfo.isCurrentlyLivingCheckbox);
            BasePage.waitUntilElementDisappeared(basicInfo.livingTo, 20);
        } else {
            String to = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER_3, "To");
            BasePage.clearAndEnterTexts(basicInfo.livingTo, to);
            BasePage.clickTabKey(basicInfo.livingTo);
        }

        // verify duration in address
        verifyDurationInAddress();

        //upload proof of address document
        String document = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER_3, "ProofOfAddressDocument");
        String absoluteFilePath1 = System.getProperty("user.dir") + "\\src\\test\\resources\\Upload\\Worker\\" + document;
        BasePage.uploadFile(basicInfo.proofOfAddressDocument, absoluteFilePath1);
        //wait until document is uploaded
        waitUntilDocumentUploaded();

        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Employment Information >>>>>>>>>>>>>>>>>>>>");
        expandSubSection(basicInfo.employmentInformationHeader, basicInfo.employmentInformationHeaderExpandIcon);
        String workerType = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER_4, "WorkerType");
        BasePage.clickWithJavaScript(basicInfo.workerTypeDropdown);
        BasePage.waitUntilElementClickable(getWorkerTypeDropdownOptionXpath(workerType), 20);
        BasePage.clickWithJavaScript(getWorkerTypeDropdownOptionXpath(workerType));

        //need to add a pause until skills are loading
        BasePage.genericWait(2000);
        String[] skills = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER_4, "Skills").split(",");
        BasePage.clickWithJavaScript(basicInfo.workerSkillsDropdown);
        BasePage.waitUntilElementClickable(getDropdownOptionXpath(skills[0]), 20);
        for (String skill : skills) {
            BasePage.clickWithJavaScript(getDropdownOptionXpath(skill));
        }

        // scroll down to travel & other information
        BasePage.scrollToWebElement(basicInfo.travelInformationHeader);

        String[] regulatorySettings = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER_4, "RegulatorySettings").split(",");
        BasePage.clickWithJavaScript(basicInfo.regulatorySettingsDropdown);
        BasePage.waitUntilElementClickable(getDropdownOptionXpath(regulatorySettings[0]), 20);
        for (String setting : regulatorySettings) {
            BasePage.clickWithJavaScript(getDropdownOptionXpath(setting));
        }

        String employmentType = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER_4, "WorkerEmploymentType");
        if (employmentType.equalsIgnoreCase("Paye")) {
            BasePage.clickWithJavaScript((basicInfo.employeementTypeRadioButton).get(0));
        } else if (employmentType.equalsIgnoreCase("Umbrella Company")) {
            BasePage.clickWithJavaScript((basicInfo.employeementTypeRadioButton).get(1));
        } else {
            BasePage.clickWithJavaScript((basicInfo.employeementTypeRadioButton).get(2));
        }

        BasePage.waitUntilElementClickable(basicInfo.payrollReferenceNumber, 20);
        String payrollReferenceNumber = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER_4, "PayrollReferenceNumber");
        BasePage.typeWithStringBuilder(basicInfo.payrollReferenceNumber, payrollReferenceNumber);

        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Passport, Visa & DBS Information >>>>>>>>>>>>>>>>>>>>");
        expandSubSection(basicInfo.passportInformationHeader, basicInfo.passportInformationHeaderExpandIcon);

        // scroll down to travel & other information
        BasePage.scrollToWebElement(basicInfo.travelInformationHeader);

        String nationalInsuranceNumber = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER_5, "NationalInsuranceNumber");
        BasePage.typeWithStringBuilder(basicInfo.nationalInsuranceNumber, nationalInsuranceNumber);

        String dbs = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER_5, "DbsCertificateNumber");
        BasePage.typeWithStringBuilder(basicInfo.dbsCertificateNumber, dbs);

        String isConviction = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER_5, "IsThereAnyConviction");
        if (isConviction.equalsIgnoreCase("Yes")) {
            BasePage.clickWithJavaScript((basicInfo.hasConvictionRadioButton).get(0));
        } else {
            BasePage.clickWithJavaScript((basicInfo.hasConvictionRadioButton).get(1));
        }

        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Travel & Other Information >>>>>>>>>>>>>>>>>>>>");
        expandSubSection(basicInfo.travelInformationHeader, basicInfo.travelInformationHeaderExpandIcon);
        String travelDistance = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "TravelInformation", "PreferredTravelDistance");
        BasePage.typeWithStringBuilder(basicInfo.travelDistance, travelDistance);

        String isDrivingLicenceAvailable = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "TravelInformation", "DrivingLicence");
        if (isDrivingLicenceAvailable.equalsIgnoreCase("Yes")) {
            BasePage.clickWithJavaScript((basicInfo.hasDrivingLicenseRadioButton).get(0));
        } else {
            BasePage.clickWithJavaScript((basicInfo.hasDrivingLicenseRadioButton).get(1));
        }

        BasePage.clickWithJavaScript(basicInfo.saveButton);

        // After successfully entering the basic information, update the increment value in the file
        DataConfigurationReader.storeNewIncrementValue(ENTITY);

        // Store the increment value in GlobalVariables for reuse in other steps
        GlobalVariables.setVariable("workerIncrementValue", incrementValue+1);
    }

    private String getDropdownOptionXpath(String option) {
        return String.format("//nb-option[contains(text(),'%s')]", option);
    }

    private void waitUntilDocumentUploaded() {
        BasePage.waitUntilElementPresent(basicInfo.documentUploadedSuccessMessage, 30);
        String actualInLowerCase = BasePage.getText(basicInfo.documentUploadedSuccessMessage).toLowerCase().trim();
        String expected = "Document is added";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Document uploaded success message is wrong!", actualInLowerCase, is(expectedInLowerCase));
    }

    private String getWorkerTypeDropdownOptionXpath(String option) {
        return String.format("//nb-option[text()='%s ']", option);
    }

    // method to select all the options available in the Agency Location multi select dropdown
    private void selectAllAgencyLocations() {
        List<WebElement> checkboxes = BasePage.findListOfWebElements(CreateWorkerBasicInformationPage.AGENCY_LOCATION_CHECKBOXES);
        for (WebElement checkbox : checkboxes) {
            BasePage.clickWithJavaScript(checkbox);
        }
    }

    private void expandSubSection(WebElement headerText, WebElement headerIcon) {
        if(BasePage.getAttributeValue(headerText, "class").equalsIgnoreCase("collapsed")) {
            BasePage.clickWithJavaScript(headerIcon);
        }
    }

    private void verifyDurationInAddress() {
        String fromDate = BasePage.getAttributeValue(basicInfo.livingFrom, VALUE_TEXT);
        String currentlyLivingThisAddress = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, YML_SUB_HEADER_3, "CurrentlyLivingInThisAddress");

        // Define the date format (e.g., 13 Nov 2019)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        String  toDate;
        LocalDate to;
        if (currentlyLivingThisAddress.equalsIgnoreCase("Yes")) {
            to = LocalDate.now();
        } else {
            toDate = BasePage.getAttributeValue(basicInfo.livingTo, VALUE_TEXT);
            to = LocalDate.parse(toDate, formatter);
        }

        // Parse the date Strings into LocalDate objects
        LocalDate from = LocalDate.parse(fromDate, formatter);

        // Calculate the duration between the two dates using the Period class
        Period period = Period.between(from, to);

        // Extract the years, months, and days from the Period
        int years = period.getYears();
        int months = period.getMonths();
        int days = period.getDays();

        // 8 years, 1 months, and 1 days
        String expectedDuration = years +" years, "+ months + " months, and " + days + " days";
        String actualDuration = BasePage.getAttributeValue(basicInfo.durationInAddress, VALUE_TEXT);
        assertThat("Duration in address is wrong!", actualDuration, is(expectedDuration));
    }
}
