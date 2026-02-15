package org.example.model.manager;

import org.example.model.ActionPilot;
import org.example.model.DirectionObjectMovment;
import org.example.model.field.Ship;

import java.util.List;

public class Player {

    private List<Ship> fleetShip;
    private Ship activeShip;

    public Player() {}

    public void setFleetShip(List<Ship> fleetShip) {
        this.fleetShip = fleetShip;
        setActiveShip(this.fleetShip.getFirst());
        System.out.println("Игрок получил флот кораблей размером в " + fleetShip.size());
    }

    public void setActiveShip(Ship activeShip) {
        this.activeShip = activeShip;
    }

    public Ship getActiveShip() {
        return activeShip;
    }

    public List<Ship> getFleetShip() {
        return fleetShip;
    }

    public int getNumberNndestroyedShips() {
        return fleetShip.size();
    }

    public void shipControl(ActionPilot actionPilot, DirectionObjectMovment directionObjectMovment) {
        switch (actionPilot) {
            case MOVE -> activeShip.move(directionObjectMovment);
            case FIRE -> activeShip.fire(directionObjectMovment);
        }
    }
}
