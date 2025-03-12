package com.carehires.actions.agreements;

import com.carehires.pages.agreements.AgreementOverviewPage;
import com.carehires.utils.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AgreementOverviewActions {

    AgreementOverviewPage agreementOverviewPage;

    private static final Logger logger = LogManager.getLogger(AgreementOverviewActions.class);

    public AgreementOverviewActions() {
        agreementOverviewPage = new AgreementOverviewPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), agreementOverviewPage);
        } catch (BasePage.WebDriverInitializationException e) {
            logger.error("Error while initializing Agreement Overview Page elements: {}", e.getMessage());
        }
    }

    public void verifyTotalAgreements() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying total agreements link is working >>>>>>>>>>>>>>>>>>>>");
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.clickWithJavaScript(agreementOverviewPage.totalAgreements);
        BasePage.waitUntilPageCompletelyLoaded();
        String actual = BasePage.getText(agreementOverviewPage.thirdBreadcrumb);
        assertThat("Total agreements link is not working", actual, is("Total Agreements"));
    }

    public void verifyIssueAgreementLink() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Verifying issue agreement link is working >>>>>>>>>>>>>>>>>>>>");
        BasePage.clickWithJavaScript(agreementOverviewPage.overviewBreadcrumb);
        BasePage.waitUntilPageCompletelyLoaded();
        BasePage.clickWithJavaScript(agreementOverviewPage.issueAgreementLink);
        BasePage.waitUntilPageCompletelyLoaded();
        String actual = BasePage.getText(agreementOverviewPage.agreementOverviewHeader);
        assertThat("Issue agreement link is not working", actual, is("Agreement Overview"));
    }
}
