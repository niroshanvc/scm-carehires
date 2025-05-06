package com.carehires.reports;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

import java.io.File;
import java.util.Collections;

public class CucumberReportGenerator {

    public static void main(String[] args) {
        File reportOutputDirectory = new File("target/cucumber-html-reports");
        String jsonFile = "target/cucumber.json";

        Configuration configuration = new Configuration(reportOutputDirectory, "CareHires");
        ReportBuilder reportBuilder = new ReportBuilder(Collections.singletonList(jsonFile), configuration);
        reportBuilder.generateReports();
    }
}
