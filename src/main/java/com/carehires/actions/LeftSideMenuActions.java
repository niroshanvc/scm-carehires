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
        PageFactory.initElements(BasePage.getDriver(), leftSideMenu);
    }

    public void gotoAgencyCreatePage() {
        BasePage.mouseHoverAndClick(leftSideMenu.agencies, leftSideMenu.agencyCreate);
        logger.info(" --------------------------- Moves to Agency Create Page ---------------------------");
    }

    public void gotoAgencyViewPage() {
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.mouseHoverAndClick(leftSideMenu.agencies, leftSideMenu.agencyView);
        BasePage.mouseHoverAndRelease(leftSideMenu.agencies, leftSideMenu.agencyView);
        logger.info(" --------------------------- Moves to Agency View Page ---------------------------");
    }

    public void gotoWorkerCreatePage() {
        BasePage.mouseHoverAndClick(leftSideMenu.workers, leftSideMenu.workerCreateIndividual);
        logger.info(" --------------------------- Moves to Worker Individual Create Page ---------------------------");
    }

    public void gotoWorkerViewPage() {
        BasePage.mouseHoverAndClick(leftSideMenu.workers, leftSideMenu.workerView);
        logger.info(" --------------------------- Moves to Worker View Page ---------------------------");
    }

    public void gotoProviderCreatePage() {
        BasePage.mouseHoverAndClick(leftSideMenu.providers, leftSideMenu.providerCreate);
        logger.info(" --------------------------- Moves to Provider Create Page ---------------------------");
    }

    public void gotoProviderViewPage() {
        BasePage.mouseHoverAndClick(leftSideMenu.providers, leftSideMenu.providerView);
        logger.info(" --------------------------- Moves to Provider View Page ---------------------------");
    }
}