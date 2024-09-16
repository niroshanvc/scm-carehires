package com.carehires.actions;

import com.carehires.pages.CreateAgencyStaffPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.openqa.selenium.support.PageFactory;

public class CreateAgencyStaffActions {

    CreateAgencyStaffPage staffPage;

    private static final String YML_FILE = "agency-create";
    private static final String YML_HEADER = "Staff";

    public CreateAgencyStaffActions() {
        staffPage = new CreateAgencyStaffPage();
        PageFactory.initElements(BasePage.getDriver(), staffPage);
    }

    public void addStaff() {
        String location = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "Location");

        String workerType = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "WorkerType");
        String noOfWorkers = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "NoOfWorkers");
        String[] workerSkills = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "WorkerSkills").split(",");
        String monthlyHoursAvailable = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "MonthlyHoursAvailable");
        String minChargeHourlyRate = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "MinChargeHourlyRate");
        String employeeType = DataConfigurationReader.readDataFromYmlFile(YML_FILE, YML_HEADER, "EmployeeType");
    }
}
