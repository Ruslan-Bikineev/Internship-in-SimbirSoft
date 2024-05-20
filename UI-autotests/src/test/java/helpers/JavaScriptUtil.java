package helpers;

import io.qameta.allure.Step;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtil {
    @Step("Убираем фокус из поля ввода")
    public static void blurToElement(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].blur();", element);
    }

    @Step("Наличие скролла на странице")
    public static boolean hasScroll(WebDriver driver) {
        boolean result = false;
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int resultHeight = (int) js.executeScript("return document.body.scrollHeight;");
        System.out.println(resultHeight);
        return result;
    }
}
