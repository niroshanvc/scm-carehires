package com.carehires.pages.worker;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ViewWorkerProfilePage {

    @FindBy(xpath = "//button[contains(@class, 'primary-outline float')]")
    public WebElement viewComplianceButton;

    @FindBy(xpath = "//button[contains(@class, 'primary float')]")
    public WebElement submitForReviewButton;

    @FindBy(xpath = "//div[contains(@class, 'status')]/div/div")
    public WebElement profileStatus;

    @FindBy(xpath = "//nb-toast//span")
    public WebElement successMessage;

    @FindBy(xpath = "//div[@class='profile-container-compliance']")
    public WebElement workerComplianceOverviewWidget;

    @FindBy(xpath = "//div[contains(@class,'compliance-table')]//nb-checkbox//input")
    public List<WebElement> complianceCheckboxes;

    @FindBy(xpath = "//nb-tab//button[contains(text(), 'Save')]")
    public WebElement complianceSaveButton;

    @FindBy(xpath = "//span[contains(text(), 'Review Profile')]/ancestor::button")
    public WebElement reviewProfileButton;

    @FindBy(xpath = "//p[text()='General Compliance']/ancestor::div[contains(@class, 'multiple')]/div[@style]")
    public WebElement generalComplianceStatusMessage;

    @FindBy(xpath = "//button[contains(text(), 'Approve')]")
    public WebElement approveButton;
}
