package com.carehires.actions.workers;

import com.carehires.pages.worker.CreateBasicInformationPage;
import com.carehires.utils.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

public class CreateWorkerBasicInformationActions {

    CreateBasicInformationPage basicInfo;

    private static final String YML_FILE = "worker-create";
    private static final String YML_AGENCY_FILE = "agency-create";
    private static final String YML_HEADER = "BasicInformation";
    private static final String YML_AGENCY_HEADER = "Location";
    private static final String YML_SUB_HEADER = "PersonalInformation";

    private static final Logger logger = LogManager.getLogger(CreateWorkerBasicInformationActions.class);

    public CreateWorkerBasicInformationActions() {
        basicInfo = new CreateBasicInformationPage();
        PageFactory.initElements(BasePage.getDriver(), basicInfo);
    }

    public void enterWorkerBasicInformationData() {

    }
}
