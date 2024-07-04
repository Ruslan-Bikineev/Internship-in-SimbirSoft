package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static data.TestData.GET_CURRENT_USER;
import static data.TestData.NON_EXISTENT_LOGIN;
import static data.TestData.NON_EXISTENT_PASSWORD;
import static data.TestData.VALID_LOGIN;
import static data.TestData.VALID_PASSWORD;
import static io.qameta.allure.SeverityLevel.BLOCKER;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Epic(value = "Авторизация")
@Feature(value = "Авторизация через API")
public class AuthorizationTests extends BaseTest {
    @Test
    @Story(value = "Авторизация с валидными данными")
    @Owner(value = "Ruslan Bikineev")
    @Severity(BLOCKER)
    public void testAuthorizationWithValidData() {
        RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .when()
                .get(GET_CURRENT_USER)
                .then()
                .assertThat().statusCode(200)
                .body(matchesJsonSchemaInClasspath(
                        "schemas/AuthorizationSuccessfulResponses.json"));
    }

    @Test
    @Story(value = "Авторизация с корректным логином и пустым паролем")
    @Owner(value = "Ruslan Bikineev")
    @Severity(BLOCKER)
    public void testAuthorizationWithValidLoginAndEmptyPassword() {
        RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, "")
                .when()
                .get(GET_CURRENT_USER)
                .then()
                .assertThat().statusCode(500)
                .body(matchesJsonSchemaInClasspath(
                        "schemas/ServerErrorResponses.json"));
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
        RestAssured.given()
                .auth()
                .preemptive()
                .basic(login, password)
                .when()
                .get(GET_CURRENT_USER)
                .then()
                .assertThat().statusCode(500)
                .body(matchesJsonSchemaInClasspath(
                        "schemas/ServerErrorResponses.json"));
    }
}
