package org.example.model.event;

import org.example.model.field.Bullet;
import org.example.model.field.Ship;

import java.util.EventObject;

public class ShipActionEvent extends EventObject {

    private Ship ship;
    private Bullet bullet;

    public void setShip(Ship ship) { this.ship = ship; }

    public Ship getShip() { return ship; }

    public void setBullet(Bullet bullet) { this.bullet = bullet; }

    public Bullet getBullet() { return bullet; }

    public ShipActionEvent(Object source) {
        super(source);
    }
}
