package pages.app;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.phone.PhoneNotificationPage;

public class NotificationPage {
    private AppiumDriver appiumDriver;
    @FindBy(xpath = "//*[@text=\"Включить всё\"]")
    private WebElement enableAllNotificationsButton;
    @FindBy(xpath = "//android.view.ViewGroup[2]/android.view.ViewGroup[2]" +
            "/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup")
    private WebElement promotionsAndSalesSwitcher;

    public NotificationPage(AppiumDriver appiumDriver) {
        this.appiumDriver = appiumDriver;
        PageFactory.initElements(appiumDriver, this);
    }

    public PhoneNotificationPage moveToPhoneNotificationPage() {
        enableAllNotificationsButton.click();
        return new PhoneNotificationPage(appiumDriver);
    }

    public boolean isPromotionsAndSalesSwitcherSelected() {
        return promotionsAndSalesSwitcher.isSelected();
    }
}
