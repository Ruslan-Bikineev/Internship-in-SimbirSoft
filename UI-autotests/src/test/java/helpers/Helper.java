package helpers;

import io.qameta.allure.Step;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class Helper {
    /**
     * Данный метод переключаем фокус на вкладку в браузере.
     *
     * @param driver       экземпляр WebDriver, используемый для взаимодействия с браузером.
     * @param windowHandle дескриптор вкладки для смены фокуса.
     */
    @Step("Переключаем фокус на вкладку с дескриптором: {windowHandle}")
    public static void switchToWindow(WebDriver driver, String windowHandle) {
        driver.switchTo().window(windowHandle);
    }

    /**
     * Данный метод добавляет Cookie в браузер.
     *
     * @param driver     экземпляр WebDriver, используемый для взаимодействия с браузером.
     * @param cookieList список Cookies, которые нужно добавить в браузер.
     */
    public static void addCookies(WebDriver driver, List<Cookie> cookieList) {
        cookieList.forEach(cookie -> driver.manage().addCookie(cookie));
    }

    /**
     * Данный метод возвращает дескриптор нового окна,
     * если ранее открытые окна были переданы в качестве входных параметров.
     *
     * @param driver           экземпляр WebDriver, используемый для взаимодействия с браузером.
     * @param oldWindowHandles дескрипторы ранее открытых окон.
     * @return String, содержащий дескриптор нового окна, или null, если новое окно не найдено.
     */
    public static String getNewWindowHandle(WebDriver driver, String... oldWindowHandles) {
        List<String> oldWindowHandlesList = List.of(oldWindowHandles);
        String resultWindowHandle = driver.getWindowHandles().stream()
                .filter(windowHandle -> !oldWindowHandlesList.contains(windowHandle))
                .findFirst().orElse(null);
        return resultWindowHandle;
    }
}
