package ui;

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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageobject.HomePage;
import pageobject.LoginPage;

import java.time.Duration;

import static io.qameta.allure.SeverityLevel.MINOR;
import static ui.TestProperties.emailError;
import static ui.TestProperties.nullFiledEmailAndPasswordError;

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


    @DataProvider()
    public Object[][] loginInvalidData() {
        return new Object[][]{
                {"", "", nullFiledEmailAndPasswordError},
                {"testexample.com", "", emailError}
        };
    }

    @Severity(MINOR)
    @Epic(value = "Страница авторизации пользователя")
    @Feature(value = "Поле ввода логина и пароля")
    @Story(value = "Проверка отображений ошибки под полями ввода логина и пароля")
    @Owner(value = "Ruslan Bikineev")
    @Test(dataProvider = "loginInvalidData")
    public void testInvalidLoginData(String email, String password, String expectedError) {
        loginPage.typeUsername(email);
        loginPage.typePassword(password);
        loginPage.clickLoginPageTitle();
        Assert.assertEquals(loginPage.getEmailFieldError(),
                expectedError, "Wrong error message in email field");
        Assert.assertEquals(loginPage.getPasswordFieldError(),
                nullFiledEmailAndPasswordError, "Wrong error message in password field");
    }

}
