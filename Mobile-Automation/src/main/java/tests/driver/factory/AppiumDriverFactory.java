package tests.driver.factory;

import io.appium.java_client.AppiumDriver;

public class AppiumDriverFactory {
    public static AppiumDriver getDriver(String config, String platformType, String platformVersion, String uuid, String avd, String systemPort, String deviceName) {
        DriverFactory driverFactory = null;
        if (config.equals("local")) {
            driverFactory = new LocalAndroidDriver();
        } else if (config.equals("bstack")) {
            driverFactory = new EmptyAndroidDriver();
        } else if (config.equals("parallel")) {
            driverFactory = new ParallelAndroidDriver();
        }
        return driverFactory.createDriver(platformType, platformVersion, uuid, avd, systemPort, deviceName);
    }
}
