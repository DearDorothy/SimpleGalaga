package org.example.model.manager;

import org.example.model.field.Ship;

import java.util.ArrayList;
import java.util.List;

public class EnemyCommander {

    private List<EnemyPilot> enemyPilotList = new ArrayList<>();

    public EnemyCommander() {}

    public void transferShipsToPilots(List<Ship> shipList) {
        for (Ship ship : shipList) {
            EnemyPilot enemyPilot = new EnemyPilot(ship);
            enemyPilotList.add(enemyPilot);
        }
        System.out.println("Вражеский командир получил флот кораблей размером в " + enemyPilotList.size()
                + " и передал каждый корабль пилоту");
    }

    public List<EnemyPilot> getEnemyPilotList() {
        return enemyPilotList;
    }

    public int getNumberLivePilots() {
        return enemyPilotList.size();
    }
}
