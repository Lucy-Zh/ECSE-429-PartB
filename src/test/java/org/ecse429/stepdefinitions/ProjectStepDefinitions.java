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
    private final ResponseContext responseContext;

    public ProjectStepDefinitions(ResponseContext responseContext) {
        this.responseContext = responseContext;
    }
//    private Response response;

//    @Given("the REST API To-Do List Manager application is running")
//    public void the_rest_api_to_do_list_manager_application_is_running() {
//        RestAssured.baseURI = BASE_URL;
//        response = null;
//    }
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
        Response response = RestAssured.get(BASE_URL);
        responseContext.setResponse(response);
        List<String> projectIds = response.jsonPath().getList("projects.id");
        for (String projectId : projectIds) {
            RestAssured.delete(BASE_URL + "/" + projectId).then().statusCode(200);
        }
    }
    @When("user requests to retrieve all projects")
    public void user_requests_to_retrieve_all_projects() {

        Response response = RestAssured.get(BASE_URL);
        responseContext.setResponse(response);
    }
    @When("user requests to retrieve a specific project with id {string}")
    public void user_requests_to_retrieve_a_specific_project_with_id(String projectId) {
        Response response = RestAssured.get(BASE_URL);
        response = RestAssured.get(BASE_URL + "/" + response.jsonPath().getList("projects.id")
                .get(Integer.parseInt(projectId)));
        responseContext.setResponse(response);
    }
    @When("user requests to retrieve project with project id {int}")
    public void user_requests_to_retrieve_a_specific_project_with_id_does_not_exist(int id) {
        Response response = RestAssured.get(BASE_URL + "/" + id);
        responseContext.setResponse(response);
    }
    @When("user requests to create a new project with title {string}")
    public void user_requests_create_new_project_with_title(String title) {
        String body = String.format("{\"title\": \"%s\"}", title);
        Response response = RestAssured.given()
                .contentType("application/json")
                .body(body)
                .post(BASE_URL);
        responseContext.setResponse(response);
    }
    @When("user requests to create a new project without title")
    public void user_requests_create_new_project_without_title() {
        String body = "{}";
        Response response = RestAssured.given()
                .contentType("application/json")
                .body(body)
                .post(BASE_URL);
        responseContext.setResponse(response);
    }
    @When("user requests to create project with request body {string}")
    public void user_requests_create_new_project_with_request_body(String request_body) {
        Response response = RestAssured.given()
                .contentType("application/json")
                .body(request_body)
                .post(BASE_URL);
        responseContext.setResponse(response);
    }
    @When("user requests to edit project {int} with new title {string}")
    public void user_requests_edit_project_with_new_title(int project_id, String new_title) {
        List<String> idList = RestAssured.get(BASE_URL).jsonPath().getList("projects.id");
        String request_body = String.format("{\"title\": \"%s\"}", new_title);
        Response response = RestAssured.given()
                .contentType("application/json")
                .body(request_body)
                .post(BASE_URL + "/" + idList.get(0));
        responseContext.setResponse(response);
    }
    @When("user requests to edit project {int} with new completed status {string}")
    public void user_requests_edit_project_with_new_completed_status(int project_id, String new_complete_status) {
        List<String> idList = RestAssured.get(BASE_URL).jsonPath().getList("projects.id");
        String request_body = String.format("{\"completed\": %s}", new_complete_status);
        Response response = RestAssured.given()
                .contentType("application/json")
                .body(request_body)
                .post(BASE_URL + "/" + idList.get(0));
        responseContext.setResponse(response);
    }
    @When("user requests to edit project with {int}")
    public void user_requests_edit_project_with_nonexistent_project(int nonexistent_project_id) {
        Response response = RestAssured.given()
                .contentType("application/json")
                .post(BASE_URL + "/" + nonexistent_project_id);
        responseContext.setResponse(response);
    }
    @When("user requests to edit project with request body {string}")
    public void user_requests_edit_project_with_invalid_request_body(String invalid_request_body) {
        List<String> idList = RestAssured.get(BASE_URL).jsonPath().getList("projects.id");
        Response response = RestAssured.given()
                .contentType("application/json")
                .body(invalid_request_body)
                .post(BASE_URL + "/" + idList.get(0));
        responseContext.setResponse(response);
    }
    @When("user requests to delete project {int}")
    public void user_requests_delete_project_with_id(int projectId) {
        List<String> idList = RestAssured.get(BASE_URL).jsonPath().getList("projects.id");
        Response response = RestAssured.given()
                .contentType("application/json")
                .delete(BASE_URL + "/" + idList.get(projectId));
        responseContext.setResponse(response);
    }
    @When("user requests to delete project {int} with no title")
    public void user_requests_delete_project_with_no_title(int projectId) {
        List<String> idList = RestAssured.get(BASE_URL).jsonPath().getList("projects.findAll { it.title == \"null\" }.id");
        Response response = RestAssured.given()
                .contentType("application/json")
                .delete(BASE_URL + "/" + idList.get(0));
        responseContext.setResponse(response);
    }
    @When("user requests to delete nonexistent project {int}")
    public void user_requests_delete_nonexistent_project(int projectId) {
        Response response = RestAssured.given()
                .contentType("application/json")
                .delete(BASE_URL + "/" + projectId);
        responseContext.setResponse(response);
    }
    @When("user requests to create a task within project {int} with title {string}")
    public void user_requests_create_task_with_title(int project_id, String title) {
        List<String> idList = RestAssured.get(BASE_URL).jsonPath().getList("projects.id");
        String body = String.format("{\"title\": \"%s\"}", title);
        Response response = RestAssured.given()
                .contentType("application/json")
                .body(body)
                .post(BASE_URL + "/" + idList.get(0) + "/tasks");
        responseContext.setResponse(response);
    }
    @When("user requests to create a task within project {int} with title {string} and description {string}")
    public void user_requests_create_task_with_title_and_description(int project_id, String title, String description) {
        List<String> idList = RestAssured.get(BASE_URL).jsonPath().getList("projects.id");
        String body = String.format("{\"title\": \"%s\", \"description\": \"%s\"}", title, description);
        Response response = RestAssured.given()
                .contentType("application/json")
                .body(body)
                .post(BASE_URL + "/" + idList.get(0) + "/tasks");
        responseContext.setResponse(response);
    }
    @When("user requests to create task for nonexistent project {int}")
    public void user_requests_create_task_for_nonexistent_project(int project_id) {
        String body = "{\"title\": \"never_created\"}";
        Response response = RestAssured.given()
                .contentType("application/json")
                .body(body)
                .post(BASE_URL + "/" + project_id + "/tasks");
        responseContext.setResponse(response);
    }
    @Then("a list of all projects in the system and its details should be displayed")
    public void a_list_of_all_projects_in_the_system_and_its_details_should_be_displayed() {
        List<Map<String, Object>> projects = responseContext.getResponse().jsonPath().getList("projects.id");
        assertFalse("Expected list of projects, but got none.", projects.isEmpty());
    }
    @Then("an empty list of projects should be displayed")
    public void an_empty_list_of_projects_should_be_displayed(){
        List<Map<String, Object>> projects = responseContext.getResponse().jsonPath().getList("projects.id");
        assertTrue("Expected empty list, but list not empty.", projects.isEmpty());
    }
    @Then("the details of project with id {string} should be displayed")
    public void the_details_of_project_with_id_should_be_displayed(String projectId) {
        List<Map<String, Object>> projects = responseContext.getResponse().jsonPath().getList("projects.id");
        assertFalse("Expected project, but got none.", projects.isEmpty());
    }
    @Then("an {string} message is displayed")
    public void a_specific_message_is_displayed(String message) {
        String actualError = (String) responseContext.getResponse().jsonPath().getList("errorMessages").get(0);
        assertEquals(message, actualError);
    }
    @Then("a new project with title {string} should be created in the system")
    public void a_new_project_with_title_should_be_created_in_the_system(String title) {
        String projectTitle = responseContext.getResponse().jsonPath().getString("title");
        assertEquals(title, projectTitle);
    }
    @Then("a new project with an empty value for title should be created in the system")
    public void a_new_project_without_title_should_be_created_in_the_system() {
        String projectTitle = responseContext.getResponse().jsonPath().getString("title");
        assertEquals("", projectTitle);
    }
    @Then("the title of project {int} should be updated to {string}")
    public void title_of_project_updated(int project_id, String new_title) {
        String projectTitle = responseContext.getResponse().jsonPath().getString("title");
        assertEquals(new_title, projectTitle);
    }
    @Then("the completed status of project {int} should be updated to {string}")
    public void complete_status_of_project_updated(int project_id, String new_complete_status) {
        String projectTitle = responseContext.getResponse().jsonPath().getString("completed");
        assertEquals(new_complete_status, projectTitle);
    }
    @Then("project {int} should be deleted from the system")
    public void project_is_deleted_from_system(int project_id) {
        assertEquals("", responseContext.getResponse().asString());
    }
    @Then("a new task with title {string} should be created")
    public void a_new_task_with_title_created(String title) {
        String taskTitle = responseContext.getResponse().jsonPath().getString("title");
        assertEquals(title, taskTitle);
    }
    @Then("a new task with title {string} and description {string} should be created")
    public void a_new_task_with_title_and_description_created(String title, String description) {
        String taskTitle = responseContext.getResponse().jsonPath().getString("title");
        String taskDescription = responseContext.getResponse().jsonPath().getString("description");
        assertEquals(title, taskTitle);
        assertEquals(description, taskDescription);
    }
//    @Then("an error message is displayed")
//    public void an_error_message_is_displayed() {
//        assertFalse(response.jsonPath().getList("errorMessages").isEmpty());
//    }
//    @Then("the response status code should be {int}")
//    public void the_response_status_code_should_be(int expectedStatusCode) {
//        assertEquals("Expected status code to match.", expectedStatusCode, response.getStatusCode());
//    }
}
