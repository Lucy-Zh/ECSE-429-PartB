Feature: Get Projects

As a REST API To-Do List Manager user
I want to retrieve project information
So that I can view details of one or more projects

Background:
Given the REST API To-Do List Manager application is running

Scenario: Retrieve all projects (Normal Flow)

  Given the following projects exist in the system:
  | project_id | title    | completed | active | description   |
  | 1          | project1 | false     | true   | my comment    |
  | 2          | project2 | true      | false  |               |
  | 3          | project3 | false     | true   | i like this   |
  | 4          | project4 | false     | false  | cool project  |
  When user requests to retrieve all projects
  Then a list of all projects in the system and its details should be displayed
  And the response status code should be 200

Scenario: Retrieve all projects but there is no project in the system (Alternate Flow)

  Given no project exists in the system
  When user requests to retrieve all projects
  Then an empty list of projects should be displayed
  And the response status code should be 200


Scenario: Retrieve a specific project by project id (Alternate Flow)

  Given the following projects exist in the system:
    | project_id | title    | completed | active | description   |
    | 1          | project1 | false     | true   | my comment    |
    | 2          | project2 | true      | false  |               |
    | 3          | project3 | false     | true   | i like this   |
    | 4          | project4 | false     | false  | cool project  |
  When user requests to retrieve a specific project with id <project_id>
  Then the details of project with id <project_id> should be displayed
  And the response status code should be 200


Scenario: Retrieve a project that does not exist (Error Flow)

  Given the following projects exist in the system:
    | project_id | title    | completed | active | description   |
    | 1          | project1 | false     | true   | my comment    |
    | 2          | project2 | true      | false  |               |
    | 3          | project3 | false     | true   | i like this   |
    | 4          | project4 | false     | false  | cool project  |
  When user requests to retrieve project with project id NONEXISTENT_PROJECT_ID
  Then an "Could not find an instance with projects/0" message is displayed
  And the response status code should be 404