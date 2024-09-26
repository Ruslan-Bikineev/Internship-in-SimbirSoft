package tests.driver.factory;

import config.ConfigReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class ParallelAndroidDriver implements DriverFactory {
    @Override
    public AppiumDriver createDriver(String platformType, String platformVersion,
                                     String uuid, String avd, String systemPort, String deviceName) {
        ThreadLocal<AppiumDriver> appiumDriverThreadLocal = new ThreadLocal<>();
        File app = new File(System.getProperty("user.dir"), "..//../apps/YandexMarket.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("avd", avd);
        capabilities.setCapability("uuid", uuid);
        capabilities.setCapability("deviceName", deviceName);
        capabilities.setCapability("platformName", platformType);
        capabilities.setCapability("platformVersion", platformVersion);
        capabilities.setCapability("fullReset", ConfigReader.emulatorConfig.fullReset());
        capabilities.setCapability("automationName", ConfigReader.emulatorConfig.automationName());
        capabilities.setCapability("app-wait-activity", ConfigReader.emulatorConfig.appWaitActivity());
        try {
            capabilities.setCapability("app", app.getCanonicalPath());
            appiumDriverThreadLocal.set(new AndroidDriver(
                    new URI(ConfigReader.emulatorConfig.remoteURL().replace("port", systemPort)).toURL(),
                    capabilities));
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
        return appiumDriverThreadLocal.get();
    }
}
