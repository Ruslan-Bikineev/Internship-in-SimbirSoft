package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
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
import static io.qameta.allure.SeverityLevel.BLOCKER;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

//TODO: need decomposition RestAssured
@Epic(value = "Авторизация")
@Feature(value = "Авторизация через API")
public class AuthorizationTests {
    @Test
    @Story(value = "Авторизация с валидными данными")
    @Owner(value = "Ruslan Bikineev")
    @Severity(BLOCKER)
    public void testAuthorizationWithValidData() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .when()
                .get("http://localhost:8000/index.php?rest_route=/wp/v2/users/me")
                .then()
                .body(matchesJsonSchemaInClasspath("Schemas/AuthorizationSuccessfulResponsesSchemaStatus.json"))
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
    @Story(value = "Авторизация с разными невалидными данными")
    @Owner(value = "Ruslan Bikineev")
    @Severity(BLOCKER)
    public void testAuthorizationWithDifferentIncorrectData(String login, String password) {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .auth()
                .preemptive()
                .basic(login, password)
                .when()
                .get("http://localhost:8000/index.php?rest_route=/wp/v2/users/me")
                .then()
                .body(matchesJsonSchemaInClasspath("Schemas/ServerErrorResponsesSchema.json"))
                .extract()
                .response();
        Assert.assertEquals(response.statusCode(), 500);
    }
}
