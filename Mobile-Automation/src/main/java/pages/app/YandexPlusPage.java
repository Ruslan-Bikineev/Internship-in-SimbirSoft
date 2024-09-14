package pages.app;

import com.google.common.collect.ImmutableMap;
import helpers.DriverFunctional;
import helpers.Waiters;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.browser.YandexPlusConditionsPage;

import static data.TestsData.YANDEX_PLUS_CONDITION_LINK_COORDINATES;
import static data.TestsData.YANDEX_PLUS_CONDITION_LINK_TEXT;

@Getter
public class YandexPlusPage {
    private AndroidDriver androidDriver;
    @FindBy(id = "ru.beru.android:id/plus_sdk_native_pay_button")
    private WebElement connectPlusButton;
    @FindBy(id = "ru.beru.android:id/checkout_legals_text")
    private WebElement openYandexPlusConditionsLink;

    public YandexPlusPage(AndroidDriver androidDriver) {
        this.androidDriver = androidDriver;
        PageFactory.initElements(androidDriver, this);
    }

    @Step("Переход в Яндекс Плюс")
    public YandexPlusPage moveToConnectPlus() {
        Waiters.elementToBeDisplayed(new WebDriverWait(androidDriver, 60), connectPlusButton);
        connectPlusButton.click();
        return this;
    }

    @Step("Скролл до ссылкы с условиями Яндекс Плюс")
    public YandexPlusPage scrollToConditionsLink() {
        androidDriver.executeScript("mobile:scroll",
                ImmutableMap.of("strategy", "-android uiautomator",
                        "selector", String.format("new UiSelector().text(\"%s\")",
                                YANDEX_PLUS_CONDITION_LINK_TEXT)));
        return this;
    }

    @Step("Открываем ссылку в браузере с условиями Яндекс Плюс")
    public YandexPlusConditionsPage openYandexPlusConditions() {
        DriverFunctional.tapToCoordinates(androidDriver, YANDEX_PLUS_CONDITION_LINK_COORDINATES);
        return new YandexPlusConditionsPage(androidDriver);
    }
}
