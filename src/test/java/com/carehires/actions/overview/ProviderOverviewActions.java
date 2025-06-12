package com.carehires.actions.overview;

import com.carehires.actions.LeftSideMenuActions;
import com.carehires.pages.overivew.ProviderOverviewPage;
import com.carehires.utils.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;

public class ProviderOverviewActions {

    private final ProviderOverviewPage providerOverviewPage;
    private final LeftSideMenuActions leftSideMenuActions = new LeftSideMenuActions();

    private static final Logger logger = LogManager.getFormatterLogger(ProviderOverviewActions.class);

    public ProviderOverviewActions() {
        providerOverviewPage = new ProviderOverviewPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), providerOverviewPage);
        } catch (BasePage.WebDriverInitializationException e) {
            logger.error("Error while initializing Provider Overview Page elements: %s", e.getMessage());
        }
    }

    public void verifyLinksAreWorking() {
        BasePage.waitUntilPageCompletelyLoaded();
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying links are working >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementPresent(providerOverviewPage.totalSiteViewDetailsLink, 60);
        BasePage.clickWithJavaScript(providerOverviewPage.totalSiteViewDetailsLink);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(getTotalSiteViewDetailsLinkDirectedHeader(), "Site Management");
        logger.info("Total Site View Details Link is working as expected!");

        goBackToOverviewPage();
        softAssert.assertEquals(getGoToFinancialDashboardLinkDirectedHeader(), "Finance Dashboard");
        logger.info("Go To Financial Dashboard Link is working as expected!");

        goBackToOverviewPage();
        boolean actual = getCompletedViewJobsLink().contains("completed-selected");
        softAssert.assertTrue(actual, "Completed View Jobs Link is not working as expected!");
        logger.info("Completed View Jobs Link is working as expected!");

        goBackToOverviewPage();
        actual = getOpenViewJobsLink().contains("open-selected");
        softAssert.assertTrue(actual, "Open View Jobs Link is not working as expected!");
        logger.info("Open View Jobs Link is working as expected!");

        goBackToOverviewPage();
        actual = getOpenOverdueViewJobsLink().contains("overdue-selected");
        softAssert.assertTrue(actual, "Open Overdue View Jobs Link is not working as expected!");
        logger.info("Open Overdue View Jobs Link is working as expected!");

        goBackToOverviewPage();
        actual = getSuggestedJobsLink().contains("suggested-selected");
        softAssert.assertTrue(actual, "Suggested Jobs Link is not working as expected!");
        logger.info("Suggested Jobs Link is working as expected!");

        goBackToOverviewPage();
        actual = getAllocatedReviewTimesheetsLink().contains("allocated-selected");
        softAssert.assertTrue(actual, "Allocated Review Timesheets Link is not working as expected!");
        logger.info("Allocated Review Timesheets Link is working as expected!");

        softAssert.assertAll();
    }

    private String getAllocatedReviewTimesheetsLink() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying Allocated Review Timesheets Link >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementPresent(providerOverviewPage.jobSummaryAllocatedReviewTimesheetsLink, 60);
        BasePage.clickWithJavaScript(providerOverviewPage.jobSummaryAllocatedReviewTimesheetsLink);
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(ProviderOverviewPage.jobsBreadcrumbByLocator, 60);
        String actual = BasePage.getAttributeValue(providerOverviewPage.jobFilterByAllocatedButton, "class");
        return actual;
    }

    private String getSuggestedJobsLink() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying Suggested Jobs Link >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementPresent(providerOverviewPage.jobSummarySuggestedAllocateWorkersLink, 60);
        BasePage.clickWithJavaScript(providerOverviewPage.jobSummarySuggestedAllocateWorkersLink);
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(ProviderOverviewPage.jobsBreadcrumbByLocator, 60);
        String actual = BasePage.getAttributeValue(providerOverviewPage.jobFilterBySuggestedButton, "class");
        return actual;
    }

    private String getOpenOverdueViewJobsLink() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying Open Overdue View Jobs Link >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementPresent(providerOverviewPage.jobSummaryOpenOverdueViewJobsLink, 60);
        BasePage.clickWithJavaScript(providerOverviewPage.jobSummaryOpenOverdueViewJobsLink);
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(ProviderOverviewPage.jobsBreadcrumbByLocator, 60);
        String actual = BasePage.getAttributeValue(providerOverviewPage.jobFilterByOpenOverdueButton, "class");
        return actual;
    }


    private String getOpenViewJobsLink() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying Open View Jobs Link >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementPresent(providerOverviewPage.jobSummaryAllOpenViewJobsLink, 60);
        BasePage.clickWithJavaScript(providerOverviewPage.jobSummaryAllOpenViewJobsLink);
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(ProviderOverviewPage.jobsBreadcrumbByLocator, 60);
        String actual = BasePage.getAttributeValue(providerOverviewPage.jobFilterByOpenButton, "class");
        return actual;
    }

    private String getCompletedViewJobsLink() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying Completed View Jobs Link >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementPresent(providerOverviewPage.jobSummaryCompletedViewJobsLink, 60);
        BasePage.clickWithJavaScript(providerOverviewPage.jobSummaryCompletedViewJobsLink);
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(ProviderOverviewPage.jobsBreadcrumbByLocator, 60);
        String actual = BasePage.getAttributeValue(providerOverviewPage.jobFilterByCompletedButton, "class");
        return actual;
    }

    private String getGoToFinancialDashboardLinkDirectedHeader() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying Outstanding Payment Link >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementPresent(providerOverviewPage.outstandingPaymentLink, 60);
        BasePage.clickWithJavaScript(providerOverviewPage.outstandingPaymentLink);
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(ProviderOverviewPage.financeHeaderByLocator, 60);
        String actual = BasePage.getText(providerOverviewPage.financeHeader);
        return actual;
    }

    private void goBackToOverviewPage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Navigating to Overview Page >>>>>>>>>>>>>>>>>>>>");
        leftSideMenuActions.gotoProviderOverviewPage();
        BasePage.waitUntilPageCompletelyLoaded();
    }

    private String getTotalSiteViewDetailsLinkDirectedHeader() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying Total Site View Details Link >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementPresent(providerOverviewPage.totalSiteViewDetailsLink, 60);
        BasePage.clickWithJavaScript(providerOverviewPage.totalSiteViewDetailsLink);
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(ProviderOverviewPage.pageHeaderByLocator, 60);
        BasePage.genericWait(500);
        String actual = BasePage.getText(providerOverviewPage.pageHeader);
        return actual;
    }
}
