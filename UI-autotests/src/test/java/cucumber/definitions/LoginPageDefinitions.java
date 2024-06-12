package cucumber.definitions;

import helpers.JavaScriptUtil;
import io.cucumber.java.After;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.То;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import pages.way2automation.HomePage;
import pages.way2automation.LoginPage;

import java.time.Duration;

public class LoginPageDefinitions {
    WebDriver driver;
    LoginPage loginPage;

    @Дано("Открываем сайт авторизации")
    public void openLoginPage() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        loginPage = new HomePage(driver)
                .openHomePage()
                .clickMemberLogin();
    }

    @Когда("Вводим логин {string} и пароль {string}")
    public void вводимЛогинЛогинИПарольПароль(String login, String password) {
        loginPage.typeUsername(login);
        loginPage.typePassword(password);
    }


    @То("Ничего не происходит и поля ввода остаются пустыми")
    public void ничегоНеПроисходитИПоляВводаОстаютсяПустыми() {
        Assert.assertEquals("", loginPage.getValueOfEmailField(),
                "Введенный логин не совпадает с логином в поле ввода");
        Assert.assertEquals("", loginPage.getValuePasswordField(),
                "Введенный пароль не совпадает с паролем в поле ввода");
    }

    @After
    public void closeBrowser() {
        driver.close();
    }

    @И("Убираем фокус с поля ввода пароля")
    public void убираемФокусСПоляВводаПароля() {
        JavaScriptUtil.removeFocusFromElement(driver, loginPage.getPasswordField());
    }

    @То("Под полем ввода логина отображается ошибка {string}")
    public void подПолемВводаЛогинаОтображаетсяОшибкаОшибкаЛогина(String expectedMessage) {
        Assert.assertEquals(loginPage.getEmailFieldError(), expectedMessage,
                "Ошибка под полем ввода логина не соответствует");
    }

    @То("Под полем ввода пароля отображается ошибка {string}")
    public void подПолемВводаПароляОтображаетсяОшибкаОшибкаПароля(String expectedMessage) {
        Assert.assertEquals(loginPage.getPasswordFieldError(), expectedMessage,
                "Ошибка под полем ввода пароля не соответствует");
    }

    @То("Нажимаем кнопку авторизации и авторизация не проходит")
    public void нажимаемКнопкуАвторизацииИАвторизацияНеПроходит() {
        boolean flag = false;
        try {
            loginPage.submitLogin();
        } catch (TimeoutException e) {
            flag = true;
        }
        Assert.assertTrue(flag);
    }

    @Когда("Авторизуемся, логин {string} и пароль {string}")
    public void авторизуемсяЛогинИПароль(String login, String password) {
        loginPage.loginAs(login, password);
    }

    @То("Переходим на домашню страницу пользователя {string}")
    public void переходимНаДомашнюСтраницуПользователя(String urlUserHomePage) {
        Assert.assertEquals(driver.getCurrentUrl(), urlUserHomePage);
    }

    @То("Вывод сообщения {string} красного цвета {string}")
    public void выводСообщенияКрасногоЦвета(String message, String messageColor) {
        Assert.assertEquals(loginPage.getInvalidLoginMessage(), message,
                "Вывод сообщения об ошибке авторизации не совпадает");
        Assert.assertEquals(loginPage.getInvalidLoginMessageColor(), messageColor,
                "Цвет сообщения об ошибке авторизации не совпадает");
    }
}
