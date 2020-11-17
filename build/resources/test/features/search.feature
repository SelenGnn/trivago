Feature: Search Feature

  Background:
    Given I open browser
    And I maximize browser

  Scenario: Location Search
    When I open "trivago" page
    And I click "search button"
    And I fill:
      | search | Berlin |
    And I press enter key to "search"