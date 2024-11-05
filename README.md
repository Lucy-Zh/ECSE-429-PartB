# ECSE 429 Project Part B
## November 5th, 2024

Note that this code was written and tested on macOS 13.2.1 (M1 chip) through IntelliJ.

## Context
This repo includes story testing for a "rest api todo manager", which can be found online at: [link](https://github.com/eviltester/thingifier/releases)

## How to run tests

1. Download the "rest api todo manager".
2. Change into directory of the application.
3. Run ````java -jar runTodoManagerRestAPI-1.5.5.jar```` in that directory. The application is now running.
4. Search [link](http://localhost:4567/docs) on your browser, documentation of the application should be there if application is running correctly.
5. Run story tests in the ide of your choice. 

* If you're using IntelliJ:
1. Right click on ECSE-429-PartB project-> Build Module 'ECSE-429-PartB'
2. Right click on src/test/resources/features -> More Run/Debug -> Run 'All Features in: features'

## Extra Info

Some important directories of this workspace:
- `src/test/java/org/ecse429/stepdefinitions`: 
  - story test automation where the step definitions for the gherkin scripts are 
- `src/test/java/resources/features`: 
  - story test suite where the gherkin scripts for acceptance tests are
- `pom.xml`: 
  - where are the project dependencies are configured