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
    @Parameters(value = {"config"})
    public void setUp(String config) {
        appiumDriver = AppiumDriverFactory.getDriver(config);
        appiumDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @AfterMethod
    public void tearDown() {
        appiumDriver.quit();
    }
}
