package org.example.model.event;

import org.example.model.field.FieldObject;
import org.example.view.widget.FieldObjectWidget;

import java.util.EventObject;

public class FieldObjectEvent extends EventObject {

    private FieldObject fieldObject;

    public void setFieldObject(FieldObject fieldObject) {
        this.fieldObject = fieldObject;
    }

    public FieldObject getFieldObject() {
        return fieldObject;
    }

    public FieldObjectEvent(Object source) {
        super(source);
    }
}
