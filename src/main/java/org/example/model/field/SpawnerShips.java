package org.example.model.field;

import org.example.Main;
import org.example.model.OwnerObject;

import java.util.ArrayList;
import java.util.List;

public class SpawnerShips {

    private static final int SHIP_SIZE = 35;
    private static final int HORIZONTAL_GAP = 10;
    private static final int VERTICAL_GAP = 10;
    private static final int ENEMY_START_Y = 100;
    private static final int NUMBER_ENEMY_IN_ROW = 5;
    private static final int PLAYER_START_Y_OFFSET = 60;

    public static List<Point> createPlayerShipsPositions(int numberShips, int fieldWidth, int fieldHeight) {
        List<Point> points = new ArrayList<>();
        if (numberShips <= 0) return points;

        int activeShipX = (fieldWidth - SHIP_SIZE) / 2;
        int activeShipY = fieldHeight - SHIP_SIZE - PLAYER_START_Y_OFFSET;
        points.add(new Point(activeShipX, activeShipY));

        // Доделать спавн кораблей иргока
        for(int i = 1; i < numberShips; i++) {
            int x = 0;
            int y = 0;
            points.add(new Point(x, y));
        }

        return points;
    }

    public static List<Point> createEnemyShipsPositions(int numberShips, int fieldWidth) {
        List<Point> points = new ArrayList<>();
        if (numberShips <= 0) return points;

        int rows = (int) Math.ceil((double) numberShips / NUMBER_ENEMY_IN_ROW);

        int totalWidth = NUMBER_ENEMY_IN_ROW * SHIP_SIZE + (NUMBER_ENEMY_IN_ROW - 1) * HORIZONTAL_GAP;
        int startX = (fieldWidth - totalWidth) / 2;

        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < NUMBER_ENEMY_IN_ROW; col++) {
                int index = row * NUMBER_ENEMY_IN_ROW + col;
                if (index >= numberShips) break;

                int x = startX + col * (SHIP_SIZE + HORIZONTAL_GAP);
                int y = ENEMY_START_Y + row * (SHIP_SIZE + VERTICAL_GAP);
                points.add(new Point(x, y));
            }
        }
        return points;
    }
}