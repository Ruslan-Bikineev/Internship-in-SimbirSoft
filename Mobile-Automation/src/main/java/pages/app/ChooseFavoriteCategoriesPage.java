package pages.app;

import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ChooseFavoriteCategoriesPage {
    private AndroidDriver androidDriver;
    @FindBy(xpath = "//*[contains(@text,'Выбрать категории')]/..")
    private WebElement chooseFavoriteCategoriesButton;
    @FindBy(xpath = "//android.widget.TextView[@content-desc=\"Как вам подборка категорий в этом месяце?\"]")
    private WebElement chooseFavoriteCategoriesText;

    public ChooseFavoriteCategoriesPage(AndroidDriver androidDriver) {
        this.androidDriver = androidDriver;
        PageFactory.initElements(androidDriver, this);
    }

    @Step("Выбираем 4 рандомных категорий")
    public ChooseFavoriteCategoriesPage chooseFourRandomCategories() {
        List<WebElement> list = IntStream.rangeClosed(1, 7)
                .mapToObj(i -> androidDriver.findElement(
                        By.xpath("//*[@content-desc=\"Выбрать категории\"][" + i + "]")))
                .collect(Collectors.toList());
        for (int i = 0; i < 4; i++) {
            int random = (int) (Math.random() * list.size());
            list.get(random).click();
            list.remove(random);
        }
        return this;
    }

    @Step("Нажимаем на кнопку \"Выбрать категории\"")
    public ChooseFavoriteCategoriesPage chooseFavoriteCategoriesButton() {
        chooseFavoriteCategoriesButton.click();
        return this;
    }

    @Step("Получаем текст \"Как вам подборка категорий в этом месяце?\"")
    public String getChooseFavoriteCategoriesText() {
        return chooseFavoriteCategoriesText.getText();
    }
}
