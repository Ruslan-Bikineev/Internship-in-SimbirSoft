package tests.driver.factory;

import config.ConfigReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public class EmptyAndroidDriver implements DriverFactory {
    @Override
    public AppiumDriver createDriver(String platformType, String platformVersion,
                                     String uuid, String avd, String systemPort, String deviceName) {
        AppiumDriver appiumDriver;
        DesiredCapabilities capabilities = new DesiredCapabilities();
        try {
            appiumDriver = new AndroidDriver(new URI(ConfigReader.emulatorConfig.remoteURL()).toURL(), capabilities);
        } catch (MalformedURLException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return appiumDriver;
    }
}
