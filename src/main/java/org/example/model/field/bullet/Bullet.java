package org.example.model.field.bullet;

import org.example.model.DirectionObjectMovment;
import org.example.model.OwnerObject;
import org.example.model.event.FieldObjectEvent;
import org.example.model.event.FieldObjectListener;
import org.example.model.field.FieldObject;
import org.example.model.field.Point;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Класс Пули
 */
public abstract class Bullet extends FieldObject {

    public Bullet(Point point, OwnerObject ownerObject, DirectionObjectMovment directionObjectMovment) {
        super(point, ownerObject);
        setDirectionObjectMovment(directionObjectMovment);
        setSpeed(10);
        setLethal(true);
    }

    @Override
    public void move() {
        Point oldPoint = point;
        switch(directionObjectMovment) {
            case UP -> point = new Point(oldPoint.getX(), getPoint().getY() - speed);
            case DOWN -> point = new Point(oldPoint.getX(), getPoint().getY() + speed);
        }
        fireBulletIsMoved();
    }

    @Override
    public void destroy() {
        if (isAlive) {
            setAlive(false);
        }
    }

    private List<FieldObjectListener> fieldObjectListeners = new ArrayList<>();

    public void addFieldObjectListener(FieldObjectListener listener) {
        fieldObjectListeners.add(listener);
    }

    public void removeFieldObjectListener(FieldObjectListener listener) {
        fieldObjectListeners.remove(listener);
    }

    private void fireBulletIsMoved() {
        for(FieldObjectListener listener: fieldObjectListeners) {
            FieldObjectEvent event = new FieldObjectEvent(this);
            event.setFieldObject(this);
            listener.fieldObjectIsMoved(event);
        }
    }
}
