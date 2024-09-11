package pages.app;

import helpers.DriverFunctional;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
@AllArgsConstructor
public class LogInPage {
    private AndroidDriver androidDriver;
    @FindBy(xpath = "//*[@resource-id=\"passp-field-login\"]")
    private WebElement loginField;
    @FindBy(xpath = "//*[@resource-id=\"passp:sign-in\"]")
    private WebElement signInButton;
    @FindBy(xpath = "//*[@resource-id=\"passp-field-passwd\"]")
    private WebElement passwordField;
    @FindBy(xpath = "//*[@resource-id=\"passp:sign-in\"]")
    private WebElement passwordSignInButton;
    @FindBy(xpath = "//*[@resource-id=\"field:input-passwd:hint\"]")
    private WebElement passwordHint;

    public LogInPage(AndroidDriver driver) {
        this.androidDriver = driver;
        PageFactory.initElements(driver, this);
    }

    @Step("Заполняем поле логин: {login}")
    public LogInPage setLogin(String login) {
        loginField.sendKeys(login);
        signInButton.click();
        return this;
    }

    @Step("Заполняем поле пароль: {password}")
    public LogInPage setPassword(String password) {
        passwordField.sendKeys(password);
        DriverFunctional.closeKeyBoard(androidDriver);
        passwordSignInButton.click();
        return this;
    }

    @Step("Получаем сообщение о некорректном пароле")
    public String getPasswordHint() {
        return passwordHint.getText();
    }

    public HomePage authorization(String login, String password) {
        setLogin(login);
        setPassword(password);
        return new HomePage(androidDriver);
    }
}
