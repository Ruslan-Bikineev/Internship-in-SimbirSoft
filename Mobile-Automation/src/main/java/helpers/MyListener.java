package helpers;

import org.testng.ITestListener;
import org.testng.ITestResult;
import tests.BaseTest;

public class MyListener implements ITestListener {
    @Override
    public void onTestFailure(ITestResult result) {
        DriverFunctional.takeAndAttachToAllureScreenshot((BaseTest) result.getInstance());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        DriverFunctional.takeAndAttachToAllureScreenshot((BaseTest) result.getInstance());
    }
}
