Feature: Newsletter Feature

  Background:
    Given I open browser
    And I maximize browser

  Scenario: Newsletter Subscribe
    When I open "trivago" page
    And I see "Elevate your next hotel experience!" text "newsletter title"
    And I fill:
      | newsletter mail | usfahgdfasd@gmail.com |
    And I click "newsletter submit"
    And I wait for 5 seconds
    And I see "You are now checked-in!" text "newsletter submitted"