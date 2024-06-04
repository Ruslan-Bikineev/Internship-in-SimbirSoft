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

import static helpers.Helper.getNewWindowHandle;
import static helpers.Helper.switchToWindow;
import static io.qameta.allure.SeverityLevel.NORMAL;

@Epic(value = "Страница с фреймами и окнами")
public class FramesAndWindowsPageTests extends BaseTest {

    @Test
    @Feature(value = "Открывание новых вкладок")
    @Story(value = "Открывание 2-х новых вкладок в браузере со сменой фокуса на каждую открытую вкладку")
    @Owner(value = "Ruslan Bikineev")
    @Severity(NORMAL)
    public void openFramesAndWindowsPageTest() {
        FramesAndWindowsPage framesAndWindowsPage = new FramesAndWindowsPage(getDriver());
        framesAndWindowsPage.openFramesAndWindowsPage();
        String originalWindowHandle = getDriver().getWindowHandle();
        NewBrowserTabPage newBrowserTabPage1 = framesAndWindowsPage.clickNewBrowserTab();
        String newBrowserTabPage1WindowHandle = getNewWindowHandle(getDriver(), originalWindowHandle);
        Assert.assertFalse(newBrowserTabPage1WindowHandle.isBlank(), "Дескриптор первой вкладки пустой");
        switchToWindow(getDriver(), newBrowserTabPage1WindowHandle);
        NewBrowserTabPage newBrowserTabPage2 = newBrowserTabPage1.clickNewBrowserTab();
        String newBrowserTabPage2WindowHandle =
                getNewWindowHandle(getDriver(), originalWindowHandle, newBrowserTabPage1WindowHandle);
        switchToWindow(getDriver(), newBrowserTabPage2WindowHandle);
        Assert.assertFalse(newBrowserTabPage2WindowHandle.isBlank(), "Дескриптор второй вкладки пустой");
        Assert.assertEquals(getDriver().getWindowHandles().size(), 3,
                "Неверное количество открытых вкладок");
    }
}
