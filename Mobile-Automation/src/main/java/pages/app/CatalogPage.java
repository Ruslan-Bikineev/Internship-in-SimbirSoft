package pages.app;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static data.TestsData.EQUIPMENT;

@Getter
public class CatalogPage {
    private AppiumDriver appiumDriver;
    @FindBy(xpath = "//*[@text=\"" + EQUIPMENT + "\"]")
    private WebElement equipmentButton;

    public CatalogPage(AppiumDriver appiumDriver) {
        this.appiumDriver = appiumDriver;
        PageFactory.initElements(appiumDriver, this);
    }

    @Step("Скролл до вкладки \"Оборудование\"")
    public CatalogPage scrollToEquipment() {
        appiumDriver.executeScript("mobile:scroll",
                ImmutableMap.of("strategy", "-android uiautomator",
                        "selector", String.format("new UiSelector().text(\"%s\")", EQUIPMENT)));
        return this;
    }

    @Step("Проверяем отображается ли вкладка \"Оборудование\"")
    public boolean isEquipmentButtonDisplayed() {
        return equipmentButton.isDisplayed();
    }
}
