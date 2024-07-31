package tests.driver.factory;

import org.openqa.selenium.WebDriver;

public class WebDriverFactory {
    public static WebDriver getDriver(String remote, String browser) {
        DriverFactory driverFactory;
        if (remote.equals("SeleniumGRID")) {
            driverFactory = new RemoteSeleniumGridWebDriverFactory();
        } else if (remote.equals("Selenoid")) {
            driverFactory = new RemoteSelenoidWebDriverFactory();
        } else {
            driverFactory = new LocalWebDriverFactory();
        }
        return driverFactory.createDriver(browser);
    }
}