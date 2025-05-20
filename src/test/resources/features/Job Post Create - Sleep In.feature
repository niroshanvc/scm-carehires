@Regression
@Job
@SuperAdminUser
Feature: Test CareHires create sleep in job post

  Background: login to carehires
    Given User logins to carehires
    And User navigates to Jobs page
    And User moves to Post New Job page

  @PostSleepInJobsCustomJobs1A
  Scenario: Custom jobs / Post 1 vacancy / No Skills / No Block booking / No job notes
    And User selects job type as Sleep In and proceed with custom job
    When User enters Job Preferences without Job note and disabling block booking
    Then User enters Job Summary

  @PostSleepInJobsCustomJobs1B
  Scenario: Custom jobs / Post more than 1 vacancy / With Skills / No Block booking / No job notes
    And User selects job type as Sleep In and proceed with multiple vacancies
    When User selects skills without Job note and disabling block booking
    Then User enters Job Summary

  @PostSleepInJobsCustomJobs1C
  Scenario: Custom jobs / With Skills / With Block booking / No job notes
    And User selects job type as Sleep In and proceed with custom job
    When User selects skills without Job note and enabling block booking
    Then User enters Job Summary

  @PostSleepInJobsCustomJobs1D
  Scenario: Custom jobs / With Skills / With Block booking / With job notes
    And User selects job type as Sleep In and proceed with custom job
    When User selects skills with Job note and enabling block booking
    Then User enters Job Summary
