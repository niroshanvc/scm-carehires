package com.carehires.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GenericElementsPage {
    @FindBy(xpath = "//label[contains(text(), 'Phone Number')]/following-sibling::div//nb-select/button")
    public WebElement phoneNumberDropdown;

    @FindBy(id = "nb-option-4")
    public WebElement mobile;

    @FindBy(xpath = "//p[contains(text(), 'Validate Email')]")
    public WebElement validateEmailTextLink;
}
