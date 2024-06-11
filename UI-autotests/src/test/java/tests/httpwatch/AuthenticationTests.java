package tests.httpwatch;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.httpwatch.AuthenticationPage;
import tests.BaseTest;

import static data.TestsData.HTTPWATCH_AFTER_BASIC_AUTHENTICATION_IMAGE_URL;
import static data.TestsData.HTTPWATCH_AUTHENTICATION_LOGIN;
import static data.TestsData.HTTPWATCH_AUTHENTICATION_PASSWORD;
import static io.qameta.allure.SeverityLevel.BLOCKER;

@Epic(value = "Страница 10. HTTP Authentication")
public class AuthenticationTests extends BaseTest {
    @Test
    @Feature(value = "Authentication")
    @Story(value = "Базовая аутентификация с проверкой успешной австоризации")
    @Owner(value = "Ruslan Bikineev")
    @Severity(BLOCKER)
    public void testBasicAuthentication() {
        AuthenticationPage authenticationPage = new AuthenticationPage(getDriver());
        authenticationPage.openAuthenticationPage()
                .setLoginAndPasswordForBasicAuth(HTTPWATCH_AUTHENTICATION_LOGIN, HTTPWATCH_AUTHENTICATION_PASSWORD)
                .clickDisplayImageButton();
        Assert.assertTrue(authenticationPage.getDownloadImageAfterBasicAuth()
                .contains(HTTPWATCH_AFTER_BASIC_AUTHENTICATION_IMAGE_URL));
    }
}
