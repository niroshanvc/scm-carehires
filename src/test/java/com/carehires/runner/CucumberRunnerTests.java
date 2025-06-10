package com.carehires.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.carehires.steps", "com.carehires.hooks"},
        plugin = {
                "json:target/cucumber-reports/cucumber.json", // JSON report in a separate directory
                "pretty", // Prints Gherkin source with additional colors and stack traces for errors
                "html:target/cucumber-reports/cucumber-html-report.html" // HTML report in a separate directory
        },
        monochrome = true,
        tags = "@ProviderUserCreateJobTemplate" // Default constant value
)

public class CucumberRunnerTests extends AbstractTestNGCucumberTests {

}