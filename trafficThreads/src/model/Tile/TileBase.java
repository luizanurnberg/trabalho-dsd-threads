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

    public void removeVehicleFromTile() {
        setCurrentVehicle(null);
        this.setTileCurrentImage();
    }

    protected void setTileCurrentImage() {
        if(this.currentVehicle == null) {
            String imagePath = this.getImagePath();
            String relativePath = "icons/" + imagePath;

            ImageIcon icon = new ImageIcon(getResource(relativePath));

            this.tileLabel.setIcon(icon);
        }

        if(this.currentVehicle != null) {
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
        if (posX == 0) {
            if (directions.get(0).equals("RIGHT")) {
                return true;
            }
        }
        if (posX == tileMap.length) {
            if (directions.get(0).equals("LEFT")) {
                return true;
            }
        }
        if (posY == 0) {
            if (directions.get(0).equals("DOWN")) {
                return true;
            }
        }
        if (posY == tileMap[0].length) {
            if (directions.get(0).equals("UP")) {
                return true;
            }
        }
        return false;
    }

    public Boolean isExitTile(TileBase[][] tileMap)  {
        if (posX == 0) {
            if (directions.get(0).equals("LEFT")) {
                return true;
            }
        }
        if (posX == tileMap.length) {
            if (directions.get(0).equals("RIGHT")) {
                return true;
            }
        }
        if (posY == 0) {
            if (directions.get(0).equals("UP")) {
                return true;
            }
        }
        if (posY == tileMap[0].length) {
            if (directions.get(0).equals("DOWN")) {
                return true;
            }
        }
        return false;
    }

    protected static java.net.URL getResource(String path) {
        return TileBase.class.getClassLoader().getResource(path);
    }

    protected boolean isAvaliable() {
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
