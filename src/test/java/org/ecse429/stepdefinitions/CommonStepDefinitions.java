package org.ecse429.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class CommonStepDefinitions {

    private static final String BASE_URL = "http://localhost:4567/";
    private final ResponseContext responseContext;

    public CommonStepDefinitions(ResponseContext responseContext) {
        this.responseContext = responseContext;
    }

    @Given("the REST API To-Do List Manager application is running")
    public void the_rest_api_to_do_list_manager_application_is_running() {
        RestAssured.baseURI = BASE_URL;
        Response response = responseContext.getResponse();
        response = null;
    }
    @Then("an error message is displayed")
    public void an_error_message_is_displayed() {
        Response response = responseContext.getResponse();
        assertFalse(response.jsonPath().getList("errorMessages").isEmpty());
    }
    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(int expectedStatusCode) {
        Response response = responseContext.getResponse();
        assertEquals("Expected status code to match.", expectedStatusCode, response.getStatusCode());
    }
}
