package org.example.model.manager;

import org.example.model.ActionPilot;
import org.example.model.DirectionObjectMovment;
import org.example.model.field.Ship;

public class EnemyPilot implements PilotManager {

    private Ship ship;

    public EnemyPilot(Ship ship) {
        this.ship = ship;
    }

    public Ship getShip() {
        return ship;
    }

    @Override
    public void shipControl(ActionPilot actionPilot, DirectionObjectMovment directionObjectMovment) {
        switch (actionPilot) {
            case MOVE -> ship.move(directionObjectMovment);
            case FIRE -> ship.fire(directionObjectMovment);
        }
    }
}
