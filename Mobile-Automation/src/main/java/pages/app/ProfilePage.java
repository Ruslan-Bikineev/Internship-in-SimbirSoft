package pages.app;

import helpers.Waiters;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Map;

@Getter
public class ProfilePage {
    private AndroidDriver androidDriver;
    @FindBy(id = "ru.beru.android:id/slideIndicatorView")
    private WebElement slideFavoriteCategories;
    @FindBy(xpath = "//*[@text=\"Настройки\"]")
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

    @Step("Свайпаем вниз окно слайдера \"Любимые категории\"")
    public ProfilePage swipeDownSlideIndicatorView() {
        Waiters.elementToBeDisplayed(new WebDriverWait(androidDriver, 15), slideFavoriteCategories);
        ((JavascriptExecutor) androidDriver).executeScript("mobile: swipeGesture", Map.of(
                "left", 456, "top", 168, "width", 168, "height", 1752,
                "direction", "down",
                "percent", 1.0
        ));
        return this;
    }
}
