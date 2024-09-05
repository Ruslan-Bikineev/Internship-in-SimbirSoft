package tests;

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
    private AndroidDriver driver;

    @BeforeMethod
    public void setUp() throws Exception {
        File app = new File(System.getProperty("user.dir"), "src\\main\\resources\\apps\\Яндекс Маркет_5.59.35009.a_apkcombo.com.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "Nexus 5");
        capabilities.setCapability("avd", "Nexus_5_API_35");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("app", app.getAbsolutePath());
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("app-wait-activity", "activity-to-wait-for");
        capabilities.setCapability("fullReset", "true");
        driver = new AndroidDriver(new URI("http://127.0.0.1:4723/wd/hub").toURL(), capabilities);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
