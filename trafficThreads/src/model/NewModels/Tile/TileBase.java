package model.NewModels.Tile;

import model.NewModels.Vehicle;

import javax.swing.*;
import java.util.List;

public class TileBase {
    protected List<String> directions;
    protected String imagePath;
    protected Vehicle currentVehicle;
    protected int posX;
    protected int posY;
    protected JLabel tileLabel;

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public JLabel getTileLabel() {
        return tileLabel;
    }

    public void setTileLabel(JLabel tileLabel) {
        this.tileLabel = tileLabel;
    }

    public boolean isAvaliable() {
        return this.currentVehicle == null;
    }

    public List<String> getDirections() {
        return directions;
    }

    public void setDirections(List<String> directions) {
        this.directions = directions;
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
