@Regression
@Job
@SuperAdminUser
Feature: Test CareHires view job

  Background: login to carehires
    Given User logins to carehires
    When User navigates to Jobs page

  @ViewJob
  Scenario: Test view job
    And User searches jobs by date range
    And User searches jobs by provider
    And User filters jobs by status as All Open
    And User filters jobs by status as Open Over-due
    And User filters jobs by status as Suggested
    And User filters jobs by status as Allocated
    And User filters jobs by status as Completed
    And User filters jobs by status as Cancelled

  @ManageAllocations
    @Worker
  Scenario: Test manage allocations for a job
    And User navigates to Worker Create page
    And User enters valid worker - basic information
    And User enters Document and Proof information
    And User enters Education and Training data
    And User enters Emergency Information data
    And User enters Vaccination and Allergy Information data
    And User enters Employment Information data
    And User collects workerId from Worker - Employment History page
    And User navigates to Worker View page
    When User finds recently created worker
    And User accepts all the compliance
    And User updates the worker profile as Submitted for Review
    And User updates the worker profile as Approve
    And User verifies the worker profile status as Approved
    And User navigates to Jobs page
    And User creates a job
    And User assign a worker to recently posted job

  @JobsAgencyView
  Scenario: Test functionality of job's agency view
    And User creates a job
    When User navigates to Jobs Agency View page
    Then User views recently created job post
    And User Cancels recently posted job

  @JobSiteView
  Scenario: Test functionality of job's site view