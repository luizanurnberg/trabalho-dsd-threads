package model.Tile;

import model.Vehicle;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TileMonitorImpl extends TileBase {
    private final Lock monitor = new ReentrantLock();

    @Override
    public boolean tryAcquire() {
        boolean acquired = false;
        try {
            acquired = this.monitor.tryLock(generateRandomCooldown(1000, 2000), TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            System.out.println(e.getStackTrace());
        }
        return acquired;
    }

    @Override
    public void release() {
        try {
            this.monitor.unlock();
        } catch (Exception e) { }
    }

    @Override
    public void addVehicle(Vehicle vehicle) {
        synchronized (this) {
            this.vehicle = vehicle;
            this.setTileCurrentImage();
        }
    }
}
