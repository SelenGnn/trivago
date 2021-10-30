Feature: Search Feature

  Background:
    Given I open browser
    And I maximize browser
    When I open "trivago" page
    And I click "search button"
    And I fill:
      | search | Texas |
    And I wait for 2 seconds
    And I press enter key to "search"

  Scenario: Texas Search
       Then I verify that the Displayed Number Search Results is correct


  Scenario: Mom Knows Best Result
    And I click "momKnowsBest"
    And I wait for 5 seconds
    And I verify that there are no broken links in the article
