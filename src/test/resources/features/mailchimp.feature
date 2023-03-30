Feature: MailChimp

  Scenario Outline: Inputting information, and try to creat a new user
    Given I chooses driver "<Driver>" and enter ChimpSite
    When I input a new "<Email>"
    When I add a random <Length> username
    When I chooses a "<Password>"
    When I push the Sign Up button
    Then I "<Attempt>" to create a account


    Examples:

      | Driver | Email                 | Length | Password | Attempt |
      | Edge   | Klas@gmail.com        | 10     | kLass1$e | OK      |
      | Chrome | KlasseKlass@gmail.com | 120    | klAss1%e | FAIL    |
      | Chrome | Linushell82@gmail.com | 0      | klaSs1&e | FAIL    |
      | Edge   |                       | 6      | klaSs1@e | FAIL    |