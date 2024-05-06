import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.PageProperties;

import static io.qameta.allure.SeverityLevel.BLOCKER;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.qameta.allure.SeverityLevel.MINOR;
import static io.qameta.allure.SeverityLevel.NORMAL;

public class LoginPageTests extends BaseTest {

    @Severity(MINOR)
    @Epic(value = "Страница авторизации пользователя")
    @Feature(value = "Поле ввода логина и пароля")
    @Story(value = "Проверка отображений ошибки под полями ввода логина и пароля")
    @Owner(value = "Ruslan Bikineev")
    @Test
    public void testMessagesUnderFieldLoginAndPasswordWhenFieldEmpty() {
        HomePage homePage = new HomePage(getDriver());
        homePage.openHomePage();
        LoginPage loginPage = homePage.clickMemberLogin();
        loginPage.typeUsername("");
        loginPage.typePassword("");
        loginPage.clickLoginPageTitle();
        Assert.assertEquals(loginPage.getEmailFieldError(),
                TestProperties.nullFiledEmailAndPasswordError, "Ошибка под полем ввода логина не соответствует");
        Assert.assertEquals(loginPage.getPasswordFieldError(),
                TestProperties.nullFiledEmailAndPasswordError, "Ошибка под полем ввода пароля не соответствует");
    }

    @Severity(MINOR)
    @Epic(value = "Страница авторизации пользователя")
    @Feature(value = "Поле ввода логина и пароля")
    @Story(value = "Проверка отображений ошибки под полями ввода логина и пароля")
    @Owner(value = "Ruslan Bikineev")
    @Test
    public void testMessagesUnderFieldLoginAndPasswordWhenInvalidEmailAndPasswordEmpty() {
        HomePage homePage = new HomePage(getDriver());
        homePage.openHomePage();
        LoginPage loginPage = homePage.clickMemberLogin();
        loginPage.typeUsername("test");
        loginPage.typePassword("");
        loginPage.clickLoginPageTitle();
        Assert.assertEquals(loginPage.getEmailFieldError(),
                TestProperties.emailError, "Ошибка под полем ввода логина не соответствует");
        Assert.assertEquals(loginPage.getPasswordFieldError(),
                TestProperties.nullFiledEmailAndPasswordError, "Ошибка под полем ввода пароля не соответствует");
    }

    @Severity(BLOCKER)
    @Epic(value = "Страница авторизации пользователя")
    @Feature(value = "Авторизация")
    @Story(value = "Авторизация с корректным логином и паролем")
    @Owner(value = "Ruslan Bikineev")
    @Test
    public void testValidLoginAndPassword() {
        HomePage homePage = new HomePage(getDriver());
        homePage.openHomePage();
        LoginPage loginPage = homePage.clickMemberLogin();
        loginPage.loginAs(TestProperties.validEmail, TestProperties.validPassword);
        Assert.assertEquals(getCurrentUrl(),
                PageProperties.userHomePageUrl, "Не перешел на страницу авторизации");
    }


    @Severity(CRITICAL)
    @Epic(value = "Страница авторизации пользователя")
    @Feature(value = "Авторизация")
    @Story(value = "Авторизация с несуществующим логином и паролем")
    @Owner(value = "Ruslan Bikineev")
    @Test
    public void testInvalidLoginAndPassword() {
        HomePage homePage = new HomePage(getDriver());
        homePage.openHomePage();
        LoginPage loginPage = homePage.clickMemberLogin();
        loginPage.loginAs(TestProperties.invalidEmail, TestProperties.invalidPassword);
        Assert.assertEquals(loginPage.getInvalidLoginMessage(),
                TestProperties.invalidLoginMessage, "Вывод сообщения об ошибке авторизации не совпадает");
        Assert.assertEquals(loginPage.getInvalidLoginMessageColor(),
                TestProperties.invalidLoginMessageColor, "Цвет сообщения об ошибке авторизации не совпадает");
    }

    @Severity(NORMAL)
    @Epic(value = "Страница авторизации пользователя")
    @Feature(value = "Авторизация")
    @Story(value = "Авторизация с корректным логином и пустым паролем")
    @Owner(value = "Ruslan Bikineev")
    @Test()
    public void testValidLoginAndEmptyPassword() {
        HomePage homePage = new HomePage(getDriver());
        homePage.openHomePage();
        LoginPage loginPage = homePage.clickMemberLogin();
        loginPage.loginAs(TestProperties.validEmail, "");
        Assert.assertEquals(TestProperties.validEmail,
                loginPage.getEmailField(), "Введенный логин не совпадает с логином в поле ввода");
        Assert.assertEquals("",
                loginPage.getPasswordField(), "Введенный пароль не совпадает с паролем в поле ввода");
    }

    @Severity(CRITICAL)
    @Epic(value = "Страница авторизации пользователя")
    @Feature(value = "Авторизация")
    @Story(value = "Авторизация с пустым логином и паролем")
    @Owner(value = "Ruslan Bikineev")
    @Test()
    public void testEmptyLoginAndPassword() {
        HomePage homePage = new HomePage(getDriver());
        homePage.openHomePage();
        LoginPage loginPage = homePage.clickMemberLogin();
        loginPage.submitLogin();
        Assert.assertEquals("", loginPage.getEmailField(),
                "Введенный логин не совпадает с логином в поле ввода");
        Assert.assertEquals("", loginPage.getPasswordField(),
                "Введенный пароль не совпадает с паролем в поле ввода");
    }
}