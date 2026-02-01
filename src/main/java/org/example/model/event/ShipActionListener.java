package org.example.model.event;

import java.util.EventListener;

public interface ShipActionListener extends EventListener {
    void shipIsMoved(ShipActionEvent event);
    void shipIsFire(ShipActionEvent event);
}
