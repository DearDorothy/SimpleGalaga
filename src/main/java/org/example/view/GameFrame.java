package org.example.view;

import org.example.controller.PlayerController;
import org.example.model.field.Field;

import javax.swing.*;
import java.awt.*;

public class GameFrame {

    public static JFrame gameFrame(Field field, PlayerController playerController, FieldWidget fieldWidget) {
        JFrame jFrame = new JFrame("SimpleGalaga");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.getContentPane().setPreferredSize(new Dimension(field.getWidth(), field.getHeight()));
        jFrame.pack();
        jFrame.setLocation(600, 130);
        jFrame.add(fieldWidget);
        jFrame.addKeyListener(playerController);
        jFrame.setVisible(true);
        jFrame.setFocusable(true);
        jFrame.requestFocusInWindow();
        return jFrame;
    }
}
