package org.example.controller;

import org.example.model.ActionPilot;
import org.example.model.DirectionObjectMovment;
import org.example.model.field.Field;
import org.example.model.manager.Player;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PlayerController extends KeyAdapter {

    private Player player;
    private Field field;
    private boolean leftButtonPressed = false;
    private boolean rightButtonPressed = false;
    private boolean spaceButtonPressed = false;

    public PlayerController(Player player, Field field) {
        this.player = player;
        this.field = field;
    }

    public void hudleInput(int sizeShipWidget) {
        if (leftButtonPressed && player.getActiveShip().getPoint().getX() > 0)
            player.shipControl(ActionPilot.MOVE, DirectionObjectMovment.LEFT);
        if (rightButtonPressed && player.getActiveShip().getPoint().getX() < (field.getWidth() - sizeShipWidget))
            player.shipControl(ActionPilot.MOVE, DirectionObjectMovment.RIGHT);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> leftButtonPressed = true;
            case KeyEvent.VK_RIGHT -> rightButtonPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> leftButtonPressed = false;
            case KeyEvent.VK_RIGHT -> rightButtonPressed = false;
        }
    }
}
