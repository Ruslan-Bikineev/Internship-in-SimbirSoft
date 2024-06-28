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
import repository.PostsRepositoryImpl;

import java.util.Map;
import java.util.Optional;

import static data.TestData.CREATE_POST;
import static data.TestData.DELETE_POST;
import static data.TestData.UPDATE_POST;
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
        PostsRepositoryImpl postsRepository = new PostsRepositoryImpl();

        Response response = RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body(body)
                .when()
                .post(CREATE_POST)
                .then()
                .assertThat().statusCode(201)
                .body(matchesJsonSchemaInClasspath(
                        "schemas/Create&EditPostSuccessfulResponses.json"))
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
        RestAssured.given()
                .body(Post.getDefaultJsonBodyPost())
                .when()
                .post(CREATE_POST)
                .then()
                .assertThat().statusCode(401)
                .body(matchesJsonSchemaInClasspath(
                        "schemas/ClientErrorResponses.json"));
    }

    @Test
    @Story(value = "Добавление нового поста с пустым телом запроса JSON авторизованным пользователем (Вызов метода POST)")
    @Owner(value = "Ruslan Bikineev")
    @Severity(CRITICAL)
    public void testCreatePostWithAuthorizationUserAndEmptyBodyMethodPost() {
        RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .when()
                .post(CREATE_POST)
                .then()
                .assertThat().statusCode(400)
                .body(matchesJsonSchemaInClasspath(
                        "schemas/ClientErrorResponses.json"));
    }

    @Test
    @Story(value = "Удаление поста авторизованным пользователем (Вызов метода DELETE)")
    @Owner(value = "Ruslan Bikineev")
    @Severity(CRITICAL)
    public void testDeletePostWithAuthorizationUserMethodDelete() {
        // Preconditions
        PostsRepositoryImpl postsRepository = new PostsRepositoryImpl();
        long id = preConditionCreatePost();

        RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body("{\"force\": \"true\"}")
                .when()
                .delete(DELETE_POST + id)
                .then()
                .assertThat().statusCode(200)
                .body(matchesJsonSchemaInClasspath(
                        "schemas/DeletePostSuccessfulResponses.json"));
        Optional<Post> byId = postsRepository.findById(id);
        Assert.assertFalse(byId.isPresent());
    }

    @Test
    @Story(value = "Удаление несуществующего поста авторизованным пользователем (Вызов метода DELETE)")
    @Owner(value = "Ruslan Bikineev")
    @Severity(CRITICAL)
    public void testDeleteNonExistentPostWithAuthorizationUserMethodDelete() {
        RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body("{\"force\": \"true\"}")
                .when()
                .delete(DELETE_POST + "0")
                .then()
                .assertThat().statusCode(404)
                .body(matchesJsonSchemaInClasspath(
                        "schemas/ClientErrorResponses.json"));
    }

    @Test
    @Story(value = "Удаление поста без авторизации (Вызов метода DELETE)")
    @Owner(value = "Ruslan Bikineev")
    @Severity(CRITICAL)
    public void testDeletePostWithoutAuthorizationUserMethodDelete() {
        // Preconditions
        long id = preConditionCreatePost();

        RestAssured.given()
                .body("{\"force\": \"true\"}")
                .when()
                .delete(DELETE_POST + id)
                .then()
                .assertThat().statusCode(401)
                .body(matchesJsonSchemaInClasspath(
                        "schemas/ClientErrorResponses.json"));
        // Post conditions
        postConditionDeletePost(id);
    }

    @Test
    @Story(value = "Редактирование поста авторизованным пользователем (Вызов метода PUT)")
    @Owner(value = "Ruslan Bikineev")
    @Severity(CRITICAL)
    public void testEditPostWithAuthorizationUserMethodPut() {
        // Preconditions
        PostsRepositoryImpl postsRepository = new PostsRepositoryImpl();
        long id = preConditionCreatePost();

        Response response = RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body("{\"title\": \"Change test title\"}")
                .put(UPDATE_POST + id)
                .then()
                .assertThat().statusCode(200)
                .body(matchesJsonSchemaInClasspath(
                        "schemas/Create&EditPostSuccessfulResponses.json"))
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
        RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .put(UPDATE_POST + "0")
                .then()
                .assertThat().statusCode(404)
                .body(matchesJsonSchemaInClasspath(
                        "schemas/ClientErrorResponses.json"));
    }

    @Test
    @Story(value = "Редактирование поста без авторизации (Вызов метода PUT)")
    @Owner(value = "Ruslan Bikineev")
    @Severity(CRITICAL)
    public void testEditPostWithoutAuthorizationUserMethodPut() {
        // Preconditions
        long id = preConditionCreatePost();

        RestAssured.given()
                .body("{\"title\": \"Change test title\"}")
                .put(UPDATE_POST + id)
                .then()
                .assertThat().statusCode(401)
                .body(matchesJsonSchemaInClasspath(
                        "schemas/ClientErrorResponses.json"));
        // Post conditions
        postConditionDeletePost(id);
    }

    private long preConditionCreatePost() {
        return RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body(Post.getDefaultJsonBodyPost())
                .when()
                .post(CREATE_POST)
                .then()
                .assertThat().statusCode(201)
                .extract().jsonPath().getLong("id");
    }

    private void postConditionDeletePost(long id) {
        RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body("{\"force\": \"true\"}")
                .when()
                .delete(DELETE_POST + id)
                .then()
                .assertThat().statusCode(200);
    }
}
