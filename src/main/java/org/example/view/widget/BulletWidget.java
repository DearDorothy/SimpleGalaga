package org.example.view.widget;

import org.example.model.OwnerObject;
import org.example.model.field.bullet.Bullet;
import org.example.model.field.bullet.NormalBullet;
import org.example.model.field.bullet.RicochetBullet;
import org.example.view.utilsView.ImageUtils;

import javax.imageio.ImageIO;
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
            if (bullet instanceof NormalBullet) {
                file = new File(ImageUtils.IMAGE_PATH + "bulletNormalPlayer.png");
            }
            if (bullet instanceof RicochetBullet) {
                file = new File(ImageUtils.IMAGE_PATH + "bulletRicochetPlayer.png");
            }
        }
        if (bullet.getOwnerObject() == OwnerObject.ENEMY) {
            file = new File(ImageUtils.IMAGE_PATH + "bulletEnemy.png");
        }
        return file;
    }
}
