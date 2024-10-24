package com.carehires.actions;

import com.carehires.pages.SignInPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import com.carehires.utils.EncryptAndDecrypt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

public class SignInPageActions {
    SignInPage signin;

    private static final Logger logger = LogManager.getLogger(SignInPageActions.class);

    public SignInPageActions() {
        signin = new SignInPage();
        PageFactory.initElements(BasePage.getDriver(), signin);
    }

    public void navigateToSignInPage() {
        logger.info("Navigating to SignIn page");
        BasePage.navigate("login_url");
    }

    public void loginToCareHires() throws Exception {
        String username = DataConfigurationReader.getUserCredentials("SuperAdmin").get("username");
        String encryptedPassword = DataConfigurationReader.getUserCredentials("SuperAdmin").get("password");

        String decryptedPassword = EncryptAndDecrypt.decrypt(encryptedPassword);

        BasePage.clearAndEnterTexts(signin.email, username);
        BasePage.clearAndEnterTexts(signin.password, decryptedPassword);
        BasePage.clickWithJavaScript(signin.signinButton);
        logger.info("successfully logged in to scm");
        BasePage.waitUntilPageCompletelyLoaded();
    }
}
