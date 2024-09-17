package pages.app;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static data.TestsData.CHOOSE_FAVORITE_CATEGORIES;
import static data.TestsData.MESSAGE_AFTER_SUCCESS_CHOOSE_FAVORITE_CATEGORIES;

public class ChooseFavoriteCategoriesPage {
    private AppiumDriver appiumDriver;
    @FindBy(xpath = "//*[contains(@text,'" + CHOOSE_FAVORITE_CATEGORIES + "')]/..")
    private WebElement chooseFavoriteCategoriesButton;
    @FindBy(xpath = ("//android.widget.TextView[@content-desc=\""
            + MESSAGE_AFTER_SUCCESS_CHOOSE_FAVORITE_CATEGORIES + "\"]"))
    private WebElement chooseFavoriteCategoriesText;

    public ChooseFavoriteCategoriesPage(AppiumDriver appiumDriver) {
        this.appiumDriver = appiumDriver;
        PageFactory.initElements(appiumDriver, this);
    }

    @Step("Выбираем 4 рандомных категорий")
    public ChooseFavoriteCategoriesPage chooseFourRandomCategories() {
        List<WebElement> list = IntStream.rangeClosed(1, 7)
                .mapToObj(i -> appiumDriver.findElement(
                        By.xpath(String.format("//*[@content-desc=\"%s\"][%d]", CHOOSE_FAVORITE_CATEGORIES, i))))
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
