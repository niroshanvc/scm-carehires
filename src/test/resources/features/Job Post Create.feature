@Regression-Create
@Job
@SuperAdminUser
Feature: Test CareHires create job

  Background: login to carehires
    Given User logins to carehires

  @CreateJobPost
  Scenario: Create a job
    When User navigates to Jobs page
    And User moves to Post Job page
    And User enters Job Details
    And User enters Job Preferences
    Then User enters Job Summary
    And User writes the job id into a text file

  @CreateJobPostWithBreaks
  Scenario: Create a job with breaks
    When User navigates to Jobs page
    And User moves to Post Job page
    And User enters Job Details with Breaks
    And User enters Job Preferences and enabling block booking
    Then User enters Job Summary