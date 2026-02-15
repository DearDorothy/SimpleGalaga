package org.example.model.field;

import org.example.model.OwnerObject;

import java.util.ArrayList;
import java.util.List;

public class SpawnerShips {

    public static List<Point> createPositionForShips(int numberShips, OwnerObject ownerObject, int widthField, int heightField) {
        List<Point> pointList = new ArrayList<>();

        if (ownerObject == OwnerObject.PLAYER) {
            // Спавн для кораблей игрока
            for(int i = 0; i < numberShips; i ++) {
                pointList.add(new Point(0, 0));
            }
        } else if (ownerObject == OwnerObject.ENEMY) {
            // Спавн для кораблей врага
            for(int i = 0; i < numberShips; i ++) {
                pointList.add(new Point(0, 0));
            }
        }

        return pointList;
    }
}