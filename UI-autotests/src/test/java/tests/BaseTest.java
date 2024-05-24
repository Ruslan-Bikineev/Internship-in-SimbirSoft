package tests;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import tests.driver.factory.WebDriverFactory;

import java.time.Duration;
import java.util.List;

public class BaseTest {
    private WebDriver driver;

    @BeforeMethod
    @Parameters(value = {"remote"})
    public void setUp(boolean remote) {
        driver = WebDriverFactory.getDriver(remote);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }

    @AfterMethod
    public void tearDown() {
        this.driver.quit();
    }

    public WebDriver getDriver() {
        return driver;
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void addCookies(List<Cookie> cookieList) {
        cookieList.forEach(cookie -> getDriver().manage().addCookie(cookie));
    }
}
