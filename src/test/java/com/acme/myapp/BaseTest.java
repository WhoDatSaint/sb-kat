package com.acme.myapp;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.ValidatableResponse;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.InputStream;

import static com.jayway.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.springframework.http.HttpStatus.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringbootKatharsisApplication.class)
@WebIntegrationTest("server.port:0")
public abstract class BaseTest {

    @Value("${local.server.port}")
    protected int port;

    protected String jsonApiSchema;

    @Before
    public final void before() {
        RestAssured.port = port;
        loadJsonApiSchema();
    }

    private void loadJsonApiSchema() {
        try {
            jsonApiSchema = loadFile("json-api-schema.json");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static String loadFile(String filename) throws Exception {
        InputStream inputStream = BaseTest.class.getClassLoader().getResourceAsStream(
                filename);
        return IOUtils.toString(inputStream);
    }

    protected void testFindOne(String url) {
        ValidatableResponse response = RestAssured.given()
                .contentType("application/json")
                .when()
                .get(url)
                .then()
                .statusCode(OK.value());
        response
                .assertThat()
                .body(matchesJsonSchema(jsonApiSchema));
    }

    protected void testFindOne_NotFound(String url) {
        RestAssured.given()
                .contentType("application/json")
                .when()
                .get(url)
                .then()
                .statusCode(NOT_FOUND.value());
    }

    protected void testFindMany(String url) {
        ValidatableResponse response = RestAssured.given()
                .contentType("application/json")
                .when()
                .get(url)
                .then()
                .statusCode(OK.value());
        response
                .assertThat()
                .body(matchesJsonSchema(jsonApiSchema));
    }

    protected void testDelete(String url) {
        RestAssured.given()
                .contentType("application/json")
                .when()
                .delete(url)
                .then()
                .statusCode(NO_CONTENT.value());
    }

}
