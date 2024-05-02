package framework;

import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ScreenShooter {

    @Attachment(value = "Screenshot", fileExtension = ".png")
    public static byte[] getBytes(String path) throws IOException {
        return Files.readAllBytes(Paths.get(path));
    }

    private Screenshot shot() {
        return new AShot().shootingStrategy(ShootingStrategies.simple()).takeScreenshot(MyWebDriver.getMyWebDriver());
    }

    private void copyFile(String path) throws IOException {
        Screenshot originalFileInTempFolder = shot();
        BufferedImage bufferedImage = originalFileInTempFolder.getImage();
        File outputFile = new File(System.currentTimeMillis() + ".png");
        ImageIO.write(bufferedImage, "PNG", outputFile);
        File screenInProperlyPath = new File(path);
        FileUtils.copyFile(outputFile, screenInProperlyPath);
        outputFile.deleteOnExit();
    }

    public void takeScreenShot(String name) {
        String path = System.getProperty("user.dir") + "/target/screenShots/" + name + ".png";
        try {
            copyFile(path);
            getBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}