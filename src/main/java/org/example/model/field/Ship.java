package org.example.model.field;

import org.example.model.Direction;
import org.example.model.Owner;

public class Ship extends FieldObject{

    public Ship(Point point, Owner owner, int speed) {
        super(point, owner, speed);
    }

    @Override
    public void move(Direction direction) {
        Point oldPoint = point;
        switch(direction) {
            case LEFT:
                point = new Point(oldPoint.getX() - speed, oldPoint.getY());
            case RIGHT:
                point = new Point(oldPoint.getX() - speed, oldPoint.getY());
        }
    }

    public void fire() {}
}
