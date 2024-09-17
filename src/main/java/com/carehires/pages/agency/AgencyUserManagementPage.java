package com.carehires.pages.agency;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AgencyUserManagementPage {

    @FindBy(xpath = "//button[contains(text(),'Add new')]")
    public WebElement addUserButton;

    @FindBy(id = "user-email")
    public WebElement emailAddress;

    @FindBy(xpath = "//p[text()='Validate Email']")
    public WebElement validateEmail;

}
