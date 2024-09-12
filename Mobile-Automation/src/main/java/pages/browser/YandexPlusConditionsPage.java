package pages.browser;

import helpers.DriverFunctional;
import io.appium.java_client.android.AndroidDriver;
import lombok.Getter;
import org.openqa.selenium.support.PageFactory;

@Getter
public class YandexPlusConditionsPage {
    private AndroidDriver androidDriver;

    public YandexPlusConditionsPage(AndroidDriver androidDriver) {
        this.androidDriver = androidDriver;
        PageFactory.initElements(androidDriver, this);
        DriverFunctional.moveToWebViewContext(androidDriver);
    }
}
