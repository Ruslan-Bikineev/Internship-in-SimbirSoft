package PageObjects;

import io.qameta.allure.Step;
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
    private WebElement emailError;

    @FindBy(css = "input[type=password] + .inline-error")
    private WebElement passwordError;

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


    @Step("Ввод логина пользователя {username}")
    public LoginPage typeUsername(String username) {
        emailField.sendKeys(username);
        return this;
    }

    @Step("Ввод пароля пользователя {password}")
    public LoginPage typePassword(String password) {
        passwordField.sendKeys(password);
        return this;
    }


    @Step("Клик по кнопке «Вход в систему»")
    public UserHomePage submitLogin() {
        loginButton.click();
        return new UserHomePage(driver);
    }


    public UserHomePage loginAs(String username, String password) {
        typeUsername(username);
        typePassword(password);
        return submitLogin();
    }

    @Step("Получение ошибки при вводе имени пользователя")
    public String getEmailError() {
        return emailError.getText();
    }

    @Step("Получение ошибки вводе пароля пользователя")
    public String getPasswordError() {
        return passwordError.getText();
    }

    @Step("Клик по логотипу")
    public LoginPage clickLoginPageTitle() {
        loginPageTitle.click();
        return this;
    }

}