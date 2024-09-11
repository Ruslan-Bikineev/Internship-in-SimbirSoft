package pages.app;

import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SettingsPage {
    private AndroidDriver androidDriver;
    @FindBy(xpath = "//*[@text=\"Уведомления\"]")
    private WebElement notificationsButton;

    public SettingsPage(AndroidDriver androidDriver) {
        this.androidDriver = androidDriver;
        PageFactory.initElements(androidDriver, this);
    }

    @Step("Переход на страницу уведомлений")
    public NotificationPage moveToNotificationPage() {
        notificationsButton.click();
        return new NotificationPage(androidDriver);
    }
}
