package model.Tile;

import model.Vehicle;

public class TileMonitorImpl extends TileBase {
    @Override
    public synchronized boolean reserveTile(Vehicle vehicle) {
        if (this.reservedFor != null && this.reservedFor != vehicle) {
            return false;
        }

        this.setReserved(vehicle);
        return true;
    }

    @Override
    public synchronized void removeReservedVehicle(Vehicle vehicle) {
        if (reservedFor != null && reservedFor == vehicle) {
            this.setReserved(null);
        }
    }

    @Override
    public synchronized boolean moveVehicleToTile(Vehicle vehicle) {
        if (this.isAvaliable()) {
            setCurrentVehicle(vehicle);
            this.setTileCurrentImage();
            System.out.println("Vehicle moved to tile: " + this);

            vehicle.getCurrentTile().removeVehicleFromTile();
            vehicle.setCurrentTile(this);
            return true;
        } else {
            System.out.println("Tile is occupied. Vehicle cannot move to tile: " + this);
            return false;
        }
    }
}
