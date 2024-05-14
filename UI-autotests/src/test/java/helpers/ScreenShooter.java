package helpers;

import io.qameta.allure.Attachment;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import tests.BaseTest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ScreenShooter {
    @Attachment(value = "Screenshot", fileExtension = ".png")
    public byte[] getScreenshotByte() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            Screenshot screenshot =
                    new AShot().shootingStrategy(ShootingStrategies.simple()).takeScreenshot(BaseTest.getDriver());
            BufferedImage bufferedImage = screenshot.getImage();
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }
}