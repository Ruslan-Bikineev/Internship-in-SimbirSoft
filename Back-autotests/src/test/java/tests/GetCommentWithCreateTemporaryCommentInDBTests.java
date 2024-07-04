package tests;

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
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class GetCommentWithCreateTemporaryCommentInDBTests extends BaseTest {
    Comment comment = Comment.getDefaultComment();
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
    public void testGetSpecificCommentWithAuthorizationCommentMethodGet() {
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
    public void testGetSpecificCommentWithoutAuthorizationUserMethodGet() {
        RestAssured.given()
                .get(GET_COMMENT + comment.getId())
                .then()
                .statusCode(401)
                .body(matchesJsonSchemaInClasspath(
                        "schemas/ClientErrorResponses.json"));
    }
}
