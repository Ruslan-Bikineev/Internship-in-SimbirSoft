package pages;

import io.appium.java_client.android.AndroidDriver;
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

    public CatalogPage scrollToEquipment() {
        androidDriver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)" +
                ".instance(0)).scrollIntoView(new UiSelector().textContains(\"Оборудование\").instance(0))");
        return this;
    }
}
