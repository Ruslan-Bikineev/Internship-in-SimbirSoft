package pages.way2automation;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DroppablePage {
    public static final String DROPPABLE_PAGE_URL = "https://way2automation.com/way2auto_jquery/droppable.php#load_box";
    private final WebDriver driver;
    @FindBy(css = "#example-1-tab-1 iframe")
    private WebElement iframeExample1;
    @FindBy(id = "draggable")
    private WebElement draggableExample1;
    @FindBy(id = "droppable")
    private WebElement droppableExample1;

    public DroppablePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Step("Открытие страницы: " + DROPPABLE_PAGE_URL)
    public DroppablePage openDroppablePage() {
        driver.get(DROPPABLE_PAGE_URL);
        return this;
    }

    @Step("Перетаскивание элемента в принимающийую область Example 1")
    public void dragAndDropExample1() {
        Actions actions = new Actions(driver);
        driver.switchTo().frame(iframeExample1);
        actions.dragAndDrop(draggableExample1, droppableExample1).perform();
        driver.switchTo().defaultContent();
    }

    @Step("Получение текста принимающего элемента Example 1")
    public String getTextDroppableExample1() {
        String text;
        driver.switchTo().frame(iframeExample1);
        text = droppableExample1.getText();
        driver.switchTo().defaultContent();
        return text;
    }
}
