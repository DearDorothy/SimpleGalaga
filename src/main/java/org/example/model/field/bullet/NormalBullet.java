package org.example.model.field.bullet;

import org.example.model.DirectionObjectMovment;
import org.example.model.OwnerObject;
import org.example.model.field.FieldObject;
import org.example.model.field.Point;

/**
 *
 * Класс Обычной пули
 */
public class NormalBullet extends Bullet {

    public NormalBullet(Point point, OwnerObject ownerObject, DirectionObjectMovment directionObjectMovment) {
        super(point, ownerObject, directionObjectMovment);
    }

    @Override
    public void collide(FieldObject object) {
        if (!isAlive) return;
        if (ownerObject != object.getOwnerObject() && object.isLethal()) {
            object.destroy();
            destroy();
        }
    }
}
