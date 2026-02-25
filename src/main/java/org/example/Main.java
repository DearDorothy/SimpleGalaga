package org.example;

import org.example.view.GameWindow;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameWindow::new);
    }
}