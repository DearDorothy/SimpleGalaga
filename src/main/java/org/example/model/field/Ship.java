package org.example.model.field;

import org.example.model.DirectionObjectMovment;
import org.example.model.OwnerObject;
import org.example.model.event.*;

import java.util.ArrayList;
import java.util.List;

public class Ship extends FieldObject{

    public Ship(Point point, OwnerObject ownerObject) {
        super(point, ownerObject);
        setSpeed(5);
    }

    @Override
    public void move(DirectionObjectMovment directionObjectMovment) {
        Point oldPoint = point;
        switch(directionObjectMovment) {
            case LEFT -> point = new Point(oldPoint.getX() - speed, oldPoint.getY());
            case RIGHT -> point = new Point(oldPoint.getX() + speed, oldPoint.getY());
            case UP -> point = new Point(oldPoint.getX(), oldPoint.getY() - speed);
            case DOWN -> point = new Point(oldPoint.getX(), oldPoint.getY() + speed);
        }
        System.out.println("Корабль передвинулся в точку: " + point);
        fireShipIsMoved();
    }

    public void fire(DirectionObjectMovment directionObjectMovment) {
        Bullet bullet = new Bullet(getPoint(), getOwnerObject());
        System.out.println("Корабль выстрелил");
        fireShipIsFired(bullet, directionObjectMovment);
    }

    private List<ShipMoveListener> shipMoveListeners = new ArrayList<>();

    public void addShipMoveListener(ShipMoveListener listener) {
        shipMoveListeners.add(listener);
    }

    public void removeShipMoveListener(ShipMoveListener listener) {
        shipMoveListeners.remove(listener);
    }

    private void fireShipIsMoved() {
        for(ShipMoveListener listener: shipMoveListeners) {
            ShipMoveEvent event = new ShipMoveEvent(this);
            event.setShip(this);
            listener.shipIsMoved(event);
        }
    }

    private List<ShipFireListener> shipFireListeners = new ArrayList<>();

    public void addShipFireListener(ShipFireListener listener) {
        shipFireListeners.add(listener);
    }

    public void removeShipFireListener(ShipFireListener listener) {
        shipFireListeners.remove(listener);
    }

    private void fireShipIsFired(Bullet bullet, DirectionObjectMovment directionObjectMovment) {
        for(ShipFireListener listener: shipFireListeners) {
            ShipFireEvent event = new ShipFireEvent(this);
            event.setBullet(bullet);
            event.setDirectionObjectMovment(directionObjectMovment);
            listener.shipIsFire(event);
        }
    }
}
