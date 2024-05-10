package model.Tile;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class TileSemaphoreImpl extends TileBase {
    private final Semaphore semaphore = new Semaphore(1);

    @Override
    public void release() {
        this.semaphore.release();
    }

    @Override
    public boolean tryAcquire() {
        if (super.vehicle != null) {
            return false;
        }

        boolean acquired = false;

        try {
            acquired = this.semaphore.tryAcquire(generateRandomCooldown(1000, 2000), TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            System.out.println("Failed to acquire Tile. Vehicle cannot move to tile: " + this);
        }

        return acquired;
    }
}
