@Regression-Create
@Job
@SuperAdminUser
Feature: Test CareHires manage timesheet - general jobs

  Background: login to carehires
    Given User logins to carehires
    And User navigates to Jobs page
    And User moves to Post New Job page

  @DetailViewCancelJobOpenShift
  Scenario: Test cancel job from job detail screen
    When User creates a job for cancellation
    Then User clicks on the Cancel Job menu in job detail page

  @ListViewCancelJobOpenShift
  Scenario: Test cancel job from job view page
    When User creates a job for cancellation
    Then User clicks on the Cancel Job menu in job view page
    And User navigates to Jobs page
    And User modifies the cancellation reason

  @CancelOpenOverDueShift
  Scenario: Test cancel job from job detail screen
    When User creates an over-due job for cancellation
    Then User clicks on the Cancel Job menu in job view page

  @CancelAllocatedShift
  Scenario: Test cancel job from job detail screen
    When User creates an over-due job for cancellation
    And User suggests a worker for job cancellation
    And User selects suggested worker
    And User clicks on job detail close icon
    Then User clicks on the Cancel Job menu in job view page

  @CancelTimesheetSubmittedShift
  Scenario: Test cancel job from job detail screen
    When User creates an over-due job for cancellation
    And User suggests a worker for job cancellation
    And User selects suggested worker
    And User enters timesheets data for job cancellation
    And User clicks on job detail close icon
    Then User clicks on the Cancel Job menu in job view page

  @CancelTimesheetDisputedShift
  Scenario: Test cancel job from job detail screen
    When User creates an over-due job for cancellation
    And User suggests a worker for job cancellation
    And User selects suggested worker
    And User enters timesheets data for job cancellation
    And User Disputes the timesheet entered for job cancellation
    And User clicks on job detail close icon
    Then User clicks on the Cancel Job menu in job view page