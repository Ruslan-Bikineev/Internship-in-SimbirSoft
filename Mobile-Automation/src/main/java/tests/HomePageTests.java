package tests;

import helpers.DriverFunctional;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LogInPage;

import static data.TestsData.VALID_LOGIN;
import static data.TestsData.VALID_PASSWORD;
import static helpers.SwipeDirection.SWIPE_LEFT;
import static io.qameta.allure.SeverityLevel.NORMAL;

@Epic(value = "Главная страница приложения")
public class HomePageTests extends BaseTest {
    @Test
    @Feature(value = "Меню на главной странице приложения")
    @Story(value = "Проверка, что меню приложения работает по свайпу")
    @Owner(value = "Ruslan Bikineev")
    @Severity(NORMAL)
    public void homePageTest() {
        HomePage homePage = new LogInPage(getAndroidDriver())
                .authorization(VALID_LOGIN, VALID_PASSWORD)
                .closeWidget()
                .skipCookieFiles();
        DriverFunctional.swipe(getAndroidDriver(), SWIPE_LEFT, homePage.getMenu());
    }
}
