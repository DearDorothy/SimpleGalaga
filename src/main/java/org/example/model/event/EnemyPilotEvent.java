package org.example.model.event;

import org.example.model.manager.EnemyPilot;

import java.util.EventObject;

public class EnemyPilotEvent extends EventObject {

    private EnemyPilot enemyPilot;

    public void setEnemyPilot(EnemyPilot enemyPilot) {
        this.enemyPilot = enemyPilot;
    }

    public EnemyPilot getEnemyPilot() {
        return enemyPilot;
    }

    public EnemyPilotEvent(Object source) {
        super(source);
    }
}
