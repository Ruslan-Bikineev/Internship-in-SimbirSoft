package framework;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class Waiters {
    private final WebDriverWait wait;

    public Waiters(WebDriverWait wait) {
        this.wait = wait;
    }

    public void waitUrlToBe(String url) {
        wait.until(ExpectedConditions.urlToBe(url));
    }
}
