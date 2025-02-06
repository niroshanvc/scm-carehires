@Regression-Edit
@Job
@SuperAdminUser
Feature: Test CareHires create job

  Background: login to carehires
    Given User logins to carehires

  @EditJobPost
  Scenario: Create a job with breaks
    When User navigates to Jobs page
    And User moves to Post Job page
    And User enters data to Job Details
    And User enters data to Job Preferences
    And User clicks on Edit button on Job Summary
    And User edits the Job Details
    And User edits Job Preferences
    And User enters Job Summary
