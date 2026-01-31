package org.example.model;

import org.example.model.field.Field;
import org.example.model.manager.EnemyCommander;
import org.example.model.manager.Player;

public class Game {

    private Player player;
    private EnemyCommander enemyCommander;
    private Field field;
    private GameStatus gameStatus;

    public Game(Player player, EnemyCommander enemyCommander, Field field) {
        this.player = player;
        this.enemyCommander = enemyCommander;
        this.field = field;
    }

    public void start() {}
}
