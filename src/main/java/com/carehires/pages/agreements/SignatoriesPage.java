package com.carehires.pages.agreements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignatoriesPage {

    @FindBy(xpath = "(//input[@formcontrolname='name'])[1]")
    public WebElement agencyName;

    @FindBy(xpath = "(//input[@formcontrolname='designation'])[1]")
    public WebElement agencyDesignation;

    @FindBy(xpath = "(//input[@formcontrolname='email'])[1]")
    public WebElement agencyEmail;

    @FindBy(xpath = "(//input[@formcontrolname='name'])[2]")
    public WebElement providerName;

    @FindBy(xpath = "(//input[@formcontrolname='designation'])[2]")
    public WebElement providerDesignation;

    @FindBy(xpath = "(//input[@formcontrolname='email'])[2]")
    public WebElement providerEmail;

    @FindBy(xpath = "//nb-checkbox[@status='basic']//input")
    public WebElement agreementCheckbox;

    @FindBy(xpath = "//button[contains(text(), 'Finalise')]")
    public WebElement finaliseAndCheckButton;

    @FindBy(xpath = "//nb-card[@class='confirm-card']//button")
    public WebElement authoriseAndCreateButton;

    @FindBy(xpath = "//nb-toast//span")
    public WebElement successMessage;
}
