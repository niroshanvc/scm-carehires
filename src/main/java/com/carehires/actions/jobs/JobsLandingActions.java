package com.carehires.actions.jobs;

import com.carehires.pages.jobs.JobsLandingPage;
import com.carehires.utils.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

public class JobsLandingActions {
    JobsLandingPage jobsLandingPage;

    private static final Logger logger = LogManager.getFormatterLogger(JobsLandingActions.class);

    public JobsLandingActions() {
        jobsLandingPage = new JobsLandingPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), jobsLandingPage);
        } catch (BasePage.WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
    }

    public void clickOnPostJobButton() {
        logger.info(" --------------------------- Moving to Post Job Page ---------------------------");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementClickable(jobsLandingPage.postJobButton, 60);
        BasePage.clickWithJavaScript(jobsLandingPage.postJobButton);
    }
}
