package org.example.view.widget;

import org.example.model.event.ShipActionEvent;
import org.example.model.event.ShipActionListener;
import org.example.model.field.Ship;

import javax.swing.*;

public class ShipWidget extends FieldObjectWidget implements ShipActionListener {

    @Override
    public void shipIsFire(ShipActionEvent event) {
    }

    @Override
    public void shipIsMoved(ShipActionEvent event) {
    }
}
