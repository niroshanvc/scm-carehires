package com.carehires.pages.agency;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AgencyNavigationMenuPage {

    @FindBy(xpath = "//div/div/div[contains(text(), 'Profile')]/../..")
    public WebElement profile;

    @FindBy(xpath = "//div/div/div[contains(text(), 'Profile')]/..//img")
    public WebElement profileImage;

    @FindBy(xpath = "//div[contains(text(), 'Credit Service')]/../..")
    public WebElement creditService;

    @FindBy(xpath = "//div[contains(text(), 'Credit Service')]/..//img")
    public WebElement creditServiceImage;

    @FindBy(xpath = "//div[contains(text(), 'Locations')]/../..")
    public WebElement locations;

    @FindBy(xpath = "//div[contains(text(), 'Locations')]/..//img")
    public WebElement locationsImage;

    @FindBy(xpath = "//div[contains(text(), 'Staff')]/../..")
    public WebElement staff;

    @FindBy(xpath = "//div[contains(text(), 'Staff')]/..//img")
    public WebElement staffImage;

    @FindBy(xpath = "//div[contains(text(), 'Billing')]/..")
    public WebElement billing;

    @FindBy(xpath = "//div[contains(text(), 'Billing')]/..//img")
    public WebElement billingImage;

    @FindBy(xpath = "//div[contains(text(), 'Users')]/../..")
    public WebElement users;

    @FindBy(xpath = "//div[contains(text(), 'Users')]/..//img")
    public WebElement usersImage;

    @FindBy(xpath = "//div[contains(text(), 'Agreement')]/../..")
    public WebElement agreement;

    @FindBy(xpath = "//div[contains(text(), 'Agreement')]/..//img")
    public WebElement agreementImage;

    @FindBy(xpath = "//div[contains(text(), 'Workers')]/..")
    public WebElement workers;

    @FindBy(xpath = "//div[contains(text(), 'Workers')]/..//img")
    public WebElement workersImage;

    @FindBy(xpath = "//div[contains(text(), 'Settings')]/..")
    public WebElement settings;

    @FindBy(xpath = "//div[contains(text(), 'Settings')]/..//img")
    public WebElement settingsImage;
}
