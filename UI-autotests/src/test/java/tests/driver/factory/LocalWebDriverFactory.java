package tests.driver.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LocalWebDriverFactory implements DriverFactory {
    @Override
    public WebDriver createDriver() {
        WebDriver driver = new ChromeDriver();
        return driver;
    }
}