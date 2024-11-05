Feature: Get Todos

As a REST API To-Do List Manager user
I want to retrieve todos
So that I can view what tasks I need to accomplish

Background:
Given the REST API To-Do List Manager application is running

  Scenario Outline: Retrieve all todos (Normal Flow)

  Given the following todos exist in the system:
  | id | title            | doneStatus | description |
  | 1  | scan paperwork   | false      |             |
  | 2  | file paperwork   | false      |             |
  When user requests to retrieve all todos
  Then the todos list returned should contain 2 items
  And the todos list returned should contain a todo with an title of "scan paperwork"
  And the todos list returned should contain a todo with an title of "file paperwork"
  And the response status code should be 200
  Examples:


  Scenario Outline: Retrieve all todos but there is no todo in the system (Alternate Flow)

  Given no todos exists in the system
  When user requests to retrieve all todos
  Then the todos list returned should contain 0 items
  And the response status code should be 200
  Examples:

  Scenario Outline: Retrieve a todo that does not exist (Error Flow)

  Given the following todos exist in the system:
  | id | title            | doneStatus | description |
  | 1  | scan paperwork   | false      |             |
  | 2  | file paperwork   | false      |             |
  When user requests to retrieve a todo with id <NONEXISTENT_TODO_ID>
  Then an "Could not find an instance with todos/<NONEXISTENT_TODO_ID>" message is displayed
  And the response status code should be 404
  Examples:
  | NONEXISTENT_TODO_ID |
  | 0                   |
  | -1                  |
  | -5                  |
