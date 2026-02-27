package org.example.model.field;

import org.example.model.field.Point;
import org.example.model.field.SpawnerShips;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SpawnerShipsTest {

    @Test
    void createPlayerShipsPositionsShouldReturnEmptyForZero() {
        List<Point> points = SpawnerShips.createPlayerShipsPositions(0, 300, 600);
        assertTrue(points.isEmpty());
    }

    @Test
    void createPlayerShipsPositionsShouldReturnEmptyForNegative() {
        List<Point> points = SpawnerShips.createPlayerShipsPositions(-5, 300, 600);
        assertTrue(points.isEmpty());
    }

    @Test
    void firstPlayerShipShouldBeCenteredHorizontally() {
        int fieldWidth = 300;
        int fieldHeight = 600;

        List<Point> points = SpawnerShips.createPlayerShipsPositions(1, fieldWidth, fieldHeight);

        Point p = points.get(0);

        int expectedX = (fieldWidth - 35) / 2;
        int expectedY = fieldHeight - 35 - 60;

        assertEquals(expectedX, p.getX());
        assertEquals(expectedY, p.getY());
    }

    @Test
    void shouldCreateCorrectNumberOfPlayerShips() {
        List<Point> points = SpawnerShips.createPlayerShipsPositions(3, 300, 600);
        assertEquals(3, points.size());
    }

    @Test
    void additionalPlayerShipsShouldBeOutsideField() {
        List<Point> points = SpawnerShips.createPlayerShipsPositions(3, 300, 600);

        Point second = points.get(1);
        Point third = points.get(2);

        assertTrue(second.getX() < 0);
        assertTrue(second.getY() < 0);

        assertTrue(third.getX() < 0);
        assertTrue(third.getY() < 0);
    }

    @Test
    void createEnemyShipsPositionsShouldReturnEmptyForZero() {
        List<Point> points = SpawnerShips.createEnemyShipsPositions(0, 350);
        assertTrue(points.isEmpty());
    }

    @Test
    void shouldCreateCorrectNumberOfEnemyShips() {
        List<Point> points = SpawnerShips.createEnemyShipsPositions(7, 350);
        assertEquals(7, points.size());
    }

    @Test
    void enemyShipsShouldBePositionedInRows() {
        List<Point> points = SpawnerShips.createEnemyShipsPositions(6, 350);

        Point firstRowFirst = points.get(0);
        Point secondRowFirst = points.get(5); // 6-й корабль → новая строка

        assertTrue(secondRowFirst.getY() > firstRowFirst.getY());
    }

    @Test
    void enemyShipsShouldBeCenteredHorizontally() {
        int fieldWidth = 350;

        List<Point> points = SpawnerShips.createEnemyShipsPositions(5, fieldWidth);

        Point first = points.get(0);
        Point last = points.get(4);

        int formationWidth = 5 * 35 + 4 * 10;
        int expectedStartX = (fieldWidth - formationWidth) / 2;

        assertEquals(expectedStartX, first.getX());
        assertEquals(expectedStartX + 4 * (35 + 10), last.getX());
    }

    @Test
    void enemyShipsShouldNotExceedRequestedAmount() {
        List<Point> points = SpawnerShips.createEnemyShipsPositions(12, 350);

        assertEquals(12, points.size());
    }

}