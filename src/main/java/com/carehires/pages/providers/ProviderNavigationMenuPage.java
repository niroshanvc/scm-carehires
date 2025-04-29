package com.carehires.pages.providers;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProviderNavigationMenuPage {

    @FindBy(xpath = "//div[contains(text(), 'Profile')]/../..")
    public WebElement profile;

    @FindBy(xpath = "//div[contains(text(), 'Profile')]/..//img")
    public WebElement profileImage;

    @FindBy(xpath = "//div[contains(text(), 'Site')]/..")
    public WebElement site;

    @FindBy(xpath = "//div[contains(text(), 'Site')]/..//img")
    public WebElement siteImage;

    @FindBy(xpath = "//div[contains(text(), 'Staff')]/..")
    public WebElement staff;

    @FindBy(xpath = "//div[contains(text(), 'Staff')]/..//img")
    public WebElement staffImage;

    @FindBy(xpath = "//div[contains(text(), 'User')]/..")
    public WebElement users;

    @FindBy(xpath = "//div[contains(text(), 'User')]/..//img")
    public WebElement usersImage;

    @FindBy(xpath = "//div[contains(text(), 'Billing')]/..")
    public WebElement billingDiv;

    @FindBy(xpath = "//h6[contains(text(), 'Billing')]/..")
    public WebElement billing;

    @FindBy(xpath = "//h6[contains(text(), 'Billing')]/..//nb-icon")
    public WebElement billingIcon;

    @FindBy(xpath = "//div[contains(text(), 'Billing')]/..//img")
    public WebElement billingImage;

    @FindBy(xpath = "//h6[contains(text(), 'Agreement')]/..")
    public WebElement agreement;

    @FindBy(xpath = "//h6contains(text(), 'Agreement')]/..//nb-icon")
    public WebElement agreementImage;

    @FindBy(xpath = "//div[contains(text(), 'Settings')]/..")
    public WebElement settings;

    @FindBy(xpath = "//div[contains(text(), 'Settings')]/..//img")
    public WebElement settingsImage;

    @FindBy(xpath = "//div[contains(text(), 'Restrictions')]/..")
    public WebElement restrictions;

    @FindBy(xpath = "//div[contains(text(), 'Restrictions')]/..//img")
    public WebElement restrictionsImage;

    @FindBy(xpath = "//div[contains(text(), 'Notifications')]/..")
    public WebElement notifications;

    @FindBy(xpath = "//div[contains(text(), 'Notifications')]/..//img")
    public WebElement notificationsImage;
}
