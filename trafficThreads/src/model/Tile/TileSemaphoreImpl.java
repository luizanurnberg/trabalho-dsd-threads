package model.Tile;

import model.Vehicle;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class TileSemaphoreImpl extends TileBase {
    private Semaphore semaphore = new Semaphore(1);

    private boolean tryAcquire() {
        boolean acquired = false;

        try {
            acquired = this.semaphore.tryAcquire(500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            System.out.println("Failed to acquire Tile. Vehicle cannot move to tile: " + this);
        }

        return acquired;
    }

    @Override
    public boolean moveVehicleToTile(Vehicle vehicle) {
        if (!this.isAvaliable()) {
            return false;
        }

        boolean hasAcquiredTile = this.tryAcquire();

        if (hasAcquiredTile) {
            this.currentVehicle = vehicle;
            this.setTileCurrentImage();

            System.out.println("Vehicle moved to tile: " + this);

            this.semaphore.release();

            vehicle.getCurrentTile().removeVehicleFromTile(vehicle);
            vehicle.setCurrentTile(this);
            return true;
        }

        return false;
    }
}
