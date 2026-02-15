package org.example.model.event;

import org.example.model.field.Bullet;
import org.example.model.field.Ship;

import java.util.EventObject;

public class ShipMoveEvent extends EventObject {

    private Ship ship;

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Ship getShip() {
        return ship;
    }

    public ShipMoveEvent(Object source) {
        super(source);
    }
}
