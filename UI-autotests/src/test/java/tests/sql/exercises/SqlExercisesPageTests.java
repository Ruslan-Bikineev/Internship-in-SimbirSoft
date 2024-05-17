package tests.sql.exercises;

import helpers.Json;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import org.openqa.selenium.Cookie;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.sql.exercises.SqlExercisesPage;
import tests.BaseTest;

import java.io.File;
import java.util.List;

import static data.TestsData.PATH_TO_COOKIES_FILE;
import static io.qameta.allure.SeverityLevel.NORMAL;

@Epic(value = "Главная страница")
@Feature(value = "Авторизация пользователем гость")
public class SqlExercisesPageTests extends BaseTest {
    private Json json = new Json();

    @Test
    @Story(value = "Авторизация пользователем гость и сохранение Cookie в файл")
    @Owner(value = "Ruslan Bikineev")
    @Severity(NORMAL)
    public void testAuthorizationWithoutLoginAndSaveCookiesToFile() {
        SqlExercisesPage sqlExercisesPage = new SqlExercisesPage(getDriver());
        sqlExercisesPage.openHomePage()
                .enterWithoutLogin();
        json.serializeJsonFile(PATH_TO_COOKIES_FILE, getDriver().manage().getCookies());
        Assert.assertNotNull(new File(PATH_TO_COOKIES_FILE));
    }

    @Test(dependsOnMethods = "testAuthorizationWithoutLoginAndSaveCookiesToFile")
    @Story(value = "Авторизация c использованием Cookie, авторизованного пользователя гость")
    @Owner(value = "Ruslan Bikineev")
    @Severity(NORMAL)
    public void testAuthorizationWithoutLoginWithTheirCookies() {
        SqlExercisesPage sqlExercisesPage = new SqlExercisesPage(getDriver());
        sqlExercisesPage.openHomePage();
        getDriver().manage().deleteAllCookies();
        List<Cookie> cookieList = json.deserializeCookiesFromJsonFile(PATH_TO_COOKIES_FILE);
        addCookies(cookieList);
        getDriver().navigate().refresh();
        Assert.assertTrue(sqlExercisesPage.getGuestLabel().equals("гость")
                || sqlExercisesPage.getGuestLabel().equals("guest"));
    }
}