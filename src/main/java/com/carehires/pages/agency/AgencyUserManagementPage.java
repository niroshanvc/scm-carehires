package com.carehires.pages.agency;

import org.openqa.selenium.By;
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

    @FindBy(xpath = "//button[contains(text(),'Update')]")
    public WebElement updateButton;

    @FindBy(xpath = "//button[contains(text(), 'Next')]")
    public WebElement nextButton;

    @FindBy(xpath = "//nb-toast//span")
    public WebElement successMessage;

    @FindBy(xpath = "//nb-option[contains(@class,'multiple') and (contains(@class, 'selected'))]")
    public List<WebElement> alreadySelectedAccessLevels;

    public String getDropdownOptionXpath(String city) {
        return String.format("//nb-option[contains(text(),'%s')]", city);
    }

    @FindBy(xpath = "//div[@class='right-actions']/button")
    public WebElement actionsThreeDots;

    @FindBy(xpath = "//nb-menu[@class='context-menu']/ul/li/a[@title='Edit User']")
    public WebElement editUser;

    public static final By editUserChildElement = By.xpath("//nb-menu[@class='context-menu']/ul/li/a[" +
            "@title='Edit User']");
}
