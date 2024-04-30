package model.NewModels;

import model.NewModels.Tile.ITile;

public class Vehicle extends Thread {
    private ITile currentTile;
    private ITile[] path;
    private int currentPathIndex;

    public Vehicle(ITile initialTile, ITile[] path) {
        this.currentTile = initialTile;
        this.path = path;
        this.currentPathIndex = 0;
    }

    @Override
    public void run() {
        while (currentPathIndex < path.length) {
            ITile nextTile = path[currentPathIndex];
            nextTile.moveVehicleToTile(this);
            currentPathIndex++;

            try {
                this.wait(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setPath(ITile[] newPath) {
        this.path = newPath;
        this.currentPathIndex = 0;
    }

    public ITile getCurrentTile() {
        return currentTile;
    }

    public void setCurrentTile(ITile tile) {
        this.currentTile = tile;
    }
}
