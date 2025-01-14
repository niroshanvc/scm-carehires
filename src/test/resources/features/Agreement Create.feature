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
    Then User verifies agreement signature status as PENDING TO SIGN
    And User clicks on Mark as signed button
    And User verifies agreement signature status as SIGNED
    And User clicks on Active Agreement button
    And User verifies agreement signature status as ACTIVE

  @CreateAgreementForDDProvider
  Scenario: Create an agreement
    When User navigates to Agreement Create page
    And user enters agreement overview data
    And User enters Worker Rates
    And User enters Cancellation Policy
    And User enters Sleep In Rates
    And User enters Policies for the provisions of service
    And User enters Signatories information
    Then User verifies agreement payment status as PENDING PAYMENT AUTHORISATION and signature status as PENDING TO SIGN
    And User clicks on Mark as signed button
    And User verifies agreement payment status as PENDING PAYMENT AUTHORISATION and signature status as SIGNED
    And User clicks on Active Agreement button
    And User verifies agreement payment status as PENDING PAYMENT AUTHORISATION and signature status as ACTIVE

  @ViewAgreement
  Scenario: View agreement and verify worker and sleep in rates
    When User navigates to Agreement View page
    Then User verifies available data in the worker rates table