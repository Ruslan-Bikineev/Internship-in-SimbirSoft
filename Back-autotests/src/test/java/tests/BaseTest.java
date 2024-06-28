package tests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;

import static data.TestData.URL;

public class BaseTest {
    @BeforeClass
    public void setUp() {
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(URL)
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured())
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
}
