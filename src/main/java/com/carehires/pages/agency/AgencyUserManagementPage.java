package com.carehires.pages.agency;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

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

    @FindBy(xpath = "//nb-select[@formcontrolname='groups']/button")
    public WebElement userAccessLevel;

    @FindBy(xpath = "//button[contains(text(), 'Add')]")
    public WebElement addButton;

    @FindBy(xpath = "(//div[contains(@class, 'new-content')]//div[contains(@class, 'document')]/div)[1]")
    public WebElement fullNameCell;

    @FindBy(xpath = "//button[contains(text(),'Update')]")
    public WebElement updateButton;

    @FindBy(xpath = "//button[contains(text(), 'Next')]")
    public WebElement nextButton;

    @FindBy(xpath = "//nb-toast//span")
    public WebElement successMessage;

    @FindBy(xpath = "//nb-icon[@icon='edit-2-outline']")
    public WebElement editDetailsIcon;

    @FindBy(xpath = "//nb-option[contains(@class,'multiple') and (contains(@class, 'selected'))]")
    public List<WebElement> alreadySelectedAccessLevels;
}
