package org.example.model.event;

import java.util.EventListener;

public interface FieldObjectListener extends EventListener {
    void addedFieldObjectOnField(FieldObjectEvent event);
    void removedFieldObjectFromField(FieldObjectEvent event);
    void fieldObjectIsMoved(FieldObjectEvent event);
}
