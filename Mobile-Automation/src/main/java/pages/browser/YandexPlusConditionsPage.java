package pages.browser;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.support.PageFactory;

public class YandexPlusConditionsPage {
    private AndroidDriver androidDriver;

    public YandexPlusConditionsPage(AndroidDriver androidDriver) {
        this.androidDriver = androidDriver;
        PageFactory.initElements(androidDriver, this);
    }
}
