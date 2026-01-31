package org.example.model.field;

import org.example.model.Direction;
import org.example.model.Owner;

public class Bullet extends FieldObject {

    public Bullet(Point point, Owner owner, int speed) {
        super(point, owner, speed);
    }

    @Override
    public void move(Direction direction) {
        Point oldPoint = point;
        switch(direction) {
            case UP:
                point = new Point(oldPoint.getX(), getPoint().getY() + speed);
            case DOWN:
                point = new Point(oldPoint.getX(), getPoint().getY() - speed);
        }
    }
}
