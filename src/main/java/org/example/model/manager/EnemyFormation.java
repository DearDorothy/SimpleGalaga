// EnemyFormation.java - управление движением флота врагов как единого целого
package org.example.model.manager;

import org.example.model.DirectionObjectMovment;
import org.example.model.field.Field;

import java.util.List;

public class EnemyFormation {
    private List<EnemyPilot> pilots;
    private Field field;
    private boolean movingRight = true;
    private int formationSpeed = 2;

    public EnemyFormation(List<EnemyPilot> pilots, Field field) {
        this.pilots = pilots;
        this.field = field;
    }

    public void update() {
        if (pilots.isEmpty()) return;

        // Проверяем, достиг ли флот границы
        boolean shouldChangeDirection = false;
        for (EnemyPilot pilot : pilots) {
            int shipX = pilot.getShip().getPoint().getX();
            if (movingRight && shipX > field.getWidth() - 40) {
                shouldChangeDirection = true;
                break;
            } else if (!movingRight && shipX < 10) {
                shouldChangeDirection = true;
                break;
            }
        }

        if (shouldChangeDirection) {
            movingRight = !movingRight;
            // Опускаем флот вниз при развороте
            for (EnemyPilot pilot : pilots) {
                pilot.getShip().move(DirectionObjectMovment.DOWN);
            }
        } else {
            // Двигаем весь флот
            for (EnemyPilot pilot : pilots) {
                pilot.getShip().move(movingRight ?
                        DirectionObjectMovment.RIGHT : DirectionObjectMovment.LEFT);
            }
        }
    }

    public List<EnemyPilot> getPilots() {
        return pilots;
    }
}