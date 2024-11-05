package org.ecse429.stepdefinitions;

import io.restassured.response.Response;

public class ResponseContext {

  private Response response;

  public Response getResponse() {
    return response;
  }

  public void setResponse(Response response) {
    this.response = response;
  }
}
