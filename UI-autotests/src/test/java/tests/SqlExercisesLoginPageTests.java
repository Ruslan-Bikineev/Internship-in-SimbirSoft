package tests;

import helpers.Json;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import org.openqa.selenium.Cookie;
import org.testng.Assert;
import org.testng.annotations.Test;
import sql_exercises_pages.HomePage;

import java.io.File;
import java.util.List;

import static data.TestsData.PATH_TO_COOKIES_FILE;
import static io.qameta.allure.SeverityLevel.NORMAL;

public class SqlExercisesLoginPageTests extends BaseTest {
    private Json json = new Json();

    @Severity(NORMAL)
    @Feature(value = "Авторизация")
    @Story(value = "Авторизация пользователем гость (без ввода данных)")
    @Owner(value = "Ruslan Bikineev")
    @Test
    public void testAuthorizationWithoutLoginAndSaveCookiesToFile() {
        HomePage homePage = new HomePage(getDriver());
        homePage.openHomePage()
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
        HomePage homePage = new HomePage(getDriver());
        homePage.openHomePage();
        getDriver().manage().deleteAllCookies();
        List<Cookie> cookieList = json.deserializeCookiesFromJsonFile(PATH_TO_COOKIES_FILE);
        addCookies(cookieList);
        getDriver().navigate().refresh();
        Assert.assertTrue(homePage.getGuestLabel().equals("гость")
                || homePage.getGuestLabel().equals("guest"));
    }
}