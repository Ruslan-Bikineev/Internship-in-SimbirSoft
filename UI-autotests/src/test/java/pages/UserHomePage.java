package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class UserHomePage {
    private final WebDriver driver;

    public UserHomePage(WebDriver driver) {
        this.driver = driver;
        if (!driver.getTitle().equals("Selenium Tutorial for beginners and professionals l Best way to learn")) {
            throw new IllegalStateException("This is not user home page");
        }
        PageFactory.initElements(driver, this);
    }
}
