package pages;

import framework.Waiters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class UserHomePage extends Waiters {
    private final WebDriver driver;

    public UserHomePage(WebDriver driver) {
        super(new WebDriverWait(driver, Duration.ofSeconds(15)));
        this.driver = driver;
        waitUrlToBe(PageProperties.USER_HOME_PAGE_URL);
        PageFactory.initElements(driver, this);
    }
}
