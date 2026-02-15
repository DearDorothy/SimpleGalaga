package org.example.model.field;

import org.example.model.DirectionObjectMovment;
import org.example.model.OwnerObject;

public abstract class FieldObject {

    protected Point point;
    protected int speed;
    protected OwnerObject ownerObject;
    protected int sizeCollisionModel;

    public FieldObject(Point point, OwnerObject ownerObject) {
        this.point = point;
        this.ownerObject = ownerObject;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setSizeCollisionModel(int sizeCollisionModel) {
        this.sizeCollisionModel = sizeCollisionModel;
    }

    public Point getPoint() {
        return point;
    }

    public OwnerObject getOwnerObject() {
        return ownerObject;
    }

    public int getSizeCollisionModel() {
        return sizeCollisionModel;
    }

    abstract public void move(DirectionObjectMovment directionObjectMovment);
}
