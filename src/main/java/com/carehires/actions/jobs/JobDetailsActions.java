package com.carehires.actions.jobs;

import com.carehires.pages.jobs.JobDetailsPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

public class JobDetailsActions {

    private JobDetailsPage jobDetailsPage;

    private static final String ENTITY = "job";
    private static final String YML_FILE = "job-create";
    private static final String YML_HEADER = "Job Details";
    private static final String YML_HEADER_SUB1 = "Care Provider / Site and Service Preferences";
    private static final String YML_HEADER_SUB2 = "Job duration and Recurrence";
    private static final String ADD = "Add";

    private static final Logger logger = LogManager.getLogger(JobDetailsActions.class);

    public JobDetailsActions() {
        jobDetailsPage = new JobDetailsPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), jobDetailsPage);
        } catch (BasePage.WebDriverInitializationException e) {
            logger.error("Error while initializing Job Details Page elements: {}", e.getMessage());
        }
    }

    public void enterJobDetails() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Job Details >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();

        String jobType = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Job Type");
        assert jobType != null;
        if (jobType.equalsIgnoreCase("sleep in")) {
            BasePage.clickWithJavaScript(jobDetailsPage.sleepInButton);
        }

        String careProvider = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Care Provider");
        BasePage.clickWithJavaScript(jobDetailsPage.careProviderDropdown);
        By by = By.xpath(getDropdownOptionXpath(careProvider));
        BasePage.waitUntilVisibilityOfElementLocated(by, 20);
        BasePage.scrollToWebElement(getDropdownOptionXpath(careProvider));
        BasePage.clickWithJavaScript(getDropdownOptionXpath(careProvider));
        BasePage.genericWait(2000);

        String site = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Site");
        BasePage.clickWithJavaScript(jobDetailsPage.siteDropdown);
        By bySite = By.xpath(getDropdownOptionXpath(careProvider));
        BasePage.waitUntilVisibilityOfElementLocated(bySite, 30);
        BasePage.scrollToWebElement(getDropdownOptionXpath(site));
        BasePage.clickWithJavaScript(getDropdownOptionXpath(site));

        String using = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Using");


        String workerType = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Worker Type");
        String numberOfVacancies = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Number of Vacancies");
        String startDate = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Start Date");
        String startTime = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Start Time");
        String endTime = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "End Time");
        String enableRecurrence = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Enable Recurrence");
        String breaks = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Breaks/ Intervals");
    }

    private String getDropdownOptionXpath(String option) {
        return String.format("//nb-option[contains(text(),'%s')]", option);
    }
}
