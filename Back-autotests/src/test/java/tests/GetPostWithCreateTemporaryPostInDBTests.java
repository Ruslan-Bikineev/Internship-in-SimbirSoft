package tests;

import io.restassured.RestAssured;
import models.Post;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import repository.PostsRepository;
import repository.PostsRepositoryImpl;

import static data.TestData.GET_POST;
import static data.TestData.VALID_LOGIN;
import static data.TestData.VALID_PASSWORD;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class GetPostWithCreateTemporaryPostInDBTests extends BaseTest {
    Post post = Post.getDefaultPost();
    private PostsRepository postsRepository;

    @BeforeMethod
    public void setUpBeforeTest() {
        postsRepository = new PostsRepositoryImpl();
        postsRepository.save(post);
        post.setId(postsRepository.getLastInsertId());
    }

    @AfterMethod
    public void tearDownAfterTest() {
        postsRepository.delete(post.getId());
    }

    @Test
    public void testGetSpecificPostWithAuthorizationUserMethodGet() {
        Post responsePost = RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .when()
                .get(GET_POST + post.getId())
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath(
                        "schemas/Get&Create&EditPostSuccessfulResponses.json"))
                .extract().body().jsonPath().getObject("", Post.class);
        Assert.assertTrue(responsePost.isEqual(post));
    }

    @Test
    public void testGetSpecificPostWithoutAuthorizationUserMethodGet() {
        Post responsePost = RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .when()
                .get(GET_POST + post.getId())
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath(
                        "schemas/Get&Create&EditPostSuccessfulResponses.json"))
                .extract().jsonPath().getObject("", Post.class);
        Assert.assertTrue(responsePost.isEqual(post));
    }
}
