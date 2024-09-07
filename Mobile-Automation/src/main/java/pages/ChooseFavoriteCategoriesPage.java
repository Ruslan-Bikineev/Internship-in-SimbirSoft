package pages;

import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.LinkedList;

public class ChooseFavoriteCategoriesPage {
    private AndroidDriver androidDriver;
    @FindBy(xpath = "(//*[@content-desc=\"Выбрать категории\"])[1]")
    private WebElement chooseFirstFavoriteCategoriesButton;
    @FindBy(xpath = "(//*[@content-desc=\"Выбрать категории\"])[2]")
    private WebElement chooseSecondFavoriteCategoriesButton;
    @FindBy(xpath = "(//*[@content-desc=\"Выбрать категории\"])[3]")
    private WebElement chooseThrirdFavoriteCategoriesButton;
    @FindBy(xpath = "(//*[@content-desc=\"Выбрать категории\"])[4]")
    private WebElement chooseFourthFavoriteCategoriesButton;
    @FindBy(xpath = "(//*[@content-desc=\"Выбрать категории\"])[5]")
    private WebElement chooseFifthFavoriteCategoriesButton;
    @FindBy(xpath = "(//*[@content-desc=\"Выбрать категории\"])[6]")
    private WebElement chooseSixthFavoriteCategoriesButton;
    @FindBy(xpath = "(//*[@content-desc=\"Выбрать категории\"])[7]")
    private WebElement chooseSeventhFavoriteCategoriesButton;
    @FindBy(xpath = "//android.view.ViewGroup[3]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup" +
            "/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup")
    private WebElement chooseFavoriteCategoriesButton;
    @FindBy(xpath = "//android.widget.TextView[@content-desc=\"Как вам подборка категорий в этом месяце?\"]")
    private WebElement chooseFavoriteCategoriesText;

    public ChooseFavoriteCategoriesPage(AndroidDriver androidDriver) {
        this.androidDriver = androidDriver;
        PageFactory.initElements(androidDriver, this);
    }

    @Step("Выбираем 4 рандомных категорий")
    public ChooseFavoriteCategoriesPage chooseFourRandomCategories() {
        LinkedList<WebElement> list = new LinkedList<>();
        list.add(chooseFirstFavoriteCategoriesButton);
        list.add(chooseSecondFavoriteCategoriesButton);
        list.add(chooseThrirdFavoriteCategoriesButton);
        list.add(chooseFourthFavoriteCategoriesButton);
        list.add(chooseFifthFavoriteCategoriesButton);
        list.add(chooseSixthFavoriteCategoriesButton);
        list.add(chooseSeventhFavoriteCategoriesButton);
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
