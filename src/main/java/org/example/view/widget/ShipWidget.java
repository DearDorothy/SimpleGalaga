package org.example.view.widget;

import org.example.model.OwnerObject;
import org.example.model.field.Ship;
import org.example.view.utilsView.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ShipWidget extends FieldObjectWidget {

    private Ship ship;

    public ShipWidget(Ship ship) {
        super(ship);
        this.ship = ship;
    }

    @Override
    protected BufferedImage getImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getImageFile());
            image = ImageUtils.resizeImage(image, ship.getSizeCollisionModel(), ship.getSizeCollisionModel());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    protected File getImageFile() {
        File file = null;
        if(ship.getOwnerObject() == OwnerObject.PLAYER) {
            file = new File(ImageUtils.IMAGE_PATH + "playerShip.png");
        }
        if(ship.getOwnerObject() == OwnerObject.ENEMY) {
            file = new File(ImageUtils.IMAGE_PATH + "enemy1.png");
        }
        return file;
    }
}
