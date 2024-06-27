package tests;

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
import repository.PostRepositoryImpl;

import java.util.Map;
import java.util.Optional;

import static data.TestData.DELETE;
import static data.TestData.POST;
import static data.TestData.PUT;
import static data.TestData.VALID_LOGIN;
import static data.TestData.VALID_PASSWORD;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Epic(value = "Пост")
@Feature(value = "Проверка CRUD запросов поста через API")
public class WordPressTests extends BaseTest {
    @Test
    @Story(value = "Добавление нового поста авторизованным пользователем (Вызов метода POST)")
    @Owner(value = "Ruslan Bikineev")
    @Severity(CRITICAL)
    public void testCreatePostWithAuthorizationUserAndFillBodyMethodPost() {
        // Preconditions
        Map<String, String> body = Post.getDefaultJsonBodyPost();
        PostRepositoryImpl postsRepository = new PostRepositoryImpl();

        setStatusCodeToResponseSpecification(201);
        Response response = RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body(body)
                .when()
                .post(POST)
                .then()
                .body(matchesJsonSchemaInClasspath(
                        "Schemas/Create&EditPostSuccessfulResponses.json"))
                .extract().response();
        long id = response.jsonPath().getLong("id");
        Optional<Post> byId = postsRepository.findById(id);
        Assert.assertTrue(byId.isPresent());
        Assert.assertTrue(byId.get().isEqualWithDefaultJsonBodyPost());
        // Post conditions
        postConditionDeletePost(id);
    }

    @Test
    @Story(value = "Добавление нового поста без авторизации (Вызов метода POST)")
    @Owner(value = "Ruslan Bikineev")
    @Severity(CRITICAL)
    public void testCreatePostWithoutAuthorizationUserAndFillBodyMethodPost() {
        setStatusCodeToResponseSpecification(401);
        RestAssured.given()
                .body(Post.getDefaultJsonBodyPost())
                .when()
                .post(POST)
                .then()
                .body(matchesJsonSchemaInClasspath(
                        "Schemas/ClientErrorResponses.json"));
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
                        "Schemas/ClientErrorResponses.json"));
    }

    @Test
    @Story(value = "Удаление поста авторизованным пользователем (Вызов метода DELETE)")
    @Owner(value = "Ruslan Bikineev")
    @Severity(CRITICAL)
    public void testDeletePostWithAuthorizationUserMethodDelete() {
        // Preconditions
        PostRepositoryImpl postsRepository = new PostRepositoryImpl();
        long id = preConditionCreatePost();

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
                        "Schemas/DeletePostSuccessfulResponses.json"));
        Optional<Post> byId = postsRepository.findById(id);
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
                        "Schemas/ClientErrorResponses.json"));
    }

    @Test
    @Story(value = "Удаление поста без авторизации (Вызов метода DELETE)")
    @Owner(value = "Ruslan Bikineev")
    @Severity(CRITICAL)
    public void testDeletePostWithoutAuthorizationUserMethodDelete() {
        // Preconditions
        long id = preConditionCreatePost();

        setStatusCodeToResponseSpecification(401);
        RestAssured.given()
                .body("{\"force\": \"true\"}")
                .when()
                .delete(DELETE + id)
                .then()
                .body(matchesJsonSchemaInClasspath(
                        "Schemas/ClientErrorResponses.json"));
        // Post conditions
        postConditionDeletePost(id);
    }

    @Test
    @Story(value = "Редактирование поста авторизованным пользователем (Вызов метода PUT)")
    @Owner(value = "Ruslan Bikineev")
    @Severity(CRITICAL)
    public void testEditPostWithAuthorizationUserMethodPut() {
        // Preconditions
        PostRepositoryImpl postsRepository = new PostRepositoryImpl();
        long id = preConditionCreatePost();

        setStatusCodeToResponseSpecification(200);
        Response response = RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body("{\"title\": \"Change test title\"}")
                .put(PUT + id)
                .then()
                .body(matchesJsonSchemaInClasspath(
                        "Schemas/Create&EditPostSuccessfulResponses.json"))
                .extract().response();
        Assert.assertEquals(response.jsonPath().getInt("id"), id);
        Optional<Post> byId = postsRepository.findById(id);
        Assert.assertTrue(byId.isPresent());
        Assert.assertEquals(byId.get().getPostTitle(), "Change test title");
        // Post conditions
        postConditionDeletePost(id);
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
                        "Schemas/ClientErrorResponses.json"));
    }

    @Test
    @Story(value = "Редактирование поста без авторизации (Вызов метода PUT)")
    @Owner(value = "Ruslan Bikineev")
    @Severity(CRITICAL)
    public void testEditPostWithoutAuthorizationUserMethodPut() {
        // Preconditions
        long id = preConditionCreatePost();

        setStatusCodeToResponseSpecification(401);
        RestAssured.given()
                .body("{\"title\": \"Change test title\"}")
                .put(PUT + id)
                .then()
                .body(matchesJsonSchemaInClasspath(
                        "Schemas/ClientErrorResponses.json"));
        // Post conditions
        postConditionDeletePost(id);
    }
}
