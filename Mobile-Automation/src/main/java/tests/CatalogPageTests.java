package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import pages.app.LogInPage;

import static data.TestsData.VALID_LOGIN;
import static data.TestsData.VALID_PASSWORD;
import static io.qameta.allure.SeverityLevel.NORMAL;

@Epic(value = "Каталог приложения")
public class CatalogPageTests extends BaseTest {
    @Test
    @Feature(value = "Каталог")
    @Story(value = "Проверка отображения вкладки \"оборудования\" на странице каталога")
    @Owner(value = "Ruslan Bikineev")
    @Severity(NORMAL)
    public void searchEquipmentTest() {
        new LogInPage(getAndroidDriver())
                .authorization(VALID_LOGIN, VALID_PASSWORD)
                .closeWidget()
                .skipCookieFiles()
                .moveToCatalogPage()
                .scrollToEquipment();
    }
}
