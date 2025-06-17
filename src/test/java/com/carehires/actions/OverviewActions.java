package com.carehires.actions;


import com.carehires.pages.OverviewPage;
import com.carehires.utils.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.PageFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class OverviewActions {
    OverviewPage overview;

    private static final Logger logger = LogManager.getFormatterLogger(OverviewActions.class);

    public OverviewActions() {
        overview = new OverviewPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), overview);
        } catch (BasePage.WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
    }

    public void waitAndAcceptCookies() {
        BasePage.waitUntilElementPresent(overview.overviewMenu, 10);
        try {
            BasePage.clickWithJavaScript(overview.acceptCookie);
        } catch (NoSuchElementException e) {
            logger.info("Cookie already accepted.");
        }
    }

    public void verifyPageTitle() {
        BasePage.waitUntilPageCompletelyLoaded();
        String actualTitle = BasePage.getPageTitle().trim();
        String expectedTitle = "Care Hires Portal Business Web Application";
        assertThat("Page title is incorrect", actualTitle, is(expectedTitle));
    }

    // close new version popup if it appears
    public void closeNewVersionPopup() {
        try {
            BasePage.waitUntilElementPresent(overview.newVersionPopupIframe, 10);

            if (overview.newVersionPopupIframe.isDisplayed()) {
                BasePage.switchToIframe(overview.newVersionPopupIframe);
                BasePage.clickWithJavaScript(overview.closeNewVersionPopupButton);
                logger.info("Closed new version popup.");
                BasePage.switchToDefaultContent();
            }
        } catch (TimeoutException | NoSuchElementException e) {
            logger.info("New version popup did not appear within the time limit.");
        }
    }
}
