package org.ecse429.stepdefinitions;

import static org.junit.Assert.*;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.List;
import java.util.Map;

public class TodosStepDefinitions {

    private static final String BASE_URL = "http://localhost:4567/todos";
    private final ResponseContext responseContext;

    public TodosStepDefinitions(ResponseContext responseContext) {
        this.responseContext = responseContext;
    }

    @Given("the following todos exist in the system:")
    public void the_following_todos_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
        // create a predefined set of todos
        List<Map<String, String>> todos = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> todo : todos) {
            String body = String.format("{\"title\": \"%s\", \"doneStatus\": %b, \"description\": \"%s\"}",
                    todo.get("title"), todo.get("doneStatus"), todo.get("description"));
            RestAssured.given()
                    .contentType("application/json")
                    .body(body)
                    .post(BASE_URL)
                    .then()
                    .statusCode(201);
        }
    }
    @Given("no todos exists in the system")
    public void no_todos_exists_in_the_system() {
        Response response = RestAssured.get(BASE_URL);
        List<String> todosIds = response.jsonPath().getList("todos.id");
        for (String todoId : todosIds) {
            RestAssured.delete(BASE_URL + "/" + todoId).then().statusCode(200);
        }
        responseContext.setResponse(response);
    }
    @When("user requests to retrieve all todos")
    public void user_requests_to_retrieve_all_todos() {
        Response response = RestAssured.get(BASE_URL);
        responseContext.setResponse(response);
    }
    @When("user requests to retrieve a todo with id {int}")
    public void user_requests_to_retrieve_a_non_existant_todo_with_id(int todoId) {
        Response response = RestAssured.get(BASE_URL + "/" + todoId);
        responseContext.setResponse(response);
    }

  @When("user requests to create a new todo with title {string}")
  public void user_requests_create_new_todo_with_title(String title) {
        String body = String.format("{\"title\": \"%s\"}", title);
        Response response = RestAssured.given()
                .contentType("application/json")
                .body(body)
                .post(BASE_URL);
        responseContext.setResponse(response);
    }
    @When("user requests to create a new todo without title")
    public void user_requests_create_new_todo_without_title() {
        String body = "{}";
        Response response = RestAssured.given()
                .contentType("application/json")
                .body(body)
                .post(BASE_URL);
        responseContext.setResponse(response);
    }
    @When("user requests to create todo with doneStatus {string}")
    public void user_requests_create_new_todo_with_done_status(String done_Status) {
        Response response = RestAssured.given()
                .contentType("application/json")
                .body(done_Status)
                .post(BASE_URL);
        responseContext.setResponse(response);
    }
    @Then("the todos list returned should contain {int} items")
    public void the_response_should_contain(int expectedCount) {
        List<Map<String, Object>> todosList = responseContext.getResponse().jsonPath().getList("$");
        assertEquals(expectedCount, todosList.size());
    }
    @Then("the todos list returned should contain a todo with an title of {string}")
    public void the_response_should_contain(String expectedTitle) {
      List<String> titles = responseContext.getResponse().jsonPath().getList("title");
      assertTrue(titles.contains(expectedTitle));
    }
    @Then("a new todo with title {string} should be created in the system")
    public void a_new_todo_with_title_should_be_created_in_the_system(String title) {
        String todoTitle = responseContext.getResponse().jsonPath().getString("title");
        assertEquals(title, todoTitle);
    }
    @Then("a new todo with an empty value for title should be created in the system")
    public void a_new_todo_without_title_should_be_created_in_the_system() {
        String todoTitle = responseContext.getResponse().jsonPath().getString("title");
        assertEquals("", todoTitle);
    }
}
