Feature: Contact Feature

  Background:
    Given I open browser
    And I maximize browser

  Scenario: Submit Contact
    When I open "trivago" page
    And I wait for 3 seconds
    And I click "accept cookie"
    And I click "contact"
    And I switch "trivago contact" page
    And I fill:
      | message | Hello World!           |
      | name    | Selen Gonen            |
      | mail    | selengonen02@gmail.com |
    And I click "confirm"
    #And I click "submit"