package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private final WebDriver driver;
    private final By emailField = new By.ByCssSelector("#email");
    private final By passwordField = new By.ByCssSelector("#password");
    private final By loginButton = new By.ByCssSelector("[data-testid=\"login-button\"]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        System.out.println(driver.getTitle());
        if (!driver.getTitle().equals("Way2Automation")) {
            throw new IllegalStateException("This is not login page");
        }
    }

    public LoginPage typeUsername(String username) {
        driver.findElement(emailField).sendKeys(username);
        return this;
    }

    public LoginPage typePassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
        return this;
    }

    public UserHomePage submitLogin() {
        driver.findElement(loginButton).click();
        return new UserHomePage(driver);
    }


    public UserHomePage loginAs(String username, String password) {
        typeUsername(username);
        typePassword(password);
        return submitLogin();
    }


}