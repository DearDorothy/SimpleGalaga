package org.example.model.event;

import java.util.EventListener;

public interface ShipActionListener extends EventListener {
    void shipIsFire(ShipActionEvent event);
    void shipIsDestroyed(ShipActionEvent event);
}
