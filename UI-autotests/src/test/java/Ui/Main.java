package Ui;

import PageObjects.HomePage;
import PageObjects.LoginPage;
import PageObjects.UserHomePage;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class Main {


    public static void main(String[] args) throws InterruptedException {
        ChromeDriver chromeDriver = new ChromeDriver();
        chromeDriver.manage().window().maximize();
        chromeDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        chromeDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));


        HomePage homePage = new HomePage(chromeDriver);
        homePage.openHomePage();
        Thread.sleep(1000);
        LoginPage loginPage = homePage.clickMemberLogin();
        Thread.sleep(2000);
        UserHomePage userHomePage = loginPage.loginAs(TestProperties.email, TestProperties.password);
        Thread.sleep(2000);

        chromeDriver.quit();
    }

}
