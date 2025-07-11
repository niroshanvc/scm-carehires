package com.carehires.actions;


import com.carehires.pages.SignInPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import com.carehires.utils.EncryptAndDecrypt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.PageFactory;

public class SignInPageActions {
    SignInPage signin;

    private static final Logger logger = LogManager.getLogger(SignInPageActions.class);

    public SignInPageActions() {
        signin = new SignInPage();
        try {
            PageFactory.initElements(BasePage.getDriver(), signin);
        } catch (BasePage.WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
    }

    public void navigateToSignInPage() {
        logger.info("Navigating to SignIn page");
        BasePage.navigate("login_url");
    }

    public void loginToCareHires() throws Exception {
        String username = DataConfigurationReader.getUserCredentials("SuperAdmin").get("username");
        String encryptedPassword = DataConfigurationReader.getUserCredentials("SuperAdmin").get("password");
        String decryptedPassword = EncryptAndDecrypt.decrypt(encryptedPassword);
        signInToCareHires(username, decryptedPassword);
    }

    private void signInToCareHires(String username, String decryptedPassword) {
        BasePage.clearAndEnterTexts(signin.email, username);
        BasePage.clearAndEnterTexts(signin.password, decryptedPassword);
        BasePage.clickWithJavaScript(signin.signinButton);
        logger.info("successfully logged in to scm");
        BasePage.waitUntilPageCompletelyLoaded();
        clickCloseButtonIfNewUpdatePopupWindowIsDisplayed();
    }

    public void doLoginOff() {
        logger.info("-------------------- Login off from Carehires --------------------");
        BasePage.waitUntilElementClickable(signin.expandUserDropdown, 60);
        BasePage.clickWithJavaScript(signin.expandUserDropdown);
        BasePage.waitUntilElementDisplayed(signin.logoutLink, 20);
        BasePage.clickWithJavaScript(signin.logoutLink);
    }

    public void loginAsProviderUser() throws Exception {
        String username = DataConfigurationReader.getUserCredentials("ProviderUser").get("username");
        String encryptedPassword = DataConfigurationReader.getUserCredentials("ProviderUser").get("password");
        String decryptedPassword = EncryptAndDecrypt.decrypt(encryptedPassword);
        signInToCareHires(username, decryptedPassword);
    }

    // click on the close button if new update popup window is displayed
    public void clickCloseButtonIfNewUpdatePopupWindowIsDisplayed() {
        try {
            BasePage.clickWithJavaScript(signin.newReleasePopupCloseButton);
        } catch (NoSuchElementException e) {
            logger.info("New release popup window is not displayed");
        }
    }

}
