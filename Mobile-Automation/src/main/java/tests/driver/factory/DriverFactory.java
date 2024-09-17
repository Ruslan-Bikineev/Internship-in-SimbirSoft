package tests.driver.factory;

import io.appium.java_client.AppiumDriver;

public interface DriverFactory {
    AppiumDriver createDriver();
}
