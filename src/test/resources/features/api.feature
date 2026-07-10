@api @regression
Feature: Demo shop public API
  As a QA engineer
  I want the REST API to return correct responses
  So that backend contracts hold

  @regression @positive
  Scenario: Product list API returns products
    When the products API is requested
    Then the API responds with product data

  @regression @negative
  Scenario: verifyLogin without parameters is rejected
    When verifyLogin is called without required parameters
    Then the API reports a bad request

  @regression @positive
  Scenario: Brands list API returns brands
    When the brands API is requested
    Then the API responds with brand data

  @regression @positive
  Scenario: Search API returns matching products
    When products are searched via API for "top"
    Then the search API returns matching products

  @regression @negative
  Scenario: Search API without a term is rejected
    When products are searched via API without a term
    Then the search API reports a missing parameter

  @regression @negative
  Scenario: verifyLogin with an unknown account returns not found
    When login is attempted via API with an unknown account
    Then the API reports the user was not found
