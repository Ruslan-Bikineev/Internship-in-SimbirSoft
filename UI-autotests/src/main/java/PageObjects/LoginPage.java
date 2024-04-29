package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    private final WebDriver driver;
    @FindBy(css = "#email")
    private WebElement emailField;
    @FindBy(css = "#password")
    private WebElement passwordField;
    @FindBy(css = "[data-testid=\"login-button\"]")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        if (!driver.getTitle().equals("Way2Automation")) {
            throw new IllegalStateException("This is not login page");
        }
        PageFactory.initElements(driver, this);
    }

    public LoginPage typeUsername(String username) {
        emailField.sendKeys(username);
        return this;
    }

    public LoginPage typePassword(String password) {
        passwordField.sendKeys(password);
        return this;
    }

    public UserHomePage submitLogin() {
        loginButton.click();
        return new UserHomePage(driver);
    }


    public UserHomePage loginAs(String username, String password) {
        typeUsername(username);
        typePassword(password);
        return submitLogin();
    }


}