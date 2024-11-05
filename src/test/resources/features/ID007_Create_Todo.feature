Feature: Create Todos

  As a REST API To-Do List Manager user
  I want to create a new todo
  So that I can remember to do the task in question

  Background:
    Given the REST API To-Do List Manager application is running

  Scenario Outline: Create a new todo with title (Normal Flow)
    Given the following todos exist in the system:
      | id | title          | doneStatus | description |
      | 1  | scan paperwork | false      |             |
      | 2  | file paperwork | false      |             |
    When user requests to create a new todo with title "<title>"
    Then a new todo with title "<title>" should be created in the system
    And the response status code should be 201
    Examples:
      | title         |
      | titleExample  |
      | titleExample2 |

  Scenario Outline: Create a new todo without title (Alternate Flow)
    Given the following todos exist in the system:
      | id | title          | doneStatus | description |
      | 1  | scan paperwork | false      |             |
      | 2  | file paperwork | false      |             |
    When user requests to create a new todo without title
    Then a new todo with an empty value for title should be created in the system
    And the response status code should be 201
    Examples:

  Scenario Outline: Create a new todo with invalid data format (Error Flow)

    Given the following todos exist in the system:
      | id | title          | doneStatus | description |
      | 1  | scan paperwork | false      |             |
      | 2  | file paperwork | false      |             |
    When user requests to create todo with doneStatus "<INVALID_DONE_STATUS>"
    Then an error message is displayed
    And the response status code should be 400
    Examples:
      | INVALID_DONE_STATUS |
      | done                |
      | not done            |
      | fini                |
