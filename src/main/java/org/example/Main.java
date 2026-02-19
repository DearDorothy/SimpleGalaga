package org.example;

import org.example.controller.PlayerController;
import org.example.model.Game;
import org.example.model.field.Field;
import org.example.model.field.Point;
import org.example.model.field.Ship;
import org.example.model.manager.EnemyCommander;
import org.example.model.manager.EnemyPilot;
import org.example.model.manager.Player;
import org.example.view.FieldWidget;
import org.example.view.widget.ShipWidget;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            Player player = new Player();
            EnemyCommander enemyCommander = new EnemyCommander();
            Field field = new Field();
            Game game = new Game(player, enemyCommander, field);


            PlayerController playerController = new PlayerController(player, field);
            JFrame jFrame = new JFrame("SimpleGalaga");
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            FieldWidget fieldWidget = new FieldWidget(field);


            game.start();

            jFrame.getContentPane().setPreferredSize(new Dimension(field.getWidth(), field.getHeight()));
            //jFrame.getContentPane().setBackground(Color.BLACK);

            jFrame.pack();
            jFrame.setLocation(600, 130);
            jFrame.add(fieldWidget);
            jFrame.addKeyListener(playerController);
            jFrame.setVisible(true);
            jFrame.setFocusable(true);
            jFrame.requestFocusInWindow();
        });
    }
}