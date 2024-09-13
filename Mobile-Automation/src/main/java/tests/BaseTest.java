package tests;

import config.ConfigReader;
import io.appium.java_client.android.AndroidDriver;
import lombok.Getter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.net.URI;
import java.util.concurrent.TimeUnit;

@Getter
public abstract class BaseTest {
    private AndroidDriver androidDriver;

    @BeforeMethod
    public void setUp() throws Exception {
        File app = new File(System.getProperty("user.dir"), ConfigReader.emulatorConfig.app());
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", ConfigReader.emulatorConfig.deviceName());
        capabilities.setCapability("avd", ConfigReader.emulatorConfig.avd());
        capabilities.setCapability("platformName", ConfigReader.emulatorConfig.platformName());
        capabilities.setCapability("app", app.getAbsolutePath());
        capabilities.setCapability("automationName", ConfigReader.emulatorConfig.automationName());
        capabilities.setCapability("app-wait-activity", ConfigReader.emulatorConfig.appWaitActivity());
        capabilities.setCapability("chromedriverExecutable", ConfigReader.emulatorConfig.chromedriverExecutable());
        capabilities.setCapability("fullReset", ConfigReader.emulatorConfig.fullReset());
        androidDriver = new AndroidDriver(new URI(ConfigReader.emulatorConfig.remoteURL()).toURL(), capabilities);
        androidDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @AfterMethod
    public void tearDown() {
        androidDriver.quit();
    }
}
