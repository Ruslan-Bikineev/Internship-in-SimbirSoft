package ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageobject.HomePage;

import java.time.Duration;

public class HomePageTests {
    WebDriver driver;
    HomePage homePage;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        homePage = new HomePage(driver);
        homePage.openHomePage();
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testHomePageImgSize() {
        Assert.assertEquals(homePage.getSiteLogoImgWidth(),
                TestProperties.logoWight, "Ширина логотипа не совпадает");
        Assert.assertEquals(homePage.getSiteLogoImgHeight(),
                TestProperties.logoHeight, "Высота логотипа не совпадает");
    }

    @Test
    public void testHomePageSupportEmailLink() {
        Assert.assertEquals(homePage.getSiteSupportEmailName(),
                TestProperties.supportEmailName, "Email службы поддержки сайта не совпадает");
    }

    @Test
    public void testHomePageSupportSkypeName() {
        Assert.assertEquals(homePage.getSiteSupportSkypeName(),
                TestProperties.supportSkypeName, "Skype службы поддержки сайта не совпадает");
    }


    @Test
    public void testHomePageSupportUSANumber() {
        Assert.assertEquals(homePage.getSiteSupportUSANumber(),
                TestProperties.supportUSANumber, "Номер USA службы поддержки сайта не совпадает");
    }


    @Test
    public void testHomePageSupportIndiaNumber1() {
        Assert.assertEquals(homePage.getSiteSupportIndiaNumber1(),
                TestProperties.supportIndiaNumber1, "Номер India 1 службы поддержки сайта не совпадает");
    }


    @Test
    public void testHomePageSupportIndiaNumber2() {
        Assert.assertEquals(homePage.getSiteSupportIndiaNumber2(),
                TestProperties.supportIndiaNumber2, "Номер India 2 службы поддержки сайта не совпадает");
    }
}
