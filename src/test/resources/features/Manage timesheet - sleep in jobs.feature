@Regression-Create
@Job
@SuperAdminUser
Feature: Test CareHires create job post

  Background: login to carehires
    Given User logins to carehires
    And User navigates to Jobs page
    And User moves to Post New Job page

  @ApproveSleepIn
  Scenario: Sleep in job - manage timesheet
    And User selects job type as Sleep In and proceed with Custom Job
    And User enters Job Preferences for sleep in job
    And User enters Job Summary
    And User suggests a worker for sleep in job
    And User selects suggested worker
    When User enters timesheets data
    Then User Approves the timesheet entered

  @DisputeSleepIn
  Scenario: Sleep in job - manage timesheet
    And User selects job type as Sleep In and Custom Job
    And User enters Job Preferences for sleep in job
    And User enters Job Summary
    And User suggests a worker for sleep in job
    And User selects suggested worker
    When User enters timesheets data for dispute
    Then User Disputes the timesheet entered

