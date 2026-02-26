package org.example.model.field.bullet.ammo;

import org.example.model.DirectionObjectMovment;
import org.example.model.OwnerObject;
import org.example.model.field.Point;
import org.example.model.field.bullet.Bullet;

public interface AmmoType {
    Bullet createBullet(Point point, OwnerObject ownerObject, DirectionObjectMovment directionObjectMovment);
}
