package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LogInPage;

import java.text.Normalizer;

import static data.TestsData.ACTIVITY_AFTER_SUCCESS_LOGIN;
import static data.TestsData.INCORRECT_PASSWORD_MESSAGE;
import static data.TestsData.INVALID_LOGIN;
import static data.TestsData.INVALID_PASSWORD;
import static data.TestsData.VALID_LOGIN;
import static data.TestsData.VALID_PASSWORD;

public class AuthorizationTests extends BaseTest {
    @Test
    public void logInWithInvalidCredentialsTest() {
        LogInPage homePage = new LogInPage(getDriver())
                .authorization(INVALID_LOGIN, INVALID_PASSWORD);
        Assert.assertEquals(Normalizer.normalize(homePage.getPasswordHint(), Normalizer.Form.NFC),
                Normalizer.normalize(INCORRECT_PASSWORD_MESSAGE, Normalizer.Form.NFC),
                "Сообщение о некорректном пароле не совпадает.");
    }

    @Test
    public void logInWithValidCredentialsTest() {
        LogInPage homePage = new LogInPage(getDriver());
        homePage.authorization(VALID_LOGIN, VALID_PASSWORD);
        Assert.assertEquals(getDriver().currentActivity(), ACTIVITY_AFTER_SUCCESS_LOGIN);
    }
}
