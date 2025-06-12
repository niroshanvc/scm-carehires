package com.carehires.actions;

import com.carehires.pages.LeftSideMenuPage;
import com.carehires.utils.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;


public class LeftSideMenuActions {
    LeftSideMenuPage leftSideMenu;

    private static final Logger logger = LogManager.getFormatterLogger(LeftSideMenuActions.class);

    public LeftSideMenuActions() {
        leftSideMenu = new LeftSideMenuPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), leftSideMenu);
        } catch (BasePage.WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
    }

    public void gotoAgencyCreatePage() {
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(leftSideMenu.jobsIcon, 60);
        BasePage.mouseHoverAndClick(LeftSideMenuPage.agenciesMainLink, LeftSideMenuPage.agencyCreateSubLink);
        logger.info(" ------------------------- Successfully Moves to Agency Create Page -------------------------");
    }

    public void gotoAgencyViewPage() {
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(leftSideMenu.jobsIcon, 60);
        BasePage.mouseHoverAndClick(LeftSideMenuPage.agenciesMainLink, LeftSideMenuPage.agencyViewSubLink);
        logger.info(" --------------------------- Successfully Moves to Agency View Page ---------------------------");
    }

    public void gotoWorkerCreatePage() {
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(leftSideMenu.jobsIcon, 60);
        BasePage.mouseHoverAndClick(LeftSideMenuPage.workersMainLink, LeftSideMenuPage.workersCreateIndividual);
        logger.info(" -------------------- Successfully Moves to Worker Individual Create Page ---------------------");
    }

    public void gotoWorkerViewPage() {
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(leftSideMenu.jobsIcon, 60);
        BasePage.mouseHoverAndClick(LeftSideMenuPage.workersMainLink, LeftSideMenuPage.workersViewSubLink);
        logger.info(" --------------------------- Successfully Moves to Worker View Page ---------------------------");
    }

    public void gotoProviderCreatePage() {
        BasePage.refreshPage();
        BasePage.genericWait(2000);
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(leftSideMenu.jobsIcon, 60);
        BasePage.mouseHoverAndClick(LeftSideMenuPage.providersMainLink, LeftSideMenuPage.providersCreateSubLink);
        logger.info(" ------------------------- Successfully Moves to Provider Create Page -------------------------");
    }

    public void gotoProviderViewPage() {
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(leftSideMenu.jobsIcon, 60);
        BasePage.mouseHoverAndClick(LeftSideMenuPage.providersMainLink, LeftSideMenuPage.providersViewSubLink);
        logger.info(" ------------------------- Successfully Moves to Provider View Page -------------------------");
    }

    public void gotoAgreementCreatePage() {
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(leftSideMenu.jobsIcon, 60);
        BasePage.mouseHoverAndClick(LeftSideMenuPage.agreementsMainLink, LeftSideMenuPage.agreementsCreateSubLink);
        logger.info(" ------------------------ Successfully Moves to Agreements Create Page ------------------------");
    }

    public void gotoAgreementViewPage() {
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(leftSideMenu.jobsIcon, 60);
        BasePage.mouseHoverAndClick(LeftSideMenuPage.agreementsMainLink, LeftSideMenuPage.agreementsViewSubLink);
        logger.info(" -------------------------- SuccessfullyMoves to Agreements View Page --------------------------");
    }

    public void gotoAgreementOverviewPage() {
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(leftSideMenu.jobsIcon, 60);
        BasePage.mouseHoverAndClick(LeftSideMenuPage.agreementsMainLink, LeftSideMenuPage.agreementsOverviewSubLink);
        logger.info(" ----------------------- Successfully Moves to Agreements Overview Page -----------------------");
    }

    public void gotoTasksPaymentAuthorisationsPage() {
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(leftSideMenu.jobsIcon, 60);
        BasePage.mouseHoverAndClick(LeftSideMenuPage.tasksMainLink, LeftSideMenuPage.paymentAuthorisationsSubLink);
        logger.info(" ---------------------- Successfully Moves to Payment Authorisations Page ----------------------");
    }

    public void gotoJobsPage() {
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(leftSideMenu.jobsIcon, 60);
        BasePage.clickWithJavaScript(leftSideMenu.jobsIcon);
        logger.info(" --------------------------- Successfully Moves to Jobs Page ---------------------------");
    }

    public void gotoSettingsPage() {
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.navigateTo("https://portal.dev.v4.carehires.co.uk/dashboard/settings");
        BasePage.waitUntilPageCompletelyLoaded();
        logger.info(" --------------------------- Successfully Moves to Settings Page ---------------------------");
    }

    public void gotoOrganisationPage() {
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(leftSideMenu.jobsIcon, 60);
        BasePage.clickWithJavaScript(leftSideMenu.organisationMainLink);
        logger.info(" --------------------------- Successfully Moves to Organisation Page ---------------------------");
    }

    public void gotoProviderAgreementViewPage() {
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(leftSideMenu.jobsIcon, 60);
        BasePage.mouseHoverAndClick(LeftSideMenuPage.agreementsMainLink, LeftSideMenuPage.agreementsViewOnlySubLink);
        logger.info(" ------------------------ Provider User Moves to Agreements View Page ------------------------");
    }

    public void gotoTasksTimesheetsApprovalPage() {
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(leftSideMenu.jobsIcon, 60);
        BasePage.mouseHoverAndClick(LeftSideMenuPage.tasksMainLink, LeftSideMenuPage.timesheetApprovalsSubLink);
        logger.info(" ---------------------- Successfully Moves to Timesheets Approval Page ----------------------");
    }

    public void gotoProviderOverviewPage() {
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(leftSideMenu.jobsIcon, 60);
        BasePage.clickWithJavaScript(leftSideMenu.providerOverviewIcon);
        logger.info(" ----------------------- Successfully Moves to Provider Overview Page -----------------------");
    }
}