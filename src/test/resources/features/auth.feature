@demo @regression @functional @auth
Feature: Demo shop authentication
  As a shopper
  I want registration and login to behave correctly
  So that only valid users get in

  Background:
    Given the shopper is on the demo login page

  @regression @positive
  Scenario: New user can start registration
    When the shopper starts signup with a new random email
    Then the account information page should be shown

  @regression @negative
  Scenario: Login with invalid credentials is rejected
    When the shopper logs in with invalid credentials
    Then a login error should be shown
