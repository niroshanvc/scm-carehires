package com.carehires.pages.worker;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WorkerNavigationMenuPage {

    @FindBy(xpath = "//div[text()= 'Profile']/../..")
    public WebElement profile;

    @FindBy(xpath = "//div[text()= 'Profile']/..//img")
    public WebElement profileImage;

    @FindBy(xpath = "//div[text()= 'Documents']/../..")
    public WebElement documents;

    @FindBy(xpath = "//div[text()= 'Documents']/..//img")
    public WebElement documentsImage;

    @FindBy(xpath = "//div[text()= 'Certificates']/../..")
    public WebElement certificates;

    @FindBy(xpath = "//div[text()= 'Certificates']/..//img")
    public WebElement certificatesImage;

    @FindBy(xpath = "//div[text()= 'Emergency']/../..")
    public WebElement emergency;

    @FindBy(xpath = "//div[text()= 'Emergency']/..//img")
    public WebElement emergencyImage;

    @FindBy(xpath = "//div[text()= 'Medical']/../..")
    public WebElement medical;

    @FindBy(xpath = "//div[text()= 'Medical']/..//img")
    public WebElement medicalImage;

    @FindBy(xpath = "//div[text()= 'Employment']/../..")
    public WebElement employment;

    @FindBy(xpath = "//div[text()= 'Employment']/..//img")
    public WebElement employmentImage;

    @FindBy(xpath = "//div[text()= 'History']/../..")
    public WebElement history;

    @FindBy(xpath = "//div[text()= 'History']/..//img")
    public WebElement historyImage;
}
