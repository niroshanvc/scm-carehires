@Regression-Create
@Job
@SuperAdminUser
Feature: Test CareHires create job post

  Background: login to carehires
    Given User logins to carehires
    And User navigates to Jobs page
    And User moves to Post New Job page

  @PostGeneralSleepInJobsCustomJobs1A
  Scenario: Custom jobs / Post 1 vacancy / No Skills / No Block booking / No job notes
    And User selects job type as Sleep In and proceed with custom job
    When User enters Job Preferences without Job note and disabling block booking
    Then User enters Job Summary

  @PostGeneralSleepInJobsCustomJobs1B
  Scenario: Custom jobs / Post more than 1 vacancy / With Skills / No Block booking / No job notes
    And User selects job type as Sleep In and proceed with multiple vacancies
    When User selects skills without Job note and disabling block booking
    Then User enters Job Summary

  @PostGeneralSleepInJobsCustomJobs1C
  Scenario: Custom jobs / With Skills / With Block booking / No job notes
    And User selects job type as Sleep In and proceed with custom job
    When User selects skills without Job note and enabling block booking
    Then User enters Job Summary

  @PostGeneralSleepInJobsCustomJobs1D
  Scenario: Custom jobs / With Skills / With Block booking / With job notes
    And User enters Job Details with Recurrence and with Breaks
    When User enters Job Preferences with Job note and enabling block booking
    Then User enters Job Summary
