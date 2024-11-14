package com.carehires.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignInPage {

    @FindBy(id = "email")
    public WebElement email;

    @FindBy(id = "password")
    public WebElement password;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement signinButton;

    @FindBy(xpath = "//nb-icon[contains(@icon, 'down')]")
    public WebElement expandUserDropdown;

    @FindBy(xpath = "//li//div[contains(text(), 'Logout')]")
    public WebElement logoutLink;
}
