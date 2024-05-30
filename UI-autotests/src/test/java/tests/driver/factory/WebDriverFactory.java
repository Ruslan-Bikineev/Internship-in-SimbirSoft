package tests.driver.factory;

import org.openqa.selenium.WebDriver;

public class WebDriverFactory {
    public static WebDriver getDriver(boolean remote, String browser) {
        DriverFactory driverFactory;
        if (remote) {
            driverFactory = new RemoteWebDriverFactory();
        } else {
            driverFactory = new LocalWebDriverFactory();
        }
        return driverFactory.createDriver(browser);
    }
}