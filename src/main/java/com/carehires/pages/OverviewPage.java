package com.carehires.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OverviewPage {
    @FindBy(id = "Overview")
    public WebElement overviewMenu;

    @FindBy(xpath = "//a[contains(text(),'Accept')]")
    public WebElement acceptCookie;

    public static final By closeNewVersionPopupButtonByLocator = By.xpath("//button[@data-testid]");

    @FindBy(xpath = "//button[@data-testid]")
    public WebElement closeNewVersionPopupButton;
}
