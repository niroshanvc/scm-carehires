package com.carehires.actions.jobs;


import com.carehires.pages.jobs.JobsLandingPage;
import com.carehires.utils.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

public class JobsLandingActions {
    private final JobsLandingPage jobsLandingPage;
    private final JobDetailsActions detailsActions = new JobDetailsActions();
    private final JobPreferencesActions preferencesActions = new JobPreferencesActions();
    private final JobSummaryActions summaryActions = new JobSummaryActions();

    private static final Logger logger = LogManager.getFormatterLogger(JobsLandingActions.class);

    public JobsLandingActions() {
        jobsLandingPage = new JobsLandingPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), jobsLandingPage);
        } catch (BasePage.WebDriverInitializationException e) {
            logger.error("Error while initializing Job Landing Page elements: %s", e.getMessage());
        }
    }

    public void clickOnPostJobButton() {
        clickPostJob();
    }

    public void createNewJob() {
        clickPostJob();
        detailsActions.enterJobDetails();
        preferencesActions.enterJobPreferences();
        summaryActions.enterJobSummary();
    }

    private void clickPostJob() {
        logger.info(" --------------------------- Moving to Post Job Page ---------------------------");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementClickable(jobsLandingPage.postJobButton, 60);
        BasePage.clickWithJavaScript(jobsLandingPage.postJobButton);
    }
}
