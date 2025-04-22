package com.carehires.pages.settings;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class JobTemplatePage {

    @FindBy(xpath = "//img[contains(@src, 'add')]/../p//img")
    public WebElement moveToTemplate;

    @FindBy(id = "business-name")
    public WebElement searchText;

    @FindBy(xpath = "//div[@class='result-list']/div")
    public WebElement firstResult;

    @FindBy(xpath = "//p[contains(@class, 'text-icon') and contains(text(), '…')]")
    public WebElement topThreeDots;

    @FindBy(xpath = "//div[contains(@class, 'side')]/ul[@class='expanded-nav']/li[1]")
    public WebElement inactiveMenuLink;

    public static final By INACTIVE_OR_ACTIVE_MENU_LINK = By.xpath("//div[contains(@class," +
            " 'side')]/ul[@class='expanded-nav']/li[1]");

    @FindBy(xpath = "//nb-card-footer//button")
    public WebElement confirmActionPopupYesButton;

    @FindBy(xpath = "//div[contains(@class, 'basic-profile-container')]//div[@class='text']")
    public WebElement templateStatus;

    @FindBy(xpath = "//nb-select[not(@id)]/button")
    public WebElement templateStatusDropdown;

    public String getDropdownOptionXpath(String templateStatus) {
        return String.format("//nb-option[contains(text(),'%s')]", templateStatus);
    }
}
