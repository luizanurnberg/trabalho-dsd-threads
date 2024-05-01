package model.NewModels.Tile;

import model.NewModels.Vehicle;

public class TileMonitorImpl extends TileBase implements ITile {
    @Override
    public synchronized void moveVehicleToTile(Vehicle vehicle) {
        if (this.isAvaliable()) {
            this.currentVehicle = vehicle;
            System.out.println("Vehicle moved to tile: " + this);
        } else {
            System.out.println("Tile is occupied. Vehicle cannot move to tile: " + this);
        }
    }
}
