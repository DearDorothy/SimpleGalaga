package org.example.model.field;

import java.util.List;

public class Field {

    private final int width = 350;
    private final int height = 600;
    private List<FieldObject> objectList;

    public Field() {}

    public int getWidth() { return width; }

    public int getHeight() { return height; }

    public void detectCollision() {}
}
