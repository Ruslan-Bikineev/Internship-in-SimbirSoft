package framework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public abstract class BaseTest {
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    @BeforeMethod
    public void setUp() {
        if (DRIVER.get() == null) {
            this.DRIVER.set(new ChromeDriver());
            this.DRIVER.get().manage().window().maximize();
            this.DRIVER.get().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
            this.DRIVER.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        }
    }

    @AfterMethod
    public void tearDown() {
        if (this.DRIVER.get() != null) {
            this.DRIVER.get().quit();
            this.DRIVER.remove();
        }
    }

    public String getCurrentUrl() {
        return DRIVER.get().getCurrentUrl();
    }
}
