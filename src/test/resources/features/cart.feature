@demo @regression @cart @e2e
Feature: Demo shop add to cart (E2E)
  As a shopper
  I want to add a searched product to my cart
  So that I can proceed toward checkout

  Background:
    Given the shopper is on the demo shop

  @regression @e2e
  Scenario: Add first searched product to the cart
    When the shopper searches products for "top"
    And the shopper adds the first product to the cart
    Then the demo cart should contain at least 1 item
    And the demo cart should offer checkout
