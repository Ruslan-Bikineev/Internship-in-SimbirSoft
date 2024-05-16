package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static helpers.Waiters.waitUrlToBe;

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
        waitUrlToBe(new WebDriverWait(driver, Duration.ofSeconds(15)), PageProperties.LOGIN_PAGE_URL);
        PageFactory.initElements(driver, this);
    }

    @Step("Ввод логина пользователя: {username}")
    public LoginPage typeUsername(String username) {
        emailField.clear();
        emailField.sendKeys(username);
        return this;
    }

    @Step("Ввод пароля пользователя: {password}")
    public LoginPage typePassword(String password) {
        passwordField.clear();
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

    @Step("Ошибка при вводе имени пользователя")
    public String getEmailFieldError() {
        String color = emailFieldError.getCssValue("color");
        return emailFieldError.getText();
    }

    @Step("Ошибка при вводе пароля пользователя")
    public String getPasswordFieldError() {
        return passwordFieldError.getText();
    }

    @Step("Клик по логотипу")
    public LoginPage clickLoginPageTitle() {
        loginPageTitle.click();
        return this;
    }

    @Step("Сообщение об ошибке при неверном логине или пароле")
    public String getInvalidLoginMessage() {
        return invalidLoginMessage.getText();
    }

    @Step("Цвет сообщение об ошибке при неверном логине или пароле")
    public String getInvalidLoginMessageColor() {
        return invalidLoginMessage.getCssValue("color");
    }

    @Step("Получение поля логина")
    public String getEmailField() {
        return emailField.getAttribute("value");
    }

    @Step("Получение поля пароля")
    public String getPasswordField() {
        return passwordField.getAttribute("value");
    }
}