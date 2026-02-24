package org.example.model.field;

import org.example.model.DirectionObjectMovment;
import org.example.model.OwnerObject;
import org.example.model.event.*;

import java.util.ArrayList;
import java.util.List;

public class Ship extends FieldObject{

    public Ship(Point point, OwnerObject ownerObject) {
        super(point, ownerObject);
        setSpeed(3);
        setLethal(true);
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
        fireShipIsMoved(directionObjectMovment);
    }

    @Override
    public void collide(FieldObject object) {
        if (!isAlive) return;
        if (ownerObject != object.ownerObject && object.isLethal()) {
            destroy();
        }
    }

    @Override
    public void destroy() {
        if (isAlive) {
            setAlive(false);
            fireShipIsDestroyed();
        }
    }

    public void fire(DirectionObjectMovment directionObjectMovment) {
//        Point point = new Point(getPoint().getX() + sizeCollisionModel/2, getPoint().getY());
//        Bullet bullet = new Bullet(point, ownerObject);
        System.out.println("Корабль выстрелил");
        fireShipIsFired(directionObjectMovment);
    }

    private List<FieldObjectListener> fieldObjectListeners = new ArrayList<>();

    public void addFieldObjectListener(FieldObjectListener listener) {
        fieldObjectListeners.add(listener);
    }

    public void removeFieldObjectListener(FieldObjectListener listener) {
        fieldObjectListeners.remove(listener);
    }

    private void fireShipIsMoved(DirectionObjectMovment directionObjectMovment) {
        for(FieldObjectListener listener: fieldObjectListeners) {
            FieldObjectEvent event = new FieldObjectEvent(this);
            event.setFieldObject(this);
            event.setDirectionObjectMovment(directionObjectMovment);
            listener.fieldObjectIsMoved(event);
        }
    }

    private List<ShipActionListener> shipActionListeners = new ArrayList<>();

    public void addShipActionListener(ShipActionListener listener) {
        shipActionListeners.add(listener);
    }

    public void removeShipActionListener(ShipActionListener listener) {
        shipActionListeners.remove(listener);
    }

    private void fireShipIsFired(DirectionObjectMovment directionObjectMovment) {
        for(ShipActionListener listener: shipActionListeners) {
            ShipActionEvent event = new ShipActionEvent(this);
            event.setShip(this);
            event.setDirectionObjectMovment(directionObjectMovment);
            listener.shipIsFire(event);
        }
    }

    private void fireShipIsDestroyed() {
        for(ShipActionListener listener: shipActionListeners) {
            ShipActionEvent event = new ShipActionEvent(this);
            event.setShip(this);
            listener.shipIsDestroyed(event);
        }
    }
}
