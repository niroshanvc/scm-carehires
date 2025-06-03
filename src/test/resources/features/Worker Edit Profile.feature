@Regression
@Worker
@SuperAdminUser
Feature: Test CareHires edit worker and make profile approved

  Background: login to carehires
    Given User logins to carehires

  @EditWorker
  Scenario: Edit worker details where profile is in draft stage
    When User navigates to Worker Create page
    And User creates a worker in draft stage
    And User moves to Basic Information page again
    And User collects workerId from Basic Information page
    And User logins off from Carehires
    And User again logins to carehires
    And User navigates to Worker View page
    And User finds recently created worker
    And User edit worker profile data
    And User moves to Documents and Proof and edit data
    And User moves to Education and Training and edit data
    And User moves to Emergency and edit data
    And User moves to Medical and edit data
    And User moves to Employment and edit data
    And User navigates to Worker View page
    When User finds recently created worker
    Then User verifies the worker profile status as Profile Complete
    And User accepts all the compliance
    And User updates the worker profile as Submitted for Review
    And User verifies the worker profile status as Submitted For Review
    And User updates the worker profile as Approve
    And User verifies the worker profile status as Approved