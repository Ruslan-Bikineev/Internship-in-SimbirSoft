package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

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
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body(requestBody.toString())
                .when()
                .post("http://localhost:8000/index.php?rest_route=/wp/v2/posts")
                .then()
                .body(matchesJsonSchemaInClasspath("Schemas/Create&EditPostSuccessfulResponsesSchema.json"))
                .extract().response();
        Assert.assertEquals(response.statusCode(), 201);


        RestAssured.given()
                .contentType(ContentType.JSON)
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body("{\"force\": \"true\"}")
                .when()
                .delete("http://localhost:8000/index.php?rest_route=/wp/v2/posts/" + response.jsonPath().getInt("id"))
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
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .when()
                .post("http://localhost:8000/index.php?rest_route=/wp/v2/posts")
                .then()
                .body(matchesJsonSchemaInClasspath("Schemas/ClientErrorResponsesSchema.json"))
                .extract().response();
        Assert.assertEquals(response.statusCode(), 401);
    }

    @Test
    @Story(value = "Добавление нового поста с пустым телом запроса JSON авторизованным пользователем (Вызов метода POST)")
    @Owner(value = "Ruslan Bikineev")
    @Severity(CRITICAL)
    public void testCreatePostWithAuthorizationUserAndEmptyBodyMethodPost() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .when()
                .post("http://localhost:8000/index.php?rest_route=/wp/v2/posts")
                .then()
                .body(matchesJsonSchemaInClasspath("Schemas/ClientErrorResponsesSchema.json"))
                .extract().response();
        Assert.assertEquals(response.statusCode(), 400);
    }

    @Test
    @Story(value = "Удаление поста авторизованным пользователем (Вызов метода DELETE)")
    @Owner(value = "Ruslan Bikineev")
    @Severity(CRITICAL)
    public void testDeletePostWithAuthorizationUserMethodDelete() {
        Response preConditionResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body("{\"title\": \"Test title for DELETE\"}")
                .when()
                .post("http://localhost:8000/index.php?rest_route=/wp/v2/posts")
                .then()
                .body(matchesJsonSchemaInClasspath("Schemas/Create&EditPostSuccessfulResponsesSchema.json"))
                .extract().response();
        Assert.assertEquals(preConditionResponse.statusCode(), 201);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body("{\"force\": \"true\"}")
                .when()
                .delete("http://localhost:8000/index.php?rest_route=/wp/v2/posts/" + preConditionResponse.jsonPath().getInt("id"))
                .then()
                .body(matchesJsonSchemaInClasspath("Schemas/DeletePostSuccessfulResponsesSchema.json"))
                .extract().response();
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test
    @Story(value = "Удаление несуществующего поста авторизованным пользователем (Вызов метода DELETE)")
    @Owner(value = "Ruslan Bikineev")
    @Severity(CRITICAL)
    public void testDeleteNonExistentPostWithAuthorizationUserMethodDelete() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body("{\"force\": \"true\"}")
                .when()
                .delete("http://localhost:8000/index.php?rest_route=/wp/v2/posts/0")
                .then()
                .body(matchesJsonSchemaInClasspath("Schemas/ClientErrorResponsesSchema.json"))
                .assertThat().statusCode(404);
    }

    @Test
    @Story(value = "Удаление поста без авторизации (Вызов метода DELETE)")
    @Owner(value = "Ruslan Bikineev")
    @Severity(CRITICAL)
    public void testDeletePostWithoutAuthorizationUserMethodDelete() {
        Response preConditionResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body("{\"title\": \"Test title for DELETE\"}")
                .when()
                .post("http://localhost:8000/index.php?rest_route=/wp/v2/posts")
                .then()
                .body(matchesJsonSchemaInClasspath("Schemas/Create&EditPostSuccessfulResponsesSchema.json"))
                .extract().response();
        Assert.assertEquals(preConditionResponse.statusCode(), 201);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{\"force\": \"true\"}")
                .when()
                .delete("http://localhost:8000/index.php?rest_route=/wp/v2/posts/" + preConditionResponse.jsonPath().getInt("id"))
                .then()
                .body(matchesJsonSchemaInClasspath("Schemas/ClientErrorResponsesSchema.json"))
                .extract().response();
        Assert.assertEquals(response.statusCode(), 401);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body("{\"force\": \"true\"}")
                .when()
                .delete("http://localhost:8000/index.php?rest_route=/wp/v2/posts/" + preConditionResponse.jsonPath().getInt("id"))
                .then()
                .assertThat().statusCode(200);
    }

    @Test
    @Story(value = "Редактирование поста авторизованным пользователем (Вызов метода PUT)")
    @Owner(value = "Ruslan Bikineev")
    @Severity(CRITICAL)
    public void testEditPostWithAuthorizationUserMethodPut() {
        Response preConditionResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body("{\"title\": \"Test title\"}")
                .when()
                .post("http://localhost:8000/index.php?rest_route=/wp/v2/posts")
                .then()
                .extract().response();

        Assert.assertEquals(preConditionResponse.statusCode(), 201);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body("{\"title\": \"Change test title\"}")
                .put("http://localhost:8000/index.php?rest_route=/wp/v2/posts/" + preConditionResponse.jsonPath().getInt("id"))
                .then()
                .body(matchesJsonSchemaInClasspath("Schemas/Create&EditPostSuccessfulResponsesSchema.json"))
                .extract().response();
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(preConditionResponse.jsonPath().getInt("id"), response.jsonPath().getInt("id"));

        RestAssured.given()
                .contentType(ContentType.JSON)
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body("{\"force\": \"true\"}")
                .when()
                .delete("http://localhost:8000/index.php?rest_route=/wp/v2/posts/" + preConditionResponse.jsonPath().getInt("id"))
                .then()
                .assertThat().statusCode(200);
    }

    @Test
    @Story(value = "Редактирование несуществующего поста авторизованным пользователем (Вызов метода PUT)")
    @Owner(value = "Ruslan Bikineev")
    @Severity(CRITICAL)
    public void testEditNonExistentPostWithAuthorizationUserMethodPut() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .put("http://localhost:8000/index.php?rest_route=/wp/v2/posts/0")
                .then()
                .body(matchesJsonSchemaInClasspath("Schemas/ClientErrorResponsesSchema.json"))
                .assertThat().statusCode(404);
    }

    @Test
    @Story(value = "Редактирование поста без авторизации (Вызов метода PUT)")
    @Owner(value = "Ruslan Bikineev")
    @Severity(CRITICAL)
    public void testEditPostWithoutAuthorizationUserMethodPut() {
        Response preConditionResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body("{\"title\": \"Test title\"}")
                .when()
                .post("http://localhost:8000/index.php?rest_route=/wp/v2/posts")
                .then()
                .extract().response();

        Assert.assertEquals(preConditionResponse.statusCode(), 201);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{\"title\": \"Change test title\"}")
                .put("http://localhost:8000/index.php?rest_route=/wp/v2/posts/" + preConditionResponse.jsonPath().getInt("id"))
                .then()
                .body(matchesJsonSchemaInClasspath("Schemas/ClientErrorResponsesSchema.json"))
                .assertThat().statusCode(401);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body("{\"force\": \"true\"}")
                .when()
                .delete("http://localhost:8000/index.php?rest_route=/wp/v2/posts/" + preConditionResponse.jsonPath().getInt("id"))
                .then()
                .assertThat().statusCode(200);
    }
}
