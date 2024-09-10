Feature: Test CareHires signin page

  @Login
    @Regression
  Scenario: Test signin page of the care hires website
    Given User navigates to the signin page
    When User enters valid username and password
    Then verifies the title of the landing page
#    And User navigates to Agency Create page