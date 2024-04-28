package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    private final WebDriver driver;
    private final String url = "https://www.way2automation.com/";
    private final By popup = new By.ByCssSelector(".elementor-location-popup");
    private final By popupClose = new By.ByCssSelector(".dialog-close-button");
    private final By buttonAllCourses = new By.ByCssSelector("#menu-item-27580");
    private final By buttonMemberLogin = new By.ByCssSelector("#menu-item-27625 .menu-link");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void openHomePage() {
        driver.get(url);
    }

    public HomePage clickAllCourses() {
        driver.findElement(buttonAllCourses).click();
        return this;
    }

    public LoginPage clickMemberLogin() {
        driver.findElement(buttonMemberLogin).click();
        return new LoginPage(driver);
    }

    public void clickPopupClose() {
        driver.findElement(popupClose).click();
    }
}
