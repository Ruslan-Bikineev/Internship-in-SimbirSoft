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
import org.testng.annotations.Test;
import pageobject.HomePage;

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
                TestProperties.logoWight, "Ширина логотипа не совпадает");
        Assert.assertEquals(homePage.getSiteLogoImgHeight(),
                TestProperties.logoHeight, "Высота логотипа не совпадает");
    }

    @Severity(MINOR)
    @Epic(value = "Домашняя страница UI тест")
    @Feature(value = "Данные верхнего дэшборда")
    @Story(value = "Проверка отображения email'a службы поддержки сайта")
    @Owner(value = "Ruslan Bikineev")
    @Test
    public void testHomePageSupportEmailLink() {
        Assert.assertEquals(homePage.getSiteSupportEmailName(),
                TestProperties.supportEmailName, "Email службы поддержки сайта не совпадает");
    }

    @Severity(MINOR)
    @Epic(value = "Домашняя страница UI тест")
    @Feature(value = "Данные верхнего дэшборда")
    @Story(value = "Проверка отображения имени Skype службы поддержки сайта")
    @Owner(value = "Ruslan Bikineev")
    @Test
    public void testHomePageSupportSkypeName() {
        Assert.assertEquals(homePage.getSiteSupportSkypeName(),
                TestProperties.supportSkypeName, "Skype службы поддержки сайта не совпадает");
    }


    @Severity(MINOR)
    @Epic(value = "Домашняя страница UI тест")
    @Feature(value = "Данные верхнего дэшборда")
    @Story(value = "Проверка отображения номера USA службы поддержки сайта")
    @Owner(value = "Ruslan Bikineev")
    @Test
    public void testHomePageSupportUSANumber() {
        Assert.assertEquals(homePage.getSiteSupportUSANumber(),
                TestProperties.supportUSANumber, "Номер USA службы поддержки сайта не совпадает");
    }


    @Severity(MINOR)
    @Epic(value = "Домашняя страница UI тест")
    @Feature(value = "Данные верхнего дэшборда")
    @Story(value = "Проверка отображения номера India 1 службы поддержки сайта")
    @Owner(value = "Ruslan Bikineev")
    @Test
    public void testHomePageSupportIndiaNumber1() {
        Assert.assertEquals(homePage.getSiteSupportIndiaNumber1(),
                TestProperties.supportIndiaNumber1, "Номер India 1 службы поддержки сайта не совпадает");
    }


    @Severity(MINOR)
    @Epic(value = "Домашняя страница UI тест")
    @Feature(value = "Данные верхнего дэшборда")
    @Story(value = "Проверка отображения номера India 2 службы поддержки сайта")
    @Owner(value = "Ruslan Bikineev")
    @Test
    public void testHomePageSupportIndiaNumber2() {
        Assert.assertEquals(homePage.getSiteSupportIndiaNumber2(),
                TestProperties.supportIndiaNumber2, "Номер India 2 службы поддержки сайта не совпадает");
    }
}
