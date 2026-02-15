package org.example.model.event;

import org.example.model.DirectionObjectMovment;
import org.example.model.field.Bullet;

import java.util.EventObject;

public class ShipFireEvent extends EventObject {

    private Bullet bullet;
    private DirectionObjectMovment directionObjectMovment;

    public void setBullet(Bullet bullet) {
        this.bullet = bullet;
    }

    public Bullet getBullet() {
        return bullet;
    }

    public void setDirectionObjectMovment(DirectionObjectMovment directionObjectMovment) {
        this.directionObjectMovment = directionObjectMovment;
    }

    public DirectionObjectMovment getDirectionObjectMovment() {
        return directionObjectMovment;
    }

    public ShipFireEvent(Object source) {
        super(source);
    }
}
