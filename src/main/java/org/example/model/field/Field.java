package org.example.model.field;

import org.example.model.OwnerObject;
import org.example.model.event.*;
import org.example.model.field.Point;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


/**
*
*/
public class Field {

    private final int width = 350;
    private final int height = 600;
    private final int SIZE_COLLISION_MODEL_SHIP = 35;
    private final int SIZE_COLLISION_MODEL_BULLET = 25;
    private List<FieldObject> objectList = new ArrayList<>();

    public Field() {}

    public List<Ship> createShips(int numberShips, OwnerObject owner) {
        List<Ship> shipList = new ArrayList<>();

        // Создать точки стартовых положений для кораблей соотв. владельца
        List<Point> pointList = createStartPositionsForShips(numberShips, owner);

        // Создание кораблей
        for(int i = 0; i < numberShips; i++) {
            Ship currentShip = new Ship(pointList.get(i), owner);
            currentShip.setSizeCollisionModel(SIZE_COLLISION_MODEL_SHIP);

            currentShip.addShipActionListener(new ShipActionObserver());

            shipList.add(currentShip);
            addObject(currentShip);
        }

        return shipList;
    }

    private List<Point> createStartPositionsForShips(int numberShips, OwnerObject owner) {
        List<Point> pointList = new ArrayList<>();
        if (owner == OwnerObject.PLAYER) {
            pointList = SpawnerShips.createPlayerShipsPositions(numberShips, width, height);
        } else if (owner == OwnerObject.ENEMY) {
            pointList = SpawnerShips.createEnemyShipsPositions(numberShips, width);
        }
        return pointList;
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
        // List<FieldObject[]> listCollidedObjects = CollisionDetector.detectCollisions(objectList);
        // fireFieldObjectsCollide(listCollidedObjects);
        // removeObjects();
    }

    private void addObject(FieldObject object) {
        objectList.add(object);
        fireAddedFieldObjectOnField(object);
    }

    private void removeObjects(List<FieldObject> objects) {
        for(FieldObject object: objects) {
            objectList.remove(object);
        }
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

    private class ShipActionObserver implements ShipActionListener {
        @Override
        public void shipIsMoved(ShipActionEvent event) {
            System.out.println("Передвинулся ");
            fireShipIsMoved(event);
        }

        @Override
        public void shipIsFire(ShipActionEvent event) {
            Ship ship = event.getShip();
            int xForBullet = ship.getPoint().getX() + (SIZE_COLLISION_MODEL_SHIP - SIZE_COLLISION_MODEL_BULLET)/2;
            int yForBullet = ship.getPoint().getY();
            Point pointForBullet = new Point(xForBullet, yForBullet);
            Bullet bullet = new Bullet(pointForBullet, ship.getOwnerObject());
            bullet.setSizeCollisionModel(SIZE_COLLISION_MODEL_BULLET);
            System.out.println("Выстрелил дошло до поля");
            addObject(bullet);
        }
    }

    private List<ShipActionListener> shipActionListeners = new ArrayList<>();

    public void addShipActionListener(ShipActionListener listener) {
        shipActionListeners.add(listener);
    }

    public void removeShipActionListener(ShipActionListener listener) {
        shipActionListeners.remove(listener);
    }

    private void fireShipIsMoved(ShipActionEvent event) {
        for(ShipActionListener listener: shipActionListeners) {
            listener.shipIsMoved(event);
        }
    }

    private List<FieldObjectListener> fieldObjectListeners = new ArrayList<>();

    public void addFieldObjectListener(FieldObjectListener listener) {
        fieldObjectListeners.add(listener);
    }

    public void removeFieldObjectListener(FieldObjectListener listener) {
        fieldObjectListeners.remove(listener);
    }

    private void fireAddedFieldObjectOnField(FieldObject object) {
        for(FieldObjectListener listener: fieldObjectListeners) {
            FieldObjectEvent event = new FieldObjectEvent(this);
            event.setFieldObject(object);
            listener.addFieldObjectOnField(event);
        }
    }

    private void fireRemovedFieldObjectFromField(FieldObject object) {
        for(FieldObjectListener listener: fieldObjectListeners) {
            FieldObjectEvent event = new FieldObjectEvent(this);
            event.setFieldObject(object);
            listener.removeFieldObjectFromField(event);
        }
    }
}
