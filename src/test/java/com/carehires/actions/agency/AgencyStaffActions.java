package com.carehires.actions.agency;


import com.carehires.common.GlobalVariables;
import com.carehires.pages.agency.AgencyStaffPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class AgencyStaffActions {

    private final AgencyStaffPage staffPage;
    private static final AgencyNavigationMenuActions navigationMenu = new AgencyNavigationMenuActions();

    private static final String ENTITY = "agency";
    private static final String YML_FILE = "agency-create";
    private static final String EDIT_YML_FILE = "agency-edit";
    private static final String YML_HEADER = "Staff";
    private static final String LOCATION_YML_HEADER = "Agency Business Location";
    private static final String ADD = "Add";
    private static final String UPDATE = "Update";
    private static final String BUSINESS_LOCATION = "BusinessLocation";
    private static final String WORKER_SKILLS = "WorkerSkills";
    private static final Logger logger = LogManager.getFormatterLogger(AgencyStaffActions.class);

    public AgencyStaffActions() {
        staffPage = new AgencyStaffPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), staffPage);
        } catch (BasePage.WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
    }

    public void addStaff() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering staff information >>>>>>>>>>>>>>>>>>>>");
        // Retrieve the incremented value
        Integer incrementValue = GlobalVariables.getVariable("agency_incrementValue", Integer.class);

        // Log the retrieved value
        logger.info("Retrieved agency increment value in Staff: %s", incrementValue);

        // Check for null or default value
        if (incrementValue == null) {
            throw new NullPointerException("Increment value for agency is not set in GlobalVariables.");
        }

        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.clickWithJavaScript(staffPage.addNewButton);

        //select a location
        String location = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, LOCATION_YML_HEADER, ADD, BUSINESS_LOCATION);
        BasePage.genericWait(3000);
        BasePage.clickWithJavaScript(staffPage.locationDropdown);
        BasePage.genericWait(1000);
        BasePage.clickWithJavaScript(staffPage.getDropdownOptionXpath(location));

        //select a worker type
        String workerType = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, ADD, "WorkerType");
        BasePage.clickWithJavaScript(staffPage.workerTypeDropdown);
        BasePage.genericWait(1000);
        BasePage.clickWithJavaScript(staffPage.getDropdownOptionXpath(workerType));

        String noOfWorkers = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, ADD, "NoOfWorkers");
        BasePage.typeWithStringBuilder(staffPage.numberOfWorkers, noOfWorkers);

        //select worker skills
        String[] workerSkills = Objects.requireNonNull(DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, ADD, WORKER_SKILLS)).split(",");
        BasePage.genericWait(3000);
        BasePage.clickWithJavaScript(staffPage.workerSkills);
        for (String skill : workerSkills) {
            BasePage.clickWithJavaScript(staffPage.getWorkerSkillXpath(skill));
        }

        String monthlyHoursAvailable = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, ADD, "MonthlyHoursAvailable");
        BasePage.clickWithJavaScript(staffPage.monthlyHoursAvailable);
        BasePage.typeWithStringBuilder(staffPage.monthlyHoursAvailable, monthlyHoursAvailable);

        String minChargeHourlyRate = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, ADD, "MinChargeHourlyRate");
        BasePage.typeWithStringBuilder(staffPage.minChargeHourlyRate, minChargeHourlyRate);

        String employeeType = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, ADD, "EmployeeType");
        assert employeeType != null;
        if (employeeType.equalsIgnoreCase("Paye")) {
            BasePage.clickWithJavaScript((staffPage.employeeType.get(0)));
        } else {
            BasePage.clickWithJavaScript((staffPage.employeeType.get(1)));
        }

        //click on Add
        BasePage.genericWait(5000);
        BasePage.clickWithJavaScript(staffPage.addButton);


        //verify if staff is added
        isStaffAdded();

        BasePage.clickWithJavaScript(staffPage.updateButton);
        BasePage.waitUntilElementClickable(staffPage.nextButton, 90);
        BasePage.clickWithJavaScript(staffPage.nextButton);
    }

    private void isStaffAdded() {
        BasePage.waitUntilElementPresent(staffPage.locationName, 60);
        String actual = BasePage.getText(staffPage.locationName);
        String expected = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, LOCATION_YML_HEADER, ADD, BUSINESS_LOCATION);
        assertThat("Staff is not added", actual, is(expected));
    }

    private void verifySuccessMessage() {
        BasePage.waitUntilElementPresent(staffPage.successMessage, 90);
        String actualInLowerCase = BasePage.getText(staffPage.successMessage).toLowerCase().trim();
        String expected = "Record created successfully.";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Staff information saved success message is wrong!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(staffPage.successMessage, 20);
    }

    public void editStaffData() {
        navigateToStaffOverviewPage();
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Add new Staff >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementClickable(staffPage.addNewButton, 30);

        // add new staff
        BasePage.clickWithJavaScript(staffPage.addNewButton);
        enterStaffData(ADD);
        //select worker skills
        String[] workerSkills = Objects.requireNonNull(DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, YML_HEADER, ADD, WORKER_SKILLS)).split(",");
        BasePage.clickWithJavaScript(staffPage.workerSkills);
        BasePage.genericWait(1000);
        for (String skill : workerSkills) {
            BasePage.clickWithJavaScript(staffPage.getWorkerSkillXpath(skill));
        }

        //click on Add
        BasePage.genericWait(5000);
        BasePage.clickWithJavaScript(staffPage.addButton);
        verifySuccessMessage();

        // edit already existing staff
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Edit staff data >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementDisplayed(staffPage.editDetailsIcon, 30);
        BasePage.clickWithJavaScript(staffPage.editDetailsIcon);
        enterStaffData(UPDATE);
        //select worker skills
        updateWorkerSkills();
        BasePage.clickWithJavaScript(staffPage.updateButton);
        verifyStaffUpdateSuccessMessage();
        BasePage.waitUntilElementClickable(staffPage.nextButton, 90);
        BasePage.clickWithJavaScript(staffPage.nextButton);
    }

    private void navigateToStaffOverviewPage() {
        navigationMenu.gotoStaffPage();
        BasePage.genericWait(3000);
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Navigating to Agency Staff Overview >>>>>>>>>>>>>>>>>>>>");
        if (BasePage.isElementDisplayed(staffPage.updateButton)) {
            BasePage.clickWithJavaScript(staffPage.updateButton);
        }
        BasePage.waitUntilElementPresent(staffPage.nextButton, 30);
        BasePage.genericWait(2000);
    }

    private void verifyStaffUpdateSuccessMessage() {
        BasePage.waitUntilElementPresent(staffPage.successMessage, 90);
        String actualInLowerCase = BasePage.getText(staffPage.successMessage).toLowerCase().trim();
        String expected = "Record updated successfully.";
        String expectedInLowerCase = expected.toLowerCase().trim();
        assertThat("Staff information update success message is wrong!", actualInLowerCase, is(expectedInLowerCase));
        BasePage.waitUntilElementDisappeared(staffPage.successMessage, 20);
    }

    private void enterStaffData(String headers) {
        //select a location
        BasePage.waitUntilElementDisplayed(staffPage.locationDropdown, 30);
        String location = DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, LOCATION_YML_HEADER, UPDATE, BUSINESS_LOCATION);
        BasePage.clickWithJavaScript(staffPage.locationDropdown);
        BasePage.clickWithJavaScript(staffPage.getDropdownOptionXpath(location));

        //select a worker type
        String workerType = DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, YML_HEADER, headers, "WorkerType");
        BasePage.clickWithJavaScript(staffPage.workerTypeDropdown);
        BasePage.clickWithJavaScript(staffPage.getDropdownOptionXpath(workerType));

        String noOfWorkers = DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, YML_HEADER, headers, "NoOfWorkers");
        BasePage.clearAndEnterTexts(staffPage.numberOfWorkers, noOfWorkers);

        String monthlyHoursAvailable = DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, YML_HEADER, headers, "MonthlyHoursAvailable");
        BasePage.clickWithJavaScript(staffPage.monthlyHoursAvailable);
        BasePage.clearAndEnterTexts(staffPage.monthlyHoursAvailable, monthlyHoursAvailable);

        String minChargeHourlyRate = DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, YML_HEADER, headers, "MinChargeHourlyRate");
        BasePage.clearAndEnterTexts(staffPage.minChargeHourlyRate, minChargeHourlyRate);

        String employeeType = DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, YML_HEADER, headers, "EmployeeType");
        assert employeeType != null;
        if (employeeType.equalsIgnoreCase("Paye")) {
            BasePage.clickWithJavaScript((staffPage.employeeType.get(0)));
        } else {
            BasePage.clickWithJavaScript((staffPage.employeeType.get(1)));
        }
    }

    private void updateWorkerSkills() {
        // Read skills from the YAML file and convert them to a Set for easy comparison
        Set<String> desiredSkills = new HashSet<>(Arrays.asList(
                DataConfigurationReader.readDataFromYmlFile(ENTITY, EDIT_YML_FILE, YML_HEADER, UPDATE, WORKER_SKILLS).split(",")
        ));
        // Click to open the worker skills dropdown
        BasePage.clickWithJavaScript(staffPage.workerSkills);
        // Get all currently selected skills, default to an empty list if null
        List<String> selectedSkills = getCurrentlySelectedSkills();
        if (selectedSkills == null) {
            selectedSkills = new ArrayList<>();
        }
        // Deselect skills that are not in the desired list
        for (String skill : selectedSkills) {
            if (!desiredSkills.contains(skill)) {
                BasePage.clickWithJavaScript(staffPage.getWorkerSkillXpath(skill));
            }
        }
        // Select skills that are in the desired list but not currently selected
        for (String skill : desiredSkills) {
            if (!selectedSkills.contains(skill)) {
                BasePage.clickWithJavaScript(staffPage.getWorkerSkillXpath(skill));
            }
        }
    }

    private List<String> getCurrentlySelectedSkills() {
        // Retrieve elements representing selected skills
        List<WebElement> selectedSkillElements = staffPage.alreadySelectedWorkerSkills;

        List<String> selectedSkills = new ArrayList<>();
        for (WebElement element : selectedSkillElements) {
            selectedSkills.add(element.getText()); // Assuming the text of each element represents the skill name
        }
        return selectedSkills;
    }
}
