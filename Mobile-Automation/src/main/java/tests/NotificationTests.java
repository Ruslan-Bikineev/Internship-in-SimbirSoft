package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.app.LogInPage;
import pages.app.NotificationPage;

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
        NotificationPage notificationPage = new LogInPage(getAppiumDriver())
                .authorization(VALID_LOGIN, VALID_PASSWORD)
                .closeWidget()
                .skipCookieFiles()
                .moveToProfilePage()
                .swipeDownSlideIndicatorView()
                .moveToSettingsPage()
                .moveToNotificationPage()
                .moveToPhoneNotificationPage()
                .enableAllNotifications()
                .moveToNotificationPage();
        Assert.assertTrue(notificationPage.isPromotionsAndSalesSwitcherSelected());
    }
}


