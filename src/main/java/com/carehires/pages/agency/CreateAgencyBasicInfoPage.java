package com.carehires.pages.agency;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateAgencyBasicInfoPage {
    @FindBy(xpath = "(//input[@id='business-name'])[1]")
    public WebElement businessName;

    @FindBy(id = "business-registration-number")
    public WebElement businessRegistrationNumber;

    @FindBy(xpath = "//span[contains(@class, 'image-edit')]")
    public WebElement uploadLogo;

    @FindBy(xpath = "//input[@type='file']")
    public WebElement fileInputButton;

    @FindBy(xpath = "//button[contains(text(),'Save')]")
    public WebElement saveButton;

    @FindBy(xpath = "(//input[@id='business-name'])[2]")
    public WebElement alsoKnownAs;

    @FindBy(xpath = "//nb-option-list[contains(@id, 'nb-autocomplete')]")
    public WebElement autoSuggestAddresses;

    @FindBy(xpath = "//input[@formcontrolname='postcode']")
    public WebElement postcode;

    @FindBy(xpath = "//input[@formcontrolname='phoneNumber']")
    public WebElement phoneNumberInput;

    @FindBy(xpath = "//nb-card-footer/button[text()='Save']")
    public WebElement imageSaveButton;

    @FindBy(xpath = "//button[contains(text(), 'Skip')]")
    public WebElement skipButton;

    public static final String BASIC_INFORMATION_SUB_XPATHS = "//h6[text()='Basic Information']/..//*";

    @FindBy(xpath = "//nb-toast//span")
    public WebElement successMessage;

    @FindBy(xpath = "//h6[text()='Basic Information']/..//*/ancestor::nb-icon")
    public WebElement basicInformationCompletedIcon;

    @FindBy(xpath = "//div[not(contains(@class, 'provider'))]//p/strong")
    public WebElement agencyId;
}
