package pages.app;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class CatalogPage {
    private AndroidDriver androidDriver;
    @FindBy(xpath = "//*[@text=\"Оборудование\"]")
    private WebElement equipmentButton;

    public CatalogPage(AndroidDriver androidDriver) {
        this.androidDriver = androidDriver;
        PageFactory.initElements(androidDriver, this);
    }

    @Step("Скролл до вкладки \"Оборудование\"")
    public CatalogPage scrollToEquipment() {
        androidDriver.executeScript("mobile:scroll",
                ImmutableMap.of("strategy", "-android uiautomator",
                        "selector", "new UiSelector().text(\"Оборудование\")"));
        return this;
    }
}
