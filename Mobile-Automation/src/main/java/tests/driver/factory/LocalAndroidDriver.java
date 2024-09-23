package tests.driver.factory;

import config.ConfigReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public class LocalAndroidDriver implements DriverFactory {
    @Override
    public AppiumDriver createDriver(String platformType, String platformVersion,
                                     String uuid, String avd, String systemPort, String deviceName) {
        AppiumDriver appiumDriver;
        File app = new File(System.getProperty("user.dir"), ConfigReader.emulatorConfig.app());
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", ConfigReader.emulatorConfig.deviceName());
        capabilities.setCapability("platformName", ConfigReader.emulatorConfig.platformName());
        capabilities.setCapability("avd", ConfigReader.emulatorConfig.avd());
        capabilities.setCapability("app", app.getAbsolutePath());
        capabilities.setCapability("automationName", ConfigReader.emulatorConfig.automationName());
        capabilities.setCapability("app-wait-activity", ConfigReader.emulatorConfig.appWaitActivity());
        capabilities.setCapability("chromedriverExecutable",
                System.getProperty("user.dir") + ConfigReader.emulatorConfig.chromedriverExecutable());
        capabilities.setCapability("fullReset", ConfigReader.emulatorConfig.fullReset());
        try {
            appiumDriver = new AndroidDriver(
                    new URI(ConfigReader.emulatorConfig.remoteURL().replace("port", systemPort)).toURL(),
                    capabilities);
        } catch (MalformedURLException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return appiumDriver;
    }
}
