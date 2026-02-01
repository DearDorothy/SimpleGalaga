package org.example.model.event;

import java.util.EventListener;

public interface GameActionListener extends EventListener {
    void gameStatusChanged(GameActionEvent event);
}
