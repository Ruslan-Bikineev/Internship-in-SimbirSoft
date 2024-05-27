package helpers;

import org.testng.IAnnotationTransformer;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;
import tests.BaseTest;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class MyListener implements ITestListener, IAnnotationTransformer {
    @Override
    public void onTestFailure(ITestResult result) {
        BaseTest baseTest = (BaseTest) result.getInstance();
        ScreenShooter screenShooter = new ScreenShooter();
        screenShooter.getScreenshotByte(baseTest.getDriver());
    }

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
}
