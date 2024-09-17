package pages.app;

import helpers.Waiters;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Map;

import static data.TestsData.SETTINGS;

@Getter
public class ProfilePage {
    private AppiumDriver appiumDriver;
    @FindBy(id = "ru.beru.android:id/slideIndicatorView")
    private WebElement slideFavoriteCategories;
    @FindBy(xpath = "//*[@text=\"" + SETTINGS + "\"]")
    private WebElement settingsButton;
    @FindBy(xpath = "//android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup")
    private WebElement chooseFavoriteCategoriesButton;


    public ProfilePage(AppiumDriver appiumDriver) {
        this.appiumDriver = appiumDriver;
        PageFactory.initElements(appiumDriver, this);
    }

    @Step("Переход на страницу настройки")
    public SettingsPage moveToSettingsPage() {
        settingsButton.click();
        return new SettingsPage(appiumDriver);
    }

    @Step("Свайпаем вниз окно слайдера \"Любимые категории\"")
    public ProfilePage swipeDownSlideIndicatorView() {
        Waiters.elementToBeDisplayed(new WebDriverWait(appiumDriver, 15), slideFavoriteCategories);
        ((JavascriptExecutor) appiumDriver).executeScript("mobile: swipeGesture", Map.of(
                "left", 456, "top", 168, "width", 168, "height", 1752,
                "direction", "down",
                "percent", 1.0
        ));
        return this;
    }

    @Step("Открываем вкладку с выбором любимых категорий")
    public ChooseFavoriteCategoriesPage openChooseFavoriteCategoriesPage() {
        chooseFavoriteCategoriesButton.click();
        return new ChooseFavoriteCategoriesPage(appiumDriver);
    }
}
