@Regression
@Agreement
@SuperAdminUser
Feature: Test CareHires agreement overview

  Background: login to carehires
    Given User logins to carehires

  @OverviewAgreement
  Scenario: Agreement Overview
    When User navigates to Agreement Overview page
    Then User verifies Total Agreements link
    And User verifies Issue Agreement link
