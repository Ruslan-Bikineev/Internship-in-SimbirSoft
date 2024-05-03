package ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.PageProperties;

import java.time.Duration;

public class LoginPageTests {
    WebDriver driver;
    LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        HomePage homePage = new HomePage(driver);
        homePage.openHomePage();
        loginPage = homePage.clickMemberLogin();
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testMessagesUnderFieldLoginAndPasswordWhenFieldEmpty() {
        loginPage.typeUsername("");
        loginPage.typePassword("");
        loginPage.clickLoginPageTitle();
        Assert.assertEquals(loginPage.getEmailFieldError(),
                TestProperties.nullFiledEmailAndPasswordError, "Ошибка под полем ввода логина не соответствует");
        Assert.assertEquals(loginPage.getPasswordFieldError(),
                TestProperties.nullFiledEmailAndPasswordError, "Ошибка под полем ввода пароля не соответствует");
    }

    @Test()
    public void testMessagesUnderFieldLoginAndPasswordWhenInvalidEmailAndPasswordEmpty() {
        loginPage.typeUsername("test");
        loginPage.typePassword("");
        loginPage.clickLoginPageTitle();
        Assert.assertEquals(loginPage.getEmailFieldError(),
                TestProperties.emailError, "Ошибка под полем ввода логина не соответствует");
        Assert.assertEquals(loginPage.getPasswordFieldError(),
                TestProperties.nullFiledEmailAndPasswordError, "Ошибка под полем ввода пароля не соответствует");
    }

    @Test()
    public void testValidLoginAndPassword() {
        loginPage.loginAs(TestProperties.validEmail, TestProperties.validPassword);
        Assert.assertEquals(driver.getCurrentUrl(),
                PageProperties.userHomePageUrl, "Не перешел на страницу авторизации");
    }

    @Test()
    public void testInvalidLoginAndPassword() {
        loginPage.loginAs(TestProperties.invalidEmail, TestProperties.invalidPassword);
        Assert.assertEquals(loginPage.getInvalidLoginMessage(),
                TestProperties.invalidLoginMessage, "Вывод сообщения об ошибке авторизации не совпадает");
        Assert.assertEquals(loginPage.getInvalidLoginMessageColor(),
                TestProperties.invalidLoginMessageColor, "Цвет сообщения об ошибке авторизации не совпадает");
    }

    @Test()
    public void testValidLoginAndEmptyPassword() {
        loginPage.loginAs(TestProperties.validEmail, "");
        Assert.assertEquals(TestProperties.validEmail,
                loginPage.getEmailField(), "Введенный логин не совпадает с логином в поле ввода");
        Assert.assertEquals("",
                loginPage.getPasswordField(), "Введенный пароль не совпадает с паролем в поле ввода");
    }

    @Test()
    public void testEmptyLoginAndPassword() {
        loginPage.submitLogin();
        Assert.assertEquals("", loginPage.getEmailField(),
                "Введенный логин не совпадает с логином в поле ввода");
        Assert.assertEquals("", loginPage.getPasswordField(),
                "Введенный пароль не совпадает с паролем в поле ввода");
    }

}
