package com.carehires.pages.worker;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SearchWorkerPage {

    @FindBy(xpath = "//input[(@id='business-name') and (not(@formcontrolname))]")
    public WebElement searchByText;

    public static final String WORKER_XPATH = "//div[contains(@class, 'result-list')]/div";

    @FindBy(xpath = "//div[contains(@class, 'basic-profile')]//h3")
    public WebElement workerName;

    @FindBy(xpath = "//div[contains(@class, 'content-empty')]//h5")
    public WebElement noResultText;

    @FindBy(xpath = "//div[contains(@class, 'result-list')]/div//p[contains(@class, 'id')]")
    public List<WebElement> workerIds;

    @FindBy(xpath = "//div[@class= 'result' or contains(@class, 'result-active')][1]//p[contains(@class, 'id')]")
    public WebElement firstWorkerId;
}
