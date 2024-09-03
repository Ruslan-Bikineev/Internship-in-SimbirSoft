package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LogInPage;

import java.text.Normalizer;

import static data.TestsData.INCORRECT_PASSWORD_MESSAGE;
import static data.TestsData.INVALID_LOGIN;
import static data.TestsData.INVALID_PASSWORD;

public class AuthorizationTests extends BaseTest {
    @Test
    public void logInWithInvalidCredentialsTest() {
        LogInPage homePage = new LogInPage(getDriver())
                .authorization(INVALID_LOGIN, INVALID_PASSWORD);
        Assert.assertEquals(Normalizer.normalize(homePage.getPasswordHint(), Normalizer.Form.NFC),
                Normalizer.normalize(INCORRECT_PASSWORD_MESSAGE, Normalizer.Form.NFC),
                "Сообщение о некорректном пароле не совпадает.");
    }
}
