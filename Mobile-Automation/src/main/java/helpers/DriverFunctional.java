package helpers;

import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import tests.BaseTest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Arrays;

public class DriverFunctional {

    /**
     * Take and attach screenshot to Allure
     *
     * @param baseTest instance of BaseTest
     */
    public static void takeAndAttachToAllureScreenshot(BaseTest baseTest) {
        File file = baseTest.getAndroidDriver().getScreenshotAs(OutputType.FILE);
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

    /**
     * Swipe webElement
     *
     * @param androidDriver instance of BaseTest
     * @param direction     swipe direction
     * @param webElement    WebElement
     */
    public static void swipe(AndroidDriver androidDriver, SwipeDirection direction, WebElement webElement) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence dragNDrop = new Sequence(finger, 1);
        int startX, startY, endX, endY;
        switch (direction) {
            case SWIPE_RIGHT:
                startX = webElement.getRect().x + (webElement.getSize().width / 4);
                startY = webElement.getRect().y + (webElement.getSize().height / 2);
                endX = webElement.getRect().x + (webElement.getSize().width * 3 / 4);
                endY = webElement.getRect().y + (webElement.getSize().height / 2);
                break;
            case SWIPE_LEFT:
                startX = webElement.getRect().x + (webElement.getSize().width * 3 / 4);
                startY = webElement.getRect().y + (webElement.getSize().height / 2);
                endX = webElement.getRect().x + (webElement.getSize().width / 4);
                endY = webElement.getRect().y + (webElement.getSize().height / 2);
                break;
            case SWIPE_DOWN:
                startX = webElement.getRect().x + (webElement.getSize().width / 2);
                startY = webElement.getRect().y + (webElement.getSize().height / 4);
                endX = webElement.getRect().x + (webElement.getSize().width / 2);
                endY = webElement.getRect().y + (webElement.getSize().height * 3 / 4);
                break;
            case SWIPE_UP:
                startX = webElement.getRect().x + (webElement.getSize().width / 2);
                startY = webElement.getRect().y + (webElement.getSize().height * 3 / 4);
                endX = webElement.getRect().x + (webElement.getSize().width / 2);
                endY = webElement.getRect().y + (webElement.getSize().height / 4);
                break;
            default:
                throw new IllegalArgumentException("Invalid swipe direction: " + direction);
        }
        dragNDrop.addAction(finger.createPointerMove(Duration.ofSeconds(0), PointerInput.Origin.viewport(), startX, startY));
        dragNDrop.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        dragNDrop.addAction(finger.createPointerMove(Duration.ofMillis(700), PointerInput.Origin.viewport(), endX, endY));
        dragNDrop.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        androidDriver.perform(Arrays.asList(dragNDrop));
    }
}
