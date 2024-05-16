package helpers;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Waiters {
    public static void waitUrlToBe(WebDriverWait wait, String url) {
        wait.until(ExpectedConditions.urlToBe(url));
    }
}
