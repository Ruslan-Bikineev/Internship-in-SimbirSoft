package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    private final WebDriver driver;
    private final String url = "https://www.way2automation.com/";
    private final By popup = new By.ByCssSelector(".elementor-location-popup");
    private final By popupClose = new By.ByCssSelector(".dialog-close-button");
    private final By buttonAllCourses = new By.ByCssSelector("#menu-item-27580");
    private final By buttonMemberLogin = new By.ByCssSelector("#menu-item-27625 .menu-link");
    private final By siteLogoImg = new By.ByCssSelector("#MzYwOjY4NA\\=\\=-1");
    private final By siteSupportEmailName = new By.ByXPath(
            "//*[@class=\"elementor-icon-list-item elementor-inline-item\"][4]");
    private final By siteSupportSkypeName = new By.ByXPath(
            "//*[@class=\"elementor-icon-list-item elementor-inline-item\"][3]");
    private final By siteSupportUSANumber = new By.ByXPath(
            "//*[@class=\"elementor-icon-list-item elementor-inline-item\"][2]");
    private final By siteSupportIndiaNumber1 = new By.ByXPath(
            "//*[@class=\"elementor-icon-list-item elementor-inline-item\"][1]");
    private final By siteSupportIndiaNumber2 = new By.ByXPath(
            "//*[@class=\"elementor-icon-list-item elementor-inline-item lazyloaded\"]");


    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void openHomePage() {
        driver.get(url);
    }

    public HomePage clickAllCourses() {
        driver.findElement(buttonAllCourses).click();
        return this;
    }

    public LoginPage clickMemberLogin() {
        driver.findElement(buttonMemberLogin).click();
        return new LoginPage(driver);
    }

    public void clickPopupClose() {
        driver.findElement(popupClose).click();
    }

    public String getSiteLogoImgWidth() {
        return driver.findElement(siteLogoImg).getAttribute("width");
    }

    public String getSiteLogoImgHeight() {
        return driver.findElement(siteLogoImg).getAttribute("height");
    }

    public String getSiteSupportEmailName() {
        return driver.findElement(siteSupportEmailName).getText();
    }

    public String getSiteSupportSkypeName() {
        return driver.findElement(siteSupportSkypeName).getText();
    }

    public String getSiteSupportUSANumber() {
        return driver.findElement(siteSupportUSANumber).getText();
    }

    public String getSiteSupportIndiaNumber1() {
        return driver.findElement(siteSupportIndiaNumber1).getText();
    }

    public String getSiteSupportIndiaNumber2() {
        return driver.findElement(siteSupportIndiaNumber2).getText();
    }

}
