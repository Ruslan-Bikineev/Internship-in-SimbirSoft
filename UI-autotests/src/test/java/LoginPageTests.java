import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.PageProperties;

public class LoginPageTests extends BaseTest {

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

    @Test
    public void testValidLoginAndPassword() {
        HomePage homePage = new HomePage(getDriver());
        homePage.openHomePage();
        LoginPage loginPage = homePage.clickMemberLogin();
        loginPage.loginAs(TestProperties.validEmail, TestProperties.validPassword);
        Assert.assertEquals(getCurrentUrl(),
                PageProperties.userHomePageUrl, "Не перешел на страницу авторизации");
    }

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

    @Test
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
