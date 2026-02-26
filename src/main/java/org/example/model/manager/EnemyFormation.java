// EnemyFormation.java - управление движением флота врагов как единого целого
package org.example.model.manager;

import org.example.model.ActionPilot;
import org.example.model.DirectionObjectMovment;
import org.example.model.field.Field;
import org.example.model.field.Ship;

import java.util.ArrayList;
import java.util.List;

public class EnemyFormation {

    private List<EnemyPilot> enemyPilotList;
    private int sizeShipModelCollision;
    private int fieldWidth;
    private int direction = 1;

    public EnemyFormation(int fieldWidth, int sizeShipModelCollision) {
        this.fieldWidth = fieldWidth;
        this.sizeShipModelCollision = sizeShipModelCollision;
    }

    public void setEnemyPilotList(List<EnemyPilot> enemyPilotList) {
        this.enemyPilotList = enemyPilotList;
    }

    public void update() {
        if (isEmpty()) return;

        int speed = enemyPilotList.getFirst().getShip().getSpeed();

        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        for(EnemyPilot enemyPilot: enemyPilotList) {
            Ship ship = enemyPilot.getShip();
            int x = ship.getPoint().getX();
            if (x < minX) minX = x;
            if (x > maxX) maxX = x;
        }

        int newMinX = minX + direction * speed;
        int newMaxX = maxX + direction * speed;

        if (newMinX < 0 || newMaxX > (fieldWidth - sizeShipModelCollision)) {
            direction *= -1;
        } else {
            DirectionObjectMovment directionMovment =
                    direction == 1 ? DirectionObjectMovment.RIGHT : DirectionObjectMovment.LEFT;

            for (EnemyPilot enemyPilot: enemyPilotList) {
                enemyPilot.getShip().setDirectionObjectMovment(directionMovment);
                enemyPilot.shipControl(ActionPilot.MOVE);
            }
        }
    }

    public void removeEnemyPilot(EnemyPilot enemyPilot) {
        if (enemyPilotList.contains(enemyPilot))
            enemyPilotList.remove(enemyPilot);
    }

    public boolean isEmpty() {
        return enemyPilotList.isEmpty();
    }
}