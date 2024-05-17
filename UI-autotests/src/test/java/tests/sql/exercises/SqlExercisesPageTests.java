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
public class SqlExercisesPageTests extends BaseTest {
    private Json json = new Json();

    @Severity(NORMAL)
    @Feature(value = "Авторизация")
    @Story(value = "Авторизация пользователем гость (без ввода данных)")
    @Owner(value = "Ruslan Bikineev")
    @Test
    public void testAuthorizationWithoutLoginAndSaveCookiesToFile() {
        SqlExercisesPage sqlExercisesPage = new SqlExercisesPage(getDriver());
        sqlExercisesPage.openHomePage()
                .enterWithoutLogin();
        json.serializeJsonFile(PATH_TO_COOKIES_FILE, getDriver().manage().getCookies());
        Assert.assertNotNull(new File(PATH_TO_COOKIES_FILE));
    }

    @Severity(NORMAL)
    @Feature(value = "Авторизация")
    @Story(value = "Авторизация пользователем гость (без ввода данных)")
    @Owner(value = "Ruslan Bikineev")
    @Test
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