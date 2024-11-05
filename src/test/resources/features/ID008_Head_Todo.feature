Feature: Retrieve Todos' headers

  As a REST API To-Do List Manager user
  I want to request the header for some todos
  So that I can check if a the todos exist without fetching its body

  Background:
    Given the REST API To-Do List Manager application is running

  Scenario Outline: Request header for all todos (Normal Flow)
    Given the following todos exist in the system:
      | id | title            | doneStatus | description |
      | 1  | scan paperwork   | false      |             |
      | 2  | file paperwork   | false      |             |
    When user requests header for all todos
    Then the response status code should be 200
    Examples:

  Scenario Outline: Request header for all todos but there is no todos in the system (Alternate Flow)
    Given no todos exists in the system
    When user requests header for all todos
    Then the response status code should be 200
    Examples:

  Scenario Outline: Retrieve a todo that does not exist (Error Flow)
    Given the following todos exist in the system:
      | id | title            | doneStatus | description |
      | 1  | scan paperwork   | false      |             |
      | 2  | file paperwork   | false      |             |
    When user requests to retrieve the header of a todo with id <NONEXISTENT_TODO_ID>
    And the response status code should be 404
    Examples:
      | NONEXISTENT_TODO_ID |
      | 0                   |
      | -1                  |
      | -5                  |
