@Regression
@Worker
@SuperAdminUser
Feature: Test CareHires create worker

  Background: login to carehires
    Given User logins to carehires

  @VerifyNonBritishWorker
  Scenario: Create a worker where nationality is British
    When User navigates to Worker Create page
    And User enters valid non-British worker basic information
    And User enters Document and Proof information
    And User enters Education and Training data
    And User enters Emergency Information data
    And User enters Vaccination and Allergy Information data
    And User enters Employment Information data
    And User collects workerId from Worker - Employment History page
    And User navigates to Worker View page
    When User finds recently created worker
    Then User verifies the worker profile status as Profile Complete
    And User accepts all the compliance
    And User updates the worker profile as Submitted for Review
    And User verifies the worker profile status as Submitted For Review
    And User updates the worker profile as Approve
    And User verifies the worker profile status as Approved