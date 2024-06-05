package pages.httpwatch;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

public class AuthenticationPage {
    public static final String AUTHENTICATION_PAGE_URL = "https://www.httpwatch.com/httpgallery/authentication/#showExample10";
    private final WebDriver driver;
    @FindBy(id = "displayImage")
    WebElement displayImageButton;

    public AuthenticationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Step("Открываем страницу: " + AUTHENTICATION_PAGE_URL)
    public AuthenticationPage openAuthenticationPage() {
        driver.get(AUTHENTICATION_PAGE_URL);
        return this;
    }

    @Step("Отправляем http запрос для базовой авторизации, логин: {login}, пароль: {password}")
    public int sendHttpRequestForBasicAuth(String login, String password) {
        int statusCode;
        String encodedCredentials = Base64.getEncoder().encodeToString((login + ":" + password).getBytes());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.httpwatch.com"))
                .header("Authorization", "Basic " + encodedCredentials)
                .build();
        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            statusCode = response.statusCode();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return statusCode;
    }

    @Step("Нажимаем на кнопку 'Display Image'")
    public AuthenticationPage clickDisplayImageButton() {
        displayImageButton.click();
        return this;
    }
}
