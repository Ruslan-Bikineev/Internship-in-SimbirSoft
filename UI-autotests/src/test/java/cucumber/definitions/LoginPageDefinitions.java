package cucumber.definitions;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import pages.way2automation.HomePage;
import pages.way2automation.LoginPage;

import java.time.Duration;

public class LoginPageDefinitions {
    WebDriver driver;
    LoginPage loginPage;

    @Given("Открываем сайт авторизации")
    public void openLoginPage() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        loginPage = new HomePage(driver)
                .openHomePage()
                .clickMemberLogin();
    }

    @When("Авторизуемся, логин {string} и пароль {string}")
    public void loginAs(String login, String password) {
        loginPage.loginAs(login, password);
    }

    @Then("Проверка, что ничего не происходит и поля остаются пустыми")
    public void checkLogin() {
        Assert.assertEquals("", loginPage.getValueOfEmailField(),
                "Введенный логин не совпадает с логином в поле ввода");
        Assert.assertEquals("", loginPage.getValuePasswordField(),
                "Введенный пароль не совпадает с паролем в поле ввода");
    }

    @After
    public void closeBrowser() {
        driver.close();
    }

}
