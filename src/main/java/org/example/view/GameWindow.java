package org.example.view;

import org.example.controller.PlayerController;
import org.example.model.Game;
import org.example.model.GameStatus;
import org.example.model.event.GameActionEvent;
import org.example.model.event.GameActionListener;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class GameWindow extends JFrame {
    private Game game;
    private PlayerController playerController;
    private FieldWidget fieldWidget;

    public GameWindow() {
        setTitle("Simple Galaga");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        createMenu();
        startNewGame();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Игра - Simple Galaga");
        gameMenu.add(new JMenuItem(new NewGameAction()));
        gameMenu.addSeparator();
        gameMenu.add(new JMenuItem(new ExitAction()));
        menuBar.add(gameMenu);
        setJMenuBar(menuBar);
    }

    private void startNewGame() {
        if (playerController != null) {
            playerController.stopTimers();
        }

        game = new Game();
        fieldWidget = new FieldWidget(game.getField());
        playerController = new PlayerController(game.getPlayer(), game.getField().getWidth());

        game.addGameActionListener(new GameActionObserver());

        getContentPane().removeAll();
        getContentPane().add(fieldWidget);
        pack();

        addKeyListener(playerController);
        setFocusable(true);
        requestFocusInWindow();

        game.start();
    }

    private class NewGameAction extends AbstractAction {
        public NewGameAction() {
            putValue(NAME, "Новая игра");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int result = JOptionPane.showConfirmDialog(GameWindow.this,
                    "Начать новую игру?", "Новая игра", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                startNewGame();
            }
        }
    }

    private static class ExitAction extends AbstractAction {
        public ExitAction() {
            putValue(NAME, "Выход");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private class GameActionObserver implements GameActionListener {
        @Override
        public void gameStatusChanged(GameActionEvent event) {
            GameStatus status = event.getStatus();
            if (status != GameStatus.RUNNING) {
                playerController.stopTimers();

                String message;
                switch (status) {
                    case PLAYER_WIN:
                        message = "Вы победили!";
                        break;
                    case ENEMY_WIN:
                        message = "Враг победил!";
                        break;
                    case DRAW:
                        message = "Ничья!";
                        break;
                    default:
                        message = "Игра завершена.";
                }

                int option = JOptionPane.showOptionDialog(GameWindow.this,
                        message + "\nХотите сыграть ещё?",
                        "Игра окончена",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        new String[]{"Новая игра", "Выход"},
                        "Новая игра");
                if (option == JOptionPane.YES_OPTION) {
                    startNewGame();
                } else {
                    System.exit(0);
                }
            }
        }
    }
}