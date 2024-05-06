package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    private final WebDriver driver;
    @FindBy(css = ".elementor-location-popup")
    private WebElement popup;
    @FindBy(css = ".dialog-close-button")
    private WebElement popupClose;
    @FindBy(css = "#ast-hf-menu-1 #menu-item-27580")
    private WebElement buttonAllCourses;
    @FindBy(css = "#ast-desktop-header .site-logo-img")
    private WebElement siteLogoImg;
    @FindBy(css = "#menu-item-27625 .menu-link")
    private WebElement buttonMemberLogin;
    @FindBy(xpath = "//*[@class=\"elementor-icon-list-item elementor-inline-item\"][4]")
    private WebElement siteSupportEmailName;
    @FindBy(xpath = "//*[@class=\"elementor-icon-list-item elementor-inline-item\"][3]")
    private WebElement siteSupportSkypeName;
    @FindBy(xpath = "//*[@class=\"elementor-icon-list-item elementor-inline-item\"][2]")
    private WebElement siteSupportUSANumber;
    @FindBy(xpath = "//*[@class=\"elementor-icon-list-item elementor-inline-item\"][1]")
    private WebElement siteSupportIndiaNumber1;
    @FindBy(xpath = "//*[@class=\"elementor-icon-list-item elementor-inline-item lazyloaded\"]")
    private WebElement siteSupportIndiaNumber2;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Step("Открытие главной страницы")
    public void openHomePage() {
        driver.get(PageProperties.homePageUrl);
    }

    @Step("Открытие вкладки «Все курсы»")
    public HomePage clickAllCourses() {
        buttonAllCourses.click();
        return this;
    }

    @Step("Переход на страницу «Вход в систему»")
    public LoginPage clickMemberLogin() {
        buttonMemberLogin.click();
        return new LoginPage(driver);
    }

    @Step("Закрытия всплывающего окна")
    public void clickPopupClose() {
        popupClose.click();
    }

    @Step("Получение ширины верхнего логотипа")
    public String getSiteLogoImgWidth() {
        return siteLogoImg.getAttribute("width");
    }

    @Step("Получение высоты верхнего логотипа")
    public String getSiteLogoImgHeight() {
        return siteLogoImg.getAttribute("height");
    }

    @Step("Получение email'a службы поддержки сайта")
    public String getSiteSupportEmailName() {
        return siteSupportEmailName.getText();
    }

    @Step("Получение Skype службы поддержки сайта")
    public String getSiteSupportSkypeName() {
        return siteSupportSkypeName.getText();
    }

    @Step("Получение номера USA службы поддержки сайта")
    public String getSiteSupportUSANumber() {
        return siteSupportUSANumber.getText();
    }

    @Step("Получение номера India 1 службы поддержки сайта")
    public String getSiteSupportIndiaNumber1() {
        return siteSupportIndiaNumber1.getText();
    }

    @Step("Получение номера India 2 службы поддержки сайта")
    public String getSiteSupportIndiaNumber2() {
        return siteSupportIndiaNumber2.getText();
    }
}
