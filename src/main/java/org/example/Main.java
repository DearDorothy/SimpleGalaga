package org.example;

import org.example.controller.PlayerController;
import org.example.model.Game;
import org.example.model.field.Field;
import org.example.model.field.Point;
import org.example.model.manager.EnemyCommander;
import org.example.model.manager.Player;
import org.example.view.widget.ShipWidget;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            Player player = new Player();
            EnemyCommander enemyCommander = new EnemyCommander();
            Field field = new Field();

            Game game = new Game(player, enemyCommander, field);
            game.getPlayer().getActiveShip().setPoint(new Point(150, 500));

            PlayerController playerController = new PlayerController(player, field);
            ShipWidget shipWidget = new ShipWidget(player.getActiveShip());

            JFrame jFrame = new JFrame("SimpleGalaga");
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            jFrame.getContentPane().setPreferredSize(new Dimension(field.getWidth(), field.getHeight()));
            jFrame.getContentPane().setBackground(Color.BLACK);

            jFrame.pack();
            jFrame.setLocation(600, 130);
            jFrame.add(shipWidget);
            jFrame.addKeyListener(playerController);
            jFrame.setVisible(true);
            jFrame.setFocusable(true);
            jFrame.requestFocusInWindow();
        });
    }
}