package org.example.model.field;

import org.example.model.OwnerObject;
import org.example.model.event.FieldActionEvent;
import org.example.model.event.FieldActionListener;
import org.example.model.event.ShipFireEvent;
import org.example.model.event.ShipFireListener;

import java.util.ArrayList;
import java.util.List;


/*
*
* */
public class Field {

    private final int width = 350;
    private final int height = 600;
    private List<FieldObject> objectList;

    public Field() {
        objectList = new ArrayList<>();
    }

    public List<Ship> createShips(int numberShips, OwnerObject owner) {
        List<Ship> shipList = new ArrayList<>();

        // Создать точки стартовых положений для кораблей соотв. владельца
        List<Point> pointList = SpawnerShips.createPositionForShips(numberShips, owner, width, height);

        for(int i = 0; i < numberShips; i++) {
            Ship currentShip = new Ship(pointList.get(i), owner);
            currentShip.setSizeCollisionModel(35);
            currentShip.addShipFireListener(new ShipFireObserver());

            shipList.add(currentShip);
            objectList.add(currentShip);
        }

        return shipList;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<FieldObject> getObjectList() {
        return objectList;
    }

    public void detectCollision() {
//        List<FieldObject[]> listCollidedObjects = CollisionDetector.detectCollisions(objectList);
//        fireFieldObjectsCollide(listCollidedObjects);
    }

    private List<FieldActionListener> fieldActionListeners = new ArrayList<>();

    public void addFieldActionListener(FieldActionListener listener) {
        fieldActionListeners.add(listener);
    }

    public void removeFieldActionListener(FieldActionListener listener) {
        fieldActionListeners.remove(listener);
    }

    private void fireFieldObjectsCollide(List<FieldObject> listCollidedObjects) {
        for(FieldActionListener listener: fieldActionListeners) {
            FieldActionEvent event = new FieldActionEvent(this);
            event.setListCollidedObjects(listCollidedObjects);
            listener.fieldObjectsCollide(event);
        }
    }

    private class ShipFireObserver implements ShipFireListener {
        @Override
        public void shipIsFire(ShipFireEvent event) {
            Bullet bullet = event.getBullet();
            bullet.setSizeCollisionModel(15);
            objectList.add(bullet);
            System.out.println("Пуля передана полю");
        }
    }
}
