package helpers;

import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import tests.BaseTest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DriverFunctional {

    /**
     * Take and attach screenshot to Allure
     *
     * @param baseTest instance of BaseTest
     */
    public static void takeAndAttachToAllureScreenshot(BaseTest baseTest) {
        File file = baseTest.getDriver().getScreenshotAs(OutputType.FILE);
        try {
            InputStream inputStream = Files.newInputStream(Paths.get(file.getAbsolutePath()));
            Allure.attachment("Screenshot", inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close keyboard if keyboard is shown
     *
     * @param androidDriver instance of BaseTest
     */
    public static void closeKeyBoard(AndroidDriver androidDriver) {
        if (androidDriver.isKeyboardShown()) {
            androidDriver.hideKeyboard();
        }
    }
}
