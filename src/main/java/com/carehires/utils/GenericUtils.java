package com.carehires.utils;

import com.carehires.pages.agency.CreateAgencyBasicInfoPage;
import com.carehires.pages.GenericElementsPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
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
        BasePage.typeWithStringBuilderAndDelay(postcodeInput, postcode, 150);
        BasePage.waitUntilElementPresent(createAgencyBasicInfoPage.autoSuggestAddresses, 60);
        List<WebElement> addresses = getDriverInstance().findElements(By.xpath("//nb-option[contains(@id, 'nb-option')]"));
        if (!addresses.isEmpty()) {
            addresses.get(1).click();
        } else {
            throw new RuntimeException("Address not found");
        }
    }

    public void fillPhoneNumber(String ymlFile, String key, String subKey, WebElement phoneNumberInput) {
        logger.info("fillPhoneNumber");
        String phoneNumber = DataConfigurationReader.readDataFromYmlFile(ymlFile, key, subKey);
        BasePage.clickWithJavaScript(genericElementsPage.phoneNumberDropdown);
        BasePage.clickWithJavaScript(genericElementsPage.mobile);
        BasePage.typeWithStringBuilder(phoneNumberInput, phoneNumber);
    }

    public void waitUntilEmailAddressValidatedForUniqueness(String emailAddress, WebElement emailAddressInput,
                                                            WebElement fieldToBeDisplayed) {
        logger.info("waitUntilEmailAddressValidatedForUniqueness");

        BasePage.waitUntilElementPresent(emailAddressInput, 60);
        BasePage.typeWithStringBuilder(emailAddressInput, emailAddress);
        BasePage.clickWithJavaScript(genericElementsPage.validateEmailTextLink);

        try {
            BasePage.waitUntilElementPresent(fieldToBeDisplayed, 60);
        } catch (ElementNotInteractableException e) {
            // Throw a dedicated exception with a more specific message
            throw new EmailAddressNotUniqueException("Email address is not guaranteed to be unique. " +
                    "Element remained hidden after validation.");
        }
    }

    // Define a dedicated exception for email address uniqueness issue
    public static class EmailAddressNotUniqueException extends RuntimeException {
        public EmailAddressNotUniqueException(String message) {
            super(message);
        }
    }
}
