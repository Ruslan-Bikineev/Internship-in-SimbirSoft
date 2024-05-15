package helpers;

import org.testng.ITestListener;
import org.testng.ITestResult;
import tests.BaseTest;

public class MyListener implements ITestListener {
    @Override
    public void onTestFailure(ITestResult result) {
        BaseTest baseTest = (BaseTest) result.getInstance();
        ScreenShooter screenShooter = new ScreenShooter();
        screenShooter.getScreenshotByte(baseTest.getDriver());
    }
}
