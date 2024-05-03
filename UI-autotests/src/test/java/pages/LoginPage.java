package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    private final WebDriver driver;
    @FindBy(xpath = "//h3")
    private WebElement loginPageTitle;

    @FindBy(css = "#email")
    private WebElement emailField;

    @FindBy(css = "input[type=email] + .inline-error")
    private WebElement emailFieldError;

    @FindBy(css = "input[type=password] + .inline-error")
    private WebElement passwordFieldError;

    @FindBy(css = "#password")
    private WebElement passwordField;
    @FindBy(css = "[data-testid=\"login-button\"]")
    private WebElement loginButton;

    @FindBy(css = ".text-with-icon")
    private WebElement invalidLoginMessage;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        if (!driver.getTitle().equals("Way2Automation")) {
            throw new IllegalStateException("This is not login page");
        }
        PageFactory.initElements(driver, this);
    }

    public LoginPage typeUsername(String username) {
        emailField.clear();
        emailField.sendKeys(username);
        return this;
    }

    public LoginPage typePassword(String password) {
        passwordField.clear();
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

    public String getEmailFieldError() {
        String color = emailFieldError.getCssValue("color");
        return emailFieldError.getText();
    }

    public String getPasswordFieldError() {
        return passwordFieldError.getText();
    }

    public LoginPage clickLoginPageTitle() {
        loginPageTitle.click();
        return this;
    }

    public String getInvalidLoginMessage() {
        return invalidLoginMessage.getText();
    }

    public String getInvalidLoginMessageColor() {
        return invalidLoginMessage.getCssValue("color");
    }

    public String getEmailField() {
        return emailField.getAttribute("value");
    }

    public String getPasswordField() {
        return passwordField.getAttribute("value");
    }

}