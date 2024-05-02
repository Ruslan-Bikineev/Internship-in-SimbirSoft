package framework;

import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class MyWebDriver {
    private static WebDriver driver;

    public MyWebDriver(WebDriver driver) {
        MyWebDriver.driver = driver;
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
    }

    public static WebDriver getMyWebDriver() {
        return driver;
    }

    public void quit() {
        if (driver != null) {
            driver.quit();
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

}
