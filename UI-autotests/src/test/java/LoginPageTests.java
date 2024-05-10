import data.TestsData;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.PageProperties;

import static io.qameta.allure.SeverityLevel.BLOCKER;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.qameta.allure.SeverityLevel.MINOR;

@Epic(value = "Страница авторизации пользователя")
public class LoginPageTests extends BaseTest {

    @DataProvider(name = "LoginInvalidDataProvider")
    public Object[][] loginInvalidData() {
        return new Object[][]{
                {"", ""},
                {"testexample.com", ""}
        };
    }

    @Severity(MINOR)
    @Feature(value = "Поле ввода логина и пароля")
    @Story(value = "Проверка отображений ошибки под полями ввода логина и пароля")
    @Owner(value = "Ruslan Bikineev")
    @Test(dataProvider = "LoginInvalidDataProvider")
    public void testMessagesUnderFieldLoginAndPassword(String email, String password) {
        HomePage homePage = new HomePage(getDriver());
        homePage.openHomePage();
        LoginPage loginPage = homePage.clickMemberLogin();
        loginPage.typeUsername(email);
        loginPage.typePassword(password);
        loginPage.clickLoginPageTitle();
        if (email.isEmpty()) {
            Assert.assertEquals(loginPage.getEmailFieldError(),
                    TestsData.thisFieldIsRequiredMessage,
                    "Ошибка под полем ввода логина не соответствует");
        } else {
            Assert.assertEquals(loginPage.getEmailFieldError(),
                    TestsData.pleaseProvideAValidEmailAddressMessage,
                    "Ошибка под полем ввода логина не соответствует");
        }
        Assert.assertEquals(loginPage.getPasswordFieldError(),
                TestsData.thisFieldIsRequiredMessage,
                "Ошибка под полем ввода пароля не соответствует");
    }

    @Severity(BLOCKER)
    @Feature(value = "Авторизация")
    @Story(value = "Авторизация с корректным логином и паролем")
    @Owner(value = "Ruslan Bikineev")
    @Test
    public void testValidLoginAndPassword() {
        HomePage homePage = new HomePage(getDriver());
        homePage.openHomePage();
        LoginPage loginPage = homePage.clickMemberLogin();
        loginPage.loginAs(TestsData.validEmail, TestsData.validPassword);
        Assert.assertEquals(getCurrentUrl(),
                PageProperties.userHomePageUrl, "Не перешел на страницу авторизации");
    }

    @Severity(CRITICAL)
    @Feature(value = "Авторизация")
    @Story(value = "Авторизация с несуществующим логином и паролем")
    @Owner(value = "Ruslan Bikineev")
    @Test
    public void testInvalidLoginAndPassword() {
        HomePage homePage = new HomePage(getDriver());
        homePage.openHomePage();
        LoginPage loginPage = homePage.clickMemberLogin();
        loginPage.loginAs(TestsData.invalidEmail, TestsData.invalidPassword);
        Assert.assertEquals(loginPage.getInvalidLoginMessage(),
                TestsData.userNotFoundMessage, "Вывод сообщения об ошибке авторизации не совпадает");
        Assert.assertEquals(loginPage.getInvalidLoginMessageColor(),
                TestsData.userNotFoundMessageMessageColor, "Цвет сообщения об ошибке авторизации не совпадает");
    }

    @Severity(CRITICAL)
    @Feature(value = "Авторизация")
    @Story(value = "Авторизация с корректным логином и пустым паролем")
    @Owner(value = "Ruslan Bikineev")
    @Test
    public void testValidLoginAndEmptyPassword() {
        HomePage homePage = new HomePage(getDriver());
        homePage.openHomePage();
        LoginPage loginPage = homePage.clickMemberLogin();
        loginPage.loginAs(TestsData.validEmail, "");
        Assert.assertEquals(TestsData.validEmail,
                loginPage.getEmailField(), "Введенный логин не совпадает с логином в поле ввода");
        Assert.assertEquals("",
                loginPage.getPasswordField(), "Введенный пароль не совпадает с паролем в поле ввода");
    }

    @Severity(CRITICAL)
    @Feature(value = "Авторизация")
    @Story(value = "Авторизация с пустым логином и паролем")
    @Owner(value = "Ruslan Bikineev")
    @Test
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
