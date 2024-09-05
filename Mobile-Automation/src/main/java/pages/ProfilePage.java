package pages;

import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class ProfilePage {
    private AndroidDriver androidDriver;
    @FindBy(id = "ru.beru.android:id/slideIndicatorView")
    private WebElement slideIndicatorView;
    @FindBy(xpath = "//android.widget.TextView[@text=\"Настройки\"]")
    private WebElement settingsButton;

    public ProfilePage(AndroidDriver androidDriver) {
        this.androidDriver = androidDriver;
        PageFactory.initElements(androidDriver, this);
    }

    @Step("Переход на страницу настройки")
    public SettingsPage moveToSettingsPage() {
        settingsButton.click();
        return new SettingsPage(androidDriver);
    }
}
