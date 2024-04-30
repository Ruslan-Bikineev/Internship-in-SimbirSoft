package Ui;

import PageObjects.HomePage;
import PageObjects.LoginPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.time.Duration;

import static io.qameta.allure.SeverityLevel.MINOR;

public class LoginPageUITests {
    WebDriver driver;
    LoginPage loginPage;

    @BeforeSuite
    public void setUp() {
        driver = new ChromeDriver();
        HomePage homePage = new HomePage(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        homePage.openHomePage();
        loginPage = homePage.clickMemberLogin();
    }

    @AfterSuite
    public void tearDown() {
        driver.quit();
    }


    @Severity(MINOR)
    @Epic(value = "Страница авторизации пользователя")
    @Feature(value = "Поле ввода логина")
    @Story(value = "Проверка отображения ошибки под полем ввода логина, при вводе пустого значения")
    @Owner(value = "Ruslan Bikineev")
    @Test
    public void testNullInputEmail() {
        loginPage.typeUsername("");
        loginPage.clickLoginPageTitle();
        Assert.assertEquals(loginPage.getEmailError(),
                TestProperties.nullFiledEmailAndPasswordError, "Wrong error message in email field");
    }

    @Severity(MINOR)
    @Epic(value = "Страница авторизации пользователя")
    @Feature(value = "Поле ввода логина")
    @Story(value = "Проверка отображения предупреждния под полем ввода логина, при вводе логина без знака @")
    @Owner(value = "Ruslan Bikineev")
    @Test(dependsOnMethods = "testNullInputEmail")
    public void testInvalidInputEmail() {
        loginPage.typeUsername("test");
        loginPage.clickLoginPageTitle();
        Assert.assertEquals(loginPage.getEmailError(),
                TestProperties.emailError, "Wrong error message");
    }


    @Severity(MINOR)
    @Epic(value = "Страница авторизации пользователя")
    @Feature(value = "Поле ввода пароля")
    @Story(value = "Проверка отображения ошибки под полем ввода пароля, при вводе пустого значения")
    @Owner(value = "Ruslan Bikineev")
    @Test
    public void testNullInputPassword() {
        loginPage.typePassword("");
        loginPage.clickLoginPageTitle();
        Assert.assertEquals(loginPage.getPasswordError(),
                TestProperties.nullFiledEmailAndPasswordError, "Wrong error message");
    }
}
