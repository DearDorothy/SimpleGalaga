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
        fireShipIsMoved(directionObjectMovment);
    }

    public void fire(DirectionObjectMovment directionObjectMovment) {
        Point point = new Point(getPoint().getX() + sizeCollisionModel/2, getPoint().getY());
        Bullet bullet = new Bullet(point, ownerObject);
        System.out.println("Корабль выстрелил");
        fireShipIsFired(directionObjectMovment);
    }

    private List<ShipActionListener> shipActionListeners = new ArrayList<>();

    public void addShipActionListener(ShipActionListener listener) {
        shipActionListeners.add(listener);
    }

    public void removeShipActionListener(ShipActionListener listener) {
        shipActionListeners.remove(listener);
    }

    private void fireShipIsMoved(DirectionObjectMovment directionObjectMovment) {
        for(ShipActionListener listener: shipActionListeners) {
            ShipActionEvent event = new ShipActionEvent(this);
            event.setShip(this);
            event.setDirectionObjectMovment(directionObjectMovment);
            listener.shipIsMoved(event);
        }
    }

    private void fireShipIsFired(DirectionObjectMovment directionObjectMovment) {
        for(ShipActionListener listener: shipActionListeners) {
            ShipActionEvent event = new ShipActionEvent(this);
            event.setShip(this);
            event.setDirectionObjectMovment(directionObjectMovment);
            listener.shipIsFire(event);
        }
    }
}
