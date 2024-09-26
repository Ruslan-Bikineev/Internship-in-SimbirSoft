package tests.driver.factory;

import io.appium.java_client.AppiumDriver;

public interface DriverFactory {
    AppiumDriver createDriver(String platformType, String platformVersion,
                              String uuid, String avd, String systemPort, String deviceName);
}
