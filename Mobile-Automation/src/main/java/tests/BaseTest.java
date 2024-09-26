package tests;

import io.appium.java_client.AppiumDriver;
import lombok.Getter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import tests.driver.factory.AppiumDriverFactory;

import java.util.concurrent.TimeUnit;

@Getter
public abstract class BaseTest {
    private AppiumDriver appiumDriver;

    @BeforeMethod
    @Parameters(value = {"config", "platformType", "platformVersion", "uuid", "avd", "systemPort", "deviceName"})
    public void setUp(String config, String platformType, String platformVersion,
                      String uuid, String avd, String systemPort, String deviceName) {
        appiumDriver = AppiumDriverFactory.getDriver(config, platformType, platformVersion, uuid, avd, systemPort, deviceName);
        appiumDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }

    @AfterMethod
    public void tearDown() {
        appiumDriver.quit();
    }
}
