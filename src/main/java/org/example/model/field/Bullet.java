package org.example.model.field;

import org.example.model.DirectionObjectMovment;
import org.example.model.OwnerObject;
import org.example.model.event.FieldObjectEvent;
import org.example.model.event.FieldObjectListener;

import java.util.ArrayList;
import java.util.List;

public class Bullet extends FieldObject {

    public Bullet(Point point, OwnerObject ownerObject) {
        super(point, ownerObject);
        setSpeed(10);
        setLethal(true);
    }

    @Override
    public void move(DirectionObjectMovment directionObjectMovment) {
        Point oldPoint = point;
        switch(directionObjectMovment) {
            case UP -> point = new Point(oldPoint.getX(), getPoint().getY() - speed);
            case DOWN -> point = new Point(oldPoint.getX(), getPoint().getY() + speed);
        }
        fireBulletIsMoved();
    }

    @Override
    public void collide(FieldObject object) {
        if (!isAlive) return;
        if (ownerObject != object.getOwnerObject() && object.isLethal()) {
            destroy();
            object.destroy();
        }
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
