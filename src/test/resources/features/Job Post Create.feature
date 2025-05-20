@Regression
@Job
@GeneralJobPosting
@SuperAdminUser
Feature: Test CareHires create job post without template

  Background: login to carehires
    Given User logins to carehires
    And User navigates to Jobs page
    And User moves to Post New Job page

  @CreateJobPost
  Scenario: Create a job
    And User enters Job Details
    And User enters Job Preferences
    When User enters Job Summary
    And User writes the job id into a text file

  @CreateJobPostWithBreaks
  Scenario: Create a job with breaks
    And User enters Job Details with Breaks
    When User enters Job Preferences and enabling block booking
    Then User enters Job Summary

  @PostGeneralJobsCustomJobs1A
  Scenario: Custom jobs / Without recurrence / Without breaks/ No Skills / No Block booking / No job notes
    And User enters Job Details without Recurrence and Breaks
    When User enters Job Preferences without Job note and disabling block booking
    Then User enters Job Summary
    And User suggests a worker
    And User rejects suggested worker
    And User selects rejected worker
    And User rejects selected worker
    And User selects rejected worker

  @PostGeneralJobsCustomJobs1B
  Scenario: Custom jobs / With recurrence / With breaks / With Skills / With Block booking / With job notes
    And User enters Job Details with Recurrence and Breaks
    When User enters Job Preferences with Job note and enabling block booking
    Then User enters Job Summary

  @PostGeneralJobsCustomJobs1C
  Scenario: Custom jobs / With recurrence / Without breaks/ No Skills / No Block booking / No job notes
    And User enters Job Details with Recurrence and without Breaks
    When User enters Job Preferences without Job note and disabling block booking
    Then User enters Job Summary

  @PostGeneralJobsCustomJobs1D
  Scenario: Custom jobs / With recurrence / With breaks/ No Skills / No Block booking / No job notes
    And User enters Job Details with Recurrence and with Breaks
    When User enters Job Preferences without Job note and disabling block booking
    Then User enters Job Summary

  @PostGeneralJobsCustomJobs1E
  Scenario: Custom jobs / With recurrence / With breaks / With Skills / No Block booking / No job notes
    And User enters Job Details with Recurrence and Breaks
    When User enters Job Preferences with skills and without Job note and disabling block booking
    Then User enters Job Summary

  @PostGeneralJobsCustomJobs1F
  Scenario: Custom jobs / With recurrence / With breaks / With Skills / With Block booking / No job notes
    And User enters Job Details with Recurrence and Breaks
    When User enters Job Preferences with skills and enabling block booking but no job notes
    Then User enters Job Summary

  @PostGeneralJobsCustomJobs1G
  Scenario: Custom jobs / With recurrence / With breaks / With Skills / With Block booking / With job notes
    And User enters Job Details with Recurrence and Breaks
    When User enters Job Preferences with skills and enabling block booking but no job notes
    And User enters Job Summary
