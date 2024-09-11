package pages.app;

import helpers.Waiters;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.browser.YandexPlusConditionsPage;

import java.util.Set;

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

    @Step("Открываем ссылку в браузере с условиями Яндекс Плюс")
    public YandexPlusConditionsPage openYandexPlusConditions() {
        Set<String> contextNames = androidDriver.getContextHandles();
        System.out.println(contextNames);
        for (String context : contextNames) {
            if (context.contains("WEBVIEW")) {
                System.out.println("Context Name is " + context);
                androidDriver.context(context);
                break;
            }
        }
//        androidDriver.context("NATIVE_APP");
//        Optional webViewOptional = contextNames.stream().filter(contextName -> contextName.contains("WEBVIEW")).findFirst();
//        if (webViewOptional.isPresent()) {
//            androidDriver.context(webViewOptional.get().toString());
//            openYandexPlusConditionsLink.click();
//        }
        return new YandexPlusConditionsPage(androidDriver);
    }
}
