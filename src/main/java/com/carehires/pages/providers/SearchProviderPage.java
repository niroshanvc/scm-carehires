package com.carehires.pages.providers;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SearchProviderPage {

    @FindBy(xpath = "//input[(@id='business-name') and (not(@formcontrolname))]")
    public WebElement searchByText;

    public static final String PROVIDER_XPATH = "//div[contains(@class, 'result-list')]/div";

    @FindBy(xpath = "//div[contains(@class, 'basic-header') and not(contains(@class, 'provider'))]/h3")
    public WebElement providerName;

    @FindBy(xpath = "//div[contains(@class, 'content-empty')]//h5")
    public WebElement noResultText;

    @FindBy(xpath = "//div[@class= 'result' or contains(@class, 'result-active')]//p[contains(@class, 'id')]")
    public List<WebElement> providerIds;

    @FindBy(xpath = "//div[@class= 'result' or contains(@class, 'result-active')][1]//p[contains(@class, 'id')]")
    public WebElement firstProviderId;
}
