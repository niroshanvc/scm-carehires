@Regression-Create
@Job
@SuperAdminUser
Feature: Test CareHires create job post

  Background: login to carehires
    Given User logins to carehires
    And User navigates to Jobs page
    And User moves to Post Job page

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

  @PostGeneralJobsCustomJobs
  Scenario: Custom jobs / Without recurrence / Without breaks/ No Skills / No Block booking / No job notes
    And User enters Job Details without Recurrence, Breaks and No Skills
    When User enters Job Preferences without Job note and disabling block booking
    Then User enters Job Summary