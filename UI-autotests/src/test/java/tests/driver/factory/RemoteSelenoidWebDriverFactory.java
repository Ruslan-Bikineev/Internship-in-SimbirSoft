package tests.driver.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class RemoteSelenoidWebDriverFactory implements DriverFactory {
    @Override
    public WebDriver createDriver(String browser) {
        WebDriver driver;
        try {
            URL gridHubUrl = new URL("http://selenoid:4444/wd/hub");
            driver = switch (browser) {
                case "Chrome" -> new RemoteWebDriver(gridHubUrl, getChromeOptions());
                case "Firefox" -> new RemoteWebDriver(gridHubUrl, getFirefoxOptions());
                default -> throw new IllegalArgumentException("Unexpected value: " + browser);
            };
        } catch (MalformedURLException | IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
        return driver;
    }

    private ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.setCapability("browserVersion", "126.0");
        options.setCapability("selenoid:options", new HashMap<String, Object>() {{
            put("name", "Test badge...");
            put("sessionTimeout", "15m");
            put("env", new ArrayList<String>() {{
                add("TZ=UTC");
            }});
            put("labels", new HashMap<String, Object>() {{
                put("manual", "true");
            }});
            put("enableVideo", false);
        }});
        return options;
    }

    private FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.setCapability("browserVersion", "125.0");
        options.setCapability("selenoid:options", new HashMap<String, Object>() {{
            put("name", "Test badge...");
            put("sessionTimeout", "15m");
            put("env", new ArrayList<String>() {{
                add("TZ=UTC");
            }});
            put("labels", new HashMap<String, Object>() {{
                put("manual", "true");
            }});
            put("enableVideo", false);
        }});
        return options;
    }
}
