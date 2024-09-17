package helpers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.offset.PointOption;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
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
import java.util.Set;

import static data.TestsData.BASE_SCREEN_HEIGHT;
import static data.TestsData.BASE_SCREEN_WIDTH;

public class DriverFunctional {

    /**
     * Take and attach screenshot to Allure
     *
     * @param baseTest instance of BaseTest
     */
    public static void takeAndAttachToAllureScreenshot(BaseTest baseTest) {
        File file = baseTest.getAppiumDriver().getScreenshotAs(OutputType.FILE);
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
     * @param appiumDriver instance of AppiumDriver
     */
    public static void closeKeyBoard(AppiumDriver appiumDriver) {
        if (appiumDriver instanceof AndroidDriver && ((AndroidDriver) appiumDriver).isKeyboardShown()) {
            appiumDriver.hideKeyboard();
        } else if (appiumDriver instanceof IOSDriver && ((IOSDriver) appiumDriver).isKeyboardShown()) {
            appiumDriver.hideKeyboard();
        }
    }

    /**
     * Move to webview context
     *
     * @param appiumDriver instance of AppiumDriver
     */
    public static void moveToWebViewContext(AppiumDriver appiumDriver) {
        Set<String> contextNames = appiumDriver.getContextHandles();
        for (String contextName : contextNames) {
            if (contextName.contains("WEBVIEW")) {
                appiumDriver.context(contextName);
                break;
            }
        }
    }

    /**
     * Get current activity working with Android
     *
     * @param appiumDriver instance of AppiumDriver
     * @return current activity
     */
    public static String getCurrentActivity(AppiumDriver appiumDriver) {
        String currentActivity = "";
        if (appiumDriver instanceof AndroidDriver) {
            currentActivity = ((AndroidDriver) appiumDriver).currentActivity();
        }
        return currentActivity;
    }

    /**
     * Tap to coordinates
     *
     * @param appiumDriver instance of AppiumDriver
     * @param point        coordinates of tap
     */
    public static void tapToCoordinates(AppiumDriver appiumDriver, Point point) {
        TouchAction touchAction = new TouchAction(appiumDriver);
        touchAction.tap(calculatePointOption(appiumDriver, point)).perform();
    }

    /**
     * Calculate point option on emulator screen size, default screen size set in TestsData class
     *
     * @param appiumDriver instance of AppiumDriver
     * @param point        point coordinate
     * @return PointOption point option after calculation
     */
    public static PointOption calculatePointOption(AppiumDriver appiumDriver, Point point) {
        int emulatorWidth = appiumDriver.manage().window().getSize().width;
        int emulatorHeight = appiumDriver.manage().window().getSize().height;
        int xCoordinate = point.getX() * emulatorWidth / BASE_SCREEN_WIDTH;
        int yCoordinate = point.getY() * emulatorHeight / BASE_SCREEN_HEIGHT;
        return PointOption.point(xCoordinate, yCoordinate);
    }

    /**
     * Swipe webElement
     *
     * @param appiumDriver instance of AppiumDriver
     * @param direction    swipe direction
     * @param webElement   WebElement
     */
    public static void swipe(AppiumDriver appiumDriver, SwipeDirection direction, WebElement webElement) {
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
        appiumDriver.perform(Arrays.asList(dragNDrop));
    }
}
