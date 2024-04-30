package model.NewModels.Tile;

import model.NewModels.Vehicle;

public class TileBase {
    protected int direction;
    protected String imagePath;
    protected Vehicle currentVehicle;

    public boolean isAvaliable() {
        return this.currentVehicle == null;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Vehicle getCurrentVehicle() {
        return currentVehicle;
    }

    public void setCurrentVehicle(Vehicle currentVehicle) {
        this.currentVehicle = currentVehicle;
    }
}
