package pages;

import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class HomePage {
    private AndroidDriver driver;
    @FindBy(xpath = "//android.widget.ImageButton[@resource-id=\"ru.beru.android:id/closeButton\"]")
    private WebElement widgetCloseButton;
    @FindBy(xpath = "//android.widget.Button[@resource-id=\"ru.beru.android:id/negativeButton\"]")
    private WebElement skipCookieFilesButton;
    @FindBy(xpath = "//android.widget.GridView[@resource-id=\"ru.beru.android:id/flexsdk_recycler_view_id\"]" +
            "/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup\n")
    private WebElement menu;

    public HomePage(AndroidDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
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
}
