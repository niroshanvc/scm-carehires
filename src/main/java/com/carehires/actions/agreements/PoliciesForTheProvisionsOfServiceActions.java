package com.carehires.actions.agreements;

import com.carehires.pages.agreements.PoliciesForTheProvisionsOfServicePage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

public class PoliciesForTheProvisionsOfServiceActions {

    PoliciesForTheProvisionsOfServicePage policiesPage;
    private static final String ENTITY = "agreement";
    private static final String YML_FILE = "agreement-create";
    private static final String YML_HEADER = "Policies for the Provisions of Service";
    private static final String ADD = "Add";
    private static final String EDIT_YML_FILE = "agreement-edit";

    private static final Logger logger = LogManager.getLogger(PoliciesForTheProvisionsOfServiceActions.class);

    public PoliciesForTheProvisionsOfServiceActions() {
        policiesPage = new PoliciesForTheProvisionsOfServicePage();
        try {
            PageFactory.initElements(BasePage.getDriver(), policiesPage);
        } catch (Exception e) {
            logger.error("Error while initializing Policies for the Provisions of Service Page elements: {}", e.getMessage());
        }
    }

    public void enterPolicies() {
        BasePage.waitUntilPageCompletelyLoaded();
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Entering Cancellation Policy Info >>>>>>>>>>>>>>>>>>>>");
        String billingCycle = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Billing Cycle");
        assert billingCycle != null;
        if (billingCycle.equalsIgnoreCase("weekly")) {
            BasePage.clickWithJavaScript(policiesPage.weeklyBillingCycle);
        }

        String creditPeriodStr = DataConfigurationReader.readDataFromYmlFile(ENTITY, YML_FILE, YML_HEADER, "Credit Period");
        assert creditPeriodStr != null;
        int creditPeriod = Integer.parseInt(creditPeriodStr);
        switch(creditPeriod) {
            case 7:
                BasePage.clickWithJavaScript(policiesPage.sevenDaysCreditPeriod);
                break;
            case 14:
                BasePage.clickWithJavaScript(policiesPage.fourteenDaysCreditPeriod);
                break;
            case 21:
                BasePage.clickWithJavaScript(policiesPage.twentyOneDaysCreditPeriod);
                break;
            case 28:
                BasePage.clickWithJavaScript(policiesPage.twentyEightDaysCreditPeriod);
                break;
            case 30:
                BasePage.clickWithJavaScript(policiesPage.thirtyDaysCreditPeriod);
                break;
            default:
                throw new IllegalArgumentException("Invalid credit period entered: " + creditPeriodStr);
        }

        BasePage.clickWithJavaScript(policiesPage.timesheetApprovalCheckbox);
        BasePage.clickTabKey(policiesPage.timesheetApprovalCheckbox);
        BasePage.waitUntilDisabledAttributeDisappears(policiesPage.continueButton, 30);
        BasePage.clickWithJavaScript(policiesPage.continueButton);
    }
}
