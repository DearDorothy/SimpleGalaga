package org.example.model.field;

import org.example.model.DirectionObjectMovment;
import org.example.model.OwnerObject;
import org.example.model.event.*;
import org.example.model.field.bullet.ammo.AmmoType;
import org.example.model.field.bullet.Bullet;
import org.example.model.field.bullet.ammo.NormalAmmo;

import java.util.ArrayList;
import java.util.List;

public class Ship extends FieldObject{

    private AmmoType ammoType;

    public Ship(Point point, OwnerObject ownerObject) {
        super(point, ownerObject);
        setSpeed(3);
        setLethal(true);
        setAmmoType(new NormalAmmo());
    }

    public void setPosition(Point point) {
        this.point = point;
        fireShipIsMoved();
    }

    public void setAmmoType(AmmoType ammoType) {
        this.ammoType = ammoType;
    }

    @Override
    public void move() {
        Point oldPoint = point;
        switch(directionObjectMovment) {
            case LEFT -> {
                point = new Point(oldPoint.getX() - speed, oldPoint.getY());
                break;
            }
            case RIGHT -> {
                point = new Point(oldPoint.getX() + speed, oldPoint.getY());
                break;
            }
            case UP -> {
                point = new Point(oldPoint.getX(), oldPoint.getY() - speed);
                break;
            }
            case DOWN -> {
                point = new Point(oldPoint.getX(), oldPoint.getY() + speed);
                break;
            }
        }
        fireShipIsMoved();
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

    public void fire() {
        if (ammoType == null) return;

        DirectionObjectMovment directionBullet = ownerObject == OwnerObject.PLAYER ? DirectionObjectMovment.UP :
                DirectionObjectMovment.DOWN;
        Bullet bullet = ammoType.createBullet(new Point(0, 0), ownerObject, directionBullet);

        System.out.println("Корабль выстрелил");
        fireShipIsFired(bullet);
    }

    private List<FieldObjectListener> fieldObjectListeners = new ArrayList<>();

    public void addFieldObjectListener(FieldObjectListener listener) {
        fieldObjectListeners.add(listener);
    }

    public void removeFieldObjectListener(FieldObjectListener listener) {
        fieldObjectListeners.remove(listener);
    }

    private void fireShipIsMoved() {
        for(FieldObjectListener listener: fieldObjectListeners) {
            FieldObjectEvent event = new FieldObjectEvent(this);
            event.setFieldObject(this);
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

    private void fireShipIsFired(Bullet bullet) {
        for(ShipActionListener listener: shipActionListeners) {
            ShipActionEvent event = new ShipActionEvent(this);
            event.setShip(this);
            event.setBullet(bullet);
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
