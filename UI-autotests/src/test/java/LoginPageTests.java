import data.TestsData;
import framework.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import org.openqa.selenium.TimeoutException;
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

    @DataProvider(name = "LoginDataOfDifferentTypesProvider")
    public Object[][] loginDataOfDifferentTypes() {
        return new Object[][]{
                {"", ""},
                {TestsData.VALID_EMAIL, ""},
                {"", TestsData.VALID_PASSWORD},
                {TestsData.VALID_EMAIL, TestsData.INVALID_PASSWORD},
                {TestsData.INVALID_EMAIL, TestsData.VALID_PASSWORD}
        };
    }

    @Severity(BLOCKER)
    @Feature(value = "Авторизация")
    @Story(value = "Авторизация с использванием разных данных (в том числе и некорректных)")
    @Owner(value = "Ruslan Bikineev")
    @Test(expectedExceptions = TimeoutException.class, dataProvider = "LoginDataOfDifferentTypesProvider")
    public void testLoginWithInvalidDataOfDifferentTypes(String email, String password) {
        HomePage homePage = new HomePage(getDriver());
        homePage.openHomePage();
        LoginPage loginPage = homePage.clickMemberLogin();
        loginPage.loginAs(email, password);
    }

    @DataProvider(name = "LoginInvalidDataProvider")
    public Object[][] loginInvalidData() {
        return new Object[][]{
                {"", "", TestsData.THIS_FIELD_IS_REQUIRED_MESSAGE},
                {"testexample.com", "", TestsData.PLEASE_PROVIDE_A_VALID_EMAIL_ADDRESS_MESSAGE}
        };
    }

    @Severity(MINOR)
    @Feature(value = "Поле ввода логина и пароля")
    @Story(value = "Проверка отображений ошибки под полями ввода логина и пароля")
    @Owner(value = "Ruslan Bikineev")
    @Test(dataProvider = "LoginInvalidDataProvider")
    public void testMessagesUnderFieldLoginAndPassword(String email, String password, String expectedMessage) {
        HomePage homePage = new HomePage(getDriver());
        homePage.openHomePage();
        LoginPage loginPage = homePage.clickMemberLogin();
        loginPage.typeUsername(email)
                .typePassword(password)
                .clickLoginPageTitle();
        Assert.assertEquals(loginPage.getEmailFieldError(), expectedMessage,
                "Ошибка под полем ввода логина не соответствует");
        Assert.assertEquals(loginPage.getPasswordFieldError(),
                TestsData.THIS_FIELD_IS_REQUIRED_MESSAGE,
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
        loginPage.loginAs(TestsData.VALID_EMAIL, TestsData.VALID_PASSWORD);
        Assert.assertEquals(getCurrentUrl(),
                PageProperties.USER_HOME_PAGE_URL, "Не перешел на страницу авторизации");
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
        loginPage.loginAs(TestsData.INVALID_EMAIL, TestsData.INVALID_PASSWORD);
        Assert.assertEquals(loginPage.getInvalidLoginMessage(),
                TestsData.USER_NOT_FOUND_MESSAGE, "Вывод сообщения об ошибке авторизации не совпадает");
        Assert.assertEquals(loginPage.getInvalidLoginMessageColor(),
                TestsData.USER_NOT_FOUND_MESSAGE_COLOR, "Цвет сообщения об ошибке авторизации не совпадает");
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
        loginPage.loginAs(TestsData.VALID_EMAIL, "");
        Assert.assertEquals(TestsData.VALID_EMAIL,
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
