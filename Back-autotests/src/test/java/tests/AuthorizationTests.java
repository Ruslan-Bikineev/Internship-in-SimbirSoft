package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static data.TestData.NON_EXISTENT_LOGIN;
import static data.TestData.NON_EXISTENT_PASSWORD;
import static data.TestData.VALID_LOGIN;
import static data.TestData.VALID_PASSWORD;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

//TODO: added testNg xml file
//TODO: need decomposition RestAssured
//TODO: added allure report and annotations
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

    @DataProvider(name = "DifferentIncorrectDataProvider")
    public Object[][] differentIncorrectDataProvider() {
        return new Object[][]{
                {"", ""},
                {"", NON_EXISTENT_PASSWORD},
                {NON_EXISTENT_LOGIN, ""},
                {NON_EXISTENT_LOGIN, NON_EXISTENT_PASSWORD}
        };
    }

    @Test(dataProvider = "DifferentIncorrectDataProvider")
    public void testAuthorizationWithDifferentIncorrectData(String login, String password) {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .auth()
                .preemptive()
                .basic(login, password)
                .when()
                .get("http://localhost:8000/index.php?rest_route=/wp/v2/users/me")
                .then()
                .body(matchesJsonSchemaInClasspath("AuthorizationSchemaStatus500.json"))
                .extract()
                .response();
        Assert.assertEquals(response.statusCode(), 500);
    }
}
