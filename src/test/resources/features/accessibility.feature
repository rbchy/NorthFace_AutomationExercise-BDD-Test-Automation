@accessibility @regression
Feature: Demo shop accessibility basics
  As a shopper using assistive technology
  I want core accessibility signals present
  So that the site is usable

  @regression @positive
  Scenario: Home page meets basic accessibility requirements
    Given the shopper opens the demo shop for accessibility checks
    Then the page should declare a document language
    And the page title should not be empty

  @regression @negative
  Scenario: Search field is not missing an accessible name
    Given the shopper opens the demo products page for accessibility checks
    Then the product search field should have an accessible name
