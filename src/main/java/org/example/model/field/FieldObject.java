package org.example.model.field;

import org.example.model.DirectionObjectMovment;
import org.example.model.OwnerObject;

public abstract class FieldObject {

    protected Point point;
    protected int speed;
    protected OwnerObject ownerObject;
    protected DirectionObjectMovment directionObjectMovment;
    protected int sizeCollisionModel;
    protected boolean isLethal = false;
    protected boolean isAlive = true;

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

    public void setLethal(boolean lethal) {
        this.isLethal = lethal;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public void setDirectionObjectMovment(DirectionObjectMovment directionObjectMovment) {
        this.directionObjectMovment = directionObjectMovment;
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

    public int getSpeed() {
        return speed;
    }

    public DirectionObjectMovment getDirectionObjectMovment() {
        return directionObjectMovment;
    }

    public boolean isLethal() {
        return isLethal;
    }

    public boolean isAlive() {
        return isAlive;
    }

    abstract public void move();

    abstract public void collide(FieldObject object);

    abstract public void destroy();
}
