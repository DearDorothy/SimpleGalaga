package org.example.model.field.bullet.ammo;

import org.example.model.DirectionObjectMovment;
import org.example.model.OwnerObject;
import org.example.model.field.Point;
import org.example.model.field.bullet.Bullet;
import org.example.model.field.bullet.NormalBullet;

public class NormalAmmo implements AmmoType {
    @Override
    public Bullet createBullet(Point point, OwnerObject ownerObject, DirectionObjectMovment directionObjectMovment) {
        return new NormalBullet(point, ownerObject, directionObjectMovment);
    }
}
