package org.example.view.widget;

import org.example.model.OwnerObject;
import org.example.model.field.Bullet;
import org.example.view.utilsView.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BulletWidget extends FieldObjectWidget {

    private Bullet bullet;

    public BulletWidget(Bullet bullet) {
        super(bullet);
        this.bullet = bullet;
    }

    @Override
    protected BufferedImage getImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getImageFile());
            image = ImageUtils.resizeImage(image, bullet.getSizeCollisionModel(), bullet.getSizeCollisionModel());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    protected File getImageFile() {
        File file = null;
        if (bullet.getOwnerObject() == OwnerObject.PLAYER) {
            file = new File(ImageUtils.IMAGE_PATH + "bulletPlayer.png");
        }
        if (bullet.getOwnerObject() == OwnerObject.ENEMY) {
            file = new File(ImageUtils.IMAGE_PATH + "bulletEnemy.png");
        }
        return file;
    }
}
