package com.carehires.pages.jobs;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class JobSummaryPage {

    @FindBy(xpath = "//button[contains(text(), 'Post Job')]")
    public WebElement postJobButton;

    @FindBy(xpath = "//nb-dialog-container//nb-card-footer/a")
    public WebElement savePopupNoButton;

    @FindBy(xpath = "//nb-toast//span")
    public WebElement successMessage;

    @FindBy(xpath = "//button[contains(text(), 'Edit')]")
    public WebElement editIcon;
}
