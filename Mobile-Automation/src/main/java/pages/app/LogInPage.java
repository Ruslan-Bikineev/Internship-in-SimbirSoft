package pages.app;

import helpers.DriverFunctional;
import helpers.Waiters;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
@AllArgsConstructor
public class LogInPage {
    private AppiumDriver appiumDriver;
    @FindBy(xpath = "//android.widget.EditText")
    private WebElement loginField;
    @FindBy(xpath = "//*[@text=\"Войти\"]")
    private WebElement signInButton;
    @FindBy(xpath = "//android.widget.EditText")
    private WebElement passwordField;
    @FindBy(xpath = "//*[@text=\"Продолжить\"]")
    private WebElement passwordSignInButton;
    @FindBy(xpath = "//*[@text=\"Неверный пароль\"]")
    private WebElement passwordHint;

    public LogInPage(AppiumDriver appiumDriver) {
        this.appiumDriver = appiumDriver;
        PageFactory.initElements(appiumDriver, this);
    }

    @Step("Заполняем поле логин: {login}")
    public LogInPage setLogin(String login) {
        Waiters.elementToBeDisplayed(new WebDriverWait(appiumDriver, 10), loginField);
        loginField.sendKeys(login);
        signInButton.click();
        return this;
    }

    @Step("Заполняем поле пароль: {password}")
    public LogInPage setPassword(String password) {
        Waiters.elementToBeDisplayed(new WebDriverWait(appiumDriver, 10), passwordField);
        passwordField.sendKeys(password);
        DriverFunctional.closeKeyBoard(appiumDriver);
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
        return new HomePage(appiumDriver);
    }
}
