package model.Tile;

import model.Vehicle;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public abstract class TileBase {
    protected List<String> directions;
    protected String imagePath;
    protected Vehicle vehicle;
    protected String currentImagePath;
    protected int posX;
    protected int posY;

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

    public boolean isCrossing() {
        return this.directions.size() > 1;
    }

    public boolean isEmpty() {
        return this.vehicle == null;
    }

    public void removeVehicle() {
        this.vehicle = null;
        this.setTileCurrentImage();
    }

    protected int generateRandomCooldown(int minMs, int maxMs) {
        Random random = new Random();

        int randomTime = random.nextInt((maxMs - minMs) + 1) + minMs;

        return randomTime;
    }

    public void setTileCurrentImage(String imagePath) {
        this.currentImagePath = imagePath;
    }

    public void setTileCurrentImage() {
        if (this.vehicle != null) {
            this.currentImagePath = this.vehicle.getImagePath();
        }

        if (this.vehicle == null) {
            this.currentImagePath = this.getImagePath();
        }
    }

    public abstract boolean tryAcquire();

    public abstract void release() throws IOException;

    public void addVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.setTileCurrentImage();
    }

    public Boolean isEntryTile(TileBase[][] tileMap) {
        if (this.posX == 0) {
            if (directions.get(0).equals("RIGHT")) {
                return true;
            }
        }
        if (this.posX == tileMap[0].length - 1) {
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
        if (this.posX == tileMap[0].length - 1) {
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

    public List<String> getDirections() {
        return directions;
    }

    public void setDirections(List<String> directions) {
        this.directions = directions;
    }

    public String getCurrentImagePath() {
        return this.currentImagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
