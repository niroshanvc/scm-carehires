package com.carehires.pages.agency;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AgencyProfilePage {

    @FindBy(xpath = "//button[contains(text(), 'Approve')]")
    public WebElement approveButton;

    @FindBy(xpath = "//button[contains(text(), 'Go to Settings')]")
    public WebElement goToSettingsButton;

    @FindBy(xpath = "//div[contains(text(), 'Profile')]/../div/img")
    public WebElement profileIcon;

    @FindBy(xpath = "//div[contains(@class, 'status') and (contains(@class, 'warning') or (contains(@class, 'success')))]")
    public WebElement profileStatus;

    @FindBy(xpath = "//nb-dialog-container//h5")
    public WebElement popupText;

    @FindBy(xpath = "//nb-toast//span")
    public WebElement successMessage;
}
