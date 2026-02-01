package org.example.view;

import org.example.controller.PlayerController;
import org.example.model.event.ShipActionEvent;
import org.example.model.event.ShipActionListener;
import org.example.model.field.Ship;

import javax.swing.*;
import java.awt.*;

public class ShipPanel extends JPanel implements ShipActionListener {

    private Ship ship;
    private Image imageShipPlayer;
    private int size;
    private PlayerController playerController;

    public ShipPanel(PlayerController playerController, Ship ship, int size) {
        this.playerController = playerController;
        this.ship = ship;
        this.size = size;
        setBackground(Color.black);
        loadImage();
        ship.addShipActionListener(this);

        Timer timer = new Timer(25, e -> {
            playerController.hudleInput(size);
            repaint();
        });
        timer.start();
    }

    private void loadImage() {
        ImageIcon imageIconShipPlayer = new ImageIcon("src/main/resources/images/playerShip.png");
        imageShipPlayer = imageIconShipPlayer.getImage();
    }

    @Override
    public void shipIsMoved(ShipActionEvent event) {
        if(event.getShip() == ship)
            repaint();
    }

    @Override
    public void shipIsFire(ShipActionEvent event) {}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.drawImage(imageShipPlayer,
                ship.getPoint().getX(),
                ship.getPoint().getY(),
                size, size, this
                );
    }
}
