package pages;

import helpers.DriverFunctional;
import io.appium.java_client.android.AndroidDriver;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
@AllArgsConstructor
public class LogInPage {
    private AndroidDriver driver;
    @FindBy(xpath = "//android.widget.EditText[@resource-id=\"passp-field-login\"]")
    private WebElement loginField;
    @FindBy(xpath = "//android.widget.Button[@resource-id=\"passp:sign-in\"]")
    private WebElement signInButton;
    @FindBy(xpath = "//android.widget.EditText[@resource-id=\"passp-field-passwd\"]")
    private WebElement passwordField;
    @FindBy(xpath = "//android.widget.Button[@resource-id=\"passp:sign-in\"]")
    private WebElement passwordSignInButton;
    @FindBy(xpath = "//android.widget.TextView[@resource-id=\"field:input-passwd:hint\"]")
    private WebElement passwordHint;

    public LogInPage(AndroidDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public LogInPage setLogin(String login) {
        loginField.sendKeys(login);
        signInButton.click();
        return this;
    }

    public LogInPage setPassword(String password) {
        passwordField.sendKeys(password);
        DriverFunctional.closeKeyBoard(driver);
        passwordSignInButton.click();
        return this;
    }

    public String getPasswordHint() {
        return passwordHint.getText();
    }

    public LogInPage authorization(String login, String password) {
        setLogin(login);
        setPassword(password);
        return this;
    }
}
