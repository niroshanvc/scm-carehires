package com.carehires.actions.tasks;

import com.carehires.pages.tasks.TimesheetApprovalsPage;
import com.carehires.utils.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

public class TimesheetApprovalsActions {

    private final TimesheetApprovalsPage timesheetApprovalsPage;

    private static final Logger logger = LogManager.getLogger(TimesheetApprovalsActions.class);

    public TimesheetApprovalsActions() {
        this.timesheetApprovalsPage = new TimesheetApprovalsPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), timesheetApprovalsPage);
        } catch (BasePage.WebDriverInitializationException e) {
            logger.error("Error while initializing Timesheet Approvals Page elements: {}", e.getMessage());
        }
    }

    public void clickOnApproveTimesheetButton() {
        logger.info("Clicking on Approve Timesheet Button");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementClickable(timesheetApprovalsPage.approveTimesheetButton, 60);
        BasePage.clickWithJavaScript(timesheetApprovalsPage.approveTimesheetButton);
    }
}
