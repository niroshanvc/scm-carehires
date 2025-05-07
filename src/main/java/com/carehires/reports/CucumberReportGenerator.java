package com.carehires.reports;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

import java.io.File;
import java.util.Collections;

public class CucumberReportGenerator {

    public static void main(String[] args) {
        File reportOutputDirectory = new File("target/cucumber-html-reports");

        // Ensure the output directory exists
        if (!reportOutputDirectory.exists()) {
            boolean created = reportOutputDirectory.mkdirs();
            if (!created) {
                System.err.println("Failed to create report output directory: " + reportOutputDirectory.getAbsolutePath());
                return;
            }
        }

        String jsonFile = "target/cucumber-reports/cucumber.json"; // Updated JSON file path

        Configuration configuration = new Configuration(reportOutputDirectory, "CareHires");
        configuration.addClassifications("Environment", "Dev");
        configuration.addClassifications("Browser", "Chrome");

        ReportBuilder reportBuilder = new ReportBuilder(Collections.singletonList(jsonFile), configuration);
        reportBuilder.generateReports();
    }
}
