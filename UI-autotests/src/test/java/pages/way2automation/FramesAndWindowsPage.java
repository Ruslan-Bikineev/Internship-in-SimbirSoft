package pages.way2automation;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FramesAndWindowsPage {
    public static final String FRAMES_AND_WINDOWS_PAGE_URL = "https://way2automation.com/way2auto_jquery/frames-and-windows.php#load_box";
    private final WebDriver driver;
    @FindBy(css = "#example-1-tab-1 iframe")
    WebElement iframeExample1;
    @FindBy(css = ".farme_window a")
    WebElement linkNewBrowserTab;

    public FramesAndWindowsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Step("Открываем страницу: " + FRAMES_AND_WINDOWS_PAGE_URL)
    public FramesAndWindowsPage openFramesAndWindowsPage() {
        driver.get(FRAMES_AND_WINDOWS_PAGE_URL);
        return this;
    }

    @Step("Открываем новую вкладку New Browser Tab Example 1")
    public NewBrowserTabPage clickNewBrowserTab() {
        driver.switchTo().frame(iframeExample1);
        linkNewBrowserTab.click();
        driver.switchTo().defaultContent();
        return new NewBrowserTabPage(driver);
    }
}
