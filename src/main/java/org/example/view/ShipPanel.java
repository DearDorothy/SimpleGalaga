package org.example.view;

import org.example.model.event.ShipActionEvent;
import org.example.model.event.ShipActionListener;

import javax.swing.*;
import java.awt.*;

public class ShipPanel extends JPanel implements ShipActionListener {

    private Image imageShipPlayer;




    private void loadImage() {
        ImageIcon imageIconShipPlayer = new ImageIcon("src/main/resources/images/player.png");
        imageShipPlayer = imageIconShipPlayer.getImage();
    }

    @Override
    public void shipIsMoved(ShipActionEvent event) {

    }

    @Override
    public void shipIsFire(ShipActionEvent event) {

    }
}
