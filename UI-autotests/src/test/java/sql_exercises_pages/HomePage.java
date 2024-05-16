package sql_exercises_pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    private final WebDriver driver;
    @FindBy(xpath = "//form[@name=\"frmguest\"]")
    private WebElement enterWithoutLoginButton;
    @FindBy(xpath = "//*[@href='/logout.php']/..")
    private WebElement dateAndGuestLabel;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Step("Открытие главной страницы: " + PageProperties.HOME_PAGE_URL)
    public HomePage openHomePage() {
        driver.get(PageProperties.HOME_PAGE_URL);
        return this;
    }

    @Step("Авторизация пользователем гость (без ввода данных)")
    public HomePage enterWithoutLogin() {
        enterWithoutLoginButton.click();
        return this;
    }

    @Step("Получение ника пользователя в качестве гостя")
    public String getGuestLabel() {
        String[] labelArray = dateAndGuestLabel.getText().split(" ");
        return labelArray[labelArray.length - 1];
    }
}