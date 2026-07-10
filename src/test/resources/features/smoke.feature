@smoke @northface
Feature: North Face smoke tests - core pages load
  As a shopper
  I want the essential North Face pages and controls to load
  So that I can trust the site is up

  # NOTE: thenorthface.com is protected by PerimeterX bot detection, which
  # blocks automated search/cart/checkout. Only bot-safe read-only checks
  # (page load, logo, navigation presence) are exercised here. Full
  # search -> cart -> checkout E2E runs against the demo shop (@demo).

  Background:
    Given the user is on the North Face home page

  @smoke @regression
  Scenario: Home page loads with brand logo
    Then the brand logo should be displayed
    And the page title should contain "The North Face"

  @smoke @regression
  Scenario: Primary navigation is available
    Then the primary navigation should be displayed
