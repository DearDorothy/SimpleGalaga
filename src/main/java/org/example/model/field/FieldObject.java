package org.example.model.field;

import org.example.model.DirectionObjectMovment;
import org.example.model.OwnerObject;

public abstract class FieldObject {

    protected Point point;
    protected int speed;
    protected OwnerObject ownerObject;

    public FieldObject(Point point, OwnerObject ownerObject, int speed) {
        this.point = point;
        this.ownerObject = ownerObject;
        this.speed = speed;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Point getPoint() { return point; }

    abstract public void move(DirectionObjectMovment directionObjectMovment);
}
