package org.example.model.event;

import org.example.model.DirectionObjectMovment;
import org.example.model.field.FieldObject;
import org.example.view.widget.FieldObjectWidget;

import java.util.EventObject;

public class FieldObjectEvent extends EventObject {

    private FieldObject fieldObject;
    private DirectionObjectMovment directionObjectMovment;

    public void setFieldObject(FieldObject fieldObject) {
        this.fieldObject = fieldObject;
    }

    public void setDirectionObjectMovment(DirectionObjectMovment directionObjectMovment) {
        this.directionObjectMovment = directionObjectMovment;
    }

    public FieldObject getFieldObject() {
        return fieldObject;
    }

    public DirectionObjectMovment getDirectionObjectMovment() {
        return directionObjectMovment;
    }

    public FieldObjectEvent(Object source) {
        super(source);
    }
}
