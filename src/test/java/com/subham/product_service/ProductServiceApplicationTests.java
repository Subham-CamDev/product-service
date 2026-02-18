package com.subham.product_service;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment  = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	@Test
	void testCreateProduct() {
		String requestBody = """
						{
						    "name": "Samsung Galaxy A21S",
						    "description": "An android mobile of Samsung company",
						    "price": "13000"
						}
						""";

		RestAssured.given()
						.contentType("application/json")
						.body(requestBody)
						.when()
						.post("/api/product")
						.then()
						.statusCode(201)
						.body(Matchers.equalTo("Product Creation Successful"));
	}

	@Test
	void testGetProducts() {
		RestAssured.given()
						.when()
						.get("/api/product")
						.then()
						.contentType(ContentType.JSON)
						.statusCode(200)
						.body("$", Matchers.not(Matchers.empty()));
	}

}
