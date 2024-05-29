package tests.driver.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class RemoteWebDriverFactory implements DriverFactory {
    @Override
    public WebDriver createDriver(String browser) {
        try {
            URL gridHubUrl = new URL("http://localhost:4444/wd/hub");
            return switch (browser) {
                case "Chrome" -> new RemoteWebDriver(gridHubUrl, new ChromeOptions());
                case "Firefox" -> new RemoteWebDriver(gridHubUrl, new FirefoxOptions());
                case "Edge" -> new RemoteWebDriver(gridHubUrl, new EdgeOptions());
                case "IE" -> new RemoteWebDriver(gridHubUrl, internetExplorerOptions());
                default -> throw new IllegalStateException("Unexpected value: " + browser);
            };
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}