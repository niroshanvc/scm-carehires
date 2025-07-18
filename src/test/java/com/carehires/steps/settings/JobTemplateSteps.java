package com.carehires.steps.settings;

import com.carehires.actions.settings.JobTemplateActions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class JobTemplateSteps {

    private final JobTemplateActions jobTemplate = new JobTemplateActions();

    @And("User moves to job templates page")
    public void userMovesToJobTemplatesPage() {
        jobTemplate.moveToJobTemplatesPage();
    }


    @And("User searches template by name")
    public void searchTemplateByName() {
        jobTemplate.searchTemplateByName();
    }

    @Then("^User makes job template (.*)$")
    public void inactiveJobTemplate(String status) {
        jobTemplate.doInactiveTemplate(status);
    }

    @And("Provider User creates a new job template")
    public void createNewJobTemplate() {
        jobTemplate.createNewJobTemplate();
    }
}
