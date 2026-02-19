package org.example.model.event;

import org.example.model.DirectionObjectMovment;
import org.example.model.field.Ship;

import java.util.EventObject;

public class ShipActionEvent extends EventObject {

    private Ship ship;
    private DirectionObjectMovment directionObjectMovment;

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public void setDirectionObjectMovment(DirectionObjectMovment directionObjectMovment) {
        this.directionObjectMovment = directionObjectMovment;
    }

    public Ship getShip() {
        return ship;
    }

    public DirectionObjectMovment getDirectionObjectMovment() {
        return directionObjectMovment;
    }

    public ShipActionEvent(Object source) {
        super(source);
    }
}
