package model.NewModels.Tile;

import model.NewModels.Vehicle;

public class TileMonitorImpl extends TileBase {
    public TileMonitorImpl(TileBase tileBase) {
        super();
        this.posX = tileBase.getPosX();
        this.posY = tileBase.getPosY();
        this.tileLabel = tileBase.getTileLabel();
        this.imagePath = tileBase.getImagePath();
    }

    @Override
    public synchronized boolean moveVehicleToTile(Vehicle vehicle) {
        if (this.isAvaliable()) {
            setCurrentVehicle(vehicle);
            this.setTileCurrentImage();
            System.out.println("Vehicle moved to tile: " + this);
            return true;
        } else {
            System.out.println("Tile is occupied. Vehicle cannot move to tile: " + this);
            return false;
        }
    }
}
