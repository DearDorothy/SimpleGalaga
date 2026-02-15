package org.example.model;

import org.example.model.event.FieldActionEvent;
import org.example.model.event.FieldActionListener;
import org.example.model.event.GameActionEvent;
import org.example.model.event.GameActionListener;
import org.example.model.field.Field;
import org.example.model.field.Ship;
import org.example.model.manager.EnemyCommander;
import org.example.model.manager.Player;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private final Player player;
    private int playerPoints = 0;
    private final EnemyCommander enemyCommander;
    private final Field field;
    private GameStatus gameStatus;

    public Game(Player player, EnemyCommander enemyCommander, Field field) {
        this.player = player;
        this.enemyCommander = enemyCommander;
        this.field = field;
        start();
    }

    private void start() {
        setGameStatus(GameStatus.RUNNING);
        transferShipsToCommanders();

        // Игровой цикл
//        while(player.getNumberNndestroyedShips() > 0 && enemyCommander.getNumberLivePilots() > 0) {
//            // Идет игровой процесс
//        }

        if(player.getNumberNndestroyedShips() > 0) {
            setGameStatus(GameStatus.PLAYER_WIN);
        } else if(enemyCommander.getNumberLivePilots() > 0) {
            setGameStatus(GameStatus.ENEMY_WIN);
        } else if(player.getNumberNndestroyedShips() == 0 && enemyCommander.getNumberLivePilots() == 0) {
            setGameStatus(GameStatus.DRAW);
        }
        System.out.println("Игра стартовала");
    }

    public void stop() {
        setGameStatus(GameStatus.STOP);
    }

    public void  paused() {
        setGameStatus(GameStatus.PAUSED);
    }

    private void updateGameState() {}

    private GameStatus determineOutcomeGame() {
        GameStatus result = GameStatus.RUNNING;
        return result;
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

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    private void setGameStatus(GameStatus status) {
        if (gameStatus != status) {
            gameStatus = status;
            fireGameStatusIsChanged(gameStatus);
        }
    }

    private class FieldObserver implements FieldActionListener {
        @Override
        public void fieldObjectsCollide(FieldActionEvent event) {

        }
    }

    private final List<GameActionListener> gameActionListeners = new ArrayList<>();

    public void addGameActionListener(GameActionListener listener) {
        gameActionListeners.add(listener);
    }

    public void removeGameActoionListener(GameActionListener listener) {
        gameActionListeners.remove(listener);
    }

    private void fireGameStatusIsChanged(GameStatus status) {
        for (GameActionListener listener: gameActionListeners) {
            GameActionEvent event = new GameActionEvent(this);
            event.setStatus(status);
            listener.gameStatusChanged(event);
        }
    }
}
