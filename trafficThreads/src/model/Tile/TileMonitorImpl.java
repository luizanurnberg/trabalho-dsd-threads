package model.Tile;

import model.Vehicle;

public class TileMonitorImpl extends TileBase {
    @Override
    public boolean tryAcquire() {
        return false;
    }

    @Override
    public void release() {

    }

    @Override
    public void addVehicle(Vehicle vehicle) {
    }

//    @Override
//    public synchronized boolean reserveTile() {
////        if (this.reservedFor != null && this.reservedFor != vehicle) {
////            this.setTileCurrentImage();
////            return false;
////        }
////
////        if (this.currentVehicle != null && this.currentVehicle != vehicle) {
////            this.setTileCurrentImage();
////            return false;
////        }
////
////        this.setReserved(vehicle);
////        this.setTileCurrentImage();
////        return true;
//        return false;
//    }
//
//    @Override
//    public boolean tryAcquire() {
//        return false;
//    }
//
//    @Override
//    public void release() {
//
//    }
//
//    @Override
//    public synchronized boolean moveVehicleToTile(Vehicle vehicle) {
//        if (this.isAvaliable()) {
//            setCurrentVehicle(vehicle);
//            this.setTileCurrentImage();
////            System.out.println("Vehicle moved to tile: " + this);
//
//            vehicle.getCurrentTile().removeVehicleFromTile(vehicle);
//            vehicle.setCurrentTile(this);
//            return true;
//        } else {
//            this.setTileCurrentImage();
////            System.out.println("Tile is occupied. Vehicle cannot move to tile: " + this);
//            return false;
//        }
//    }
//
//    @Override
//    public boolean moveVehicleToCrossing(Vehicle vehicle) {
//        return false;
//    }

}
