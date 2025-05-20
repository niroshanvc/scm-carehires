@Regression
@Agreement
@SuperAdminUser
Feature: Test CareHires create agreement with disable Bid4Care

  Background: login to carehires
    Given User logins to carehires

  @CreateAgreement
  Scenario: Create an agreement
    When User navigates to Agreement Create page
    And user enters agreement overview data
    And User enters Worker Rates and verify calculations
    And User enters Cancellation Policy and verify calculations
    And User enters Sleep In Rates and verify calculations
    And User enters Policies for the provisions of service
    And User enters Signatories information
    Then User verifies agreement signature status as PENDING TO SIGN
    And User clicks on Mark as signed button and do verifications
    And User verifies agreement signature status as SIGNED
    And User clicks on Active Agreement button
    And User verifies agreement signature status as ACTIVE
    And User makes that agreement inactive
    And User verifies agreement signature status as INACTIVE
    And User marks it as active again
    And User verifies agreement signature status as ACTIVE

  @CreateAgreementForDDProvider
  Scenario: Create an agreement
    When User navigates to Agreement Create page
    And user enters agreement overview data
    And User enters Worker Rates and verify calculations
    And User enters Cancellation Policy and verify calculations
    And User enters Sleep In Rates and verify calculations
    And User enters Policies for the provisions of service
    And User enters Signatories information
    Then User verifies agreement payment status as PENDING PAYMENT AUTHORISATION and signature status as PENDING TO SIGN
    And User clicks on Mark as signed button and do verifications
    And User verifies agreement payment status as PENDING PAYMENT AUTHORISATION and signature status as SIGNED
    And User clicks on Active Agreement button
    And User verifies agreement payment status as PENDING PAYMENT AUTHORISATION and signature status as ACTIVE

  @ViewAgreement
  Scenario: View agreement and verify worker and sleep in rates
    When User navigates to Agreement View page
    Then User verifies available data in the worker rates table
    And User verifies available data in the Sleep in Request table

  @CreateAgreementWithoutSkills
  Scenario: Create an agreement without skills
    When User navigates to Agreement Create page
    And user enters agreement overview data
    And User enters Worker Rates without skills
    And User enters Cancellation Policy and verify calculations
