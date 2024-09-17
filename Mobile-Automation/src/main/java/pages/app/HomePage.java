package pages.app;

import helpers.Waiters;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
public class HomePage {
    private AppiumDriver appiumDriver;
    @FindBy(id = "ru.beru.android:id/closeButton")
    private WebElement widgetCloseButton;
    @FindBy(id = "ru.beru.android:id/negativeButton")
    private WebElement skipCookieFilesButton;
    @FindBy(xpath = "//*[@resource-id=\"ru.beru.android:id/flexsdk_recycler_view_id\"]/android.view.ViewGroup[2]/*/.")
    private WebElement menu;
    @FindBy(id = "ru.beru.android:id/nav_profile")
    private WebElement profileButton;
    @FindBy(id = "ru.beru.android:id/nav_catalog")
    private WebElement catalogButton;
    @FindBy(xpath = "//*[@content-desc=\"плюс\"]")
    private WebElement yandexPlusButton;

    public HomePage(AppiumDriver appiumDriver) {
        this.appiumDriver = appiumDriver;
        PageFactory.initElements(appiumDriver, this);
    }

    @Step("Закрываем открывшееся всплывающее окно")
    public HomePage closeWidget() {
        if (widgetCloseButton.isDisplayed()) {
            widgetCloseButton.click();
        }
        return this;
    }

    @Step("При всплывающем окне Cookie files нажимаем кнопку SKIP")
    public HomePage skipCookieFiles() {
        if (skipCookieFilesButton.isDisplayed()) {
            skipCookieFilesButton.click();
        }
        return this;
    }

    @Step("Переходимация на страницу профиля")
    public ProfilePage moveToProfilePage() {
        profileButton.click();
        return new ProfilePage(appiumDriver);
    }

    @Step("Переход на страницу Яндекс Плюс")
    public YandexPlusPage moveToYandexPlusPage() {
        Waiters.elementToBeDisplayed(new WebDriverWait(appiumDriver, 15), yandexPlusButton);
        yandexPlusButton.click();
        return new YandexPlusPage(appiumDriver);
    }

    @Step("Переходим на страницу с каталогами")
    public CatalogPage moveToCatalogPage() {
        catalogButton.click();
        return new CatalogPage(appiumDriver);
    }
}
