package com.carehires.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateAgencyBasicInfoPage {
    @FindBy(xpath = "(//input[@id='business-name'])[1]")
    public WebElement businessName;

    @FindBy(id = "business-registration-number")
    public WebElement businessRegistrationNumber;

    @FindBy(xpath = "(//input[@id='business-name'])[2]")
    public WebElement alsoKnownAs;


    @FindBy(id = "nb-autocomplete-0")
    public WebElement autoSuggestAddresses;

    @FindBy(xpath = "//input[@formcontrolname='postcode']")
    public WebElement postcode;

    @FindBy(xpath = "//input[@formcontrolname='phoneNumber']")
    public WebElement phoneNumberInput;

    @FindBy(xpath = "//button[contains(@class, 'float-end')]")
    public WebElement saveButton;
}
