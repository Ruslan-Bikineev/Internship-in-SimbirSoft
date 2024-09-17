package pages.phone;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.app.NotificationPage;

public class PhoneNotificationPage {
    private AppiumDriver appiumDriver;
    @FindBy(xpath = "//*[@content-desc=\"Navigate up\"]")
    private WebElement navigateUpButton;
    @FindBy(id = "android:id/switch_widget")
    private WebElement enableAllMarketNotificationsSwitch;

    public PhoneNotificationPage(AppiumDriver appiumDriver) {
        this.appiumDriver = appiumDriver;
        PageFactory.initElements(appiumDriver, this);
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
        return new NotificationPage(appiumDriver);
    }
}
