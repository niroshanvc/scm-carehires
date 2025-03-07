package com.carehires.actions.workers;

import com.carehires.common.GlobalVariables;
import com.carehires.pages.worker.WorkerBasicInformationPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import com.carehires.utils.GenericUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class WorkerBasicInformationActions {

    private final WorkerBasicInformationPage basicInfo;
    private static final GenericUtils genericUtils;

    static {
        try {
            genericUtils = new GenericUtils();
        } catch (BasePage.WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String ENTITY = "worker";
    private static final String YML_FILE = "worker-create";
    private static final String EDIT_YML_FILE = "worker-edit";
    private static final String YML_AGENCY_FILE = "agency-edit";
    private static final String YML_HEADER = "Basic Information";
    private static final String ADD = "Add";
    private static final String UPDATE = "Update";
    private static final String YML_SUB_HEADER_2 = "Personal Information";
    private static final String YML_SUB_HEADER_3 = "Residential Address Information";
    private static final String YML_SUB_HEADER_4 = "Employment Information";
    private static final String YML_SUB_HEADER_5 = "Passport Visa DBS Information";

    private static final String VALUE_TEXT = "value";
    private static final String CLASS_TEXT = "class";
    private String nationality;

    private static final Logger logger = LogManager.getLogger(WorkerBasicInformationActions.class);

    public WorkerBasicInformationActions() {
        basicInfo = new WorkerBasicInformationPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), basicInfo);
        } catch (BasePage.WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
    }

    public void enterWorkerBasicInformationData() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Creating a worker >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();

        // Retrieve the current increment value for the worker (from the file)
        int incrementValue = DataConfigurationReader.getCurrentIncrementValue(ENTITY);

        BasePage.genericWait(2000);
        enterAgencyInformation(YML_FILE, ADD);
        enterPersonalInformation(YML_FILE, ADD);
        enterResidentialAddressInformation(YML_FILE, ADD);
        enterEmploymentInformation(YML_FILE, ADD);
        enterPassportAndOtherInformation(YML_FILE, ADD);
        enterTravelInformation(YML_FILE, ADD);

        BasePage.clickWithJavaScript(basicInfo.saveButton);

        // After successfully entering the basic information, update the increment value in the file
        DataConfigurationReader.storeNewIncrementValue(ENTITY);

        // Store the increment value in GlobalVariables for reuse in other steps
        GlobalVariables.setVariable("worker_incrementValue", incrementValue);
    }

    private void enterTravelInformation(String ymlFile, String subHeader) {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Travel & Other Information >>>>>>>>>>>>>>>>>>>>");
        expandSubSection(basicInfo.travelInformationHeader, basicInfo.travelInformationHeaderExpandIcon);
        String travelDistance = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                "Travel Information", subHeader, "PreferredTravelDistance");
        BasePage.clearAndEnterTexts(basicInfo.travelDistance, travelDistance);

        String isDrivingLicenceAvailable = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile,
                YML_HEADER, "Travel Information", subHeader, "DrivingLicence");
        assert isDrivingLicenceAvailable != null;
        if (isDrivingLicenceAvailable.equalsIgnoreCase("Yes")) {
            BasePage.clickWithJavaScript((basicInfo.hasDrivingLicenseRadioButton).get(0));
        } else {
            BasePage.clickWithJavaScript((basicInfo.hasDrivingLicenseRadioButton).get(1));
        }
    }

    private void enterPassportAndOtherInformation(String ymlFile, String subHeader) {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Passport, Visa & DBS Information >>>>>>>>>>>>>>>>>>>>");
        expandSubSection(basicInfo.passportInformationHeader, basicInfo.passportInformationHeaderExpandIcon);

        // scroll down to travel & other information
        BasePage.scrollToWebElement(basicInfo.travelInformationHeader);

        if (!nationality.equalsIgnoreCase("British")) {
            String passportNumber = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                    YML_SUB_HEADER_5, subHeader, "PassportNumber");
            BasePage.clearAndEnterTexts(basicInfo.passportNumber, passportNumber);

            String issuedCountry = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                    YML_SUB_HEADER_5, subHeader, "IssuedCountry");
            BasePage.clickWithJavaScript(basicInfo.issuedCountryDropdown);
            BasePage.waitUntilElementClickable(basicInfo.getDropdownOptionXpath(issuedCountry), 30);
            BasePage.clickWithJavaScript(basicInfo.getDropdownOptionXpath(issuedCountry));

            String visaType = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                    YML_SUB_HEADER_5, subHeader, "VisaType");
            BasePage.clickWithJavaScript(basicInfo.visaTypeDropdown);
            BasePage.waitUntilElementClickable(basicInfo.getDropdownOptionXpath(visaType), 30);
            BasePage.clickWithJavaScript(basicInfo.getDropdownOptionXpath(visaType));

            // to view maximum weekly hours field
            BasePage.clickWithJavaScript(basicInfo.passportNumber);
            String maximumHours = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                    YML_SUB_HEADER_5, subHeader, "MaximumWeeklyHours");
            BasePage.waitUntilElementPresent(basicInfo.maximumWeeklyHours, 20);
            BasePage.clearAndEnterTexts(basicInfo.maximumWeeklyHours, maximumHours);

            assert visaType != null;
            if (visaType.equalsIgnoreCase("Health and care worker visa") || visaType.equalsIgnoreCase(
                    "Skilled Worker")) {
                String companyName = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                        YML_SUB_HEADER_5, subHeader, "CompanyName");
                BasePage.clearAndEnterTexts(basicInfo.companyNameCosDocument, companyName);
            }

            String shareCode = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                    YML_SUB_HEADER_5, subHeader, "ShareCode");
            assert shareCode != null;
            if (!shareCode.equalsIgnoreCase("same")) {
                BasePage.clearAndEnterTexts(basicInfo.shareCode, shareCode);
            }
        }

        String nationalInsuranceNumber = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                YML_SUB_HEADER_5, subHeader, "NationalInsuranceNumber");
        assert nationalInsuranceNumber != null;
        if (!nationalInsuranceNumber.equalsIgnoreCase("same")) {
            BasePage.clearAndEnterTexts(basicInfo.nationalInsuranceNumber, nationalInsuranceNumber);
        }

        String dbs = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, YML_SUB_HEADER_5,
                subHeader, "DbsCertificateNumber");
        BasePage.clearAndEnterTexts(basicInfo.dbsCertificateNumber, dbs);

        String isConviction = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                YML_SUB_HEADER_5, subHeader, "IsThereAnyConviction");
        assert isConviction != null;
        if (isConviction.equalsIgnoreCase("Yes")) {
            BasePage.clickWithJavaScript((basicInfo.hasConvictionRadioButton).get(0));
        } else {
            BasePage.clickWithJavaScript((basicInfo.hasConvictionRadioButton).get(1));
        }
    }

    private void enterEmploymentInformation(String ymlFile, String subHeader) {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Employment Information >>>>>>>>>>>>>>>>>>>>");
        expandSubSection(basicInfo.employmentInformationHeader, basicInfo.employmentInformationHeaderExpandIcon);

        String workerType = fetchWorkerType(ymlFile, subHeader);
        selectWorkerType(workerType);

        BasePage.genericWait(2000); // Pause to ensure skills are loaded

        if (subHeader.equalsIgnoreCase(ADD)) {
            handleSkillsForAdd(ymlFile);
            handleRegulatorySettingsForAdd(ymlFile);

        } else if (subHeader.equalsIgnoreCase(UPDATE)) {
            handleSkillsForUpdate();
            handleRegulatorySettingsForUpdate();
        }

        String employmentType = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                YML_SUB_HEADER_4, subHeader, "WorkerEmploymentType");
        if (Objects.requireNonNull(employmentType).equalsIgnoreCase("paye")) {
            BasePage.clickWithJavaScript(basicInfo.payeEmploymentTypeRadio);
            BasePage.waitUntilElementPresent(basicInfo.payrollReferenceNumberInput, 10);
            String text = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                    YML_SUB_HEADER_4, subHeader, "WorkerEmploymentTypeValue");
            BasePage.clearAndEnterTexts(basicInfo.payrollReferenceNumberInput, text);
        } else if (employmentType.equalsIgnoreCase("umbrella")) {
            BasePage.clickWithJavaScript(basicInfo.umbrellaEmploymentTypeRadio);
            BasePage.waitUntilElementPresent(basicInfo.umbrellaCompanyNameInput, 10);
            String text = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                    YML_SUB_HEADER_4, subHeader, "WorkerEmploymentTypeValue");
            BasePage.clearAndEnterTexts(basicInfo.umbrellaCompanyNameInput, text);
        } else {
            BasePage.clickWithJavaScript(basicInfo.otherEmploymentTypeRadio);
        }
    }

    private String fetchWorkerType(String ymlFile, String subHeader) {
        return DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, YML_SUB_HEADER_4,
                subHeader, "WorkerType");
    }

    private void selectWorkerType(String workerType) {
        BasePage.clickWithJavaScript(basicInfo.workerTypeDropdown);
        BasePage.waitUntilElementClickable(basicInfo.getWorkerTypeDropdownOptionXpath(workerType), 20);
        BasePage.clickWithJavaScript(basicInfo.getWorkerTypeDropdownOptionXpath(workerType));
    }

    private void handleSkillsForAdd(String ymlFile) {
        String[] skills = Objects.requireNonNull(DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile,
                YML_HEADER, YML_SUB_HEADER_4, ADD, "Skills")).split(",");
        BasePage.clickWithJavaScript(basicInfo.workerSkillsDropdown);
        BasePage.waitUntilElementClickable(basicInfo.getDropdownOptionXpath(skills[0]), 20);
        for (String skill : skills) {
            BasePage.clickWithJavaScript(basicInfo.getDropdownOptionXpath(skill));
        }
    }

    private void handleSkillsForUpdate() {
        Set<String> desiredSkills = fetchDesiredSkills();
        BasePage.clickWithJavaScript(basicInfo.workerSkillsDropdown);

        List<String> alreadySelectedSkills = fetchAlreadySelectedOptions();
        deselectUnwantedOptions(alreadySelectedSkills, desiredSkills);
        selectNewOptions(alreadySelectedSkills, desiredSkills);
    }

    private Set<String> fetchDesiredSkills() {
        return new HashSet<>(Arrays.asList(
                Objects.requireNonNull(DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE,
                        YML_HEADER, YML_SUB_HEADER_4, UPDATE, "Skills")).split(",")
        ));
    }

    private void handleRegulatorySettingsForAdd(String ymlFile) {
        String[] settings = Objects.requireNonNull(DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile,
                YML_HEADER, YML_SUB_HEADER_4, ADD, "RegulatorySettings")).split(",");
        BasePage.clickWithJavaScript(basicInfo.regulatorySettingsDropdown);
        BasePage.waitUntilElementClickable(basicInfo.getDropdownOptionXpath(settings[0]), 20);
        for (String setting : settings) {
            BasePage.clickWithJavaScript(basicInfo.getDropdownOptionXpath(setting));
        }
    }

    private void handleRegulatorySettingsForUpdate() {
        Set<String> desiredSettings = fetchDesiredRegulatorySettings();
        BasePage.clickWithJavaScript(basicInfo.regulatorySettingsDropdown);

        List<String> alreadySelectedSettings = fetchAlreadySelectedOptions();
        deselectUnwantedOptions(alreadySelectedSettings, desiredSettings);
        selectNewOptions(alreadySelectedSettings, desiredSettings);
    }

    private Set<String> fetchDesiredRegulatorySettings() {
        return new HashSet<>(Arrays.asList(
                Objects.requireNonNull(DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE,
                        YML_HEADER, YML_SUB_HEADER_4, UPDATE, "RegulatorySettings")).split(",")
        ));
    }

    private List<String> fetchAlreadySelectedOptions() {
        List<String> alreadySelectedOptions = genericUtils.getSelectedValues(basicInfo.alreadySelectedOptions);
        return alreadySelectedOptions != null ? alreadySelectedOptions : new ArrayList<>();
    }

    private void deselectUnwantedOptions(List<String> alreadySelectedOptions, Set<String> desiredOptions) {
        for (String skill : alreadySelectedOptions) {
            if (!desiredOptions.contains(skill)) {
                BasePage.clickWithJavaScript(basicInfo.getDropdownOptionXpath(skill));
            }
        }
    }

    private void selectNewOptions(List<String> alreadySelectedOptions, Set<String> desiredOptions) {
        for (String option : desiredOptions) {
            if (!alreadySelectedOptions.contains(option)) {
                BasePage.clickWithJavaScript(basicInfo.getDropdownOptionXpath(option));
            }
        }
    }

    private void enterResidentialAddressInformation(String ymlFile, String subHeader) {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Residential Address Information >>>>>>>>>>>>>>>>>>>>");
        expandSubSection(basicInfo.residentialAddressInformationHeader, basicInfo.
                residentialAddressInformationHeaderExpandIcon);
        String country = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                YML_SUB_HEADER_3, subHeader, "Country");
        BasePage.clickWithJavaScript(basicInfo.countryDropdown);
        BasePage.clickWithJavaScript(basicInfo.getDropdownOptionXpath(country));

        //enter postcode and select a valid address
        String postcode = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                YML_SUB_HEADER_3, subHeader, "PostCode");
        BasePage.clickWithJavaScript(basicInfo.postcode);
        if (Objects.requireNonNull(country).equalsIgnoreCase("United Kingdom")) {
            genericUtils.fillAddress(basicInfo.postcode, postcode, 190);
        } else {
            BasePage.clearAndEnterTexts(basicInfo.postcode, postcode);
            String residentialAddress = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                 YML_SUB_HEADER_3, subHeader, "ResidentialAddress");
            BasePage.clearAndEnterTexts(basicInfo.residentialAddressInput, residentialAddress);
        }

        //scroll down to employment information
        BasePage.scrollToWebElement(basicInfo.employmentInformationHeader);

        String fromDateMonthYear = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                YML_SUB_HEADER_3, subHeader, "From");
        BasePage.clickWithJavaScript(basicInfo.livingFrom);
        genericUtils.selectDateFromCalendarPopup(fromDateMonthYear);

        if (country.equalsIgnoreCase("United Kingdom")) {
            String currentlyLivingThisAddress = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile,
                    YML_HEADER, YML_SUB_HEADER_3, subHeader, "CurrentlyLivingInThisAddress");
            assert currentlyLivingThisAddress != null;
            if(currentlyLivingThisAddress.equalsIgnoreCase("Yes")) {
                String text = BasePage.getAttributeValue(basicInfo.isCurrentlyLivingCheckboxChecked, CLASS_TEXT);
                if (!text.contains("checked")) {
                    BasePage.clickWithJavaScript(basicInfo.isCurrentlyLivingCheckbox);
                    BasePage.waitUntilElementDisappeared(basicInfo.livingTo, 20);
                }
            } else {
                String toDateMonthYear = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                        YML_SUB_HEADER_3, subHeader, "To");
                BasePage.clickWithJavaScript(basicInfo.livingTo);
                genericUtils.selectDateFromCalendarPopup(toDateMonthYear);
            }
        } else {
            String toDateMonthYear = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                    YML_SUB_HEADER_3, subHeader, "To");
            BasePage.clickWithJavaScript(basicInfo.livingTo);
            genericUtils.selectDateFromCalendarPopup(toDateMonthYear);
        }

        // verify duration in address
        verifyDurationInAddress(ymlFile, subHeader);

        //upload proof of address document
        String document = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                YML_SUB_HEADER_3, subHeader, "ProofOfAddressDocument");
        String absoluteFilePath1 = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                + File.separator + "resources" + File.separator + "Upload" + File.separator + "Worker" + File.separator
                + document;
        BasePage.uploadFile(basicInfo.proofOfAddressDocument, absoluteFilePath1);
        //wait until document is uploaded
        waitUntilDocumentUploaded();
    }

    private void enterPersonalInformation(String ymlFile, String subHeader) {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Personal Information >>>>>>>>>>>>>>>>>>>>");
        //upload a logo
        String workerImage = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                YML_SUB_HEADER_2, subHeader, "WorkerLogo");
        String absoluteFilePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                + File.separator + "resources" + File.separator + "Upload" + File.separator + "Worker" + File.separator
                + workerImage;
        BasePage.clickWithJavaScript(basicInfo.uploadLogo);
        BasePage.uploadFile(basicInfo.uploadLogoInput, absoluteFilePath);
        BasePage.waitUntilElementDisplayed(basicInfo.imageSaveButton, 60);
        BasePage.clickWithJavaScript(basicInfo.imageSaveButton);

        String firstName = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                YML_SUB_HEADER_2, subHeader, "FirstName");
        BasePage.clearAndEnterTexts(basicInfo.firstName, firstName);

        String lastName = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                YML_SUB_HEADER_2, subHeader, "LastName");
        BasePage.clearAndEnterTexts(basicInfo.lastName, lastName);

        String alsoKnownAs = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                YML_SUB_HEADER_2, subHeader, "AlsoKnownAs");
        BasePage.clearAndEnterTexts(basicInfo.alsoKnownAs, alsoKnownAs);

        String workerEmail = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                YML_SUB_HEADER_2, subHeader, "WorkerEmailAddress");
        BasePage.clearAndEnterTexts(basicInfo.email, workerEmail);

        BasePage.scrollToWebElement(basicInfo.passportInformationHeader);

        genericUtils.fillPhoneNumber(ENTITY, ymlFile, basicInfo.phoneNumberInput, YML_HEADER, YML_SUB_HEADER_2,
                subHeader, "PhoneNumberType");
        genericUtils.fillPhoneNumber(ENTITY, ymlFile, basicInfo.phoneNumberInput, YML_HEADER, YML_SUB_HEADER_2,
                subHeader, "PhoneNumber");

        String dateOfBirth = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                YML_SUB_HEADER_2, subHeader, "DateOfBirth");
        BasePage.clickWithJavaScript(basicInfo.dateOfBirth);
        genericUtils.selectDateFromCalendarPopup(dateOfBirth);

        String gender = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, YML_SUB_HEADER_2,
                subHeader, "Gender");
        assert gender != null;
        if (gender.equalsIgnoreCase("Male")) {
            BasePage.clickWithJavaScript((basicInfo.genderRadioButton).get(0));
        } else if (gender.equalsIgnoreCase("Female")) {
            BasePage.clickWithJavaScript((basicInfo.genderRadioButton).get(1));
        } else {
            BasePage.clickWithJavaScript((basicInfo.genderRadioButton).get(2));
        }

        nationality = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER, YML_SUB_HEADER_2,
                subHeader, "Nationality");
        BasePage.clickWithJavaScript(basicInfo.nationalityDropdown);
        BasePage.clickWithJavaScript(basicInfo.getDropdownOptionXpath(nationality));
    }

    private void enterAgencyInformation(String ymlFile, String subHeader) {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Agency Information >>>>>>>>>>>>>>>>>>>>");
        // Retrieve the latest agency increment value
        int agencyIncrementValue = DataConfigurationReader.getCurrentIncrementValue("agency");

        // Read agency name from YAML and replace <agencyIncrement> placeholder
        String agencyTemplate = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile, YML_HEADER,
                "Agency Information", subHeader, "Agency");
        assert agencyTemplate != null;
        String agency = agencyTemplate.replace("<agencyIncrement>", String.valueOf(agencyIncrementValue));

        BasePage.genericWait(3000);
        BasePage.clickWithJavaScript(basicInfo.agencyDropdown);
        By by = By.xpath(basicInfo.getDropdownOptionXpath(agency));
        BasePage.waitUntilVisibilityOfElementLocated(by, 60);
        BasePage.scrollToWebElement(basicInfo.getDropdownOptionXpath(agency));
        BasePage.clickWithJavaScript(basicInfo.getDropdownOptionXpath(agency));

        BasePage.genericWait(2000);
        String agencyLocation = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_AGENCY_FILE,
                "Agency Business Location", UPDATE, "BusinessLocation");
        BasePage.clickWithJavaScript(basicInfo.agencyLocationDropdown);
        BasePage.waitUntilElementClickable(basicInfo.getDropdownOptionXpath(agencyLocation), 30);
        selectAllAgencyLocations();
    }

    private void waitUntilDocumentUploaded() {
        BasePage.waitUntilElementPresent(basicInfo.documentUploadedSuccessMessage, 30);
        String actualInLowerCase = BasePage.getText(basicInfo.documentUploadedSuccessMessage).toLowerCase().trim();
        String expected = "Document is added";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Document uploaded success message is wrong!", actualInLowerCase, is(expectedInLowerCase));
    }

    // method to select all the options available in the Agency Location multi select dropdown
    private void selectAllAgencyLocations() {
        List<WebElement> checkboxes = BasePage.findListOfWebElements(WorkerBasicInformationPage.AGENCY_LOCATION_CHECKBOXES);
        if (!BasePage.getAttributeValue(basicInfo.agencyLocation, CLASS_TEXT).contains("selected")) {
            for (WebElement checkbox : checkboxes) {
                BasePage.clickWithJavaScript(checkbox);
            }
        }
    }

    private void expandSubSection(WebElement headerText, WebElement headerIcon) {
        if(BasePage.getAttributeValue(headerText, CLASS_TEXT).equalsIgnoreCase("collapsed")) {
            BasePage.clickWithJavaScript(headerIcon);
        }
    }

    private void verifyDurationInAddress(String ymlFile, String subHeader) {
        String fromDate = BasePage.getAttributeValue(basicInfo.livingFrom, VALUE_TEXT);
        String currentlyLivingThisAddress = DataConfigurationReader.readDataFromYmlFile(ENTITY, ymlFile,
                YML_HEADER, YML_SUB_HEADER_3, subHeader, "CurrentlyLivingInThisAddress");

        // Define the date format (e.g., 13 Nov 2019)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        String  toDate;
        LocalDate to;
        assert currentlyLivingThisAddress != null;
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

    public void createWorkerInDraftStage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Creating a worker in Draft Stage >>>>>>>>>>>>>>>>>>>>");
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Worker - Basic Information >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();

        // Retrieve the current increment value for the worker (from the file)
        int incrementValue = DataConfigurationReader.getCurrentIncrementValue(ENTITY);
        BasePage.clickWithJavaScript(basicInfo.firstName);
        enterAgencyInformation(EDIT_YML_FILE, ADD);
        enterPersonalInformation(EDIT_YML_FILE, ADD);
        enterResidentialAddressInformation(EDIT_YML_FILE, ADD);
        enterEmploymentInformation(EDIT_YML_FILE, ADD);
        enterPassportAndOtherInformation(EDIT_YML_FILE, ADD);
        enterTravelInformation(EDIT_YML_FILE, ADD);
        BasePage.genericWait(10000);

        BasePage.clickWithJavaScript(basicInfo.saveButton);
        // After successfully entering the basic information, update the increment value in the file
        DataConfigurationReader.storeNewIncrementValue(ENTITY);
        // Store the increment value in GlobalVariables for reuse in other steps
        GlobalVariables.setVariable("worker_incrementValue", incrementValue);
    }

    public void updateWorkerBasicInfo() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Updating worker - Basic info >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        clickOnUpdateProfileLink();
        enterAgencyInformation(EDIT_YML_FILE, UPDATE);
        enterPersonalInformation(EDIT_YML_FILE, UPDATE);
        enterResidentialAddressInformation(EDIT_YML_FILE, UPDATE);
        enterEmploymentInformation(EDIT_YML_FILE, UPDATE);
        enterPassportAndOtherInformation(EDIT_YML_FILE, UPDATE);
        enterTravelInformation(EDIT_YML_FILE, UPDATE);
        BasePage.clickWithJavaScript(basicInfo.saveButton);
        verifyUpdateSuccessMessage();
    }

    private void clickOnUpdateProfileLink() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Clicking on the update profile link >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.genericWait(3000);
        BasePage.waitUntilElementPresent(basicInfo.topThreeDots, 30);
        BasePage.mouseHoverAndClick(basicInfo.topThreeDots, basicInfo.updateProfileLink,
                WorkerBasicInformationPage.updateProfileLinkChildElement);
        BasePage.genericWait(3000);
        BasePage.waitUntilElementClickable(basicInfo.saveButton, 30);
    }

    private void verifyUpdateSuccessMessage() {
        BasePage.waitUntilElementPresent(basicInfo.successMessage, 30);
        String actualInLowerCase = BasePage.getText(basicInfo.successMessage).toLowerCase().trim();
        String expected = "Profile updated successfully.";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Worker profile is not updated!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(basicInfo.successMessage, 20);
    }

    // get auto generated worker id and save it on the memory
    private void getWorkerId() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Reading auto generated worker id >>>>>>>>>>>>>>>>>>>>");
        BasePage.genericWait(6000);
        BasePage.waitUntilElementPresent(basicInfo.workerId, 90);
        String headerText = BasePage.getText(basicInfo.workerId).trim();
        String workerId = headerText.split("\n")[0];
        GlobalVariables.setVariable("workerId", workerId);
    }

    public void getGeneratedWorkerId() {
        getWorkerId();
    }
}
