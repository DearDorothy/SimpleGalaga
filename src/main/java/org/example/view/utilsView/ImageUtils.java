package org.example.view.utilsView;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageUtils {

    public static BufferedImage resizeImage(BufferedImage img, int width, int height) {
        Image tmpImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D graphics2D =bufferedImage.createGraphics();
        graphics2D.drawImage(tmpImg, 0, 0, null);
        graphics2D.dispose();

        return bufferedImage;
    }

    public static final String IMAGE_PATH  = "src/main/resources/images/";

}
