package org.example.model.manager;

import org.example.model.ActionPilot;
import org.example.model.DirectionObjectMovment;
import org.example.model.field.Ship;

import java.util.List;

public class Player {

    private List<Ship> fleetShip;
    private Ship activeShip;

    public Player(List<Ship> fleetShip) {
        this.fleetShip = fleetShip;
        activeShip = this.fleetShip.getFirst();
    }

    public void shipControl(ActionPilot actionPilot, DirectionObjectMovment directionObjectMovment) {
        switch (actionPilot) {
            case MOVE:
                activeShip.move(directionObjectMovment);
            case FIRE:
                activeShip.fire();
        }
    }

    public Ship getActiveShip() { return activeShip; }

    public List<Ship> getFleetShip() { return fleetShip; }
}
