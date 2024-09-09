package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.app.LogInPage;

import static data.TestsData.VALID_LOGIN;
import static data.TestsData.VALID_PASSWORD;
import static data.TestsData.YANDEX_PLUS_CONDITIONS_LINK;

public class YandexPlusTests extends BaseTest {
    @Test
    public void openYandexPlusConditionsTest() {
        new LogInPage(getAndroidDriver())
                .authorization(VALID_LOGIN, VALID_PASSWORD)
                .closeWidget()
                .skipCookieFiles()
                .moveToYandexPlusPage()
                .moveToConnectPlus()
                .openYandexPlusConditions();
        System.out.println(getAndroidDriver().currentActivity());
        Assert.assertEquals(getAndroidDriver().getCurrentUrl(), YANDEX_PLUS_CONDITIONS_LINK);
    }
}
