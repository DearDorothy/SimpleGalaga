package org.example;

import org.example.controller.PlayerController;
import org.example.model.Game;
import org.example.model.field.Field;
import org.example.view.FieldWidget;
import org.example.view.GameFrame;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            Game game = new Game();
            PlayerController playerController = new PlayerController(game.getPlayer(), game.getField().getWidth());
            Field field = game.getField();
            FieldWidget fieldWidget = new FieldWidget(field);

            game.start();

            JFrame jFrame = GameFrame.gameFrame(field, playerController, fieldWidget);
        });
    }
}