package com.carehires.pages.providers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ProvidersUserManagementPage {

    @FindBy(xpath = "//button[contains(@class, 'inserted') and (contains(@class, 'button'))]")
    public WebElement addNewButton;

    @FindBy(id = "user-email")
    public WebElement emailAddress;

    @FindBy(id = "user-name")
    public WebElement name;

    @FindBy(xpath = "//input[@formcontrolname='jobTitle']")
    public WebElement jobTitle;

    @FindBy(xpath = "//nb-select[@formcontrolname='careHomes']/button")
    public WebElement assignToSiteDropdown;

    @FindBy(xpath = "//input[@formcontrolname='phone']")
    public WebElement phone;

    @FindBy(xpath = "//nb-toggle[@formcontrolname='isAuthorizer']//input")
    public WebElement markAsAnAuthoriserToggle;

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

    @FindBy(xpath = "//div[@class='right-actions']/button")
    public WebElement actionsThreeDots;

    @FindBy(xpath = "//nb-menu[@class='context-menu']/ul/li/a[@title='Edit User']")
    public WebElement editUser;

    public static final By editUserChildElement = By.xpath("//nb-menu[@class='context-menu']/ul/li/a[" +
            "@title='Edit User']");

    @FindBy(xpath = "//nb-option[contains(@class,'multiple') and (contains(@class, 'selected'))]")
    public List<WebElement> alreadySelectedOptions;

    @FindBy(xpath = "//nb-option[contains(@class,'multiple')]")
    public List<WebElement> allAvailableOptions;

    public String getDropdownOptionXpath(String city) {
        return String.format("//nb-option[contains(text(),'%s')]", city);
    }

    @FindBy(xpath = "//button[contains(@class, 'provider-user-menu-btn')]")
    public List<WebElement> threeDots;
}
