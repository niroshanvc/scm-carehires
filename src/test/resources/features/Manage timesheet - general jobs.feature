@Regression-Create
@Job
@SuperAdminUser
Feature: Test CareHires manage timesheet - general jobs

  Background: login to carehires
    Given User logins to carehires
    And User navigates to Jobs page
    And User moves to Post New Job page

  @ApproveGeneral
  Scenario: General job - approve timesheet
    And User selects job type as General and proceed with Custom Job
    And User enters Job Preferences for general job
    And User enters Job Summary
    And User suggests a worker for general job
    And User selects suggested worker
    When User enters timesheets data for general job
    Then User Approves the timesheet entered

  @ResubmitGene
  Scenario: General job - resubmit timesheet
    And User enters job type as General and Custom Job
    And User enters Job Preferences for general job
    And User enters Job Summary
    And User suggests a worker for general job
    And User selects suggested worker
    And User completes timesheets entry for general job
    And User Disputes the timesheet entered
#    When User proceeds with submit timesheet again
#    Then User Approves the timesheet entered