package org.example.model.event;

import java.util.EventListener;

public interface ShipFireListener extends EventListener {
    void shipIsFire(ShipFireEvent event);
}
