package org.example.model;

import org.example.model.field.Field;
import org.example.model.field.Ship;
import org.example.model.manager.EnemyCommander;
import org.example.model.manager.Player;

import java.util.List;

public class Game {

    private Player player;
    private EnemyCommander enemyCommander;
    private Field field;
    private GameStatus gameStatus;

    public Game(Player player, EnemyCommander enemyCommander, Field field) {
        this.player = player;
        this.enemyCommander = enemyCommander;
        this.field = field;
        start();
    }

    private void transferShipsToCommanders() {
        List<Ship> shipListForPlayer = field.createShips(3, OwnerObject.PLAYER);
        List<Ship> shipListForEnemy = field.createShips(12, OwnerObject.ENEMY);

        player.setFleetShip(shipListForPlayer);
        enemyCommander.transferShipsToPilots(shipListForEnemy);

        System.out.println("Корабли переданы командирам");
    }

    public Player getPlayer() {
        return player;
    }

    public EnemyCommander getEnemyCommander() {
        return enemyCommander;
    }

    public Field getField() {
        return field;
    }

    public void start() {
        transferShipsToCommanders();
        System.out.println("Игра стартовала");
    }

}
