package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;
import pages.LogInPage;
import pages.NotificationPage;
import pages.ProfilePage;

import java.util.Map;

import static data.TestsData.VALID_LOGIN;
import static data.TestsData.VALID_PASSWORD;
import static io.qameta.allure.SeverityLevel.NORMAL;

@Epic(value = "Уведомления приложения")
public class NotificationTests extends BaseTest {
    @Test
    @Feature(value = "Уведомления")
    @Story(value = "Включение всех уведомлений")
    @Owner(value = "Ruslan Bikineev")
    @Severity(NORMAL)
    public void enableAllNotifications() {
        ProfilePage profilePage = new LogInPage(getDriver())
                .authorization(VALID_LOGIN, VALID_PASSWORD)
                .closeWidget()
                .skipCookieFiles()
                .moveToProfilePage();
        ((JavascriptExecutor) getDriver()).executeScript("mobile: swipeGesture", Map.of(
                "left", 100, "top", 100, "width", 200, "height", 200,
                "direction", "down",
                "percent", 0.75
        ));
        NotificationPage notificationPage = profilePage.moveToSettingsPage()
                .moveToNotificationPage()
                .moveToPhoneNotificationPage()
                .enableAllNotifications()
                .moveToNotificationPage();
//        Assert.assertTrue(notificationPage.isPromotionsAndSalesSwitcherSelected());
    }
}


