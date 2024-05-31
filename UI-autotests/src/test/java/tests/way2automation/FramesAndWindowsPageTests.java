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

import static io.qameta.allure.SeverityLevel.NORMAL;

@Epic(value = "Страница с фреймами и окнами")
public class FramesAndWindowsPageTests extends BaseTest {

    @Test
    @Feature(value = "Открывание новых вкладок")
    @Story(value = "Открывание новых вкладок в браузере со сменой фокуса на каждую открытую вкладку")
    @Owner(value = "Ruslan Bikineev")
    @Severity(NORMAL)
    public void openFramesAndWindowsPageTest() {
        FramesAndWindowsPage framesAndWindowsPage = new FramesAndWindowsPage(getDriver());
        framesAndWindowsPage.openFramesAndWindowsPage();
        String originalWindowHandle = getDriver().getWindowHandle();
        NewBrowserTabPage newBrowserTabPage1 = framesAndWindowsPage.clickNewBrowserTab();
        String newBrowserTabPage1WindowHandle = getNewWindowHandle(originalWindowHandle);
        Assert.assertFalse(newBrowserTabPage1WindowHandle.isBlank(), "Новая вкладка не открылась");
        getDriver().switchTo().window(newBrowserTabPage1WindowHandle);
        NewBrowserTabPage newBrowserTabPage2 = newBrowserTabPage1.clickNewBrowserTab();
        String newBrowserTabPage2WindowHandle =
                getNewWindowHandle(originalWindowHandle, newBrowserTabPage1WindowHandle);
        getDriver().switchTo().window(newBrowserTabPage2WindowHandle);
        Assert.assertFalse(newBrowserTabPage2WindowHandle.isBlank(), "Новая вкладка не открылась");
        Assert.assertEquals(getDriver().getWindowHandles().size(), 3,
                "Неверное количество открытых вкладок");
    }
}
