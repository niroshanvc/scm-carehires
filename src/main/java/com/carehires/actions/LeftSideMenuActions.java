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
        logger.info(" --------------------------- Moves to Agency Create Page ---------------------------");
    }

    public void gotoAgencyViewPage() {
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(leftSideMenu.jobsIcon, 60);
        BasePage.mouseHoverAndClick(LeftSideMenuPage.agenciesMainLink, LeftSideMenuPage.agencyViewSubLink);
        logger.info(" --------------------------- Moves to Agency View Page ---------------------------");
    }

    public void gotoWorkerCreatePage() {
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(leftSideMenu.jobsIcon, 60);
        BasePage.mouseHoverAndClick(LeftSideMenuPage.workersMainLink, LeftSideMenuPage.workersCreateIndividual);
        logger.info(" --------------------------- Moves to Worker Individual Create Page ---------------------------");
    }

    public void gotoWorkerViewPage() {
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(leftSideMenu.jobsIcon, 60);
        BasePage.mouseHoverAndClick(LeftSideMenuPage.workersMainLink, LeftSideMenuPage.workersViewSubLink);
        logger.info(" --------------------------- Moves to Worker View Page ---------------------------");
    }

    public void gotoProviderCreatePage() {
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(leftSideMenu.jobsIcon, 60);
        BasePage.mouseHoverAndClick(LeftSideMenuPage.providersMainLink, LeftSideMenuPage.providersCreateSubLink);
        logger.info(" --------------------------- Moves to Provider Create Page ---------------------------");
    }

    public void gotoProviderViewPage() {
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(leftSideMenu.jobsIcon, 60);
        BasePage.mouseHoverAndClick(LeftSideMenuPage.providersMainLink, LeftSideMenuPage.providersViewSubLink);
        logger.info(" --------------------------- Moves to Provider View Page ---------------------------");
    }

    public void gotoAgreementCreatePage() {
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(leftSideMenu.jobsIcon, 60);
        BasePage.mouseHoverAndClick(LeftSideMenuPage.agreementsMainLink, LeftSideMenuPage.agreementsCreateSubLink);
        logger.info(" --------------------------- Moves to Agreements Create Page ---------------------------");
    }

    public void gotoTasksPaymentAuthorisationsPage() {
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(leftSideMenu.jobsIcon, 60);
        BasePage.mouseHoverAndClick(LeftSideMenuPage.tasksMainLink, LeftSideMenuPage.paymentAuthorisationsSubLink);
        logger.info(" --------------------------- Moves to Payment Authorisations Page ---------------------------");
    }
}