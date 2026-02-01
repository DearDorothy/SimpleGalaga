package org.example.model.field;

import org.example.model.DirectionObjectMovment;
import org.example.model.OwnerObject;
import org.example.model.event.ShipActionEvent;
import org.example.model.event.ShipActionListener;

import java.util.ArrayList;
import java.util.List;

public class Ship extends FieldObject{

    public Ship(Point point, OwnerObject ownerObject, int speed) {
        super(point, ownerObject, speed);
    }

    @Override
    public void move(DirectionObjectMovment directionObjectMovment) {
        Point oldPoint = point;
        switch(directionObjectMovment) {
            case LEFT ->
                point = new Point(oldPoint.getX() - speed, oldPoint.getY());
            case RIGHT ->
                point = new Point(oldPoint.getX() + speed, oldPoint.getY());
            case UP ->
                point = new Point(oldPoint.getX(), oldPoint.getY() - speed);
            case DOWN ->
                point = new Point(oldPoint.getX(), oldPoint.getY() + speed);
        }
        System.out.println("Корабль передвинулся в точку: " + point);
        fireShipIsMoved();
    }

    public void fire() {}

    private List<ShipActionListener> shipActionListeners = new ArrayList<>();

    public void addShipActionListener(ShipActionListener listener) { shipActionListeners.add(listener); }

    public void removeShipActionListener(ShipActionListener listener) { shipActionListeners.remove(listener); }

    public void fireShipIsMoved() {
        for(ShipActionListener listener : shipActionListeners) {
            ShipActionEvent event = new ShipActionEvent(listener);
            event.setShip(this);
            listener.shipIsMoved(event);
        }
    }

    public void fireShipIsFired() {}
}
