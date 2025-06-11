package com.carehires.steps.tasks;

import com.carehires.actions.tasks.TimesheetApprovalsActions;
import io.cucumber.java.en.And;

public class TimesheetApprovalsSteps {

    private TimesheetApprovalsActions timesheetApprovals = new TimesheetApprovalsActions();

    @And("Provider User approves timesheet")
    public void clickOnApproveButton() {
        timesheetApprovals.clickOnApproveTimesheetButton();
    }
}
