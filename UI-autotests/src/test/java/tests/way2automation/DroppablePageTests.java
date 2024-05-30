package tests.way2automation;

import data.TestsData;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.way2automation.DroppablePage;
import tests.BaseTest;

import static io.qameta.allure.SeverityLevel.NORMAL;

@Epic(value = "Страница для перетаскивания элемента")
public class DroppablePageTests extends BaseTest {

    @Test
    @Feature(value = "Перетаскивание элемента")
    @Story(value = "Изменение текста принимающего элемента при перетаскивании элемента в принимающую область")
    @Owner(value = "Ruslan Bikineev")
    @Severity(NORMAL)
    public void testDragAndDropExample1() {
        DroppablePage droppablePage = new DroppablePage(getDriver());
        droppablePage.openDroppablePage();
        Assert.assertEquals(droppablePage.getTextDroppableExample1(),
                TestsData.DROP_HERE_TEXT, "Текст не соответствует");
        droppablePage.dragAndDropExample1();
        Assert.assertEquals(droppablePage.getTextDroppableExample1(),
                TestsData.DROPPED_TEXT, "Текст не соответствует");
    }
}
