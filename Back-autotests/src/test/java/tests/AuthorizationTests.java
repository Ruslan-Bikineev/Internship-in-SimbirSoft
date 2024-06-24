package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static data.TestData.NON_EXISTENT_LOGIN;
import static data.TestData.NON_EXISTENT_PASSWORD;
import static data.TestData.VALID_LOGIN;
import static data.TestData.VALID_PASSWORD;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

//TODO: added testNg xml file
//TODO: added allure report and annotations
//TODO: need decomposition tests with DataProvider
public class AuthorizationTests {
    @Test
    public void testAuthorizationWithValidData() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .when()
                .get("http://localhost:8000/index.php?rest_route=/wp/v2/users/me")
                .then()
                .body(matchesJsonSchemaInClasspath("AuthorizationSchemaStatus200.json"))
                .extract()
                .response();
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test
    public void testAuthorizationWithValidLoginAndEmptyPassword() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, "")
                .when()
                .get("http://localhost:8000/index.php?rest_route=/wp/v2/users/me")
                .then()
                .body(matchesJsonSchemaInClasspath("AuthorizationSchemaStatus500.json"))
                .extract()
                .response();
        Assert.assertEquals(response.statusCode(), 500);
    }

    @Test
    public void testAuthorizationWithEmptyLoginAndValidPassword() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .auth()
                .preemptive()
                .basic("", VALID_PASSWORD)
                .when()
                .get("http://localhost:8000/index.php?rest_route=/wp/v2/users/me")
                .then()
                .body(matchesJsonSchemaInClasspath("AuthorizationSchemaStatus500.json"))
                .extract()
                .response();
        Assert.assertEquals(response.statusCode(), 500);
    }

    @Test
    public void testAuthorizationWithEmptyData() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .auth()
                .preemptive()
                .basic("", "")
                .when()
                .get("http://localhost:8000/index.php?rest_route=/wp/v2/users/me")
                .then()
                .body(matchesJsonSchemaInClasspath("AuthorizationSchemaStatus500.json"))
                .extract()
                .response();
        Assert.assertEquals(response.statusCode(), 500);
    }

    @Test
    public void testAuthorizationWithNonExistentData() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .auth()
                .preemptive()
                .basic(NON_EXISTENT_LOGIN, NON_EXISTENT_PASSWORD)
                .when()
                .get("http://localhost:8000/index.php?rest_route=/wp/v2/users/me")
                .then()
                .body(matchesJsonSchemaInClasspath("AuthorizationSchemaStatus500.json"))
                .extract()
                .response();
        Assert.assertEquals(response.statusCode(), 500);
    }

    @Test
    public void testAuthorizationWithValidLoginAndNonExistentPassword() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, NON_EXISTENT_PASSWORD)
                .when()
                .get("http://localhost:8000/index.php?rest_route=/wp/v2/users/me")
                .then()
                .body(matchesJsonSchemaInClasspath("AuthorizationSchemaStatus500.json"))
                .extract()
                .response();
        Assert.assertEquals(response.statusCode(), 500);
    }
}
