@Regression-Edit
@Job
@SuperAdminUser
Feature: Test CareHires edit job

  Background: login to carehires
    Given User logins to carehires

  @EditJobPost
  Scenario: Edit a job post
    When User navigates to Jobs page
    And User moves to Post New Job page
    And User enters data to Job Details
    And User enters data to Job Preferences
    And User clicks on Edit button on Job Summary
    And User edits the Job Details
    And User edits Job Preferences
    And User enters Job Summary
