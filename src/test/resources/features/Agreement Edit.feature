@Regression
@Agreement
@SuperAdminUser
Feature: Test CareHires edit agreement with disable Bid4Care

  Background: login to carehires
    Given User logins to carehires

  @EditAgreement
  Scenario: Edit an agreement
    When User navigates to Agreement View page
    And User searches previously created agreement
#    And User edit site
#    And User remove worker rates
#    And User edits worker rates
    And User removes cancellation policy
    And User add new cancellation policy
    And User removes sleep in request
    And User adds sleep in request
    And User downloads the manually signed agreement with agency and provider
