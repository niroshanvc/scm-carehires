package com.carehires.actions.workers;

import com.carehires.pages.worker.WorkerNavigationMenuPage;
import com.carehires.utils.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

public class WorkerNavigationMenuActions {

    private final WorkerNavigationMenuPage navigationMenuPage;

    private static final Logger logger = LogManager.getLogger(WorkerNavigationMenuActions.class);

    public WorkerNavigationMenuActions() {
        navigationMenuPage = new WorkerNavigationMenuPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), navigationMenuPage);
        } catch (BasePage.WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
    }

    public void gotoProfilePage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Moving to Profile page >>>>>>>>>>>>>>>>>>>>");
        BasePage.mouseHoverOverElement(navigationMenuPage.profile);
        BasePage.clickWithJavaScript(navigationMenuPage.profileImage);
    }

    public void gotoDocumentsPage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Moving to Documents page >>>>>>>>>>>>>>>>>>>>");
        BasePage.mouseHoverOverElement(navigationMenuPage.documents);
        BasePage.clickWithJavaScript(navigationMenuPage.documentsImage);
    }

    public void gotoCertificatesPage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Moving to Certificates page >>>>>>>>>>>>>>>>>>>>");
        BasePage.mouseHoverOverElement(navigationMenuPage.certificates);
        BasePage.clickWithJavaScript(navigationMenuPage.certificatesImage);
    }

    public void gotoEmergencyPage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Moving to Emergency page >>>>>>>>>>>>>>>>>>>>");
        BasePage.mouseHoverOverElement(navigationMenuPage.emergency);
        BasePage.clickWithJavaScript(navigationMenuPage.emergencyImage);
    }

    public void gotoMedicalPage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Moving to Medical page >>>>>>>>>>>>>>>>>>>>");
        BasePage.mouseHoverOverElement(navigationMenuPage.medical);
        BasePage.clickWithJavaScript(navigationMenuPage.medicalImage);
    }

    public void gotoEmploymentPage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Moving to Employment page >>>>>>>>>>>>>>>>>>>>");
        BasePage.mouseHoverOverElement(navigationMenuPage.employment);
        BasePage.clickWithJavaScript(navigationMenuPage.employmentImage);
    }

    public void gotoHistoryPage() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<< Moving to History page >>>>>>>>>>>>>>>>>>>>");
        BasePage.mouseHoverOverElement(navigationMenuPage.history);
        BasePage.clickWithJavaScript(navigationMenuPage.historyImage);
    }
}
