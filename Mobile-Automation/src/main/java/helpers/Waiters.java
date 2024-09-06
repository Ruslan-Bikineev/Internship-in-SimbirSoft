package helpers;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;

public class Waiters {
    public static void elementToBeDisplayed(Wait wait, WebElement element) {
        wait.until(x -> element.isDisplayed());
    }
}