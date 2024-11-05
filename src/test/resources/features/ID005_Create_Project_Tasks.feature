Feature: Create Project Task

  As a REST API To-Do List Manager user
  I want to create tasks for a project
  So that I can add tasks to my project

  Background:
    Given the REST API To-Do List Manager application is running


  Scenario Outline: Create task for a specific project with a specific title (Normal Flow)

    Given the following projects exist in the system:
      | project_id | title    | completed | active | description  |
      | 1          | project1 | false     | true   | my comment   |
      | 2          | project2 | true      | false  |              |
      | 3          | project3 | false     | true   | i like this  |
      | 4          | project4 | false     | false  | cool project |
    When user requests to create a task within project <project_id> with title "<title>"
    Then a new task with title "<title>" should be created
    And the response status code should be 201
    Examples:
      | project_id | title |
      | 1          | test1 |
      | 1          | test2 |
      | 1          | test3 |
      | 1          | test4 |

  Scenario Outline: Create task for a specific project with a specific title and specific description (Alternate Flow)

    Given the following projects exist in the system:
      | title    | completed | active | description  |
      | project1 | false     | true   | my comment   |
      | project2 | true      | false  |              |
      | project3 | false     | true   | i like this  |
      | project4 | false     | false  | cool project |
    When user requests to create a task within project <project_id> with title "<title>" and description "<description>"
    Then a new task with title "<title>" and description "<description>" should be created
    And the response status code should be 201
    Examples:
      | project_id | title | description |
      | 1          | test1 | my comment  |
      | 1          | test2 | information |
      | 1          | test3 | descriptive |
      | 1          | test4 | adjective   |

  Scenario Outline: Create task for an nonexistent project (Error Flow)

    Given the following projects exist in the system:
      | title    | completed | active | description  |
      | project1 | false     | true   | my comment   |
      | project2 | true      | false  |              |
      | project3 | false     | true   | i like this  |
      | project4 | false     | false  | cool project |
    When user requests to create task for nonexistent project <NONEXISTENT_PROJECT_ID>
    Then an error message is displayed
    And the response status code should be 404
    Examples:
      | NONEXISTENT_PROJECT_ID |
      | 0                      |
      | -1                     |
      | -5                     |