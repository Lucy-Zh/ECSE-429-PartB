Feature: Edit Project

As a REST API To-Do List Manager user
I want to edit an existing project
So that I can update the details of a project


Background:
Given the REST API To-Do List Manager application is running


  Scenario Outline: Edit the title of an existing project (Normal Flow)

Given the following projects exist in the system:
  | project_id | title    | completed | active | description   |
  | 1          | project1 | false     | true   | my comment    |
  | 2          | project2 | true      | false  |               |
  | 3          | project3 | false     | true   | i like this   |
  | 4          | project4 | false     | false  | cool project  |
When user requests to edit project <project_id> with new title "<new_title>"
Then the title of project <project_id> should be updated to "<new_title>"
And the response status code should be 200
    Examples:
    | project_id | new_title |
    | 1          | NewTitle1 |
    | 2          | NewTitle2 |
    | 3          | NewTitle3 |


  Scenario Outline: Edit an the complete status of an existing project (Alternate Flow)

Given the following projects exist in the system:
  | project_id | title    | completed | active | description   |
  | 1          | project1 | false     | true   | my comment    |
  | 2          | project2 | true      | false  |               |
  | 3          | project3 | false     | true   | i like this   |
  | 4          | project4 | false     | false  | cool project  |
When user requests to edit project <project_id> with new completed status "<new_complete_status>"
Then the completed status of project <project_id> should be updated to "<new_complete_status>"
And the response status code should be 200
    Examples:
      | project_id | new_complete_status |
      | 1          | true                |
      | 2          | false               |
      | 3          | true                |

  Scenario Outline: Edit an nonexistent project (Error Flow)

Given the following projects exist in the system:
  | project_id | title    | completed | active | description   |
  | 1          | project1 | false     | true   | my comment    |
  | 2          | project2 | true      | false  |               |
  | 3          | project3 | false     | true   | i like this   |
  | 4          | project4 | false     | false  | cool project  |
When user requests to edit project with <NONEXISTENT_PROJECT_ID>
Then an error message is displayed
And the response status code should be 404
    Examples:
      | NONEXISTENT_PROJECT_ID |
      | 0                      |
      | -1                     |
      | -5                     |


  Scenario Outline: Edit an existing project with invalid data format (Error Flow)

Given the following projects exist in the system:
  | project_id | title    | completed | active | description   |
  | 1          | project1 | false     | true   | my comment    |
  | 2          | project2 | true      | false  |               |
  | 3          | project3 | false     | true   | i like this   |
  | 4          | project4 | false     | false  | cool project  |
When user requests to edit project with request body "<INVALID_REQUEST_BODY>"
Then an error message is displayed
And the response status code should be 400
Examples:
  | INVALID_REQUEST_BODY |
  | {                    |
  | {title}              |
  | {title:              |