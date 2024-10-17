@Regression
@Worker
@SuperAdminUser
Feature: Test CareHires create worker

  Background: login to carehires
    Given User logins to carehires

  @CreateWorker
  Scenario: Create agency with one location
    When User navigates to Worker Create page
    And User enters valid worker - basic information