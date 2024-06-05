package pages.way2automation;

import io.qameta.allure.Step;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static data.TestsData.IT_IS_A_CUSTOM_TEXT;

public class AlertPage {
    public static final String ALERT_PAGE_URL = "https://way2automation.com/way2auto_jquery/alert.php#load_box";
    private final WebDriver driver;
    @FindBy(xpath = "//a[@href='#example-1-tab-2']")
    WebElement inputAlertButton;
    @FindBy(xpath = "//button[@onclick='myFunction()']")
    WebElement demonstrateInputBoxButton;
    @FindBy(css = "#example-1-tab-2 iframe")
    WebElement iframeExample2;
    @FindBy(id = "demo")
    WebElement alertText;

    public AlertPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Step("Открываем страницу: " + ALERT_PAGE_URL)
    public AlertPage openAlertPage() {
        driver.get(ALERT_PAGE_URL);
        return this;
    }

    @Step("Открываем вкладку Input Alert")
    public AlertPage submitInputAlert() {
        inputAlertButton.click();
        return this;
    }

    @Step("Открываем демонстрацию Input Box")
    public AlertPage sudmitDemonstrateInputBox() {
        driver.switchTo().frame(iframeExample2);
        demonstrateInputBoxButton.click();
        writeTextToAlert(IT_IS_A_CUSTOM_TEXT);
        driver.switchTo().defaultContent();
        return this;
    }

    @Step("Получаем текст из Alert")
    public String getAlertText() {
        driver.switchTo().frame(iframeExample2);
        String text = alertText.getText();
        driver.switchTo().defaultContent();
        return text;
    }

    @Step("Вводим текст в Alert: {text} и нажимаем ОК")
    private void writeTextToAlert(String text) {
        Alert alert = driver.switchTo().alert();
        alert.sendKeys(text);
        alert.accept();
    }
}
