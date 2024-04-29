package Ui;

import PageObjects.HomePage;
import PageObjects.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.time.Duration;

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

    @Test
    public void testNullInputEmail() {
        loginPage.typeUsername("");
        loginPage.clickLoginPageTitle();
        Assert.assertEquals(loginPage.getEmailError(),
                TestProperties.nullFiledEmailAndPasswordError, "Wrong error message in email field");
    }

    @Test(dependsOnMethods = "testNullInputEmail")
    public void testInvalidInputEmail() {
        loginPage.typeUsername("test");
        loginPage.clickLoginPageTitle();
        Assert.assertEquals(loginPage.getEmailError(),
                TestProperties.emailError, "Wrong error message");
    }

    @Test
    public void testNullInputPassword() {
        loginPage.typePassword("");
        loginPage.clickLoginPageTitle();
        Assert.assertEquals(loginPage.getPasswordError(),
                TestProperties.nullFiledEmailAndPasswordError, "Wrong error message");
    }
}
