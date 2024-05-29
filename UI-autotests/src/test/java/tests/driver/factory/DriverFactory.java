package tests.driver.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import java.time.Duration;

public interface DriverFactory {
    WebDriver createDriver(String browserName);

    default InternetExplorerOptions internetExplorerOptions() {
        InternetExplorerOptions options = new InternetExplorerOptions();
        options.waitForUploadDialogUpTo(Duration.ofSeconds(15));
        options.introduceFlakinessByIgnoringSecurityDomains();
        options.destructivelyEnsureCleanSession();
        options.ignoreZoomSettings();
        return options;
    }
}