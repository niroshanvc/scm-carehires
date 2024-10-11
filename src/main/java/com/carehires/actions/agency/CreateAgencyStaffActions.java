package com.carehires.actions.agency;

import com.carehires.pages.agency.CreateAgencyStaffPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CreateAgencyStaffActions {

    CreateAgencyStaffPage staffPage;

    private static final String ENTITY = "agency";
    private static final String YML_FILE = "agency-create";
    private static final String YML_HEADER = "Staff";
    private static final String LOCATION_YML_HEADER = "Location";
    private static final Logger logger = LogManager.getFormatterLogger(CreateAgencyStaffActions.class);

    public CreateAgencyStaffActions() {
        staffPage = new CreateAgencyStaffPage();
        PageFactory.initElements(BasePage.getDriver(), staffPage);
    }

    public void addStaff() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering staff information >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.clickWithJavaScript(staffPage.addNewButton);

        //select a location
        String location = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, LOCATION_YML_HEADER, "BusinessLocation");
        BasePage.clickWithJavaScript(staffPage.locationDropdown);
        BasePage.genericWait(1000);
        BasePage.clickWithJavaScript(getDropdownOptionXpath(location));

        //select a worker type
        String workerType = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "WorkerType");
        BasePage.clickWithJavaScript(staffPage.workerTypeDropdown);
        BasePage.genericWait(1000);
        BasePage.clickWithJavaScript(getDropdownOptionXpath(workerType));

        String noOfWorkers = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "NoOfWorkers");
        BasePage.typeWithStringBuilder(staffPage.numberOfWorkers, noOfWorkers);

        //select worker skills
        String[] workerSkills = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "WorkerSkills").split(",");
        BasePage.clickWithJavaScript(staffPage.workerSkills);
        BasePage.genericWait(1000);
        for (String skill : workerSkills) {
            BasePage.clickWithJavaScript(getWorkerSkillXpath(skill));
        }

        String monthlyHoursAvailable = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "MonthlyHoursAvailable");
        BasePage.clickWithJavaScript(staffPage.monthlyHoursAvailable);
        BasePage.typeWithStringBuilder(staffPage.monthlyHoursAvailable, monthlyHoursAvailable);

        String minChargeHourlyRate = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "MinChargeHourlyRate");
        BasePage.typeWithStringBuilder(staffPage.minChargeHourlyRate, minChargeHourlyRate);

        String employeeType = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "EmployeeType");
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
        String expected = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, LOCATION_YML_HEADER, "BusinessLocation");
        assertThat("Staff is not added", actual, is(expected));
    }

    private String getDropdownOptionXpath(String city) {
        return String.format("//nb-option[contains(text(),'%s')]", city);
    }

    private String getWorkerSkillXpath(String skill) {
        return String.format("//nb-option[contains(@class,'multiple') and (contains(text(), '%s'))]", skill);
    }
}
