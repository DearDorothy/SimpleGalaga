package org.example.model.field;

import org.example.model.Direction;
import org.example.model.Owner;

public abstract class FieldObject {

    protected Point point;
    protected int speed;
    protected Owner owner;

    public FieldObject(Point point, Owner owner, int speed) {
        this.point = point;
        this.owner = owner;
        this.speed = speed;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Point getPoint() { return point; }

    abstract public void move(Direction direction);
}
