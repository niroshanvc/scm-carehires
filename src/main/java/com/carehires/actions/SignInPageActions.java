package com.carehires.actions;

import com.carehires.pages.SignInPage;
import com.carehires.utils.BasePage;
import com.carehires.utils.DataConfigurationReader;
import com.carehires.utils.EncryptAndDecrypt;
import org.openqa.selenium.support.PageFactory;

public class SignInPageActions {
    SignInPage signin;

    public SignInPageActions() {
        signin = new SignInPage();
        PageFactory.initElements(BasePage.getDriver(), signin);
    }

    public void navigateToSignInPage() {
        BasePage.navigate("url");
    }

    public void loginToCareHires() throws Exception {
        String username = DataConfigurationReader.readDataFromYmlFile("user-credentials", "SuperAdmin", "username");
        String encryptedPassword = DataConfigurationReader.readDataFromYmlFile("user-credentials", "SuperAdmin", "password");

        String decryptedPassword = EncryptAndDecrypt.decrypt(encryptedPassword);

        BasePage.clearAndEnterTexts(signin.email, username);
        BasePage.clearAndEnterTexts(signin.password, decryptedPassword);
        BasePage.clickWithJavaScript(signin.signin);
    }
}
