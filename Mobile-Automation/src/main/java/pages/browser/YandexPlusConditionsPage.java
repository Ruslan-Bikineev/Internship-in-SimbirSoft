package pages.browser;

import helpers.DriverFunctional;
import io.appium.java_client.AppiumDriver;
import lombok.Getter;
import org.openqa.selenium.support.PageFactory;

@Getter
public class YandexPlusConditionsPage {
    private AppiumDriver appiumDriver;

    public YandexPlusConditionsPage(AppiumDriver appiumDriver) {
        this.appiumDriver = appiumDriver;
        PageFactory.initElements(appiumDriver, this);
        DriverFunctional.moveToWebViewContext(appiumDriver);
    }
}
