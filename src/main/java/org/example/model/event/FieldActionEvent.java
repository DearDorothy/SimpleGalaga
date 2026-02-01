package org.example.model.event;

import org.example.model.field.FieldObject;

import java.util.EventObject;
import java.util.List;

public class FieldActionEvent extends EventObject {

    private List<FieldObject> listCollidedObjects;

    public void setListCollidedObjects(List<FieldObject> listCollidedObjects) { this.listCollidedObjects = listCollidedObjects; }

    public List<FieldObject> getListCollidedObjects() { return listCollidedObjects; }

    public FieldActionEvent(Object source) {
        super(source);
    }
}
