package org.example.view.widget;

import org.example.model.OwnerObject;
import org.example.model.event.ShipMoveEvent;
import org.example.model.event.ShipMoveListener;
import org.example.model.field.Ship;
import org.example.view.utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ShipWidget extends FieldObjectWidget {

    private Ship ship;

    public ShipWidget(Ship ship) {
        super(ship);
        this.ship = ship;
        ship.addShipMoveListener(new ShipMoveObserver());
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

    private class ShipMoveObserver implements ShipMoveListener {
        @Override
        public void shipIsMoved(ShipMoveEvent event) {
            if(event.getShip() == ship)
                repaint();
        }
    }
}
