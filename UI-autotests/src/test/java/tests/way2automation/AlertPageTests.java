package tests.way2automation;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.way2automation.AlertPage;
import tests.BaseTest;

import static data.TestsData.IT_IS_A_CUSTOM_TEXT;
import static io.qameta.allure.SeverityLevel.NORMAL;

@Epic(value = "Страница с alert боксом")
public class AlertPageTests extends BaseTest {
    @Test
    @Feature(value = "Alert бокс")
    @Story(value = "Ввод кастомного текста в alert бокс и проверка его содержимого")
    @Owner(value = "Ruslan Bikineev")
    @Severity(NORMAL)
    public void testInputAlertBox() {
        AlertPage alertPage = new AlertPage(getDriver());
        alertPage.openAlertPage()
                .submitInputAlert()
                .sudmitDemonstrateInputBox();
        Assert.assertTrue(alertPage.getAlertText().contains(IT_IS_A_CUSTOM_TEXT));
    }
}
