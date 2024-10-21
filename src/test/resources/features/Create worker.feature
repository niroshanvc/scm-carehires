@Regression
@Worker
@SuperAdminUser
Feature: Test CareHires create worker

  Background: login to carehires
    Given User logins to carehires

  @CreateWorker
  Scenario: Create a worker where nationality is British
    When User navigates to Worker Create page
    And User enters valid worker - basic information
    And User enters Document and Proof information
    And User enters Education and Training data