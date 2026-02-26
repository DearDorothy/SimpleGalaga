package org.example.controller;

import org.example.model.ActionPilot;
import org.example.model.DirectionObjectMovment;
import org.example.model.field.bullet.ammo.NormalAmmo;
import org.example.model.field.Ship;
import org.example.model.field.bullet.ammo.RicochetAmmo;
import org.example.model.manager.Player;
import org.example.utils.Delays;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;

public class PlayerController extends KeyAdapter {

    private Player player;
    private int fieldWidth;
    private boolean leftButtonPressed = false;
    private boolean rightButtonPressed = false;
    private boolean spaceButtonPressed = false;
    private boolean zButtonPressed = false;
    private boolean xButtonPressed = false;
    private boolean canFire = true;

    private Timer moveTimer;
    private Timer fireTimer;

    public PlayerController(Player player, int fieldWidth) {
        this.player = player;
        this.fieldWidth = fieldWidth;

        startTimers();
    }

    private void startTimers() {
        // Таймер движений
        moveTimer = new Timer(Delays.MOVE_DELAY, e -> hudleInput());
        moveTimer.start();

        // Таймер стрельбы
        fireTimer = new Timer(Delays.FIRE_DELAY, e -> {
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

        Ship activeShip = player.getActiveShip();

        if (activeShip == null) {
            return;
        }

        int sizeShipWidget = player.getActiveShip().getSizeCollisionModel();

        if (leftButtonPressed && activeShip.getPoint().getX() > 0) {
            int lim = activeShip.getPoint().getX();
            activeShip.setSpeed(correctSpeed(lim));
            activeShip.setDirectionObjectMovment(DirectionObjectMovment.LEFT);
            player.shipControl(ActionPilot.MOVE);
        }
        if (rightButtonPressed && activeShip.getPoint().getX() < (fieldWidth - sizeShipWidget)) {
            int lim = Math.abs(activeShip.getPoint().getX() - (fieldWidth - sizeShipWidget));
            activeShip.setSpeed(correctSpeed(lim));
            activeShip.setDirectionObjectMovment(DirectionObjectMovment.RIGHT);
            player.shipControl(ActionPilot.MOVE);
        }
        if (spaceButtonPressed && canFire) {
            player.shipControl(ActionPilot.FIRE);
            canFire = false;
            fireTimer.start();
        }
        if (xButtonPressed) {
            player.getActiveShip().setAmmoType(new NormalAmmo());
        }
        if (zButtonPressed) {
            player.getActiveShip().setAmmoType(new RicochetAmmo());
        }
    }

    private int correctSpeed(int lim) {
        return player.getActiveShip().getSpeed() > lim ? lim : 3;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> {
                leftButtonPressed = true;
                break;
            }
            case KeyEvent.VK_RIGHT -> {
                rightButtonPressed = true;
                break;
            }
            case KeyEvent.VK_SPACE -> {
                spaceButtonPressed = true;
                break;
            }
            case KeyEvent.VK_Z -> {
                zButtonPressed = true;
                break;
            }
            case KeyEvent.VK_X -> {
                xButtonPressed = true;
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> {
                leftButtonPressed = false;
                break;
            }
            case KeyEvent.VK_RIGHT -> {
                rightButtonPressed = false;
                break;
            }
            case KeyEvent.VK_SPACE -> {
                spaceButtonPressed = false;
                break;
            }
            case KeyEvent.VK_Z -> {
                zButtonPressed = false;
                break;
            }
            case KeyEvent.VK_X -> {
                xButtonPressed = false;
                break;
            }
        }
    }
}
