@Regression-Create
@Agreement
@SuperAdminUser
Feature: Test CareHires create agreement with disable Bid4Care

  Background: login to carehires
    Given User logins to carehires

  @CreateAgreement
  Scenario: Create an agreement
    When User navigates to Agreement Create page
    And user enters agreement overview data
    And User enters Worker Rates