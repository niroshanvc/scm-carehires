package com.carehires.steps;

import com.carehires.actions.LeftSideMenuActions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;

public class LeftSideMenuSteps {
    private final LeftSideMenuActions leftSideMenuActions = new LeftSideMenuActions();

    @When("^User navigates to Agency Create page$")
    public void moveToAgencyCreatePage() {
        leftSideMenuActions.gotoAgencyCreatePage();
    }

    @And("^User navigates to Agency View page$")
    public void moveToAgencyViewPage() {
        leftSideMenuActions.gotoAgencyViewPage();
    }

    @When("^User navigates to Worker Create page$")
    public void moveToWorkerCreatePage() {
        leftSideMenuActions.gotoWorkerCreatePage();
    }

    @When("^User navigates to Worker View page$")
    public void moveToWorkerViewPage() {
        leftSideMenuActions.gotoWorkerViewPage();
    }

    @When("User navigates to Provider Create page")
    public void moveToProviderCreatePage() {
        leftSideMenuActions.gotoProviderCreatePage();
    }

    @And("^User navigates to Provider View page$")
    public void moveToProviderViewPage() {
        leftSideMenuActions.gotoProviderViewPage();
    }

    @When("User navigates to Agreement Create page")
    public void moveToAgreementCreatePage() {
        leftSideMenuActions.gotoAgreementCreatePage();
    }

    @When("User navigates to Agreement View page")
    public void moveToAgreementViewPage() {
        leftSideMenuActions.gotoAgreementViewPage();
    }

    @When("User navigates to Agreement Overview page")
    public void moveToAgreementOverviewPage() {
        leftSideMenuActions.gotoAgreementOverviewPage();
    }

    @When("User navigates to Jobs page")
    public void moveToJobsPage() {
        leftSideMenuActions.gotoJobsPage();
    }

    @And("User navigates to Settings page")
    public void moveToSettingsPage() {
        leftSideMenuActions.gotoSettingsPage();
    }

    @And("Provider user navigates to Organisation page")
    public void moveToOrganisationPage() {
        leftSideMenuActions.gotoOrganisationPage();
    }

    @And("Provider User navigates to Agreement View page")
    public void providerNavigatesToAgreementViewPage() {
        leftSideMenuActions.gotoProviderAgreementViewPage();
    }

    /**
     * This step navigates to the settings page for a provider user.
     */
    @When("Provider User navigates to Settings")
    public void navigatesToSettings() {
        leftSideMenuActions.gotoSettingsPage();
    }

    @When("Provider User navigates to Tasks - Timesheets approval page")
    public void navigatesToTimesheetsApprovalPage() {
        leftSideMenuActions.gotoTasksTimesheetsApprovalPage();
    }

    @When("Provider User navigates to Overview page")
    public void providerNavigateToOverview() {
        leftSideMenuActions.gotoProviderOverviewPage();
    }
}