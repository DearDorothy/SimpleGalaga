// CollisionDetector.java
package org.example.model.field;

import java.util.ArrayList;
import java.util.List;

public class CollisionDetector {

    public static List<FieldObject[]> detectCollisions(List<FieldObject> objects) {
        List<FieldObject[]> collisions = new ArrayList<>();

        for (int i = 0; i < objects.size(); i++) {
            for (int j = i + 1; j < objects.size(); j++) {
                FieldObject obj1 = objects.get(i);
                FieldObject obj2 = objects.get(j);

                if (areColliding(obj1, obj2)) {
                    collisions.add(new FieldObject[]{obj1, obj2});
                }
            }
        }

        return collisions;
    }

    private static boolean areColliding(FieldObject obj1, FieldObject obj2) {
        // Простая проверка пересечения прямоугольников
        int obj1X = obj1.getPoint().getX();
        int obj1Y = obj1.getPoint().getY();
        int obj2X = obj2.getPoint().getX();
        int obj2Y = obj2.getPoint().getY();

        // Предполагаем стандартные размеры
        int size = 35;

        return obj1X < obj2X + size &&
                obj1X + size > obj2X &&
                obj1Y < obj2Y + size &&
                obj1Y + size > obj2Y;
    }
}