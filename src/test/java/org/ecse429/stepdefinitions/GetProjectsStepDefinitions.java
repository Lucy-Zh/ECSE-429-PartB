package org.ecse429.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

public class GetProjectsStepDefinitions {

    private static final String BASE_URL = "http://localhost:4567/projects";
    private Response response;

    @Given("the REST API To-Do List Manager application is running")
    public void the_rest_api_to_do_list_manager_application_is_running() {
        RestAssured.baseURI = BASE_URL;
        response = null;
    }
    @Given("the following projects exist in the system:")
    public void the_following_projects_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> projects = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> project : projects) {
            String body = String.format("{\"title\": \"%s\", \"completed\": %b, \"active\": %b, \"description\": \"%s\"}",
                    project.get("title"), project.get("completed"), project.get("active"), project.get("description"));
            RestAssured.given()
                    .contentType("application/json")
                    .body(body)
                    .post(BASE_URL)
                    .then()
                    .statusCode(201);
        }
    }
    @Given("no project exists in the system")
    public void no_project_exists_in_the_system() {
        response = RestAssured.get(BASE_URL);
        List<String> projectIds = response.jsonPath().getList("projects.id");
        for (String projectId : projectIds) {
            RestAssured.delete(BASE_URL + "/" + projectId).then().statusCode(200);
        }
    }
    @When("user requests to retrieve all projects")
    public void user_requests_to_retrieve_all_projects() {
        response = RestAssured.get(BASE_URL);
    }
    @When("user requests to retrieve a specific project with id <project_id>")
    public void user_requests_to_retrieve_a_specific_project_with_id() {
        response = RestAssured.get(BASE_URL);
        response = RestAssured.get(BASE_URL + "/" + response.jsonPath().getList("projects.id").get(0));
    }
    @When("user requests to retrieve project with project id NONEXISTENT_PROJECT_ID")
    public void user_requests_to_retrieve_a_specific_project_with_id_does_not_exist() {
        response = RestAssured.get(BASE_URL + "/" + 0); // id is never 0 so always nonexistent
    }
    @Then("a list of all projects in the system and its details should be displayed")
    public void a_list_of_all_projects_in_the_system_and_its_details_should_be_displayed() {
        List<Map<String, Object>> projects = response.jsonPath().getList("projects.id");
        assertFalse("Expected list of projects, but got none.", projects.isEmpty());
    }
    @Then("an empty list of projects should be displayed")
    public void an_empty_list_of_projects_should_be_displayed(){
        List<Map<String, Object>> projects = response.jsonPath().getList("projects.id");
        assertTrue("Expected list of projects, but got none.", projects.isEmpty());
    }
    @Then("the details of project with id <project_id> should be displayed")
    public void the_details_of_project_with_id_should_be_displayed() {
        List<Map<String, Object>> projects = response.jsonPath().getList("projects.id");
        assertFalse("Expected list of projects, but got none.", projects.isEmpty());
    }
    @Then("an {string} message is displayed")
    public void a_specific_message_is_displayed(String message) {
        String actualError = (String) response.jsonPath().getList("errorMessages").get(0);
        assertEquals(message, actualError);
    }
    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(int expectedStatusCode) {
        assertEquals("Expected status code to match.", expectedStatusCode, response.getStatusCode());
    }
}
