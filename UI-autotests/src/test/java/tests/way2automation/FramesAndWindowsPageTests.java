package tests.way2automation;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.way2automation.FramesAndWindowsPage;
import pages.way2automation.NewBrowserTabPage;
import tests.BaseTest;

import java.util.Optional;

import static io.qameta.allure.SeverityLevel.NORMAL;

@Epic(value = "Страница для действий над фреймами и окнами")
public class FramesAndWindowsPageTests extends BaseTest {

    @Test
    @Feature(value = "Открытие новых вкладок")
    @Story(value = "Открытие новых вкладок в браузере")
    @Owner(value = "Ruslan Bikineev")
    @Severity(NORMAL)
    public void openFramesAndWindowsPageTest() {
        FramesAndWindowsPage framesAndWindowsPage = new FramesAndWindowsPage(getDriver());
        framesAndWindowsPage.openFramesAndWindowsPage();
        String originalWindowHandle = getDriver().getWindowHandle();
        NewBrowserTabPage newBrowserTabPage1 = framesAndWindowsPage.clickNewBrowserTab();
        Optional<String> newBrowserTabPage1WindowHandle = getNewWindowHandle(originalWindowHandle);
        newBrowserTabPage1WindowHandle.ifPresent(h -> getDriver().switchTo().window(h));
        NewBrowserTabPage newBrowserTabPage2 = newBrowserTabPage1.clickNewBrowserTab();
        Optional<String> newBrowserTabPage2WindowHandle = getNewWindowHandle(originalWindowHandle,
                newBrowserTabPage1WindowHandle.orElse(null));
        Assert.assertTrue(newBrowserTabPage1WindowHandle.isPresent());
        Assert.assertTrue(newBrowserTabPage2WindowHandle.isPresent());
        Assert.assertEquals(getDriver().getWindowHandles().size(), 3);
    }
}
