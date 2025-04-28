package com.carehires.pages.providers;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProviderWorkerRestrictionManagementPage {

    public String getDropdownOptionXpath(String city) {
        return String.format("//nb-option[contains(text(),'%s')]", city);
    }

    @FindBy(id = "worker")
    public WebElement workerInputField;

    @FindBy(xpath = "//nb-toast//span")
    public WebElement successMessage;

    @FindBy(xpath = "//button[contains(text(),'Update')]")
    public WebElement updateButton;

    @FindBy(xpath = "//button[contains(text(), 'Add')]")
    public WebElement addButton;

    @FindBy(xpath = "//div[@class='right-actions']/button")
    public WebElement actionsThreeDots;

    @FindBy(xpath = "//button[contains(text(), 'Add New')]")
    public WebElement addNewButton;

    @FindBy(xpath = "//nb-select[@formcontrolname='careHomes']/button")
    public WebElement siteDropdown;

    @FindBy(xpath = "//nb-select[@formcontrolname='agency']/button")
    public WebElement agencyDropdown;

    @FindBy(xpath = "//textarea[@formcontrolname='reason']")
    public WebElement reasonTextArea;

    @FindBy(xpath = "//nb-option-list/ul/nb-option[2]")
    public WebElement firstOptionInSiteDropdown;

    @FindBy(xpath = "//ul[@class='option-list']/nb-option")
    public WebElement firstOptionInAgencyDropdown;

    @FindBy(xpath = "//nb-option-list/ul/nb-option")
    public WebElement firstWorker;

    @FindBy(xpath = "//nb-tabset/ul/li/a/span[text()='Pending Approval']")
    public WebElement pendingApprovalTab;

    @FindBy(xpath = "(//nb-tab//div[@class='action-panel']/button[contains(@class, 'success')])[1]")
    public WebElement firstApproveButton;

    public String firstRowInTable = "//div[contains(@class, 'ch-table-new-content') and not(contains(" +
            "@class, 'header'))]";

    @FindBy(xpath = "//div[contains(@class, 'ch-table-new-content')]//nb-icon[@status='danger']")
    public WebElement firstDeleteIcon;

    @FindBy(xpath = "//nb-card-footer/button")
    public WebElement confirmButton;
}
