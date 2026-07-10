@demo @regression @negative
Feature: Demo shop negative search
  As a shopper
  I want clear feedback when a search matches nothing
  So that I am not confused by an empty page

  Background:
    Given the shopper is on the demo shop

  @regression @negative
  Scenario: Searching gibberish shows no products
    When the shopper searches products for "zxqwlkjhgfd12345"
    Then no demo products should be shown
