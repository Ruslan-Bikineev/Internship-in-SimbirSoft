package pages.app;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SettingsPage {
    private AppiumDriver appiumDriver;
    @FindBy(xpath = "//*[@text=\"Уведомления\"]")
    private WebElement notificationsButton;

    public SettingsPage(AppiumDriver appiumDriver) {
        this.appiumDriver = appiumDriver;
        PageFactory.initElements(appiumDriver, this);
    }

    @Step("Переход на страницу уведомлений")
    public NotificationPage moveToNotificationPage() {
        notificationsButton.click();
        return new NotificationPage(appiumDriver);
    }
}
