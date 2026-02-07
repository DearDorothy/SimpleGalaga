package org.example;

import org.example.controller.PlayerController;
import org.example.model.OwnerObject;
import org.example.model.field.Field;
import org.example.model.field.Point;
import org.example.model.field.Ship;
import org.example.model.manager.Player;
import org.example.view.ShipPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
//            Field field = new Field();
//
//            Point point = new Point(150, 500);
//            List<Ship> fleetShip = new ArrayList<>(Arrays.asList(new Ship(point, OwnerObject.PLAYER, 5)));
//
//            Player player = new Player(fleetShip);
//            PlayerController playerController = new PlayerController(player, field);
//
//            ShipPanel shipPanel = new ShipPanel(playerController, player.getActiveShip(), 35);
//
//            JFrame jFrame = new JFrame("SimpleGalaga");
//            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            jFrame.getContentPane().setPreferredSize(new Dimension(field.getWidth(), field.getHeight()));
//            jFrame.pack();
//            jFrame.setLocation(600, 130);
//            jFrame.add(shipPanel);
//            jFrame.addKeyListener(playerController);
//            jFrame.setVisible(true);
//            jFrame.setFocusable(true);
//            jFrame.requestFocusInWindow();
        });
    }
}