package PageObjects;

import org.openqa.selenium.WebDriver;

public class UserHomePage {
    private final WebDriver driver;

    public UserHomePage(WebDriver driver) {
        this.driver = driver;
        if (driver.getTitle().equals("Our apologies! We were unable to process your request")) {
            throw new IllegalStateException("This is not user home page");
        }
    }


}
