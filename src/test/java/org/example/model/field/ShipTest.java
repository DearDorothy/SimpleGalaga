package org.example.model.field;

import org.example.model.DirectionObjectMovment;
import org.example.model.OwnerObject;
import org.example.model.event.ShipActionEvent;
import org.example.model.event.ShipActionListener;
import org.example.model.field.FieldObject;
import org.example.model.field.Point;
import org.example.model.field.Ship;
import org.example.model.field.bullet.Bullet;
import org.example.model.field.bullet.ammo.AmmoType;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class ShipTest {

    @Test
    void moveLeftShouldDecreaseXBySpeed() {
        Ship ship = new Ship(new Point(100, 100), OwnerObject.PLAYER);
        ship.setDirectionObjectMovment(DirectionObjectMovment.LEFT);

        ship.move();

        assertEquals(97, ship.getPoint().getX());
        assertEquals(100, ship.getPoint().getY());
    }

    @Test
    void moveRightShouldIncreaseXBySpeed() {
        Ship ship = new Ship(new Point(100, 100), OwnerObject.PLAYER);
        ship.setDirectionObjectMovment(DirectionObjectMovment.RIGHT);

        ship.move();

        assertEquals(103, ship.getPoint().getX());
    }

    @Test
    void moveUpShouldDecreaseYBySpeed() {
        Ship ship = new Ship(new Point(100, 100), OwnerObject.PLAYER);
        ship.setDirectionObjectMovment(DirectionObjectMovment.UP);

        ship.move();

        assertEquals(97, ship.getPoint().getY());
    }

    @Test
    void moveDownShouldIncreaseYBySpeed() {
        Ship ship = new Ship(new Point(100, 100), OwnerObject.PLAYER);
        ship.setDirectionObjectMovment(DirectionObjectMovment.DOWN);

        ship.move();

        assertEquals(103, ship.getPoint().getY());
    }

    @Test
    void destroyShouldSetAliveFalse() {
        Ship ship = new Ship(new Point(0, 0), OwnerObject.PLAYER);

        ship.destroy();

        assertFalse(ship.isAlive());
    }

    @Test
    void destroyShouldFireEventOnlyOnce() {
        Ship ship = new Ship(new Point(0, 0), OwnerObject.PLAYER);

        AtomicInteger counter = new AtomicInteger(0);

        ship.addShipActionListener(new ShipActionListener() {
            @Override
            public void shipIsFire(ShipActionEvent event) {}

            @Override
            public void shipIsDestroyed(ShipActionEvent event) {
                counter.incrementAndGet();
            }
        });

        ship.destroy();
        ship.destroy(); // повторный вызов

        assertEquals(1, counter.get());
    }

    @Test
    void fireShouldTriggerEventWithBullet() {
        Ship ship = new Ship(new Point(0, 0), OwnerObject.PLAYER);

        AtomicBoolean fired = new AtomicBoolean(false);
        AtomicReference<Bullet> bulletRef = new AtomicReference<>();

        ship.addShipActionListener(new ShipActionListener() {
            @Override
            public void shipIsFire(ShipActionEvent event) {
                fired.set(true);
                bulletRef.set(event.getBullet());
                assertEquals(ship, event.getShip());
            }

            @Override
            public void shipIsDestroyed(ShipActionEvent event) {}
        });

        ship.fire();

        assertTrue(fired.get());
        assertNotNull(bulletRef.get());
    }

    @Test
    void fireShouldUseCustomAmmoType() {
        Ship ship = new Ship(new Point(0, 0), OwnerObject.PLAYER);

        AmmoType customAmmo = (point, owner, direction) -> {
            Bullet bullet = new Bullet(point, owner, direction) {
                @Override public void collide(FieldObject object) {}
                @Override public void destroy() {}
            };
            return bullet;
        };

        ship.setAmmoType(customAmmo);

        AtomicReference<Bullet> bulletRef = new AtomicReference<>();

        ship.addShipActionListener(new ShipActionListener() {
            @Override
            public void shipIsFire(ShipActionEvent event) {
                bulletRef.set(event.getBullet());
            }

            @Override
            public void shipIsDestroyed(ShipActionEvent event) {}
        });

        ship.fire();

        assertNotNull(bulletRef.get());
    }

    @Test
    void collideWithLethalEnemyShouldDestroyShip() {
        Ship playerShip = new Ship(new Point(0,0), OwnerObject.PLAYER);
        Ship enemyShip = new Ship(new Point(0,0), OwnerObject.ENEMY);
        enemyShip.setLethal(true);

        playerShip.collide(enemyShip);

        assertFalse(playerShip.isAlive());
    }

    @Test
    void collideWithSameOwnerShouldNotDestroyShip() {
        Ship ship1 = new Ship(new Point(0,0), OwnerObject.PLAYER);
        Ship ship2 = new Ship(new Point(0,0), OwnerObject.PLAYER);
        ship2.setLethal(true);

        ship1.collide(ship2);

        assertTrue(ship1.isAlive());
    }
}