package way2automation_pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static helpers.Waiters.waitUrlToBe;

public class UserHomePage {
    private final WebDriver driver;

    public UserHomePage(WebDriver driver) {
        this.driver = driver;
        waitUrlToBe(new WebDriverWait(driver, Duration.ofSeconds(15)), PageProperties.USER_HOME_PAGE_URL);
        PageFactory.initElements(driver, this);
    }
}
