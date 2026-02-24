package org.example.model.event;

import org.example.model.manager.EnemyPilot;

import java.util.EventListener;

public interface EnemyPilotListener extends EventListener {
    void enemyPilotIsDied(EnemyPilotEvent event);
}
