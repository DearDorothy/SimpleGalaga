package org.example.model.manager;

import org.example.model.ActionPilot;
import org.example.model.event.EnemyPilotEvent;
import org.example.model.event.EnemyPilotListener;
import org.example.model.field.bullet.ammo.NormalAmmo;
import org.example.model.field.Ship;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * Класс Вражеского командира
 */
public class EnemyCommander {

    private final int NUMBER_PILOT = 15;
    private List<EnemyPilot> enemyPilotList = new ArrayList<>();
    private EnemyFormation enemyFormation;
    private long lastShotTime = 0;
    private static final long SHOT_INTERVAL = 3000;
    private Random random = new Random();

    public EnemyCommander(EnemyFormation enemyFormation) {
        this.enemyFormation = enemyFormation;
    }

    public void setEnemyFormation(EnemyFormation enemyFormation) {
        this.enemyFormation = enemyFormation;
    }

    public List<EnemyPilot> getEnemyPilotList() {
        return enemyPilotList;
    }

    public int getNumebrPilot() {
        return NUMBER_PILOT;
    }

    public int getNumberLivePilots() {
        return (int) enemyPilotList.stream().map(EnemyPilot::getShip).filter(Ship::isAlive).count();
    }

    public void transferShipsToPilots(List<Ship> shipList) {
        for (Ship ship : shipList) {
            EnemyPilot enemyPilot = new EnemyPilot(ship);
            enemyPilot.addEnemyPilotListener(new EnemyPilotObserver());
            enemyPilotList.add(enemyPilot);
        }

        enemyFormation.setEnemyPilotList(enemyPilotList);

        System.out.println("Вражеский командир получил флот кораблей размером в " + enemyPilotList.size()
                + " и передал каждый корабль пилоту");
    }

    public void updateFormation() {
        if (enemyFormation != null) {
            enemyFormation.update();
        }
    }

    public void tryToShoot(long currentTime) {
        if (enemyPilotList.isEmpty()) return;

        if (currentTime - lastShotTime >= SHOT_INTERVAL) {
            int index = random.nextInt(enemyPilotList.size());
            EnemyPilot pilot = enemyPilotList.get(index);
            pilot.getShip().setAmmoType(new NormalAmmo());
            pilot.shipControl(ActionPilot.FIRE);
            lastShotTime = currentTime;
        }
    }

    private void removeEnemyPilot(EnemyPilot enemyPilot) {
        if (enemyPilotList.contains(enemyPilot)) {
            enemyPilotList.remove(enemyPilot);
            enemyFormation.removeEnemyPilot(enemyPilot);
        }
    }

    private class EnemyPilotObserver implements EnemyPilotListener {
        @Override
        public void enemyPilotIsDied(EnemyPilotEvent event) {
            EnemyPilot enemyPilot = event.getEnemyPilot();
            removeEnemyPilot(enemyPilot);
            System.out.println("Дошло");
        }
    }
}
