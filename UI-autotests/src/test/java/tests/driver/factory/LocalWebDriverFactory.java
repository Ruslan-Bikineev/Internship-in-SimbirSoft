package tests.driver.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class LocalWebDriverFactory implements DriverFactory {
    @Override
    public WebDriver createDriver(String browser) {
        return switch (browser) {
            case "Chrome" -> new ChromeDriver();
            case "Firefox" -> new FirefoxDriver();
            case "Edge" -> new EdgeDriver();
            case "IE" -> new InternetExplorerDriver(internetExplorerOptions());
            default -> throw new IllegalArgumentException("Unexpected value: " + browser);
        };
    }
}