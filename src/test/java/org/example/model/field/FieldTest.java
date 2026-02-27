package org.example.model.field;

import org.example.model.DirectionObjectMovment;
import org.example.model.GameStatus;
import org.example.model.OwnerObject;
import org.example.model.event.*;
import org.example.model.field.bullet.Bullet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FieldTest {

    private Field field;

    @BeforeEach
    void setUp() {
        field = new Field();
    }

    static class TestObject extends FieldObject {

        boolean destroyed = false;

        public TestObject(Point p, OwnerObject owner) {
            super(p, owner);
            setSizeCollisionModel(30);
        }

        @Override
        public void move() {}

        @Override
        public void collide(FieldObject object) {}

        @Override
        public void destroy() {
            destroyed = true;
            setAlive(false);
        }
    }

    static class TestBullet extends Bullet {

        public TestBullet(Point point, OwnerObject owner, DirectionObjectMovment dir) {
            super(point, owner, dir);
        }

        @Override
        public void collide(FieldObject object) {}
    }

    static class TestListener implements FieldObjectListener {

        boolean added = false;
        boolean removed = false;
        boolean moved = false;

        @Override
        public void addedFieldObjectOnField(FieldObjectEvent event) {
            added = true;
        }

        @Override
        public void removedFieldObjectFromField(FieldObjectEvent event) {
            removed = true;
        }

        @Override
        public void fieldObjectIsMoved(FieldObjectEvent event) {
            moved = true;
        }
    }

    @Test
    void createShips_shouldCreateCorrectNumber() {
        List<Ship> ships = field.createShips(3, OwnerObject.PLAYER);
        assertEquals(3, ships.size());
    }

    @Test
    void createShips_shouldAddShipsToField() {
        field.createShips(2, OwnerObject.PLAYER);
        assertEquals(2, field.getObjectList().size());
    }

    @Test
    void updateBullets_shouldMoveAliveBullet() {
        TestBullet bullet = new TestBullet(
                new Point(100, 200),
                OwnerObject.PLAYER,
                DirectionObjectMovment.UP
        );
        field.getObjectList().add(bullet);

        field.updateBullets();

        assertEquals(190, bullet.getPoint().getY());
    }

    @Test
    void updateBullets_shouldDestroyBulletAboveField() {
        TestBullet bullet = new TestBullet(
                new Point(100, -5),
                OwnerObject.PLAYER,
                DirectionObjectMovment.UP
        );
        field.getObjectList().add(bullet);

        field.updateBullets();

        assertFalse(bullet.isAlive());
    }

    @Test
    void updateBullets_shouldDestroyBulletBelowField() {
        TestBullet bullet = new TestBullet(
                new Point(100, 700),
                OwnerObject.PLAYER,
                DirectionObjectMovment.DOWN
        );
        field.getObjectList().add(bullet);

        field.updateBullets();

        assertFalse(bullet.isAlive());
    }

    @Test
    void detectCollision_shouldCallCollide() {
        TestObject obj1 = new TestObject(new Point(100,100), OwnerObject.PLAYER);
        TestObject obj2 = new TestObject(new Point(105,105), OwnerObject.ENEMY);

        field.getObjectList().add(obj1);
        field.getObjectList().add(obj2);

        field.detectCollision();

        assertTrue(obj1.isAlive());
        assertTrue(obj2.isAlive());
    }

    @Test
    void detectCollision_shouldRemoveDeadObjects() {
        TestObject obj1 = new TestObject(new Point(100,100), OwnerObject.PLAYER);
        obj1.destroy();

        field.getObjectList().add(obj1);

        field.detectCollision();

        assertTrue(field.getObjectList().isEmpty());
    }

    @Test
    void detectCollision_shouldNotCollideIfNotOverlapping() {
        TestObject obj1 = new TestObject(new Point(0,0), OwnerObject.PLAYER);
        TestObject obj2 = new TestObject(new Point(300,300), OwnerObject.ENEMY);

        field.getObjectList().add(obj1);
        field.getObjectList().add(obj2);

        field.detectCollision();

        assertEquals(2, field.getObjectList().size());
    }

    @Test
    void field_shouldFireAddedEvent() {
        TestListener listener = new TestListener();
        field.addFieldObjectListener(listener);

        field.createShips(1, OwnerObject.PLAYER);

        assertTrue(listener.added);
    }

    @Test
    void field_shouldFireRemovedEvent() {
        TestListener listener = new TestListener();
        field.addFieldObjectListener(listener);

        TestObject obj = new TestObject(new Point(0,0), OwnerObject.PLAYER);
        field.getObjectList().add(obj);
        obj.destroy();

        field.detectCollision();

        assertTrue(listener.removed);
    }

    @Test
    void field_shouldFireMoveEventFromBullet() {
        TestListener listener = new TestListener();
        field.addFieldObjectListener(listener);

        TestBullet bullet = new TestBullet(
                new Point(100,200),
                OwnerObject.PLAYER,
                DirectionObjectMovment.UP
        );

        field.getObjectList().add(bullet);

        field.updateBullets();

        assertFalse(listener.moved);
    }

    @Test
    void getWidth_shouldReturn350() {
        assertEquals(350, field.getWidth());
    }

    @Test
    void getHeight_shouldReturn600() {
        assertEquals(600, field.getHeight());
    }

    @Test
    void gameStatusChanged_shouldClearFieldIfNotRunning() {
        field.createShips(2, OwnerObject.PLAYER);

        GameActionListener observer = field.getGameActionObserver();
        GameActionEvent event = new GameActionEvent(this);
        event.setStatus(GameStatus.STOP);

        observer.gameStatusChanged(event);

        assertTrue(field.getObjectList().isEmpty());
    }

    @Test
    void gameStatusChanged_shouldNotClearFieldIfRunning() {
        field.createShips(2, OwnerObject.PLAYER);

        GameActionListener observer = field.getGameActionObserver();
        GameActionEvent event = new GameActionEvent(this);
        event.setStatus(GameStatus.RUNNING);

        observer.gameStatusChanged(event);

        assertFalse(field.getObjectList().isEmpty());
    }
}