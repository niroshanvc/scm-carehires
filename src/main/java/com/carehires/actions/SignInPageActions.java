package com.carehires.actions;

import com.carehires.pages.SignInPage;
import com.carehires.utils.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

public class SignInPageActions {
    SignInPage signin;
    private static final CreateAgencyCreditServiceActions creditServiceActions = new CreateAgencyCreditServiceActions();

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
        BasePage.clickAfterWait(signin.acceptCookie);
    }

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://id.dev.v4.carehires.co.uk/login?redirectUrl=https%3A%2F%2Fportal.dev.v4.carehires.co.uk%2Fsignin");
        SignInPageActions signInPageActions = new SignInPageActions();
        signInPageActions.loginToCareHires();
        creditServiceActions.enterCreditServiceInformation();
    }
}
