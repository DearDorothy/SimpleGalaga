package org.example.model.manager;

import org.example.model.ActionPilot;
import org.example.model.DirectionObjectMovment;
import org.example.model.OwnerObject;
import org.example.model.field.Point;
import org.example.model.field.Ship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EnemyFormationTest {

    private EnemyFormation formation;
    private List<EnemyPilot> pilots;
    private List<Ship> ships;
    private int fieldWidth = 350;
    private int shipSize = 35;

    @BeforeEach
    void setUp() {
        formation = new EnemyFormation(fieldWidth, shipSize);
        ships = new ArrayList<>();
        pilots = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Ship ship = new Ship(new Point(50 + i * 45, 100), OwnerObject.ENEMY);
            ship.setSizeCollisionModel(shipSize);
            ship.setSpeed(3);
            ships.add(ship);
            EnemyPilot pilot = new EnemyPilot(ship);
            pilots.add(pilot);
        }
        formation.setEnemyPilotList(pilots);
    }

    @Test
    void testUpdateMovesFormationRight() {
        int oldX = ships.get(0).getPoint().getX();
        formation.update();
        assertEquals(oldX + 3, ships.get(0).getPoint().getX());
    }

    @Test
    void testUpdateWhenAtRightEdgeChangesDirection() {
        for (int i = 0; i < pilots.size(); i++) {
            int x = fieldWidth - shipSize - 10 - (pilots.size() - 1 - i) * 45;
            ships.get(i).setPoint(new Point(x, 100));
        }
        formation.update();
        int x0 = ships.get(0).getPoint().getX();
        formation.update();
        assertFalse(ships.get(0).getPoint().getX() < x0);
    }

    @Test
    void testUpdateWhenAtLeftEdgeChangesDirection() {
        for (int i = 0; i < pilots.size(); i++) {
            int x = 10 + i * 45;
            ships.get(i).setPoint(new Point(x, 100));
        }
        formation.update();
        int x0 = ships.get(0).getPoint().getX();
        formation.update();
        assertTrue(ships.get(0).getPoint().getX() > x0);
    }

    @Test
    void testRemoveEnemyPilotRemovesFromList() {
        EnemyPilot pilotToRemove = pilots.get(2);
        formation.removeEnemyPilot(pilotToRemove);
        assertEquals(4, pilots.size());
        assertFalse(pilots.contains(pilotToRemove));
    }

    @Test
    void testIsEmptyTrueWhenNoPilots() {
        formation.setEnemyPilotList(new ArrayList<>());
        assertTrue(formation.isEmpty());
    }

    @Test
    void testIsEmptyFalseWhenPilotsExist() {
        assertFalse(formation.isEmpty());
    }

    @Test
    void testSetEnemyPilotListReplacesList() {
        List<EnemyPilot> newList = new ArrayList<>();
        newList.add(pilots.get(0));
        formation.setEnemyPilotList(newList);
        assertEquals(1, formation.getEnemyPilotList().size());
        assertEquals(pilots.get(0), formation.getEnemyPilotList().get(0));
    }

    @Test
    void testUpdateWhenSinglePilotMovesCorrectly() {
        List<EnemyPilot> single = new ArrayList<>();
        single.add(pilots.get(0));
        formation.setEnemyPilotList(single);
        int oldX = ships.get(0).getPoint().getX();
        formation.update();
        assertEquals(oldX + 3, ships.get(0).getPoint().getX());
    }

    @Test
    void testUpdateChangesDirectionWhenSinglePilotAtEdge() {
        List<EnemyPilot> single = new ArrayList<>();
        single.add(pilots.get(0));
        formation.setEnemyPilotList(single);
        ships.get(0).setPoint(new Point(10, 100));
        formation.update();
        int xAfterFirst = ships.get(0).getPoint().getX();
        formation.update();
        assertTrue(ships.get(0).getPoint().getX() > xAfterFirst);
    }

    @Test
    void testRemoveEnemyPilotWhenNotInListDoesNothing() {
        EnemyPilot outside = new EnemyPilot(new Ship(new Point(0,0), OwnerObject.ENEMY));
        int sizeBefore = pilots.size();
        formation.removeEnemyPilot(outside);
        assertEquals(sizeBefore, pilots.size());
    }
}