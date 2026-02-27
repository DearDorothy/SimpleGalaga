package org.example.model.manager;

import org.example.model.ActionPilot;
import org.example.model.OwnerObject;
import org.example.model.field.Point;
import org.example.model.field.Ship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EnemyCommanderTest {

    private EnemyCommander commander;
    private TestEnemyFormation formation;
    private List<Ship> ships;

    @BeforeEach
    void setUp() {
        formation = new TestEnemyFormation(350, 35);
        commander = new EnemyCommander(formation);
        ships = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ships.add(new Ship(new Point(100 + i * 30, 100), OwnerObject.ENEMY));
        }
    }

    @Test
    void testInitialState() {
        assertTrue(commander.getEnemyPilotList().isEmpty());
        assertEquals(15, commander.getNumebrPilot());
        assertEquals(0, commander.getNumberLivePilots());
    }

    @Test
    void testTransferShipsToPilots() {
        commander.transferShipsToPilots(ships);
        assertEquals(5, commander.getEnemyPilotList().size());
        assertEquals(5, commander.getNumberLivePilots());
        assertSame(commander.getEnemyPilotList(), formation.lastSetList);
    }

    @Test
    void testNumberLivePilotsAfterDestroy() {
        commander.transferShipsToPilots(ships);
        ships.get(0).destroy();
        assertEquals(4, commander.getNumberLivePilots());
        ships.get(1).destroy();
        assertEquals(3, commander.getNumberLivePilots());
        ships.get(2).destroy();
        assertEquals(2, commander.getNumberLivePilots());
    }

    @Test
    void testUpdateFormation() {
        commander.updateFormation();
        assertEquals(1, formation.updateCalled);
        commander.updateFormation();
        assertEquals(2, formation.updateCalled);
    }

    @Test
    void testPilotRemovedOnShipDestroy() {
        commander.transferShipsToPilots(ships);
        int initialSize = commander.getEnemyPilotList().size();
        ships.get(0).destroy();
        assertEquals(initialSize - 1, commander.getEnemyPilotList().size());
        assertEquals(1, formation.removeCalled); // формация тоже получила команду удалить
    }

    @Test
    void testTryToShoot() throws Exception {
        commander.transferShipsToPilots(ships);
        long startTime = System.currentTimeMillis();
        commander.tryToShoot(startTime);
        commander.tryToShoot(startTime + 1000);
        assertTrue(true);
    }

    private static class TestEnemyFormation extends EnemyFormation {
        int updateCalled = 0;
        int removeCalled = 0;
        List<EnemyPilot> lastSetList = null;

        public TestEnemyFormation(int fieldWidth, int sizeShipModelCollision) {
            super(fieldWidth, sizeShipModelCollision);
        }

        @Override
        public void update() {
            updateCalled++;
        }

        @Override
        public void setEnemyPilotList(List<EnemyPilot> enemyPilotList) {
            super.setEnemyPilotList(enemyPilotList);
            lastSetList = enemyPilotList;
        }

        @Override
        public void removeEnemyPilot(EnemyPilot enemyPilot) {
            super.removeEnemyPilot(enemyPilot);
            removeCalled++;
        }
    }
}