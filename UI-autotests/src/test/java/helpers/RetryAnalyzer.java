package helpers;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    private int retryCount = 0;
    private int retryMaxCount = 2;

    @Override
    public boolean retry(ITestResult iTestResult) {
        boolean result = false;
        if (!iTestResult.isSuccess() && retryCount < retryMaxCount) {
            retryCount++;
            result = true;
        }
        return result;
    }
}