package model.Tile;

import model.Vehicle;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TileMonitorImpl extends TileBase {
    private final Lock monitor = new ReentrantLock();

    @Override
    public boolean tryAcquire() {
        try{
            return this.monitor.tryLock(500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            System.out.println(e.getStackTrace());
            return false;
        }
    }

    @Override
    public void release() {
        this.monitor.unlock();
    }

    @Override
    public void addVehicle(Vehicle vehicle) {
        synchronized (this) {
            this.vehicle = vehicle;
            this.setTileCurrentImage();
        }
    }
}
