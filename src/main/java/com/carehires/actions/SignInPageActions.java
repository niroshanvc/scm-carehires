package com.carehires.actions;

import com.carehires.pages.SignInPage;
import com.carehires.utils.BasePage;
import org.openqa.selenium.support.PageFactory;

public class SignInPageActions {
    SignInPage signin;

    public SignInPageActions() {
        signin = new SignInPage();
        PageFactory.initElements(BasePage.getDriver(), signin);
    }

    public void navigateToSigninPage() {
        BasePage.navigate("url");
    }

    public void loginToCareHires() {
        BasePage.clearAndEnterTexts(signin.email, BasePage.getProperty("username"));
        BasePage.clearAndEnterTexts(signin.password, BasePage.getProperty("password"));
        BasePage.clickWithJavaScript(signin.signin);
    }
}
