package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import tests.driver.factory.WebDriverFactory;

import java.time.Duration;

public class BaseTest {
    private WebDriver driver;

    @BeforeMethod
    @Parameters(value = {"remote", "browser"})
    public void setUp(boolean remote, String browser) {
        driver = WebDriverFactory.getDriver(remote, browser);
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
}
