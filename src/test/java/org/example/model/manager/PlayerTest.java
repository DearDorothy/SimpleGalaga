package org.example.model.manager;

import org.example.model.ActionPilot;
import org.example.model.DirectionObjectMovment;
import org.example.model.OwnerObject;
import org.example.model.event.ShipActionEvent;
import org.example.model.event.ShipActionListener;
import org.example.model.field.Point;
import org.example.model.field.Ship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;
    private List<Ship> fleet;
    private Point startPoint;
    private Ship ship1;
    private Ship ship2;
    private Ship ship3;

    @BeforeEach
    void setUp() {
        player = new Player();
        ship1 = new Ship(new Point(100, 500), OwnerObject.PLAYER);
        ship2 = new Ship(new Point(200, 500), OwnerObject.PLAYER);
        ship3 = new Ship(new Point(300, 500), OwnerObject.PLAYER);
        fleet = new ArrayList<>(List.of(ship1, ship2, ship3));
        startPoint = new Point(150, 550);
        player.setStartPoint(startPoint);
    }

    @Test
    void testSetFleetShipShouldSetActiveShipToFirst() {
        player.setFleetShip(fleet);
        assertEquals(ship1, player.getActiveShip());
        assertEquals(3, player.getFleetShip().size());
    }

    @Test
    void testGetNumberUndestroyedShips() {
        player.setFleetShip(fleet);
        assertEquals(3, player.getNumberNndestroyedShips());
        ship1.setAlive(false);
        assertEquals(2, player.getNumberNndestroyedShips());
        ship2.setAlive(false);
        assertEquals(1, player.getNumberNndestroyedShips());
        ship3.setAlive(false);
        assertEquals(0, player.getNumberNndestroyedShips());
    }

    @Test
    void testShipControlMoveShouldMoveActiveShip() {
        player.setFleetShip(fleet);
        ship1.setDirectionObjectMovment(DirectionObjectMovment.LEFT);
        int oldX = ship1.getPoint().getX();
        player.shipControl(ActionPilot.MOVE);
        assertEquals(oldX - ship1.getSpeed(), ship1.getPoint().getX());
    }

    @Test
    void testShipControlFireShouldGenerateEvent() {
        player.setFleetShip(fleet);
        TestShipActionListener listener = new TestShipActionListener();
        ship1.addShipActionListener(listener);
        player.shipControl(ActionPilot.FIRE);
        assertTrue(listener.fireCalled);
        assertNotNull(listener.lastBullet);
    }

    @Test
    void testWhenActiveShipDestroyedShouldRemoveAndActivateNext() {
        player.setFleetShip(fleet);
        ship1.destroy();
        assertFalse(player.getFleetShip().contains(ship1));
        assertEquals(ship2, player.getActiveShip());
    }

    @Test
    void testWhenActiveShipDestroyedNewActiveShipMovesToStartPoint() {
        player.setFleetShip(fleet);
        ship1.destroy();
        assertEquals(startPoint.getX(), ship2.getPoint().getX());
        assertEquals(startPoint.getY(), ship2.getPoint().getY());
    }

    @Test
    void testWhenNonActiveShipDestroyedOnlyThatShipIsRemoved() {
        player.setFleetShip(fleet);
        ship2.destroy();
        assertFalse(player.getFleetShip().contains(ship2));
        assertEquals(ship1, player.getActiveShip());
        assertEquals(100, ship1.getPoint().getX()); // позиция не изменилась
    }

    @Test
    void testWhenAllShipsDestroyedActiveShipBecomesNull() {
        player.setFleetShip(fleet);
        ship1.destroy();
        ship2.destroy();
        ship3.destroy();
        assertNull(player.getActiveShip());
        assertTrue(player.getFleetShip().isEmpty());
    }

    @Test
    void testWhenStartPointNotSetNewActiveShipDoesNotMove() {
        Player playerWithoutStart = new Player();
        playerWithoutStart.setFleetShip(fleet);
        ship1.destroy();
        assertEquals(200, ship2.getPoint().getX()); // исходная координата
        assertEquals(500, ship2.getPoint().getY());
    }

    @Test
    void testWhenOnlyOneShipDestroyedActiveShipBecomesNull() {
        List<Ship> singleShipFleet = new ArrayList<>(List.of(ship1));
        player.setFleetShip(singleShipFleet);
        ship1.destroy();
        assertNull(player.getActiveShip());
        assertTrue(player.getFleetShip().isEmpty());
    }

    // Вспомогательный слушатель для проверки событий
    private static class TestShipActionListener implements ShipActionListener {
        boolean fireCalled = false;
        Object lastBullet = null;

        @Override
        public void shipIsFire(ShipActionEvent event) {
            fireCalled = true;
            lastBullet = event.getBullet();
        }

        @Override
        public void shipIsDestroyed(ShipActionEvent event) {}
    }
}