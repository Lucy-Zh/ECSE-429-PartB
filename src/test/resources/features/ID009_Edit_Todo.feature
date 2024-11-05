Feature: Edit Todo

  As a REST API To-Do List Manager user
  I want to edit an existing todo
  So that I can update the details of a todo

  Background:
    Given the REST API To-Do List Manager application is running

  Scenario Outline: Edit the title of an existing todo (Normal Flow)
    Given the following todos exist in the system:
      | id | title          | doneStatus | description |
      | 1  | scan paperwork | false      |             |
      | 2  | file paperwork | false      |             |
    When user requests to edit todo <todo_id> with new title "<new_title>"
    Then the title of todo <todo_id> should be updated to "<new_title>"
    And the response status code should be 200
    Examples:
      | todo_id | new_title |
      | 1       | NewTitle1 |
      | 2       | NewTitle2 |

  Scenario Outline: Edit the doneStatus of an existing todo (Alternate Flow)
    Given the following todos exist in the system:
      | id | title          | doneStatus | description |
      | 1  | scan paperwork | false      |             |
      | 2  | file paperwork | false      |             |
    When user requests to edit todo <todo_id> with new done status "<new_done_status>"
    Then the done status of todo <todo_id> should be updated to "<new_done_status>"
    And the response status code should be 200
    Examples:
      | todo_id | new_done_status |
      | 1       | true            |
      | 2       | true            |

  Scenario Outline: Edit an nonexistent todo (Error Flow)
    Given the following todos exist in the system:
      | id | title          | doneStatus | description |
      | 1  | scan paperwork | false      |             |
      | 2  | file paperwork | false      |             |
    When user requests to edit a todo with id <NONEXISTENT_TODO_ID>
    Then an error message is displayed
    And the response status code should be 404
    Examples:
      | NONEXISTENT_TODO_ID |
      | 0                   |
      | -1                  |
      | -5                  |