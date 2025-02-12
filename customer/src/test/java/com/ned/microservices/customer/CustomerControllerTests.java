package com.ned.microservices.customer;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.containers.PostgreSQLContainer;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerTests {

  static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres");
  @LocalServerPort
  private int port;

  String reqBody = """
       {
          "email": "test@example.com",
          "name": "Test Bill",
          "address": "Test Street",
          "customerType": "RETAIL"
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
  void testSave() {
    RestAssured.given()
        .contentType("application/json")
        .body(reqBody)
        .post("/api/v1/customers")
        .then().statusCode(200)
        .body("id", Matchers.notNullValue())
        .body("id", Matchers.greaterThan(999999))
        .body("id", Matchers.lessThan(10000000))
        .body("name", Matchers.equalTo("Test Bill"))
        .body("address", Matchers.equalTo("Test Street"))
        .body("email", Matchers.equalTo("test@example.com"))
        .body("customerType", Matchers.oneOf("RETAIL", "CORPORATE", "INVESTMENT"))
        .body("accountIds", Matchers.empty());
  }

  @Test
  void testFindAll() {
    RestAssured.given()
        .contentType("application/json")
        .body(reqBody)
        .post("/api/v1/customers");

    RestAssured.given()
        .get("/api/v1/customers")
        .then().statusCode(200)
        .body("$.size()", Matchers.greaterThan(0))
        .body("[0].email", Matchers.equalTo("test@example.com"));
  }

  @Test
  void testFindCustomerWithAccounts() {
    var customerId = 0;

    customerId = RestAssured.given()
        .contentType("application/json")
        .body(reqBody)
        .post("/api/v1/customers")
        .then()
        .extract()
        .path("id");

    RestAssured.given()
        .get("/api/v1/customers/with-accounts/" + customerId)
        .then().statusCode(200);
  }

  @Test
  void testGetCustomer() {
    var customerId = 0;

    customerId = RestAssured.given()
        .contentType("application/json")
        .body(reqBody)
        .post("/api/v1/customers")
        .then()
        .extract()
        .path("id");

    RestAssured.given()
        .get("/api/v1/customers/" + customerId)
        .then().statusCode(200)
        .body("id", Matchers.equalTo(customerId));
  }

  @Test
  void testGetCustomerNotFound() {
    RestAssured.given()
        .get("/api/v1/customers/999999")
        .then().statusCode(404);
  }

  @Test
  void testGetCustomerWithAccountsNotFound() {
    RestAssured.given()
        .get("/api/v1/customers/with-accounts/999999")
        .then().statusCode(404);
  }
}
