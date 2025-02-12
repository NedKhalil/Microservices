package com.ned.microservices.account.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.testcontainers.containers.PostgreSQLContainer;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountControllerTest {

  static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres");
  @LocalServerPort
  private int port;

  String reqBody1 = """
       {
          "customerId": "2119736",
          "accountType": "INVESTMENT"
       }
      """;
  String reqBody2 = """
       {
          "customerId": "2119736",
          "accountType": "SALARY"
       }
      """;
  String reqBody3 = """
       {
          "customerId": "2119736",
          "accountType": "SAVING"
       }
      """;

  @BeforeEach
  void setup() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = port;
  }

  static {
    postgresContainer.start();
  }

  @Test
  void testFindAll() {
    RestAssured.given()
        .contentType("application/json")
        .body(reqBody1)
        .post("/api/v1/accounts");

    RestAssured.get("/api/v1/accounts")
        .then()
        .statusCode(200)
        .body("size()", Matchers.greaterThanOrEqualTo(0));
  }

  @Test
  void testFindAllByCustomer() {
    int customerId = RestAssured.given()
        .contentType("application/json")
        .body(reqBody1)
        .post("/api/v1/accounts")
        .then()
        .extract()
        .path("customerId");
    RestAssured.get("/api/v1/accounts/customer/" + customerId)
        .then()
        .statusCode(200)
        .body("size()", Matchers.greaterThanOrEqualTo(0));
  }

  @Test
  void testSave() {
    RestAssured.given()
        .contentType("application/json")
        .body(reqBody1)
        .post("/api/v1/accounts")
        .then()
        .statusCode(200)
        .body("id", Matchers.notNullValue());
    RestAssured.given()
        .contentType("application/json")
        .body(reqBody2)
        .post("/api/v1/accounts")
        .then()
        .statusCode(200)
        .body("id", Matchers.notNullValue());
    RestAssured.given()
        .contentType("application/json")
        .body(reqBody3)
        .post("/api/v1/accounts")
        .then()
        .statusCode(200)
        .body("id", Matchers.notNullValue());
  }

  @Test
  void testSaveInvalidAccountType() throws MethodArgumentNotValidException {

    RestAssured.given()
        .contentType("application/json")
        .body("""
            {
              "customerId": "1452768",
              "accountType": "INVALID"
            }
            """)
        .post("/api/v1/accounts")
        .then()
        .statusCode(400);
  }

  @Test
  void testCustomerIdNotFound() {
    RestAssured.given()
        .contentType("application/json")
        .body("""
            {
              "customerId": "1234567",
              "accountType": "INVESTMENT"
            }
            """)
        .post("/api/v1/accounts")
        .then()
        .statusCode(404);
  }

  @Test
  void testMaxAccountIdsReached() {
    RestAssured.given()
        .contentType("application/json")
        .body(reqBody1)
        .post("/api/v1/accounts");
    RestAssured.given()
        .contentType("application/json")
        .body(reqBody2)
        .post("/api/v1/accounts")
        .then()
        .statusCode(200);
    RestAssured.given()
        .contentType("application/json")
        .body(reqBody3)
        .post("/api/v1/accounts");
    RestAssured.given()
        .contentType("application/json")
        .body(reqBody1)
        .post("/api/v1/accounts");
    RestAssured.given()
        .contentType("application/json")
        .body(reqBody1)
        .post("/api/v1/accounts");
    RestAssured.given()
        .contentType("application/json")
        .body(reqBody2)
        .post("/api/v1/accounts")
        .then()
        .statusCode(200);
    RestAssured.given()
        .contentType("application/json")
        .body(reqBody3)
        .post("/api/v1/accounts");
    RestAssured.given()
        .contentType("application/json")
        .body(reqBody1)
        .post("/api/v1/accounts");
    RestAssured.given()
        .contentType("application/json")
        .body(reqBody1)
        .post("/api/v1/accounts");
    RestAssured.given()
        .contentType("application/json")
        .body(reqBody2)
        .post("/api/v1/accounts");
    RestAssured.given()
        .contentType("application/json")
        .body(reqBody3)
        .post("/api/v1/accounts")
        .then()
        .statusCode(400);
  }
}
