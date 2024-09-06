package pages;

import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PhoneNotificationPage {
    private AndroidDriver androidDriver;
    @FindBy(xpath = "//*[@content-desc=\"Navigate up\"]")
    private WebElement navigateUpButton;
    @FindBy(id = "android:id/switch_widget")
    private WebElement enableAllMarketNotificationsSwitch;

    public PhoneNotificationPage(AndroidDriver androidDriver) {
        this.androidDriver = androidDriver;
        PageFactory.initElements(androidDriver, this);
    }

    @Step("Включаем все уведомления приложения в настройках телефона")
    public PhoneNotificationPage enableAllNotifications() {
        if (!enableAllMarketNotificationsSwitch.isSelected()) {
            enableAllMarketNotificationsSwitch.click();
        }
        return this;
    }

    @Step("Переход на страницу уведомлений")
    public NotificationPage moveToNotificationPage() {
        navigateUpButton.click();
        return new NotificationPage(androidDriver);
    }
}
