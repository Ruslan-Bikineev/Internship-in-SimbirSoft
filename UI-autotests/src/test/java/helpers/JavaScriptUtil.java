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

    @Step("Наличие скролла по высоте страницы")
    public static boolean hasHeightScroll(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        // Высота видимой части документа
        long visibleHeight = (long) (js.executeScript("return document.documentElement.clientHeight"));
        // Высота всего документа со всей прокручиваемой областью страницы
        long fullHeight = (long) js.executeScript("return Math.max(document.body.scrollHeight, " +
                "document.documentElement.scrollHeight, document.body.offsetHeight, " +
                "document.documentElement.offsetHeight, document.body.clientHeight, " +
                "document.documentElement.clientHeight)");
        return fullHeight > visibleHeight;
    }
}
