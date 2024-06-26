package tests;

import heplers.helpers;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.Post;
import org.testng.Assert;
import org.testng.annotations.Test;
import repository.WpPostsRepositoryImpl;

import java.util.Optional;

import static data.TestData.DELETE;
import static data.TestData.POST;
import static data.TestData.PUT;
import static data.TestData.VALID_LOGIN;
import static data.TestData.VALID_PASSWORD;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

//TODO: decomposition tests
@Epic(value = "Пост")
@Feature(value = "Проверка CRUD запросов поста через API")
public class WordPressTests extends BaseTest {
    @Test
    @Story(value = "Добавление нового поста авторизованным пользователем (Вызов метода POST)")
    @Owner(value = "Ruslan Bikineev")
    @Severity(CRITICAL)
    public void testCreatePostWithAuthorizationUserAndFillBodyMethodPost() {
        WpPostsRepositoryImpl wpPostsRepository = new WpPostsRepositoryImpl();
        setStatusCodeToResponseSpecification(201);
        Response response = RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body(helpers.getDefaultJsonBody().toString())
                .when()
                .post(POST)
                .then()
                .body(matchesJsonSchemaInClasspath(
                        "Schemas/Create&EditPostSuccessfulResponsesSchema.json"))
                .extract().response();

        long id = response.jsonPath().getLong("id");
        Optional<Post> byId = wpPostsRepository.findById(id);
        Assert.assertTrue(byId.isPresent());

        Assert.assertEquals(byId.get().getPostStatus(), "publish");
        Assert.assertEquals(byId.get().getPostTitle(), "Test title");
        Assert.assertEquals(byId.get().getPostContent(), "Test content");
        Assert.assertEquals(byId.get().getPostExcerpt(), "Test excerpt");
        Assert.assertEquals(byId.get().getCommentStatus(), "open");
        Assert.assertEquals(byId.get().getPingStatus(), "open");

        setStatusCodeToResponseSpecification(200);
        RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body("{\"force\": \"true\"}")
                .when()
                .delete(DELETE + id)
                .then();
    }

    @Test
    @Story(value = "Добавление нового поста без авторизации (Вызов метода POST)")
    @Owner(value = "Ruslan Bikineev")
    @Severity(CRITICAL)
    public void testCreatePostWithoutAuthorizationUserAndFillBodyMethodPost() {
        setStatusCodeToResponseSpecification(401);
        RestAssured.given()
                .body(helpers.getDefaultJsonBody().toString())
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
        setStatusCodeToResponseSpecification(400);
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
        WpPostsRepositoryImpl wpPostsRepository = new WpPostsRepositoryImpl();
        setStatusCodeToResponseSpecification(201);
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

        long id = preConditionResponse.jsonPath().getLong("id");
        setStatusCodeToResponseSpecification(200);
        RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body("{\"force\": \"true\"}")
                .when()
                .delete(DELETE + id)
                .then()
                .body(matchesJsonSchemaInClasspath(
                        "Schemas/DeletePostSuccessfulResponsesSchema.json"));
        Optional<Post> byId = wpPostsRepository.findById(id);
        Assert.assertFalse(byId.isPresent());
    }

    @Test
    @Story(value = "Удаление несуществующего поста авторизованным пользователем (Вызов метода DELETE)")
    @Owner(value = "Ruslan Bikineev")
    @Severity(CRITICAL)
    public void testDeleteNonExistentPostWithAuthorizationUserMethodDelete() {
        setStatusCodeToResponseSpecification(404);
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
        setStatusCodeToResponseSpecification(201);
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

        setStatusCodeToResponseSpecification(401);
        RestAssured.given()
                .body("{\"force\": \"true\"}")
                .when()
                .delete(DELETE + preConditionResponse.jsonPath().getInt("id"))
                .then()
                .body(matchesJsonSchemaInClasspath(
                        "Schemas/ClientErrorResponsesSchema.json"));

        setStatusCodeToResponseSpecification(200);
        RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body("{\"force\": \"true\"}")
                .when()
                .delete(DELETE + preConditionResponse.jsonPath().getInt("id"));
    }

    @Test
    @Story(value = "Редактирование поста авторизованным пользователем (Вызов метода PUT)")
    @Owner(value = "Ruslan Bikineev")
    @Severity(CRITICAL)
    public void testEditPostWithAuthorizationUserMethodPut() {
        WpPostsRepositoryImpl wpPostsRepository = new WpPostsRepositoryImpl();
        setStatusCodeToResponseSpecification(201);
        Response preConditionResponse = RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body("{\"title\": \"Test title\"}")
                .when()
                .post(POST)
                .then()
                .extract().response();
        long id = preConditionResponse.jsonPath().getLong("id");
        setStatusCodeToResponseSpecification(200);
        Response response = RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body("{\"title\": \"Change test title\"}")
                .put(PUT + id)
                .then()
                .body(matchesJsonSchemaInClasspath(
                        "Schemas/Create&EditPostSuccessfulResponsesSchema.json"))
                .extract().response();
        Assert.assertEquals(response.jsonPath().getInt("id"), id);
        Optional<Post> byId = wpPostsRepository.findById(id);
        Assert.assertTrue(byId.isPresent());
        Assert.assertEquals(byId.get().getPostTitle(), "Change test title");
        RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body("{\"force\": \"true\"}")
                .when()
                .delete(DELETE + preConditionResponse.jsonPath().getInt("id"));
    }

    @Test
    @Story(value = "Редактирование несуществующего поста авторизованным пользователем (Вызов метода PUT)")
    @Owner(value = "Ruslan Bikineev")
    @Severity(CRITICAL)
    public void testEditNonExistentPostWithAuthorizationUserMethodPut() {
        setStatusCodeToResponseSpecification(404);
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
        setStatusCodeToResponseSpecification(201);
        Response preConditionResponse = RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body("{\"title\": \"Test title\"}")
                .when()
                .post(POST)
                .then()
                .extract().response();

        setStatusCodeToResponseSpecification(401);
        RestAssured.given()
                .body("{\"title\": \"Change test title\"}")
                .put(PUT + preConditionResponse.jsonPath().getInt("id"))
                .then()
                .body(matchesJsonSchemaInClasspath(
                        "Schemas/ClientErrorResponsesSchema.json"));

        setStatusCodeToResponseSpecification(200);
        RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body("{\"force\": \"true\"}")
                .when()
                .delete(DELETE + preConditionResponse.jsonPath().getInt("id"));
    }
}
