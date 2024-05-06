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
    public boolean reserveTile(Vehicle vehicle) {
        if (!this.isAvaliable()) {
            return false;
        }

        boolean hasAcquiredTile = this.tryAcquire();

        if (!hasAcquiredTile) {
            return false;
        }

        this.setReserved(vehicle);

        if (this.reservedFor != null && this.reservedFor != vehicle) {
            return false;
        }

        if (this.currentVehicle != null && this.currentVehicle != vehicle) {
            return false;
        }

        return true;
    }

    @Override
    public void removeReservedVehicle(Vehicle vehicle) {
        if (reservedFor != null && reservedFor == vehicle) {
            this.setReserved(null);
        }
        this.semaphore.release();
    }

    @Override
    public boolean moveVehicleToTile(Vehicle vehicle) {
        if (!this.isAvaliable()) {
            return false;
        }

//        boolean hasAcquiredTile = this.tryAcquire();

//        if (hasAcquiredTile) {
        this.currentVehicle = vehicle;
        this.setTileCurrentImage();

//            System.out.println("Vehicle moved to tile: " + this);

        vehicle.getCurrentTile().removeVehicleFromTile(vehicle);
        vehicle.setCurrentTile(this);
        this.semaphore.release();
        return true;
//        }

//        return false;
    }
}
