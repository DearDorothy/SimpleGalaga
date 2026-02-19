package org.example.view.adapter;

import org.example.model.event.ShipActionEvent;
import org.example.model.event.ShipActionListener;

public class ShipActionAdapter implements ShipActionListener {
    @Override
    public void shipIsMoved(ShipActionEvent event) {}

    @Override
    public void shipIsFire(ShipActionEvent event) {}
}
