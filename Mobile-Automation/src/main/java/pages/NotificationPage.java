package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NotificationPage {
    private AndroidDriver androidDriver;
    @FindBy(xpath = "//android.widget.TextView[@text=\"Включить всё\"]")
    private WebElement enableAllNotificationsButton;
    @FindBy(xpath = "//android.widget.GridView[@resource-id=\"ru.beru.android:id/flexsdk_recycler_view_id\"]" +
            "/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]" +
            "/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup\n")
    private WebElement promotionsAndSalesSwitcher;

    public NotificationPage(AndroidDriver androidDriver) {
        this.androidDriver = androidDriver;
        PageFactory.initElements(androidDriver, this);
    }

    public PhoneNotificationPage moveToPhoneNotificationPage() {
        enableAllNotificationsButton.click();
        return new PhoneNotificationPage(androidDriver);
    }

    public boolean isPromotionsAndSalesSwitcherSelected() {
        return promotionsAndSalesSwitcher.isSelected();
    }
}
