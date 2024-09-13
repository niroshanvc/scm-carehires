package com.carehires.utils;

import com.carehires.pages.CreateAgencyBasicInfoPage;
import com.carehires.pages.GenericElementsPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class GenericUtils {

    CreateAgencyBasicInfoPage createAgencyBasicInfoPage;
    GenericElementsPage genericElementsPage;

    public GenericUtils() {
        createAgencyBasicInfoPage = new CreateAgencyBasicInfoPage();
        PageFactory.initElements(BasePage.getDriver(), createAgencyBasicInfoPage);

        genericElementsPage = new GenericElementsPage();
        PageFactory.initElements(BasePage.getDriver(), genericElementsPage);
    }

    private static final Logger logger = LogManager.getLogger(GenericUtils.class);

    private static WebDriver getDriverInstance(){
        return BasePage.getDriver();
    }

    public void fillAddress(WebElement postcodeInput, String postcode) {
        logger.info("fillAddress");
        BasePage.typeWithStringBuilder(postcodeInput, postcode);
        BasePage.waitUntilElementPresent(createAgencyBasicInfoPage.autoSuggestAddresses, 60);
        List<WebElement> addresses = getDriverInstance().findElements(By.xpath("//nb-option[contains(@id, 'nb-option')]"));
        if (!addresses.isEmpty()) {
            addresses.get(1).click();
        } else {
            throw new RuntimeException("Address not found");
        }
    }

    public void fillPhoneNumber(WebElement phoneNumberInput) {
        logger.info("fillPhoneNumber");
        String phoneNumber = DataConfigurationReader.readDataFromYmlFile("agency-create", "BasicInfo", "PhoneNumber");
        BasePage.clickWithJavaScript(genericElementsPage.phoneNumberDropdown);
        BasePage.clickWithJavaScript(genericElementsPage.mobile);
        BasePage.typeWithStringBuilder(phoneNumberInput, phoneNumber);
    }
}
