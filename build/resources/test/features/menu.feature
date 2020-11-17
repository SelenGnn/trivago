Feature: Menu Feature

  Background:
    Given I open browser
    And I maximize browser

  Scenario: Menu Navigation
    When I open "trivago" page
    And I wait for 1 seconds
    And I click "menu"
    And I wait for 2 seconds
    And I click "destinations"
    And I click "southwest"
    And I see "southwest page" page