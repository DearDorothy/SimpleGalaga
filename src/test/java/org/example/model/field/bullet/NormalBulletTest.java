package org.example.model.field.bullet;

import org.example.adapter.FieldObjectAdapter;
import org.example.model.DirectionObjectMovment;
import org.example.model.OwnerObject;
import org.example.model.event.FieldObjectEvent;
import org.example.model.event.FieldObjectListener;
import org.example.model.field.FieldObject;
import org.example.model.field.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NormalBulletTest {

    private NormalBullet bullet;

    static class TestFieldObject extends FieldObject {

        private boolean destroyed = false;

        public TestFieldObject(Point point, OwnerObject ownerObject, boolean lethal) {
            super(point, ownerObject);
            setLethal(lethal);
        }

        @Override
        public void move() { }

        @Override
        public void destroy() {
            destroyed = true;
            setAlive(false);
        }

        @Override
        public void collide(FieldObject object) { }

        public boolean isDestroyed() {
            return destroyed;
        }
    }

    static class TestListener extends FieldObjectAdapter {

        boolean movedCalled = false;

        @Override
        public void fieldObjectIsMoved(FieldObjectEvent event) {
            movedCalled = true;
        }
    }

    @BeforeEach
    void setUp() {
        bullet = new NormalBullet(
                new Point(100, 200),
                OwnerObject.PLAYER,
                DirectionObjectMovment.UP
        );
    }

    @Test
    void collide_shouldDestroyEnemyLethalObject() {
        TestFieldObject enemy =
                new TestFieldObject(new Point(0,0), OwnerObject.ENEMY, true);

        bullet.collide(enemy);

        assertTrue(enemy.isDestroyed());
        assertFalse(bullet.isAlive());
    }

    @Test
    void collide_shouldNotDestroySameOwner() {
        TestFieldObject ally =
                new TestFieldObject(new Point(0,0), OwnerObject.PLAYER, true);

        bullet.collide(ally);

        assertFalse(ally.isDestroyed());
        assertTrue(bullet.isAlive());
    }

    @Test
    void collide_shouldNotDestroyNonLethal() {
        TestFieldObject enemy =
                new TestFieldObject(new Point(0,0), OwnerObject.ENEMY, false);

        bullet.collide(enemy);

        assertFalse(enemy.isDestroyed());
        assertTrue(bullet.isAlive());
    }

    @Test
    void collide_shouldDoNothingIfBulletDead() {
        bullet.destroy();

        TestFieldObject enemy =
                new TestFieldObject(new Point(0,0), OwnerObject.ENEMY, true);

        bullet.collide(enemy);

        assertFalse(enemy.isDestroyed());
        assertFalse(bullet.isAlive());
    }

    @Test
    void collide_shouldSetBulletDeadAfterHit() {
        TestFieldObject enemy =
                new TestFieldObject(new Point(0,0), OwnerObject.ENEMY, true);

        bullet.collide(enemy);

        assertFalse(bullet.isAlive());
    }

    @Test
    void move_shouldMoveUp() {
        bullet.move();

        assertEquals(190, bullet.getPoint().getY());
        assertEquals(100, bullet.getPoint().getX());
    }

    @Test
    void move_shouldMoveDown() {
        NormalBullet downBullet =
                new NormalBullet(new Point(100,200),
                        OwnerObject.PLAYER,
                        DirectionObjectMovment.DOWN);

        downBullet.move();

        assertEquals(210, downBullet.getPoint().getY());
        assertEquals(100, downBullet.getPoint().getX());
    }

    @Test
    void destroy_shouldSetAliveFalseOnlyOnce() {
        bullet.destroy();
        bullet.destroy();

        assertFalse(bullet.isAlive());
    }

    @Test
    void move_shouldTriggerListener() {
        TestListener listener = new TestListener();
        bullet.addFieldObjectListener(listener);

        bullet.move();

        assertTrue(listener.movedCalled);
    }

    @Test
    void destroyedBullet_shouldNotCollide() {
        bullet.destroy();

        TestFieldObject enemy =
                new TestFieldObject(new Point(0,0), OwnerObject.ENEMY, true);

        bullet.collide(enemy);

        assertFalse(enemy.isDestroyed());
    }
}