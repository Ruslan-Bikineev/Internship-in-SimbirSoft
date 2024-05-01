package ui;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageobject.HomePage;
import pageobject.LoginPage;
import pageobject.PageProperties;

import java.time.Duration;

import static io.qameta.allure.SeverityLevel.BLOCKER;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.qameta.allure.SeverityLevel.MINOR;
import static io.qameta.allure.SeverityLevel.NORMAL;

public class LoginPageUITests {
    WebDriver driver;
    LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        HomePage homePage = new HomePage(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        homePage.openHomePage();
        loginPage = homePage.clickMemberLogin();
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }


    @DataProvider()
    public Object[][] loginInvalidData() {
        return new Object[][]{
                {"", "", TestProperties.nullFiledEmailAndPasswordError},
                {"testexample.com", "", TestProperties.emailError}
        };
    }

    @Severity(MINOR)
    @Epic(value = "Страница авторизации пользователя")
    @Feature(value = "Поле ввода логина и пароля")
    @Story(value = "Проверка отображений ошибки под полями ввода логина и пароля")
    @Owner(value = "Ruslan Bikineev")
    @Test(dataProvider = "loginInvalidData")
    public void testInvalidLoginAndPasswordMessage(String email, String password, String expectedError) {
        loginPage.typeUsername(email);
        loginPage.typePassword(password);
        loginPage.clickLoginPageTitle();
        Assert.assertEquals(loginPage.getEmailFieldError(),
                expectedError, "Ошибка под полем ввода логина не совпадает");
        Assert.assertEquals(loginPage.getPasswordFieldError(),
                TestProperties.nullFiledEmailAndPasswordError, "Ошибка под полем ввода пароля не совпадает");
    }

    @Severity(BLOCKER)
    @Epic(value = "Страница авторизации пользователя")
    @Feature(value = "Авторизация")
    @Story(value = "Авторизация с корректным логином и паролем")
    @Owner(value = "Ruslan Bikineev")
    @Test()
    public void testValidLoginAndPassword() {
        loginPage.loginAs(TestProperties.validEmail, TestProperties.validPassword);
        Assert.assertEquals(driver.getCurrentUrl(),
                PageProperties.loginPageUrl, "Не перешел на страницу авторизации");
    }

    @Severity(CRITICAL)
    @Epic(value = "Страница авторизации пользователя")
    @Feature(value = "Авторизация")
    @Story(value = "Авторизация с несуществующим логином и паролем")
    @Owner(value = "Ruslan Bikineev")
    @Test()
    public void testInvalidLoginAndPassword() {
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
        loginPage.submitLogin();
        Assert.assertEquals("", loginPage.getEmailField(),
                "Введенный логин не совпадает с логином в поле ввода");
        Assert.assertEquals("", loginPage.getPasswordField(),
                "Введенный пароль не совпадает с паролем в поле ввода");
    }
}
