package org.example.model.event;

import java.util.EventListener;

public interface ShipMoveListener extends EventListener {
    void shipIsMoved(ShipMoveEvent event);
}
