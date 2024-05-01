package model.NewModels.Tile;

import model.NewModels.Vehicle;

import java.util.concurrent.Semaphore;

public class TileSemaphoreImpl extends TileBase implements ITile {
    private Semaphore semaphore = new Semaphore(1);

    @Override
    public void moveVehicleToTile(Vehicle vehicle) {
        try {
            semaphore.acquire();
            if (this.isAvaliable()) {
                this.currentVehicle = vehicle;
                System.out.println("Vehicle moved to tile: " + this);
            } else {
                System.out.println("Tile is occupied. Vehicle cannot move to tile: " + this);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }
}
