package org.example.model;

import org.example.model.event.GameActionEvent;
import org.example.model.event.GameActionListener;
import org.example.model.field.Field;
import org.example.model.field.Point;
import org.example.model.field.Ship;
import org.example.model.manager.EnemyCommander;
import org.example.model.manager.EnemyFormation;
import org.example.model.manager.Player;
import org.example.utils.Delays;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Класс Игры
*/
public class Game {

    private final Player player;
    private int playerPoints = 0;
    private final EnemyCommander enemyCommander;
    private final Field field;

    private GameStatus gameStatus;
    private Timer gameTimer;

    public Game() {
        player = new Player();
        field = new Field();
        addGameActionListener(field.getGameActionObserver());
        EnemyFormation enemyFormation = new EnemyFormation(field.getWidth(), field.get_SIZE_COLLISION_MODEL_SHIP());
        enemyCommander = new EnemyCommander(enemyFormation);
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

    void setGameStatus(GameStatus status) {
        if (gameStatus != status) {
            gameStatus = status;
            fireGameStatusIsChanged(gameStatus);
        }
    }

    public void start() {
        setGameStatus(GameStatus.RUNNING);
        transferShipsToCommanders();
        startGameLoop();
        System.out.println("Игра стартовала");
    }

    private void startGameLoop() {
        gameTimer = new Timer(Delays.GAME_DELAY, e -> {
            long currentTime = System.currentTimeMillis();
            if (gameStatus == GameStatus.RUNNING) {
                updateEnemyFaromation();
                updateBullets();
                detectCollision();
                tryToShootEnemyPilot(currentTime);

                GameStatus status = determineOutcomeGame();
                if (status != GameStatus.RUNNING) {
                    System.out.println(status);
                    setGameStatus(status);
                    stopGameLoop();
                }
            }
        });
        gameTimer.start();
    }

    private void updateEnemyFaromation() {
        enemyCommander.updateFormation();
    }

    private void updateBullets() {
        field.updateBullets();
    }

    private void detectCollision() {
        field.detectCollision();
    }

    private void tryToShootEnemyPilot(long currentTime) {
        enemyCommander.tryToShoot(currentTime);
    }

    private GameStatus determineOutcomeGame() {
        GameStatus status;

        int playerShips = player.getNumberNndestroyedShips();
        int enemyPilots = enemyCommander.getNumberLivePilots();

        if (playerShips == 0 && enemyPilots == 0) {
            status = GameStatus.DRAW;
        } else if (playerShips == 0) {
            status = GameStatus.ENEMY_WIN;
        } else if (enemyPilots == 0) {
            status = GameStatus.PLAYER_WIN;
        } else {
            status = GameStatus.RUNNING;
        }

        return status;
    }

    private void stopGameLoop() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
    }

    void transferShipsToCommanders() {

        int numberShipPLayer = player.getNumberShip();
        int numberEnemyPilot = enemyCommander.getNumebrPilot();

        List<Ship> shipListForPlayer = field.createShips(numberShipPLayer, OwnerObject.PLAYER);
        if (!shipListForPlayer.isEmpty()) {
            Point startPoint = shipListForPlayer.get(0).getPoint();
            player.setStartPoint(startPoint);
        }
        List<Ship> shipListForEnemy = field.createShips(numberEnemyPilot, OwnerObject.ENEMY);

        player.setFleetShip(shipListForPlayer);
        enemyCommander.transferShipsToPilots(shipListForEnemy);

        System.out.println("Корабли переданы командирам");
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
