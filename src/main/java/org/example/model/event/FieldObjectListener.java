package org.example.model.event;

import java.util.EventListener;

public interface FieldObjectListener extends EventListener {
    void addFieldObjectOnField(FieldObjectEvent event);
    void removeFieldObjectFromField(FieldObjectEvent event);
}
