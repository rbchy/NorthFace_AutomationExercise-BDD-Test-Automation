@demo @smoke
Feature: Demo shop smoke
  As a shopper
  I want the demo shop to load
  So that E2E flows have a stable base

  @smoke
  Scenario: Demo shop home page loads
    Given the shopper is on the demo shop
    Then the demo shop home page should load
