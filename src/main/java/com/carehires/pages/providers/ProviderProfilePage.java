package com.carehires.pages.providers;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProviderProfilePage {

    @FindBy(xpath = "//button[contains(text(), 'Approve')]")
    public WebElement approveButton;

    @FindBy(xpath = "//div[contains(text(), 'Profile')]/../div/img")
    public WebElement profileIcon;

    @FindBy(xpath = "//div[contains(@class, 'status') and (contains(@class, 'warning') or (contains(@class, 'success')))]")
    public WebElement profileStatus;

    @FindBy(xpath = "//nb-toast//span")
    public WebElement successMessage;
}