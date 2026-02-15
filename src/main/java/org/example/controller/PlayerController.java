package org.example.controller;

import org.example.model.ActionPilot;
import org.example.model.DirectionObjectMovment;
import org.example.model.field.Field;
import org.example.model.manager.Player;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.EventListener;
import javax.swing.Timer;

public class PlayerController extends KeyAdapter {

    private Player player;
    private Field field;
    private boolean leftButtonPressed = false;
    private boolean rightButtonPressed = false;
    private boolean spaceButtonPressed = false;
    private boolean canFire = true;

    private Timer moveTimer;
    private Timer fireTimer;

    public PlayerController(Player player, Field field) {
        this.player = player;
        this.field = field;

        startTimers();
    }

    private void startTimers() {
        // Таймер движений
        moveTimer = new Timer(25, e -> hudleInput());
        moveTimer.start();

        // Таймер стрельбы
        fireTimer = new Timer(300, e -> {
            canFire = true;
            fireTimer.stop();
        });
        fireTimer.setRepeats(false);
    }

    public void stopTimers() {
        if (moveTimer != null) moveTimer.stop();
        if (fireTimer != null) fireTimer.stop();
    }

    public void hudleInput() {

        int sizeShipWidget = player.getActiveShip().getSizeCollisionModel();

        if (leftButtonPressed && player.getActiveShip().getPoint().getX() > 0)
            player.shipControl(ActionPilot.MOVE, DirectionObjectMovment.LEFT);

        if (rightButtonPressed && player.getActiveShip().getPoint().getX() < (field.getWidth() - sizeShipWidget))
            player.shipControl(ActionPilot.MOVE, DirectionObjectMovment.RIGHT);

        if (spaceButtonPressed && canFire) {
            player.shipControl(ActionPilot.FIRE, DirectionObjectMovment.UP);
            canFire = false;
            fireTimer.start();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> leftButtonPressed = true;
            case KeyEvent.VK_RIGHT -> rightButtonPressed = true;
            case KeyEvent.VK_SPACE -> spaceButtonPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> leftButtonPressed = false;
            case KeyEvent.VK_RIGHT -> rightButtonPressed = false;
            case KeyEvent.VK_SPACE -> spaceButtonPressed = false;
        }
    }
}
