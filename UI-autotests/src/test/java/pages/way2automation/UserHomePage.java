package pages.way2automation;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static helpers.Waiters.waitUrlToBe;

public class UserHomePage {
    public static final String USER_HOME_PAGE_URL = "https://www.selenium-tutorial.com/";
    private final RemoteWebDriver driver;

    public UserHomePage(RemoteWebDriver driver) {
        this.driver = driver;
        waitUrlToBe(new WebDriverWait(driver, Duration.ofSeconds(15)), USER_HOME_PAGE_URL);
        PageFactory.initElements(driver, this);
    }
}
