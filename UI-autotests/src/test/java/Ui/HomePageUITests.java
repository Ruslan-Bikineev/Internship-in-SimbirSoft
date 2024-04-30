package Ui;

import PageObjects.HomePage;
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

public class HomePageUITests {
    WebDriver driver;
    HomePage homePage;

    @BeforeSuite
    public void setUp() {
        driver = new ChromeDriver();
        homePage = new HomePage(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        homePage.openHomePage();
    }

    @AfterSuite
    public void tearDown() {
        driver.quit();
    }

    @Severity(MINOR)
    @Epic(value = "Домашняя страница UI тест")
    @Feature(value = "Данные верхнего дэшборда")
    @Story(value = "Проверка размера верхнего логотипа")
    @Owner(value = "Ruslan Bikineev")
    @Test
    public void testHomePageImgSize() {
        Assert.assertEquals(homePage.getSiteLogoImgWidth(),
                TestProperties.logoWight, "Logo width is not equal");
        Assert.assertEquals(homePage.getSiteLogoImgHeight(),
                TestProperties.logoHeight, "Logo height is not equal");
    }

    @Severity(MINOR)
    @Epic(value = "Домашняя страница UI тест")
    @Feature(value = "Данные верхнего дэшборда")
    @Story(value = "Проверка отображения email'a службы поддержки сайта")
    @Owner(value = "Ruslan Bikineev")
    @Test
    public void testHomePageSupportEmailLink() {
        Assert.assertEquals(homePage.getSiteSupportEmailName(),
                TestProperties.supportEmailName, "Email is not equal");
    }

    @Severity(MINOR)
    @Epic(value = "Домашняя страница UI тест")
    @Feature(value = "Данные верхнего дэшборда")
    @Story(value = "Проверка отображения имени Skype службы поддержки сайта")
    @Owner(value = "Ruslan Bikineev")
    @Test
    public void testHomePageSupportSkypeName() {
        Assert.assertEquals(homePage.getSiteSupportSkypeName(),
                TestProperties.supportSkypeName, "Skype is not equal");
    }


    @Severity(MINOR)
    @Epic(value = "Домашняя страница UI тест")
    @Feature(value = "Данные верхнего дэшборда")
    @Story(value = "Проверка отображения номера USA службы поддержки сайта")
    @Owner(value = "Ruslan Bikineev")
    @Test
    public void testHomePageSupportUSANumber() {
        Assert.assertEquals(homePage.getSiteSupportUSANumber(),
                TestProperties.supportUSANumber, "USA number is not equal");
    }


    @Severity(MINOR)
    @Epic(value = "Домашняя страница UI тест")
    @Feature(value = "Данные верхнего дэшборда")
    @Story(value = "Проверка отображения номера India 1 службы поддержки сайта")
    @Owner(value = "Ruslan Bikineev")
    @Test
    public void testHomePageSupportIndiaNumber1() {
        Assert.assertEquals(homePage.getSiteSupportIndiaNumber1(),
                TestProperties.supportIndiaNumber1, "India number 1 is not equal");
    }


    @Severity(MINOR)
    @Epic(value = "Домашняя страница UI тест")
    @Feature(value = "Данные верхнего дэшборда")
    @Story(value = "Проверка отображения номера India 2 службы поддержки сайта")
    @Owner(value = "Ruslan Bikineev")
    @Test
    public void testHomePageSupportIndiaNumber2() {
        Assert.assertEquals(homePage.getSiteSupportIndiaNumber2(),
                TestProperties.supportIndiaNumber2, "India number 2 is not equal");
    }
}
