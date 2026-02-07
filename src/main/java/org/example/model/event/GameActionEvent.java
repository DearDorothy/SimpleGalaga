package org.example.model.event;

import org.example.model.GameStatus;
import org.example.model.manager.EnemyCommander;
import org.example.model.manager.Player;

import java.util.EventObject;

public class GameActionEvent extends EventObject {

    private Player player;
    private EnemyCommander enemyCommander;
    private GameStatus status;

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setEnemyCommander(EnemyCommander enemyCommander) {
        this.enemyCommander = enemyCommander;
    }

    public EnemyCommander getEnemyCommander() {
        return enemyCommander;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public GameStatus getStatus() {
        return status;
    }

    public GameActionEvent(Object source) {
        super(source);
    }
}
