package com.carehires.pages.agency;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AgencyProfilePage {

    @FindBy(xpath = "//button[contains(text(), 'Approve')]")
    public WebElement approveButton;

    @FindBy(xpath = "//div[contains(text(), 'Profile')]/../div/img")
    public WebElement profileIcon;

    @FindBy(xpath = "//div[contains(@class, 'status') and (contains(@class, 'warning') or (contains(@class, 'success')))]")
    public WebElement profileStatus;

    @FindBy(xpath = "//nb-dialog-container//h5")
    public WebElement popupText;

    @FindBy(xpath = "//nb-toast//span")
    public WebElement successMessage;

    @FindBy(xpath = "//p[contains(@class, 'text-icon') and contains(text(), '…')]")
    public WebElement topThreeDots;

    @FindBy(xpath = "//div[contains(@class, 'side')]/ul[@class='expanded-nav']/li[1]")
    public WebElement updateProfileLink;

    public static final By updateProfileLinkChildElement = By.xpath("//div[contains(@class, 'side')]/ul" +
            "[@class='expanded-nav']/li[1]");

    @FindBy(xpath = "//button[contains(text(), 'Save')]")
    public WebElement saveButton;

    @FindBy(xpath = "//div[contains(@class,'search-result-container')]/div[2]/div[1]")
    public WebElement firstResultInTheList;

    @FindBy(xpath = "//div[contains(text(), 'Add Another')]")
    public WebElement addAnotherPhoneNumberLink;

    @FindBy(id = "business-registration-number")
    public WebElement businessRegistrationNumber;

    @FindBy(xpath = "//input[@formcontrolname='phoneNumber']")
    public WebElement phoneNumberInput;

    @FindBy(xpath = "(//input[@formcontrolname='phoneNumber'])[2]/ancestor::div[2]//nb-icon")
    public WebElement removePhoneNumberInput;

    @FindBy(xpath = "(//input[@formcontrolname='phoneNumber'])[2]")
    public WebElement phoneNumberInput2;

    @FindBy(xpath = "(//nb-select[@formcontrolname='typeName'])[2]")
    public WebElement phoneNumberType2;

    @FindBy(xpath = "//div[not(contains(@class, 'agency'))]//p/strong")
    public WebElement agencyId;

    @FindBy(xpath = "//button[contains(text(), 'Go to Settings')]")
    public WebElement goToSettingsButton;
}
