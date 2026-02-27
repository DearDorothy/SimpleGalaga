package org.example.model.manager;

import org.example.adapter.ShipActionAdapter;
import org.example.model.ActionPilot;
import org.example.model.DirectionObjectMovment;
import org.example.model.event.ShipActionEvent;
import org.example.model.field.Point;
import org.example.model.field.Ship;

import java.util.List;

/**
 *
 * Класс Игрока
 */
public class Player {

    private final int NUMBER_SHIP = 3;
    private List<Ship> fleetShip;
    private Ship activeShip;
    private Point startPoint;

    public Player() {}

    public void setActiveShip(Ship activeShip) {
        this.activeShip = activeShip;
    }

    public void setStartPoint(Point point) {
        startPoint = point;
    }

    public Ship getActiveShip() {
        return activeShip;
    }

    public int getNumberShip() {
        return NUMBER_SHIP;
    }

    public List<Ship> getFleetShip() {
        return fleetShip;
    }

    public int getNumberNndestroyedShips() {
        return (int) fleetShip.stream().filter(Ship::isAlive).count();
    }

    public void setFleetShip(List<Ship> fleetShip) {
        this.fleetShip = fleetShip;
        setActiveShip(this.fleetShip.getFirst());

        for (Ship ship: fleetShip) {
            ship.addShipActionListener(new ShipDestroyedObserver());
        }

        System.out.println("Игрок получил флот кораблей размером в " + fleetShip.size());
    }

    private void removeShipFromFleet(Ship ship) {
        if (fleetShip.isEmpty()) return;

        if (fleetShip.contains(ship)) {
            fleetShip.remove(ship);
        }
    }

    private void swapActiveShip(Ship ship) {
        if (fleetShip.isEmpty()) {
            activeShip = null;
            return;
        }
        if (ship == activeShip) {
            activeShip = fleetShip.get(0);
            if (startPoint != null) {
                activeShip.setPosition(startPoint);
            }
        }
    }

    public void shipControl(ActionPilot actionPilot) {
        switch (actionPilot) {
            case MOVE -> activeShip.move();
            case FIRE -> activeShip.fire();
        }
    }

    private class ShipDestroyedObserver extends ShipActionAdapter {
        @Override
        public void shipIsDestroyed(ShipActionEvent event) {
            super.shipIsDestroyed(event);
            Ship ship = event.getShip();
            removeShipFromFleet(ship);
            swapActiveShip(ship);
        }
    }
}
