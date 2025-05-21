@Regression-Create
@Job
@SuperAdminUser
Feature: Test CareHires agreement's worker rates display in jobs

  Background: login to carehires
    Given User logins to carehires

  @VerifyJobPostForSpecialHoliday
  Scenario: Verify vat exempt provider has an agreement for a special holiday job
#    creating an agency
    And User navigates to Agency Create page
    And User creates an agency
    And User navigates to Agency View page
    When User finds recently created agency by agency name
    And User completes the profile approve status
    And User again navigates to the Agency Profile page
    And User verifies the agency profile status as Approved
#    creating a worker
    And User navigates to Worker Create page
    And User creates a worker
    And User collects workerId from Worker - Employment History page
    And User navigates to Worker View page
    When User finds recently created worker
    And User accepts all the compliance
    And User updates the worker profile as Submitted for Review
    And User updates the worker profile as Approve
    And User verifies the worker profile status as Approved
#    creating a provider
    And User navigates to Provider Create page
    And User creates a provider
    And User navigates to Provider View page
    And User finds recently created provider
    And User clicks on the approve button on provider information page
    And User verifies the provider profile status as Approved
#    creating an agreement
    And User navigates to Agreement Create page
    And user enters agreement overview data
    And User enters Worker Rates
    And User enters Cancellation Policy
    And User enters Sleep In Rates
    And User enters Policies for the provisions of service
    And User enters Signatories information
    And User clicks on Mark as signed button
    And User clicks on Active Agreement button
    And User verifies agreement signature status as ACTIVE
    And User moves to worker rates popup
    And User write downs Special Holiday Rate worker rates into a text file
    And User write downs Bank Holiday Rate worker rates into a text file
    And User closes worker rates popup
#    creating a job
    And User navigates to Jobs page
    And User moves to Post New Job page
    And User enters Job Details for special holiday
    And User enters Job Preferences
    And User enters Job Summary
#    When User suggests a worker
#    Then User moves to suggested workers tab and verifies Special Holiday worker rates