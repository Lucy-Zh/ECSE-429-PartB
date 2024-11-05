Feature: Delete Todos

  As a REST API To-Do List Manager user
  I want to delete todos
  So that I can remove a todo from the system when no longer needed

  Background:
    Given the REST API To-Do List Manager application is running

  Scenario Outline: Delete a specific to-do item (Normal Flow)
    Given the following todos exist in the system:
      | id | title            | doneStatus | description |
      | 1  | scan paperwork   | false      |             |
      | 2  | file paperwork   | false      |             |
      | 3  | buy supplies     | false      |             |
    When user requests to delete todo <todo_id>
    Then todo <todo_id> should be deleted from the system
    And the response status code should be 200
    Examples:
      | todo_id |
      | 1          |
      | 2          |

  Scenario Outline: Delete an nonexistent todo (Alternate Flow)
    Given the following todos exist in the system:
      | id | title            | doneStatus | description |
      | 1  | scan paperwork   | false      |             |
      | 2  | file paperwork   | false      |             |
    When user requests to delete todo with invalid <NONEXISTENT_TODO_ID>
    Then an error message is displayed
    And the response status code should be 404
    Examples:
      | NONEXISTENT_TODO_ID |
      | 0                   |
      | -1                  |
      | -5                  |

  Scenario Outline: Delete a todo with invalid data format (Error Flow)
    Given the following todos exist in the system:
      | id | title            | doneStatus | description |
      | 1  | scan paperwork   | false      |             |
      | 2  | file paperwork   | false      |             |
    When user requests to delete todo "<INVALID_TODO_ID>"
    Then an error message is displayed
    And the response status code should be 404
    Examples:
      | INVALID_TODO_ID |
      | id              |
      | not an id       |
      | strings         |
