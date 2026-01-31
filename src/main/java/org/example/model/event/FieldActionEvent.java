package org.example.model.event;

import org.example.model.field.FieldObject;

import java.util.EventObject;

public class FieldActionEvent extends EventObject {

    public FieldActionEvent(Object source) {
        super(source);
    }
}
