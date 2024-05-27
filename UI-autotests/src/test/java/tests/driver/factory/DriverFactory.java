package tests.driver.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import java.time.Duration;

public interface DriverFactory {
    WebDriver createDriver(String browserName);

    default InternetExplorerOptions internetExplorerSetCapability() {
        InternetExplorerOptions options = new InternetExplorerOptions();
        options.waitForUploadDialogUpTo(Duration.ofSeconds(15));
        options.introduceFlakinessByIgnoringSecurityDomains();
        options.ignoreZoomSettings();
        return options;
    }
}