package com.carehires.actions;

import com.carehires.pages.OverviewPage;
import com.carehires.utils.BasePage;
import org.openqa.selenium.support.PageFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class OverviewActions {
    OverviewPage overview;

    public OverviewActions() {
        overview = new OverviewPage();
        PageFactory.initElements(BasePage.getDriver(), overview);
    }

    public void waitUntilElementDisplayed() {
        BasePage.waitUntilElementPresent(overview.overviewMenu, 60);
        BasePage.clickWithJavaScript(overview.acceptCookie);
    }

    public void verifyPageTitle() {
        BasePage.waitUntilPageCompletelyLoaded();
        String actualTitle = BasePage.getPageTitle().trim();
        String expectedTitle = "Care Hires Portal Business Web Application";
        assertThat("Page title is incorrect", actualTitle, is(expectedTitle));
    }
}
