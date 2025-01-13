package com.carehires.pages.providers;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProviderNavigationMenuPage {

    @FindBy(xpath = "//div[contains(text(), 'Profile')]/../..")
    public WebElement profile;

    @FindBy(xpath = "//div[contains(text(), 'Profile')]/..//img")
    public WebElement profileImage;

    @FindBy(xpath = "//div[contains(text(), 'Site')]/../..")
    public WebElement site;

    @FindBy(xpath = "//div[contains(text(), 'Site')]/..//img")
    public WebElement siteImage;

    @FindBy(xpath = "//div[contains(text(), 'Staff')]/../..")
    public WebElement staff;

    @FindBy(xpath = "//div[contains(text(), 'Staff')]/..//img")
    public WebElement staffImage;

    @FindBy(xpath = "//h6[contains(text(), 'User')]/../..")
    public WebElement users;

    @FindBy(xpath = "//h6[contains(text(), 'User')]/..//nb-icon")
    public WebElement usersImage;

    @FindBy(xpath = "//h6[contains(text(), 'Billing')]/..")
    public WebElement billing;

    @FindBy(xpath = "//h6[contains(text(), 'Billing')]/..//nb-icon")
    public WebElement billingImage;

    @FindBy(xpath = "//div[contains(text(), 'Agreement')]/../..")
    public WebElement agreement;

    @FindBy(xpath = "//div[contains(text(), 'Agreement')]/..//img")
    public WebElement agreementImage;

    @FindBy(xpath = "//div[contains(text(), 'Settings')]/..")
    public WebElement settings;

    @FindBy(xpath = "//div[contains(text(), 'Settings')]/..//img")
    public WebElement settingsImage;
}
