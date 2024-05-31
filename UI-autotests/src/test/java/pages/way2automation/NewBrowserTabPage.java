package pages.way2automation;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NewBrowserTabPage {
    public static final String NEW_BROWSER_TAB_PAGE_URL = "https://way2automation.com/way2auto_jquery/frames-windows/defult1.html#";
    private final WebDriver driver;
    @FindBy(css = ".farme_window a")
    WebElement linkNewBrowserTab;

    public NewBrowserTabPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Step("Открываем новую вкладку New Browser Tab")
    public NewBrowserTabPage clickNewBrowserTab() {
        linkNewBrowserTab.click();
        return new NewBrowserTabPage(driver);
    }
}