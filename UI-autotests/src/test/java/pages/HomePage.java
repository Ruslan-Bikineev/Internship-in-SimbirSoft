package pages;

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

    public void openHomePage() {
        driver.get(PageProperties.homePageUrl);
    }

    public HomePage clickAllCourses() {
        buttonAllCourses.click();
        return this;
    }

    public LoginPage clickMemberLogin() {
        buttonMemberLogin.click();
        return new LoginPage(driver);
    }

    public void clickPopupClose() {
        popupClose.click();
    }

    public String getSiteLogoImgWidth() {
        return siteLogoImg.getAttribute("width");
    }

    public String getSiteLogoImgHeight() {
        return siteLogoImg.getAttribute("height");
    }

    public String getSiteSupportEmailName() {
        return siteSupportEmailName.getText();
    }

    public String getSiteSupportSkypeName() {
        return siteSupportSkypeName.getText();
    }

    public String getSiteSupportUSANumber() {
        return siteSupportUSANumber.getText();
    }

    public String getSiteSupportIndiaNumber1() {
        return siteSupportIndiaNumber1.getText();
    }

    public String getSiteSupportIndiaNumber2() {
        return siteSupportIndiaNumber2.getText();
    }
}
