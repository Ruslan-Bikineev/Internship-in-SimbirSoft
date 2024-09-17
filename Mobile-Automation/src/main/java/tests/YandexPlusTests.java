package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.app.LogInPage;

import static data.TestsData.VALID_LOGIN;
import static data.TestsData.VALID_PASSWORD;
import static data.TestsData.YANDEX_PLUS_CONDITIONS_LINK;
import static io.qameta.allure.SeverityLevel.NORMAL;

@Epic("Управление Яндекс Плюс с приложения Яндекс Маркет")
public class YandexPlusTests extends BaseTest {
    @Test
    @Feature(value = "Яндекс Плюс при авторизации")
    @Story(value = "Проверка работы ссылки на условия подключения")
    @Owner(value = "Ruslan Bikineev")
    @Severity(NORMAL)
    public void openYandexPlusConditionsTest() {
        new LogInPage(getAppiumDriver())
                .authorization(VALID_LOGIN, VALID_PASSWORD)
                .closeWidget()
                .skipCookieFiles()
                .moveToYandexPlusPage()
                .moveToConnectPlus()
                .scrollToConditionsLink()
                .openYandexPlusConditions();
        Assert.assertEquals(getAppiumDriver().getCurrentUrl(), YANDEX_PLUS_CONDITIONS_LINK);
    }
}
