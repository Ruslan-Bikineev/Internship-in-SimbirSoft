package tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.Post;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;

import static data.TestData.DELETE;
import static data.TestData.POST;
import static data.TestData.URL;
import static data.TestData.VALID_LOGIN;
import static data.TestData.VALID_PASSWORD;

public class BaseTest {
    @BeforeClass
    public void setUp() {
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(URL)
                .setContentType(ContentType.JSON)
                .build();
    }

    @AfterTest
    public void tearDown() {
        RestAssured.reset();
    }

    public void setStatusCodeToResponseSpecification(int statusCode) {
        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .build();
    }

    public long preConditionCreatePost() {
        setStatusCodeToResponseSpecification(201);
        Response response = RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body(Post.getDefaultJsonBodyPost())
                .when()
                .post(POST)
                .then()
                .extract().response();
        return response.jsonPath().getLong("id");
    }

    public void postConditionDeletePost(long id) {
        setStatusCodeToResponseSpecification(200);
        RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body("{\"force\": \"true\"}")
                .when()
                .delete(DELETE + id);
    }
}
