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
    And User enters Cancellation Policy
    And User enters Sleep In Rates
    And User enters Policies for the provisions of service
    And User enters Signatories information
    Then User verifies agreement payment status and signature status
    And User clicks on Mark as signed button