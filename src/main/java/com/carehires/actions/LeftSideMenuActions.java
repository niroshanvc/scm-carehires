package com.carehires.actions;

import com.carehires.pages.LeftSideMenuPage;
import com.carehires.utils.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class LeftSideMenuActions {
    LeftSideMenuPage leftSideMenu;

    private static final Logger logger = LogManager.getFormatterLogger(LeftSideMenuActions.class);

    public LeftSideMenuActions() {
        leftSideMenu = new LeftSideMenuPage();
        PageFactory.initElements(BasePage.getDriver(), leftSideMenu);
    }

    public void gotoAgencyCreatePage() {
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(leftSideMenu.jobsIcon, 60);
        clickOnSubMenuLink(LeftSideMenuPage.agenciesMainLink, LeftSideMenuPage.createSubLink);
        logger.info(" --------------------------- Moves to Agency Create Page ---------------------------");
    }

    public void gotoAgencyViewPage() {
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(leftSideMenu.jobsIcon, 60);
        clickOnSubMenuLink(LeftSideMenuPage.agenciesMainLink, LeftSideMenuPage.viewSubLink);
        logger.info(" --------------------------- Moves to Agency View Page ---------------------------");
    }

    public void gotoWorkerCreatePage() {
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(leftSideMenu.jobsIcon, 60);
        clickOnSubMenuLink(LeftSideMenuPage.workersMainLink, LeftSideMenuPage.workerCreateIndividual);
        logger.info(" --------------------------- Moves to Worker Individual Create Page ---------------------------");
    }

    public void gotoWorkerViewPage() {
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(leftSideMenu.jobsIcon, 60);
        clickOnSubMenuLink(LeftSideMenuPage.workersMainLink, LeftSideMenuPage.viewSubLink);
        logger.info(" --------------------------- Moves to Worker View Page ---------------------------");
    }

    public void gotoProviderCreatePage() {
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(leftSideMenu.jobsIcon, 60);
        clickOnSubMenuLink(LeftSideMenuPage.providersMainLink, LeftSideMenuPage.createSubLink);
        logger.info(" --------------------------- Moves to Provider Create Page ---------------------------");
    }

    public void gotoProviderViewPage() {
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(leftSideMenu.jobsIcon, 60);
        clickOnSubMenuLink(LeftSideMenuPage.providersMainLink, LeftSideMenuPage.viewSubLink);
        logger.info(" --------------------------- Moves to Provider View Page ---------------------------");
    }

    private void clickOnSubMenuLink(By mainLocator, By subLocator) {

        // Actions object for hover
        Actions actions = new Actions(BasePage.getDriver());

        // Wait object for explicit wait
        WebDriverWait wait = new WebDriverWait(BasePage.getDriver(), Duration.ofSeconds(90));

        try {
            // Hover over the main element
            WebElement mainLink = wait.until(ExpectedConditions.visibilityOfElementLocated(mainLocator));
            actions.moveToElement(mainLink).perform();

            // Pause briefly to ensure submenu is displayed
            BasePage.genericWait(500);

            // Wait for the Create or View submenu to be clickable and click it
            WebElement subMenu = wait.until(ExpectedConditions.presenceOfElementLocated(subLocator));

            // Move to a submenu and click
            actions.moveToElement(subMenu).click().build().perform();
        } catch (Exception e) {
            logger.info("Unable to click on %s - %s link", mainLocator, subLocator);
        }
    }
}