package org.example.model.event;

public interface ShipActionListener {
    void shipIsMoved(ShipActionEvent event);
    void shipIsFire(ShipActionEvent event);
}
