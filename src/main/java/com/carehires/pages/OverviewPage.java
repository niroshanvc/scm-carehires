package com.carehires.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OverviewPage {
    @FindBy(id = "Overview")
    public WebElement overviewMenu;

    @FindBy(xpath = "//a[contains(text(),'Accept')]")
    public WebElement acceptCookie;
    
    @FindBy(xpath = "//iframe[@title='modal'  and not(@aria-hidden)]")
    public WebElement newVersionPopupIframe;

    @FindBy(xpath = "//button[@data-testid]")
    public WebElement closeNewVersionPopupButton;
}
