package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import models.Post;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import repository.PostsRepositoryImpl;

import static data.TestData.CREATE_POST;
import static data.TestData.DELETE_POST;
import static data.TestData.UPDATE_POST;
import static data.TestData.VALID_LOGIN;
import static data.TestData.VALID_PASSWORD;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Epic(value = "Пост")
@Feature(value = "Проверка CRUD запросов поста через API")
public class WordPressPostCRUD2Tests extends BaseTest {
    long preConditionPostId;

    @BeforeMethod
    public void setUpBeforeMethod() {
        preConditionPostId = RestAssured.given()
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

    @AfterMethod
    public void tearDownAfterMethod() {
        RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body("{\"force\": \"true\"}")
                .when()
                .delete(DELETE_POST + preConditionPostId)
                .then()
                .assertThat().statusCode(200);
    }

    @Test
    @Story(value = "Удаление поста без авторизации (Вызов метода DELETE)")
    @Owner(value = "Ruslan Bikineev")
    @Severity(CRITICAL)
    public void testDeletePostWithoutAuthorizationUserMethodDelete() {
        RestAssured.given()
                .body("{\"force\": \"true\"}")
                .when()
                .delete(DELETE_POST + preConditionPostId)
                .then()
                .assertThat().statusCode(401)
                .body(matchesJsonSchemaInClasspath(
                        "schemas/ClientErrorResponses.json"));
    }

    @Test
    @Story(value = "Редактирование поста авторизованным пользователем (Вызов метода PUT)")
    @Owner(value = "Ruslan Bikineev")
    @Severity(CRITICAL)
    public void testEditPostWithAuthorizationUserMethodPut() {
        PostsRepositoryImpl postsRepository = new PostsRepositoryImpl();
        long responseId = RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .body("{\"title\": \"Change test title\"}")
                .put(UPDATE_POST + preConditionPostId)
                .then()
                .assertThat().statusCode(200)
                .body(matchesJsonSchemaInClasspath(
                        "schemas/Create&EditPostSuccessfulResponses.json"))
                .extract().jsonPath().getLong("id");
        Assert.assertEquals(responseId, preConditionPostId);
        Post postsRepositoryById = postsRepository.findById(preConditionPostId);
        Assert.assertNotNull(postsRepositoryById);
        Assert.assertEquals(postsRepositoryById.getPostTitle(), "Change test title");
    }

    @Test
    @Story(value = "Редактирование поста без авторизации (Вызов метода PUT)")
    @Owner(value = "Ruslan Bikineev")
    @Severity(CRITICAL)
    public void testEditPostWithoutAuthorizationUserMethodPut() {
        RestAssured.given()
                .body("{\"title\": \"Change test title\"}")
                .put(UPDATE_POST + preConditionPostId)
                .then()
                .assertThat().statusCode(401)
                .body(matchesJsonSchemaInClasspath(
                        "schemas/ClientErrorResponses.json"));
    }
}
