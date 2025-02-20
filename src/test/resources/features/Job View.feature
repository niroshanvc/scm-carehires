@Regression-View
@Job
@SuperAdminUser
Feature: Test CareHires create job

  Background: login to carehires
    Given User logins to carehires

  @ViewJob
  Scenario: Test view job
    When User navigates to Jobs page
#    And User searches jobs by date range
#    And User searches jobs by provider
#    And User filters jobs by status as All Open
#    And User filters jobs by status as Open Over-due
#    And User filters jobs by status as Suggested
#    And User filters jobs by status as Allocated
#    And User filters jobs by status as Completed
#    And User filters jobs by status as Cancelled
#    And User suggests a worker
    And User rejects suggested worker
    And User selects rejected worker
    And User rejects selected worker
    And User selects rejected worker

