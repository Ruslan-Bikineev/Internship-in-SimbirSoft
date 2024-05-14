package helpers;

import io.qameta.allure.Attachment;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import tests.BaseTest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ScreenShooter {

    public void takeScreenShot() {
        try {
            Screenshot originalFileInTempFolder = shot();
            BufferedImage bufferedImage = originalFileInTempFolder.getImage();
            File file = new File(System.currentTimeMillis() + ".png");
            ImageIO.write(bufferedImage, "PNG", file);
            getBytes(file.getAbsolutePath());
            file.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Attachment(value = "Screenshot", fileExtension = ".png")
    public static byte[] getBytes(String path) throws IOException {
        return Files.readAllBytes(Paths.get(path));
    }

    private Screenshot shot() {
        return new AShot().shootingStrategy(ShootingStrategies.simple()).takeScreenshot(BaseTest.getDriver());
    }
}