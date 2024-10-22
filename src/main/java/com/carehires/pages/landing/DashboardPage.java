package com.carehires.pages.landing;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DashboardPage {

    @FindBy(xpath = "//nav[contains(@class, 'TopBar')]/p[3]")
    public WebElement securityLink;
}
