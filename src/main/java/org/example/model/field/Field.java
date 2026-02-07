package org.example.model.field;

import org.example.model.OwnerObject;

import java.util.ArrayList;
import java.util.List;

public class Field {

    private final int width = 350;
    private final int height = 600;
    private List<FieldObject> objectList;
    private Spawner spawner;

    public Field() {
        objectList = new ArrayList<>();
        spawner = new Spawner();
    }

    public List<Ship> createShips(int numberShips, OwnerObject owner) {
        List<Ship> shipList = new ArrayList<>();
        for(int i = 0; i < numberShips; i++) {
            Point point = new Point(0, 0);
            Ship currentShip = new Ship(point, owner, 5);
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

    public void detectCollision() {}
}
