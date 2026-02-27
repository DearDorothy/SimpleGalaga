package org.example.model.field.bullet;

import org.example.model.DirectionObjectMovment;
import org.example.model.OwnerObject;
import org.example.model.field.FieldObject;
import org.example.model.field.Point;
import org.example.model.field.Ship;

/**
 *
 * Класс Рикошетной пули
 */
public class RicochetBullet extends Bullet {

    private int ricochetCount = 0;
    private static final int MAX_RICOCHET = 2;

    public RicochetBullet(Point point, OwnerObject ownerObject, DirectionObjectMovment directionObjectMovment) {
        super(point, ownerObject, directionObjectMovment);
        setLethal(false);
    }

    @Override
    public void collide(FieldObject object) {
        if (!isAlive) return;
        if (object instanceof Ship) {
            if (ricochetCount < MAX_RICOCHET) {
                ricochet();
            } else {
                setLethal(true);
                object.destroy();
                destroy();
            }
        } else {
            object.destroy();
            destroy();
        }
    }

    private void ricochet() {
        if (getDirectionObjectMovment() == DirectionObjectMovment.UP) {
            setDirectionObjectMovment(DirectionObjectMovment.DOWN);
        } else if (getDirectionObjectMovment() == DirectionObjectMovment.DOWN) {
            setDirectionObjectMovment(DirectionObjectMovment.UP);
        }
        ricochetCount++;
    }
}
