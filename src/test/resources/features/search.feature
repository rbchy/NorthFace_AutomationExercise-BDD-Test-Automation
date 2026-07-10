@demo @regression @search
Feature: Demo shop product search
  As a shopper
  I want to search the demo catalogue
  So that I can find products

  Background:
    Given the shopper is on the demo shop

  @regression @data-driven
  Scenario Outline: Valid searches return products
    When the shopper searches products for "<term>"
    Then the demo product results should be displayed

    Examples:
      | term  |
      | dress |
      | top   |
      | jeans |
      | tshirt|
