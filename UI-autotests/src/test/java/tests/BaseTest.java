package tests;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import tests.driver.factory.WebDriverFactory;

import java.time.Duration;
import java.util.List;

public class BaseTest {
    private WebDriver driver;

    @BeforeMethod
    @Parameters(value = {"remote", "browser"})
    public void setUp(boolean remote, String browser) {
        driver = WebDriverFactory.getDriver(remote, browser);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }

    @AfterMethod
    public void tearDown() {
        this.driver.quit();
    }

    public WebDriver getDriver() {
        return driver;
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void addCookies(List<Cookie> cookieList) {
        cookieList.forEach(cookie -> getDriver().manage().addCookie(cookie));
    }

    /**
     * Данный метод возвращает дескриптор нового окна,
     * если ранее открытые окна были переданы в качестве входных параметров.
     *
     * @param oldWindowHandles дескрипторы ранее открытых окон
     * @return String, содержащий дескриптор нового окна, или null, если новое окно не найдено
     */
    public String getNewWindowHandle(String... oldWindowHandles) {
        List<String> oldWindowHandlesList = List.of(oldWindowHandles);
        String resultWindowHandle = driver.getWindowHandles().stream()
                .filter(windowHandle -> !oldWindowHandlesList.contains(windowHandle))
                .findFirst().orElse(null);
        return resultWindowHandle;
    }
}
