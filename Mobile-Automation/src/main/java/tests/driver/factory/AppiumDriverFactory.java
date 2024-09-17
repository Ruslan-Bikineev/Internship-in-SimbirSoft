package tests.driver.factory;

import io.appium.java_client.AppiumDriver;

public class AppiumDriverFactory {
    public static AppiumDriver getDriver(String config) {
        DriverFactory driverFactory = null;
        if (config.equals("local")) {
            driverFactory = new LocalAndroidDriver();
        } else if (config.equals("bstack")) {
            driverFactory = new EmptyAndroidDriver();
        }
        return driverFactory.createDriver();
    }
}
