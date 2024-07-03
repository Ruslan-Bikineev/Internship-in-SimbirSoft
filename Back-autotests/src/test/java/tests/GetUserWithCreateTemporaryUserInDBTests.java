package tests;

import io.restassured.RestAssured;
import models.User;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import repository.UserRepository;
import repository.UserRepositoryImpl;

import static data.TestData.GET_USER;
import static data.TestData.VALID_LOGIN;
import static data.TestData.VALID_PASSWORD;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class GetUserWithCreateTemporaryUserInDBTests extends BaseTest {
    User user = User.getDefaultUser();
    UserRepository userRepository;

    @BeforeMethod
    public void setUpBeforeMethod() {
        userRepository = new UserRepositoryImpl();
        userRepository.save(user);
        user.setId(userRepository.getLastInsertId());
    }

    @AfterMethod
    public void tearDownAfterMethod() {
        userRepository.delete(user.getId());
    }

    @Test
    public void testGetSpecificUserWithAuthorizationUserMethodGet() {
        User responseUser = RestAssured.given()
                .auth()
                .preemptive()
                .basic(VALID_LOGIN, VALID_PASSWORD)
                .when()
                .get(GET_USER + user.getId())
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath(
                        "schemas/Get&Create&EditUserSuccessfulResponses.json"))
                .extract().body().jsonPath().getObject("", User.class);
        Assert.assertTrue(responseUser.isEqual(user));
    }

    @Test
    public void testGetSpecificUserWithoutAuthorizationUserMethodGet() {
        RestAssured.given()
                .get(GET_USER + user.getId())
                .then()
                .statusCode(401)
                .body(matchesJsonSchemaInClasspath(
                        "schemas/ClientErrorResponses.json"));
    }
}
