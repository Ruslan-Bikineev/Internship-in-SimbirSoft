package tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ChooseFavoriteCategoriesPage;
import pages.LogInPage;

import static data.TestsData.MESSAGE_AFTER_SUCCESS_CHOOSE_FAVORITE_CATEGORIES;
import static data.TestsData.VALID_LOGIN;
import static data.TestsData.VALID_PASSWORD;
import static io.qameta.allure.SeverityLevel.NORMAL;

public class ChooseFavoriteCategoriesTests extends BaseTest {
    @Test
    @Feature(value = "Каталог")
    @Story(value = "Проверка отображения вкладки \"оборудования\" на странице каталога")
    @Owner(value = "Ruslan Bikineev")
    @Severity(NORMAL)
    public void chooseFourChooseFavoriteCategoriesTest() {
        ChooseFavoriteCategoriesPage chooseFavoriteCategoriesPage = new LogInPage(getAndroidDriver())
                .authorization(VALID_LOGIN, VALID_PASSWORD)
                .closeWidget()
                .skipCookieFiles()
                .moveToProfilePage()
                .openChooseFavoriteCategoriesPage()
                .chooseFourRandomCategories()
                .chooseFavoriteCategoriesButton();
        Assert.assertEquals(chooseFavoriteCategoriesPage.getChooseFavoriteCategoriesText(),
                MESSAGE_AFTER_SUCCESS_CHOOSE_FAVORITE_CATEGORIES);
    }
}
