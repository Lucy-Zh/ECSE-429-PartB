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
/*    @Given("the following tasks exist for each project:")
    public void the_following_tasks_exist_for_each_project(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> tasks = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> task : tasks) {
            String projectId = task.get("project_id");
            String taskId = task.get("task_id");
            RestAssured.given()
                    .contentType("application/json")
                    .body("{\"title\": \"test_task\"}")
                    .post(BASE_URL + "/" + projectId + "/tasks")
                    .then()
                    .statusCode(201);
        }
    }*/
    @When("user requests to retrieve all projects")
    public void user_requests_to_retrieve_all_projects() {
        response = RestAssured.get(BASE_URL);
    }
    @Then("a list of all projects in the system and its details should be displayed")
    public void a_list_of_all_projects_in_the_system_and_its_details_should_be_displayed() {
        List<Map<String, Object>> projects = response.jsonPath().getList("projects.id");
        assertFalse("Expected list of projects, but got none.", projects.isEmpty());
    }
    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(int expectedStatusCode) {
        assertEquals("Expected status code to match.", expectedStatusCode, response.getStatusCode());
    }
}
