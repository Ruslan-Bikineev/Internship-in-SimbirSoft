package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static data.TestData.DELETE;
import static data.TestData.POST;
import static data.TestData.PUT;
import static data.TestData.URL;
import static data.TestData.VALID_LOGIN;
import static data.TestData.VALID_PASSWORD;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;


//TODO: check in DB
//TODO: decomposition tests
@Epic(value = "Пост")
@Feature(value = "Проверка CRUD запросов поста через API")
public class WordPressTests {
    @Test
    @Story(value = "Добавление нового поста авторизованным пользователем (Вызов метода POST)")
    @Owner(value = "Ruslan Bikineev")
    @Severity(CRITICAL)
    public void testCreatePostWithAuthorizationUserAndFillBodyMethodPost() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("slug", "Test slug");
        requestBody.put("status", "publish");
        requestBody.put("title", "Test title");
        requestBody.put("content", "Test content");
        requestBody.put("author", "1");
        requestBody.put("excerpt", "Test excerpt");
        requestBody.put("comment_status", "open");
        requestBody.put("ping_status", "open");
        requestBody.put("format", "standard");
        requestBody.put("sticky", false);
        Specification.installSpecification(Specification.requestSpecification(URL));
        Response response = RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body(requestBody.toString())
                .when()
                .post(POST)
                .then()
                .body(matchesJsonSchemaInClasspath(
                        "Schemas/Create&EditPostSuccessfulResponsesSchema.json"))
                .extract().response();
        Assert.assertEquals(response.statusCode(), 201);

        RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body("{\"force\": \"true\"}")
                .when()
                .delete(DELETE + response.jsonPath().getInt("id"))
                .then()
                .assertThat().statusCode(200);
    }

    @Test
    @Story(value = "Добавление нового поста без авторизации (Вызов метода POST)")
    @Owner(value = "Ruslan Bikineev")
    @Severity(CRITICAL)
    public void testCreatePostWithoutAuthorizationUserAndFillBodyMethodPost() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("slug", "Test slug");
        requestBody.put("status", "publish");
        requestBody.put("title", "Test title");
        requestBody.put("content", "Test content");
        requestBody.put("author", "1");
        requestBody.put("excerpt", "Test excerpt");
        requestBody.put("comment_status", "open");
        requestBody.put("ping_status", "open");
        requestBody.put("format", "standard");
        requestBody.put("sticky", false);
        Specification.installSpecification(Specification.requestSpecification(URL),
                Specification.responseSpecification(401));
        RestAssured.given()
                .body(requestBody.toString())
                .when()
                .post(POST)
                .then()
                .body(matchesJsonSchemaInClasspath(
                        "Schemas/ClientErrorResponsesSchema.json"));
    }

    @Test
    @Story(value = "Добавление нового поста с пустым телом запроса JSON авторизованным пользователем (Вызов метода POST)")
    @Owner(value = "Ruslan Bikineev")
    @Severity(CRITICAL)
    public void testCreatePostWithAuthorizationUserAndEmptyBodyMethodPost() {
        Specification.installSpecification(Specification.requestSpecification(URL),
                Specification.responseSpecification(400));
        RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .when()
                .post(POST)
                .then()
                .body(matchesJsonSchemaInClasspath(
                        "Schemas/ClientErrorResponsesSchema.json"));
    }

    @Test
    @Story(value = "Удаление поста авторизованным пользователем (Вызов метода DELETE)")
    @Owner(value = "Ruslan Bikineev")
    @Severity(CRITICAL)
    public void testDeletePostWithAuthorizationUserMethodDelete() {
        Specification.installSpecification(Specification.requestSpecification(URL));
        Response preConditionResponse = RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body("{\"title\": \"Test title for DELETE\"}")
                .when()
                .post(POST)
                .then()
                .body(matchesJsonSchemaInClasspath(
                        "Schemas/Create&EditPostSuccessfulResponsesSchema.json"))
                .extract().response();
        Assert.assertEquals(preConditionResponse.statusCode(), 201);
        RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body("{\"force\": \"true\"}")
                .when()
                .delete(DELETE + preConditionResponse.jsonPath().getInt("id"))
                .then()
                .body(matchesJsonSchemaInClasspath(
                        "Schemas/DeletePostSuccessfulResponsesSchema.json"))
                .assertThat().statusCode(200);
    }

    @Test
    @Story(value = "Удаление несуществующего поста авторизованным пользователем (Вызов метода DELETE)")
    @Owner(value = "Ruslan Bikineev")
    @Severity(CRITICAL)
    public void testDeleteNonExistentPostWithAuthorizationUserMethodDelete() {
        Specification.installSpecification(Specification.requestSpecification(URL),
                Specification.responseSpecification(404));
        RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body("{\"force\": \"true\"}")
                .when()
                .delete(DELETE + "0")
                .then()
                .body(matchesJsonSchemaInClasspath(
                        "Schemas/ClientErrorResponsesSchema.json"));
    }

    @Test
    @Story(value = "Удаление поста без авторизации (Вызов метода DELETE)")
    @Owner(value = "Ruslan Bikineev")
    @Severity(CRITICAL)
    public void testDeletePostWithoutAuthorizationUserMethodDelete() {
        Specification.installSpecification(Specification.requestSpecification(URL));
        Response preConditionResponse = RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body("{\"title\": \"Test title for DELETE\"}")
                .when()
                .post(POST)
                .then()
                .body(matchesJsonSchemaInClasspath(
                        "Schemas/Create&EditPostSuccessfulResponsesSchema.json"))
                .extract().response();
        Assert.assertEquals(preConditionResponse.statusCode(), 201);

        RestAssured.given()
                .body("{\"force\": \"true\"}")
                .when()
                .delete(DELETE + preConditionResponse.jsonPath().getInt("id"))
                .then()
                .body(matchesJsonSchemaInClasspath(
                        "Schemas/ClientErrorResponsesSchema.json"))
                .assertThat().statusCode(401);

        RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body("{\"force\": \"true\"}")
                .when()
                .delete(DELETE + preConditionResponse.jsonPath().getInt("id"))
                .then()
                .assertThat().statusCode(200);
    }

    @Test
    @Story(value = "Редактирование поста авторизованным пользователем (Вызов метода PUT)")
    @Owner(value = "Ruslan Bikineev")
    @Severity(CRITICAL)
    public void testEditPostWithAuthorizationUserMethodPut() {
        Specification.installSpecification(Specification.requestSpecification(URL));
        Response preConditionResponse = RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body("{\"title\": \"Test title\"}")
                .when()
                .post(POST)
                .then()
                .extract().response();
        Assert.assertEquals(preConditionResponse.statusCode(), 201);

        Response response = RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body("{\"title\": \"Change test title\"}")
                .put(PUT + preConditionResponse.jsonPath().getInt("id"))
                .then()
                .body(matchesJsonSchemaInClasspath(
                        "Schemas/Create&EditPostSuccessfulResponsesSchema.json"))
                .extract().response();
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getInt("id"),
                preConditionResponse.jsonPath().getInt("id"));

        RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body("{\"force\": \"true\"}")
                .when()
                .delete(DELETE + preConditionResponse.jsonPath().getInt("id"))
                .then()
                .assertThat().statusCode(200);
    }

    @Test
    @Story(value = "Редактирование несуществующего поста авторизованным пользователем (Вызов метода PUT)")
    @Owner(value = "Ruslan Bikineev")
    @Severity(CRITICAL)
    public void testEditNonExistentPostWithAuthorizationUserMethodPut() {
        Specification.installSpecification(Specification.requestSpecification(URL),
                Specification.responseSpecification(404));
        RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .put(PUT + "0")
                .then()
                .body(matchesJsonSchemaInClasspath(
                        "Schemas/ClientErrorResponsesSchema.json"));
    }

    @Test
    @Story(value = "Редактирование поста без авторизации (Вызов метода PUT)")
    @Owner(value = "Ruslan Bikineev")
    @Severity(CRITICAL)
    public void testEditPostWithoutAuthorizationUserMethodPut() {
        Specification.installSpecification(Specification.requestSpecification(URL));
        Response preConditionResponse = RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body("{\"title\": \"Test title\"}")
                .when()
                .post(POST)
                .then()
                .extract().response();
        Assert.assertEquals(preConditionResponse.statusCode(), 201);

        RestAssured.given()
                .body("{\"title\": \"Change test title\"}")
                .put(PUT + preConditionResponse.jsonPath().getInt("id"))
                .then()
                .body(matchesJsonSchemaInClasspath(
                        "Schemas/ClientErrorResponsesSchema.json"))
                .assertThat().statusCode(401);

        RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body("{\"force\": \"true\"}")
                .when()
                .delete(DELETE + preConditionResponse.jsonPath().getInt("id"))
                .then()
                .assertThat().statusCode(200);
    }
}
