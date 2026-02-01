package org.example.model.event;

import org.example.model.field.Field;
import org.example.model.field.FieldObject;

import java.util.EventListener;
import java.util.List;

public interface FieldActionListener extends EventListener {
    void fieldObjectsCollide(FieldActionEvent event);
}
