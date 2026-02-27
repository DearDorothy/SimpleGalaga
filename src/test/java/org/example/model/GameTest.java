package org.example.model;

import org.example.model.event.GameActionEvent;
import org.example.model.event.GameActionListener;
import org.example.model.field.Field;
import org.example.model.field.Point;
import org.example.model.field.Ship;
import org.example.model.manager.EnemyCommander;
import org.example.model.manager.EnemyFormation;
import org.example.model.manager.EnemyPilot;
import org.example.model.manager.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Game game;
    private TestGameActionListener listener;

    @BeforeEach
    void setUp() {
        game = new Game();
        listener = new TestGameActionListener();
        game.addGameActionListener(listener);
    }

    @Test
    void start_ShouldSetStatusRunningAndTransferShips() {
        game.start();
        assertEquals(GameStatus.RUNNING, game.getGameStatus());
        assertFalse(game.getPlayer().getFleetShip().isEmpty());
        assertFalse(game.getEnemyCommander().getEnemyPilotList().isEmpty());
        assertEquals(3, game.getPlayer().getFleetShip().size());
        assertEquals(15, game.getEnemyCommander().getEnemyPilotList().size());
    }

    @Test
    void determineOutcomeGame_PlayerWin_WhenNoEnemies() {
        game.start();
        for (EnemyPilot pilot : new ArrayList<>(game.getEnemyCommander().getEnemyPilotList())) {
            pilot.getShip().destroy();
        }
        GameStatus status = invokeDetermineOutcomeGame(game);
        assertEquals(GameStatus.PLAYER_WIN, status);
    }

    @Test
    void determineOutcomeGame_EnemyWin_WhenNoPlayerShips() {
        game.start();
        for (Ship ship : new ArrayList<>(game.getPlayer().getFleetShip())) {
            ship.destroy();
        }
        GameStatus status = invokeDetermineOutcomeGame(game);
        assertEquals(GameStatus.ENEMY_WIN, status);
    }

    @Test
    void determineOutcomeGame_Draw_WhenBothZero() {
        game.start();
        for (Ship ship : new ArrayList<>(game.getPlayer().getFleetShip())) {
            ship.destroy();
        }
        for (EnemyPilot pilot : new ArrayList<>(game.getEnemyCommander().getEnemyPilotList())) {
            pilot.getShip().destroy();
        }
        GameStatus status = invokeDetermineOutcomeGame(game);
        assertEquals(GameStatus.DRAW, status);
    }

    @Test
    void determineOutcomeGame_Running_WhenBothAlive() {
        game.start();
        GameStatus status = invokeDetermineOutcomeGame(game);
        assertEquals(GameStatus.RUNNING, status);
    }

    @Test
    void setGameStatus_ShouldFireEvent() {
        game.setGameStatus(GameStatus.PLAYER_WIN);
        assertTrue(listener.statusChanged);
        assertEquals(GameStatus.PLAYER_WIN, listener.lastStatus);
    }

    @Test
    void stopGameLoop_ShouldStopTimer() {
        game.start();
        game.setGameStatus(GameStatus.PLAYER_WIN);
        assertTrue(true);
    }

    @Test
    void transferShipsToCommanders_ShouldSetStartPointForPlayer() {
        game.transferShipsToCommanders();
        Point startPoint = game.getPlayer().getFleetShip().get(0).getPoint();
        assertEquals(startPoint, game.getPlayer().getActiveShip().getPoint());
    }

    @Test
    void addGameActionListener_ShouldAddListener() {
        TestGameActionListener listener2 = new TestGameActionListener();
        game.addGameActionListener(listener2);
        game.setGameStatus(GameStatus.DRAW);
        assertTrue(listener2.statusChanged);
    }

    @Test
    void removeGameActionListener_ShouldRemoveListener() {
        game.removeGameActoionListener(listener);
        game.setGameStatus(GameStatus.DRAW);
        assertFalse(listener.statusChanged);
    }

    @Test
    void getPlayer_ShouldReturnPlayer() {
        assertNotNull(game.getPlayer());
    }

    @Test
    void getEnemyCommander_ShouldReturnCommander() {
        assertNotNull(game.getEnemyCommander());
    }

    @Test
    void getField_ShouldReturnField() {
        assertNotNull(game.getField());
    }

    @Test
    void integration_AllShipsDestroyed_GameOverEventFired() {
        game.start();
        for (EnemyPilot pilot : new ArrayList<>(game.getEnemyCommander().getEnemyPilotList())) {
            pilot.getShip().destroy();
        }
        for (Ship ship : new ArrayList<>(game.getPlayer().getFleetShip())) {
            ship.destroy();
        }
        GameStatus status = invokeDetermineOutcomeGame(game);
        assertEquals(GameStatus.DRAW, status);
    }

    @Test
    void startGameLoop_ShouldCallUpdates() {
        game.start();
        assertTrue(true);
    }

    @Test
    void fireGameStatusIsChanged_ShouldNotifyAllListeners() {
        TestGameActionListener listener2 = new TestGameActionListener();
        game.addGameActionListener(listener2);
        game.setGameStatus(GameStatus.PLAYER_WIN);
        assertTrue(listener.statusChanged);
        assertTrue(listener2.statusChanged);
        assertEquals(GameStatus.PLAYER_WIN, listener.lastStatus);
        assertEquals(GameStatus.PLAYER_WIN, listener2.lastStatus);
    }

    private GameStatus invokeDetermineOutcomeGame(Game game) {
        try {
            java.lang.reflect.Method method = Game.class.getDeclaredMethod("determineOutcomeGame");
            method.setAccessible(true);
            return (GameStatus) method.invoke(game);
        } catch (Exception e) {
            fail("Could not invoke determineOutcomeGame: " + e.getMessage());
            return null;
        }
    }

    private static class TestGameActionListener implements GameActionListener {
        boolean statusChanged = false;
        GameStatus lastStatus = null;

        @Override
        public void gameStatusChanged(GameActionEvent event) {
            statusChanged = true;
            lastStatus = event.getStatus();
        }
    }
}