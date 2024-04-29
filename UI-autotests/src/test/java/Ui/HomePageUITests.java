package Ui;

import PageObjects.HomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.time.Duration;

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

    @Test
    public void testHomePageImgSize() {
        Assert.assertEquals(homePage.getSiteLogoImgWidth(),
                TestProperties.logoWight, "Logo width is not equal");
        Assert.assertEquals(homePage.getSiteLogoImgHeight(),
                TestProperties.logoHeight, "Logo height is not equal");
    }

    @Test
    public void testHomePageSupportEmailLink() {
        Assert.assertEquals(homePage.getSiteSupportEmailName(),
                TestProperties.supportEmailName, "Email is not equal");
    }

    @Test
    public void testHomePageSupportSkypeName() {
        Assert.assertEquals(homePage.getSiteSupportSkypeName(),
                TestProperties.supportSkypeName, "Skype is not equal");
    }

    @Test
    public void testHomePageSupportUSANumber() {
        Assert.assertEquals(homePage.getSiteSupportUSANumber(),
                TestProperties.supportUSANumber, "USA number is not equal");
    }

    @Test
    public void testHomePageSupportIndiaNumber() {
        Assert.assertEquals(homePage.getSiteSupportIndiaNumber1(),
                TestProperties.supportIndiaNumber1, "India number 1 is not equal");
    }

    @Test
    public void testHomePageSupportIndiaNumber2() {
        Assert.assertEquals(homePage.getSiteSupportIndiaNumber2(),
                TestProperties.supportIndiaNumber2, "India number 2 is not equal");
    }
}
