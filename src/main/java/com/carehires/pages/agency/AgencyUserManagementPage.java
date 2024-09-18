package com.carehires.pages.agency;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AgencyUserManagementPage {

    @FindBy(xpath = "//button[contains(text(),'Add new')]")
    public WebElement addNewButton;

    @FindBy(id = "user-email")
    public WebElement emailAddress;

    @FindBy(xpath = "//p[text()='Validate Email']")
    public WebElement validateEmail;

    @FindBy(id = "user-name")
    public WebElement name;

    @FindBy(xpath = "//input[@formcontrolname='jobTitle']")
    public WebElement jobTitle;

    @FindBy(xpath = "//nb-select[@formcontrolname='locations']/button")
    public WebElement location;

    @FindBy(xpath = "//input[@formcontrolname='phone']")
    public WebElement phone;

    @FindBy(xpath = "//nb-select[@formcontrolname='city']/button")
    public WebElement city;

    @FindBy(xpath = "//nb-select[@formcontrolname='userType']/button")
    public WebElement userAccessLevel;

    @FindBy(xpath = "//button[contains(text(), 'Add')]")
    public WebElement addButton;

    @FindBy(xpath = "(//div[contains(@class, 'new-content')]//div[contains(@class, 'document')]/div)[1]")
    public WebElement fullNameCell;

    @FindBy(xpath = "//button[contains(text(),'Update')]")
    public WebElement updateButton;

    @FindBy(xpath = "//button[contains(text(), 'Next')]")
    public WebElement nextButton;
}
