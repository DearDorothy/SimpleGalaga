package org.example.model.field;

import org.example.model.DirectionObjectMovment;
import org.example.model.OwnerObject;

public class Bullet extends FieldObject {

    public Bullet(Point point, OwnerObject ownerObject, int speed) {
        super(point, ownerObject, speed);
    }

    @Override
    public void move(DirectionObjectMovment directionObjectMovment) {
        Point oldPoint = point;
        switch(directionObjectMovment) {
            case UP:
                point = new Point(oldPoint.getX(), getPoint().getY() + speed);
            case DOWN:
                point = new Point(oldPoint.getX(), getPoint().getY() - speed);
        }
    }
}
