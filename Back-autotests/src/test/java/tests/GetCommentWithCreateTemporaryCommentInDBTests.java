package tests;

import helpers.Helper;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import models.Comment;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import repository.CommentRepository;
import repository.CommentRepositoryImpl;

import static data.TestData.GET_COMMENT;
import static data.TestData.VALID_LOGIN;
import static data.TestData.VALID_PASSWORD;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Epic(value = "Комментарий")
@Feature(value = "Проверка GET запросов комментария через API")
public class GetCommentWithCreateTemporaryCommentInDBTests extends BaseTest {
    Comment comment = Helper.getDefaultComment();
    CommentRepository commentRepository;

    @BeforeMethod
    public void setUpBeforeMethod() {
        commentRepository = new CommentRepositoryImpl();
        commentRepository.save(comment);
        comment.setId(commentRepository.getLastInsertId());
    }

    @AfterMethod
    public void tearDownAfterMethod() {
        commentRepository.delete(comment.getId());
    }

    @Test
    @Story(value = "Получение данных конкретного комментария заранее " +
            "подготовленного в БД авторизованным пользователем (Вызов метода GET)")
    @Owner(value = "Ruslan Bikineev")
    @Severity(CRITICAL)
    public void testGetSpecificCommentWithAuthorizationUserMethodGet() {
        Comment responseComment = RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .when()
                .get(GET_COMMENT + comment.getId())
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath(
                        "schemas/Get&Create&EditCommentSuccessfulResponses.json"))
                .extract().body().jsonPath().getObject("", Comment.class);
        Assert.assertTrue(responseComment.isEqual(comment));
    }

    @Test
    @Story(value = "Получение данных конкретного комментария заранее " +
            "подготовленного в БД не авторизованным пользователем (Вызов метода GET)")
    @Owner(value = "Ruslan Bikineev")
    @Severity(CRITICAL)
    public void testGetSpecificCommentWithoutAuthorizationUserMethodGet() {
        RestAssured.given()
                .get(GET_COMMENT + comment.getId())
                .then()
                .statusCode(401)
                .body(matchesJsonSchemaInClasspath(
                        "schemas/ClientErrorResponses.json"));
    }
}
