package org.example.model;

import org.example.model.field.Field;
import org.example.model.field.Ship;
import org.example.model.manager.EnemyCommander;
import org.example.model.manager.Player;

public class Test {
    public static void main(String[] args) {

        Player player = new Player();
        EnemyCommander enemyCommander = new EnemyCommander();
        Field field = new Field();

        Game game = new Game(player, enemyCommander, field);
    }
}
