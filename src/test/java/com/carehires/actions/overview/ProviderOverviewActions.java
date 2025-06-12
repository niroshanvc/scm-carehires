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

        goBackToOverviewPage();
        softAssert.assertEquals(getRaiseATicketLinkPageHeader(), "Send us your request");
        logger.info("Raise A Ticket Link is working as expected!");

        closeNewTabAndSwitchBack();
        softAssert.assertEquals(getKnowledgeBaseLinkPageHeader(), "Hello. How can we help you?");
        logger.info("Knowledge Base Link is working as expected!");

        closeNewTabAndSwitchBack();
        String actualTitle = getGetInTouchButtonPageHeader();
        softAssert.assertTrue(actualTitle.contains("Care Academy"), "Title did not contain 'Care Academy'");
        softAssert.assertTrue(actualTitle.contains("Support Request"),"Title did not contain 'Support Request'");
        logger.info("Get In Touch Button is working as expected!");

        softAssert.assertAll();
    }

    private String getGetInTouchButtonPageHeader() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying Get In Touch Button >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementPresent(providerOverviewPage.getInTouchButton, 60);
        BasePage.switchToNewTabAndWait(providerOverviewPage.getInTouchButton, ProviderOverviewPage.
                helpCenterPageHeaderByLocator);
        String pageTitle = BasePage.getText(providerOverviewPage.helpCenterPageHeader).trim();
        BasePage.switchToOtherTab();
        return pageTitle;
    }

    private String getKnowledgeBaseLinkPageHeader() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying Knowledge Base Link >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementPresent(providerOverviewPage.knowledgeBaseLink, 60);
        BasePage.switchToNewTabAndWait(providerOverviewPage.knowledgeBaseLink, ProviderOverviewPage.
                knowledgeBasePageHeaderByLocator);
        String pageHeader = BasePage.getText(providerOverviewPage.knowledgeBasePageHeader).trim();
        BasePage.switchToOtherTab();
        return pageHeader;
    }

    // close newly opened tab window and switch back to the main window
    private void closeNewTabAndSwitchBack() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Closing new tab and switching back >>>>>>>>>>>>>>>>>>>>");
        BasePage.closeCurrentTabAndSwitchToMainWindow();
        BasePage.waitUntilPageCompletelyLoaded();
    }

    private String getRaiseATicketLinkPageHeader() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying Raise A Ticket Link >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementPresent(providerOverviewPage.raiseATicketLink, 60);
        BasePage.switchToNewTabAndWait(providerOverviewPage.raiseATicketLink, ProviderOverviewPage.
                helpCenterPageHeaderByLocator);
        String pageHeader = BasePage.getText(providerOverviewPage.helpCenterPageHeader).trim();
        BasePage.switchToOtherTab();
        return pageHeader;
    }

    private String getAllocatedReviewTimesheetsLink() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying Allocated Review Timesheets Link >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementPresent(providerOverviewPage.jobSummaryAllocatedReviewTimesheetsLink, 60);
        BasePage.clickWithJavaScript(providerOverviewPage.jobSummaryAllocatedReviewTimesheetsLink);
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(ProviderOverviewPage.jobsBreadcrumbByLocator, 60);
        return BasePage.getAttributeValue(providerOverviewPage.jobFilterByAllocatedButton, "class");
    }

    private String getSuggestedJobsLink() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying Suggested Jobs Link >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementPresent(providerOverviewPage.jobSummarySuggestedAllocateWorkersLink, 60);
        BasePage.clickWithJavaScript(providerOverviewPage.jobSummarySuggestedAllocateWorkersLink);
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(ProviderOverviewPage.jobsBreadcrumbByLocator, 60);
        return BasePage.getAttributeValue(providerOverviewPage.jobFilterBySuggestedButton, "class");
    }

    private String getOpenOverdueViewJobsLink() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying Open Overdue View Jobs Link >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementPresent(providerOverviewPage.jobSummaryOpenOverdueViewJobsLink, 60);
        BasePage.clickWithJavaScript(providerOverviewPage.jobSummaryOpenOverdueViewJobsLink);
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(ProviderOverviewPage.jobsBreadcrumbByLocator, 60);
        return BasePage.getAttributeValue(providerOverviewPage.jobFilterByOpenOverdueButton, "class");
    }


    private String getOpenViewJobsLink() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying Open View Jobs Link >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementPresent(providerOverviewPage.jobSummaryAllOpenViewJobsLink, 60);
        BasePage.clickWithJavaScript(providerOverviewPage.jobSummaryAllOpenViewJobsLink);
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(ProviderOverviewPage.jobsBreadcrumbByLocator, 60);
        return BasePage.getAttributeValue(providerOverviewPage.jobFilterByOpenButton, "class");
    }

    private String getCompletedViewJobsLink() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying Completed View Jobs Link >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementPresent(providerOverviewPage.jobSummaryCompletedViewJobsLink, 60);
        BasePage.clickWithJavaScript(providerOverviewPage.jobSummaryCompletedViewJobsLink);
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(ProviderOverviewPage.jobsBreadcrumbByLocator, 60);
        return BasePage.getAttributeValue(providerOverviewPage.jobFilterByCompletedButton, "class");
    }

    private String getGoToFinancialDashboardLinkDirectedHeader() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying Outstanding Payment Link >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilElementPresent(providerOverviewPage.outstandingPaymentLink, 60);
        BasePage.clickWithJavaScript(providerOverviewPage.outstandingPaymentLink);
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.waitUntilElementPresent(ProviderOverviewPage.financeHeaderByLocator, 60);
        return BasePage.getText(providerOverviewPage.financeHeader);
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
        return BasePage.getText(providerOverviewPage.pageHeader);
    }
}
