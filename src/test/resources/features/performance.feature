@performance @regression
Feature: Demo shop performance smoke
  As a QA engineer
  I want page load time within budget
  So that performance regressions are caught early

  Background:
    Given the shopper measures the demo shop load time

  @regression @positive
  Scenario: Home page loads within the performance budget
    Then the page should load within the performance budget

  @regression @negative
  Scenario: Load time exceeds an unrealistic 1 ms budget
    Then the page load time should exceed 1 ms
