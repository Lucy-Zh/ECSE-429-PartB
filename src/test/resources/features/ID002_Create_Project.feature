Feature: Create Project

As a REST API To-Do List Manager user
I want to create a new project
So that I can organize my tasks into a new project


Background:
Given the REST API To-Do List Manager application is running


  Scenario Outline: Create a new project with title (Normal Flow)

Given the following projects exist in the system:
  | project_id | title    | completed | active | description   |
  | 1          | project1 | false     | true   | my comment    |
  | 2          | project2 | true      | false  |               |
  | 3          | project3 | false     | true   | i like this   |
  | 4          | project4 | false     | false  | cool project  |
When user requests to create a new project with title "<title>"
Then a new project with title "<title>" should be created in the system
And the response status code should be 201
    Examples:
    | title |
    | titleExample |
    | titleExample2 |
    | titleExample3 |


  Scenario Outline: Create a new project without title (Alternate Flow)

Given the following projects exist in the system:
  | project_id | title    | completed | active | description   |
  | 1          | project1 | false     | true   | my comment    |
  | 2          | project2 | true      | false  |               |
  | 3          | project3 | false     | true   | i like this   |
  | 4          | project4 | false     | false  | cool project  |
When user requests to create a new project without title
Then a new project with an empty value for title should be created in the system
And the response status code should be 201
    Examples:


  Scenario Outline: Create a new project with invalid data format (Error Flow)

Given the following projects exist in the system:
  | project_id | title    | completed | active | description   |
  | 1          | project1 | false     | true   | my comment    |
  | 2          | project2 | true      | false  |               |
  | 3          | project3 | false     | true   | i like this   |
  | 4          | project4 | false     | false  | cool project  |
When user requests to create project with request body "<invalid_request_body>"
Then an error message is displayed
And the response status code should be 400
    Examples:
    | invalid_request_body |
    | {                    |
    | {title}              |
    | {title:              |
