Feature: Test CareHires create agency with one location


  Background: login to carehires
    Given User logins to carehires

  @Regression
  @CreateProvider
  Scenario: Create agency with one location
    When User navigates to Worker Create page
    And User enters valid worker - basic information