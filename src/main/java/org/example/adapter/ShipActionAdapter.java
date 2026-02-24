package org.example.adapter;

import org.example.model.event.ShipActionEvent;
import org.example.model.event.ShipActionListener;

public class ShipActionAdapter implements ShipActionListener {
    @Override
    public void shipIsFire(ShipActionEvent event) {}

    @Override
    public void shipIsDestroyed(ShipActionEvent event) {}
}
