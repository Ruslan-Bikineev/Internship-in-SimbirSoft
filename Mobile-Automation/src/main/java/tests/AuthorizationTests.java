package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.app.HomePage;
import pages.app.LogInPage;

import java.text.Normalizer;

import static data.TestsData.ACTIVITY_AFTER_SUCCESS_LOGIN;
import static data.TestsData.INCORRECT_PASSWORD_MESSAGE;
import static data.TestsData.INVALID_LOGIN;
import static data.TestsData.INVALID_PASSWORD;
import static data.TestsData.VALID_LOGIN;
import static data.TestsData.VALID_PASSWORD;
import static io.qameta.allure.SeverityLevel.BLOCKER;

@Epic(value = "Авторизация в приложении")
public class AuthorizationTests extends BaseTest {
    @Test
    @Feature(value = "Авторизация")
    @Story(value = "Авторизация с не корректным логином и паролем")
    @Owner(value = "Ruslan Bikineev")
    @Severity(BLOCKER)
    public void logInWithInvalidCredentialsTest() {
        LogInPage logInPage = new LogInPage(getAndroidDriver());
        logInPage.authorization(INVALID_LOGIN, INVALID_PASSWORD);
        Assert.assertEquals(Normalizer.normalize(logInPage.getPasswordHint(), Normalizer.Form.NFC),
                Normalizer.normalize(INCORRECT_PASSWORD_MESSAGE, Normalizer.Form.NFC),
                "Сообщение о некорректном пароле не совпадает.");
    }

    @Test
    @Feature(value = "Авторизация")
    @Story(value = "Авторизация с корректным логином и паролем")
    @Owner(value = "Ruslan Bikineev")
    @Severity(BLOCKER)
    public void logInWithValidCredentialsTest() {
        HomePage homePage = new LogInPage(getAndroidDriver())
                .authorization(VALID_LOGIN, VALID_PASSWORD)
                .closeWidget()
                .skipCookieFiles();
        Assert.assertEquals(getAndroidDriver().currentActivity(), ACTIVITY_AFTER_SUCCESS_LOGIN);
    }
}
