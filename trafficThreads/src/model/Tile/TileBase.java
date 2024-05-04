package model.Tile;

import model.Vehicle;

import javax.swing.*;
import java.util.List;

public class TileBase {
    protected List<String> directions;
    protected String imagePath;
    protected Vehicle currentVehicle;
    protected int posX;
    protected int posY;
    protected JLabel tileLabel;
    protected Vehicle reservedFor;

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

    public boolean reserveTile(Vehicle vehicle) {
        if (this.reservedFor != null && this.reservedFor != vehicle) {
            return false;
        }

        this.setReserved(vehicle);
        return true;
    }

    public void removeReservedVehicle(Vehicle vehicle) {
        if (reservedFor != null && reservedFor == vehicle) {
            this.setReserved(null);
        }
    }

    public void setReserved(Vehicle reserveFor) {
        this.reservedFor = reserveFor;
    }

    public boolean isCrossing() {
        return this.directions.size() > 1;
    }

    public void removeVehicleFromTile() {
        setCurrentVehicle(null);
        this.setTileCurrentImage();
    }

    protected void setTileCurrentImage() {
        if (this.currentVehicle == null) {
            String imagePath = this.getImagePath();
            String relativePath = "icons/" + imagePath;

            ImageIcon icon = new ImageIcon(getResource(relativePath));

            this.tileLabel.setIcon(icon);
        }

        if (this.currentVehicle != null) {
            String imagePath = this.currentVehicle.getImagePath();
            String relativePath = "icons/" + imagePath;

            ImageIcon icon = new ImageIcon(getResource(relativePath));

            this.tileLabel.setIcon(icon);
        }
    }

    public boolean moveVehicleToTile(Vehicle vehicle) {
        return false;
    }

    public Boolean isEntryTile(TileBase[][] tileMap) {
        if (this.posX == 0) {
            if (directions.get(0).equals("RIGHT")) {
                return true;
            }
        }
        if (this.posX == tileMap.length - 1) {
            if (directions.get(0).equals("LEFT")) {
                return true;
            }
        }
        if (this.posY == 0) {
            if (directions.get(0).equals("DOWN")) {
                return true;
            }
        }
        if (this.posY == tileMap.length - 1) {
            if (directions.get(0).equals("UP")) {
                return true;
            }
        }
        return false;
    }

    public Boolean isExitTile(TileBase[][] tileMap) {
        if (this.posX == 0) {
            if (directions.get(0).equals("LEFT")) {
                return true;
            }
        }
        if (this.posX == tileMap.length) {
            if (directions.get(0).equals("RIGHT")) {
                return true;
            }
        }
        if (this.posY == 0) {
            if (directions.get(0).equals("UP")) {
                return true;
            }
        }
        if (this.posY == tileMap.length - 1) {
            if (directions.get(0).equals("DOWN")) {
                return true;
            }
        }
        return false;
    }

    protected static java.net.URL getResource(String path) {
        return TileBase.class.getClassLoader().getResource(path);
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
