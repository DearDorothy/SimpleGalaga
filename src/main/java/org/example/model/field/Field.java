package org.example.model.field;

import org.example.adapter.FieldObjectAdapter;
import org.example.adapter.ShipActionAdapter;
import org.example.model.GameStatus;
import org.example.model.OwnerObject;
import org.example.model.event.*;
import org.example.model.field.bullet.Bullet;

import java.util.ArrayList;
import java.util.List;


/**
*
*/
public class Field {

    private final int width = 350;
    private final int height = 600;
    private final int SIZE_COLLISION_MODEL_SHIP = 35;
    private final int SIZE_COLLISION_MODEL_BULLET = 20;
    private List<FieldObject> objectList = new ArrayList<>();

    public Field() {}

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int get_SIZE_COLLISION_MODEL_SHIP() {
        return SIZE_COLLISION_MODEL_SHIP;
    }

    public List<FieldObject> getObjectList() {
        return objectList;
    }

    public List<Ship> createShips(int numberShips, OwnerObject owner) {
        List<Ship> shipList = new ArrayList<>();

        List<Point> pointList = createStartPositionsForShips(numberShips, owner);

        for(int i = 0; i < numberShips; i++) {
            Ship currentShip = new Ship(pointList.get(i), owner);
            currentShip.setSizeCollisionModel(SIZE_COLLISION_MODEL_SHIP);

            currentShip.addShipActionListener(new ShipActionObserver());
            currentShip.addFieldObjectListener(new FieldOblectMoveObsrerver());

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

    public void updateBullets() {
        for (FieldObject obj : objectList) {
            if (obj instanceof Bullet) {
                Bullet bullet = (Bullet) obj;
                if (!bullet.isAlive()) continue;
                bullet.move();
                int y = bullet.getPoint().getY();
                if (y < 0 || y > height) {
                    bullet.destroy();
                }
            }
        }
    }

    public void detectCollision() {
        for (int i = 0; i < objectList.size(); i++) {
            for (int j = i + 1; j < objectList.size(); j++) {
                FieldObject object1 = objectList.get(i);
                FieldObject object2 = objectList.get(j);
                if (object1.isAlive() && object2.isAlive() && areColliding(object1, object2)) {
                    object1.collide(object2);
                    object2.collide(object1);
                }
            }
        }

        List<FieldObject> dead = getAllDeadObject();
        removeObjects(dead);
    }

    private List<FieldObject> getAllLiveObject() {
        List<FieldObject> liveObjects = new ArrayList<>();
        for (FieldObject object : objectList) {
            if (object.isAlive()) {
                liveObjects.add(object);
            }
        }
        return liveObjects;
    }

    private List<FieldObject> getAllDeadObject() {
        List<FieldObject> dead = new ArrayList<>();
        for (FieldObject object : objectList) {
            if (!object.isAlive()) {
                dead.add(object);
            }
        }
        return dead;
    }

    private boolean areColliding(FieldObject object1, FieldObject object2) {
        int obj1X = object1.getPoint().getX();
        int obj1Y = object1.getPoint().getY();
        int obj2X = object2.getPoint().getX();
        int obj2Y = object2.getPoint().getY();

        int size1 = object1.getSizeCollisionModel();
        int size2 = object2.getSizeCollisionModel();

        return obj1X < obj2X + size2 &&
                obj1X + size1 > obj2X &&
                obj1Y < obj2Y + size2 &&
                obj1Y + size1 > obj2Y;
    }

    private void addObject(FieldObject object) {
        objectList.add(object);
        fireAddedFieldObjectOnField(object);
    }

    private void removeObjects(List<FieldObject> objects) {
        List<FieldObject> toRemove = new ArrayList<>(objects);
        for (FieldObject object : toRemove) {
            objectList.remove(object);
            fireRemovedFieldObjectFromField(object);
        }
    }

    private class ShipActionObserver extends ShipActionAdapter {
        @Override
        public void shipIsFire(ShipActionEvent event) {
            super.shipIsFire(event);
            Ship ship = event.getShip();
            Bullet bullet = event.getBullet();
            bullet.setSizeCollisionModel(SIZE_COLLISION_MODEL_BULLET);
            Point pointForBullet = calcStartPositionForBullet(ship, bullet);
            bullet.setPoint(pointForBullet);
            bullet.addFieldObjectListener(new FieldOblectMoveObsrerver());
            System.out.println("Выстрелил дошло до поля");
            addObject(bullet);
        }
    }

    private Point calcStartPositionForBullet(Ship ship, Bullet bullet) {
        int xForBullet = ship.getPoint().getX() + (ship.getSizeCollisionModel() - bullet.getSizeCollisionModel())/2;
        int yForBullet = ship.getPoint().getY() - bullet.getSizeCollisionModel();
        return new Point(xForBullet, yForBullet);
    }

    private class FieldOblectMoveObsrerver extends FieldObjectAdapter {
        @Override
        public void fieldObjectIsMoved(FieldObjectEvent event) {
            fireFieldObjectIsMoved(event);
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
            listener.addedFieldObjectOnField(event);
        }
    }

    private void fireRemovedFieldObjectFromField(FieldObject object) {
        for(FieldObjectListener listener: fieldObjectListeners) {
            FieldObjectEvent event = new FieldObjectEvent(this);
            event.setFieldObject(object);
            listener.removedFieldObjectFromField(event);
        }
    }

    private void fireFieldObjectIsMoved(FieldObjectEvent event) {
        for(FieldObjectListener listener: fieldObjectListeners) {
            listener.fieldObjectIsMoved(event);
        }
    }

    public GameActionListener getGameActionObserver() {
        return new GameActionObserver();
    }

    private class GameActionObserver implements GameActionListener {
        @Override
        public void gameStatusChanged(GameActionEvent event) {
            if (event.getStatus() != GameStatus.RUNNING) {
                removeObjects(objectList);
            }
        }
    }
}
