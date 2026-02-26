package org.example.model.manager;

import org.example.adapter.ShipActionAdapter;
import org.example.model.ActionPilot;
import org.example.model.DirectionObjectMovment;
import org.example.model.event.EnemyPilotEvent;
import org.example.model.event.EnemyPilotListener;
import org.example.model.event.ShipActionEvent;
import org.example.model.field.Ship;

import java.util.ArrayList;
import java.util.List;

public class EnemyPilot {

    private Ship ship;
    private boolean isAlive = true;

    public EnemyPilot(Ship ship) {
        this.ship = ship;
        ship.addShipActionListener(new ShipDestroyedObserver());
    }

    public Ship getShip() {
        return ship;
    }

    public void shipControl(ActionPilot actionPilot) {
        switch (actionPilot) {
            case MOVE -> ship.move();
            case FIRE -> ship.fire();
        }
    }

    private class ShipDestroyedObserver extends ShipActionAdapter {
        @Override
        public void shipIsDestroyed(ShipActionEvent event) {
            super.shipIsDestroyed(event);
            ship = null;
            isAlive = false;
            fireEnemyPilotDied();
        }
    }

    private List<EnemyPilotListener> enemyPilotListeners = new ArrayList<>();

    public void addEnemyPilotListener(EnemyPilotListener listener) {
        enemyPilotListeners.add(listener);
    }

    public void removeEnemyPilotListener(EnemyPilotListener listener) {
        enemyPilotListeners.remove(listener);
    }

    private void fireEnemyPilotDied() {
        for(EnemyPilotListener listener : enemyPilotListeners) {
            EnemyPilotEvent event = new EnemyPilotEvent(this);
            event.setEnemyPilot(this);
            listener.enemyPilotIsDied(event);
        }
    }
}
