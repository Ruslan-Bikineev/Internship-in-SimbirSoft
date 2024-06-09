package pages.httpwatch;

import io.qameta.allure.Step;
import org.openqa.selenium.Credentials;
import org.openqa.selenium.HasAuthentication;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.net.URI;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class AuthenticationPage {
    public static final String AUTHENTICATION_PAGE_URL = "https://www.httpwatch.com/httpgallery/authentication/#showExample10";
    private final WebDriver driver;
    @FindBy(id = "displayImage")
    WebElement displayImageButton;
    @FindBy(id = "downloadImg")
    WebElement downloadImageAfterBasicAuth;

    public AuthenticationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Step("Открываем страницу: " + AUTHENTICATION_PAGE_URL)
    public AuthenticationPage openAuthenticationPage() {
        driver.get(AUTHENTICATION_PAGE_URL);
        return this;
    }

    @Step("Заполняем логин: {login} и пароль: {password} для базовой авторизации")
    public AuthenticationPage setLoginAndPasswordForBasicAuth(String login, String password) {
        Predicate<URI> uriPredicate = uri -> uri.toString().contains("httpwatch.com");
        Supplier<Credentials> authentication = UsernameAndPassword.of(login, password);
        ((HasAuthentication) driver).register(uriPredicate, authentication);
        return this;
    }

    @Step("Нажимаем на кнопку 'Display Image'")
    public AuthenticationPage clickDisplayImageButton() {
        displayImageButton.click();
        return this;
    }

    @Step("Получаем ссылку на картинку после базовой авторизации")
    public String getDownloadImageAfterBasicAuth() {
        return downloadImageAfterBasicAuth.getAttribute("src");
    }
}
