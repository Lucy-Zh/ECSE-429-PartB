package org.ecse429.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

public class ProjectStepDefinitions {

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
    @When("user requests to retrieve a specific project with id {string}")
    public void user_requests_to_retrieve_a_specific_project_with_id(String projectId) {
        response = RestAssured.get(BASE_URL);
        response = RestAssured.get(BASE_URL + "/" + response.jsonPath().getList("projects.id").get(0));
    }
    @When("user requests to retrieve project with project id {int}")
    public void user_requests_to_retrieve_a_specific_project_with_id_does_not_exist(int id) {
        response = RestAssured.get(BASE_URL + "/" + id);
    }
    @When("user requests to create a new project with title {string}")
    public void user_requests_create_new_project_with_title(String title) {
        String body = String.format("{\"title\": \"%s\"}", title);
        response = RestAssured.given()
                .contentType("application/json")
                .body(body)
                .post(BASE_URL);
    }
    @When("user requests to create a new project without title")
    public void user_requests_create_new_project_without_title() {
        String body = "{}";
        response = RestAssured.given()
                .contentType("application/json")
                .body(body)
                .post(BASE_URL);
    }
    @When("user requests to create project with request body {string}")
    public void user_requests_create_new_project_with_request_body(String request_body) {
        response = RestAssured.given()
                .contentType("application/json")
                .body(request_body)
                .post(BASE_URL);
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
    @Then("the details of project with id {string} should be displayed")
    public void the_details_of_project_with_id_should_be_displayed(String projectId) {
        List<Map<String, Object>> projects = response.jsonPath().getList("projects.id");
        assertFalse("Expected list of projects, but got none.", projects.isEmpty());
    }
    @Then("an {string} message is displayed")
    public void a_specific_message_is_displayed(String message) {
        String actualError = (String) response.jsonPath().getList("errorMessages").get(0);
        assertEquals(message, actualError);
    }
    @Then("a new project with title {string} should be created in the system")
    public void a_new_project_with_title_should_be_created_in_the_system(String title) {
        String projectTitle = response.jsonPath().getString("title");
        assertEquals(title, projectTitle);
    }
    @Then("a new project with an empty value for title should be created in the system")
    public void a_new_project_without_title_should_be_created_in_the_system() {
        String projectTitle = response.jsonPath().getString("title");
        assertEquals("", projectTitle);
    }
    @Then("an error message is displayed")
    public void an_error_message_is_displayed() {
        assertFalse(response.jsonPath().getList("errorMessages").isEmpty());
    }
    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(int expectedStatusCode) {
        assertEquals("Expected status code to match.", expectedStatusCode, response.getStatusCode());
    }
}
