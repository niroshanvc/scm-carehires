package com.carehires.pages.agency;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SubContractingAgreementPage {

    @FindBy(xpath = "//button[contains(text(), 'Invite')]")
    public WebElement inviteButton;

    @FindBy(xpath = "//button[contains(text(), 'Complete Profile')]")
    public WebElement completeProfileButton;

    @FindBy(xpath = "//a[contains(text(), 'No')]")
    public WebElement noButton;
}
