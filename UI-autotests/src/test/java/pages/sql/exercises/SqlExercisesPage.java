package pages.sql.exercises;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SqlExercisesPage {
    public static final String HOME_PAGE_URL = "https://www.sql-ex.ru/";
    private final WebDriver driver;
    @FindBy(xpath = "//form[@name=\"frmguest\"]")
    private WebElement enterWithoutLoginButton;
    @FindBy(xpath = "//*[@href='/logout.php']/..")
    private WebElement dateAndGuestLabel;

    public SqlExercisesPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Step("Открытие главной страницы: " + HOME_PAGE_URL)
    public SqlExercisesPage openHomePage() {
        driver.get(HOME_PAGE_URL);
        return this;
    }

    @Step("Авторизация пользователем гость (без ввода данных)")
    public SqlExercisesPage enterWithoutLogin() {
        enterWithoutLoginButton.click();
        return this;
    }

    @Step("Получение ник пользователя в качестве гостя")
    public String getGuestLabel() {
        String[] labelArray = dateAndGuestLabel.getText().split(" ");
        return labelArray[labelArray.length - 1];
    }
}