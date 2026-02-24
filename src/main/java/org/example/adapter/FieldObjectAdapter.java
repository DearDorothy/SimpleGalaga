package org.example.adapter;

import org.example.model.event.FieldObjectEvent;
import org.example.model.event.FieldObjectListener;

public class FieldObjectAdapter implements FieldObjectListener {
    @Override
    public void addFieldObjectOnField(FieldObjectEvent event) {}

    @Override
    public void removeFieldObjectFromField(FieldObjectEvent event) {}

    @Override
    public void fieldObjectIsMoved(FieldObjectEvent event) {}
}
