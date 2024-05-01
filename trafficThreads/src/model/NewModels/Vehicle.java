package model.NewModels;

import model.NewModels.Tile.TileBase;

public class Vehicle extends Thread {
    private TileBase currentTile;
    private TileBase[] path;
    private int currentPathIndex;
    private String imagePath;

    public Vehicle(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setupVehicle(TileBase[][] tileMap) {
        TileBase[] tilePath = generateTilePath(tileMap);
        this.path = tilePath;
        this.currentTile = tilePath[0];
        this.currentPathIndex = 0;
    }

    protected TileBase[] generateTilePath(TileBase[][] tileMap) {
        TileBase[] generatedList = new TileBase[20];

        for (int i = 0; i < generatedList.length; i++) {
            generatedList[i] = tileMap[i][5];
        }

        return generatedList;
    }

    @Override
    public void run() {
        while (currentPathIndex < path.length) {
            TileBase nextTile = path[currentPathIndex];
            boolean vehicleMoved = nextTile.moveVehicleToTile(this);

            if(vehicleMoved){
                currentPathIndex++;
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

//        this.currentTile.removeVehicleFromTile();
    }

    public TileBase[] getPath() {
        return path;
    }

    public int getCurrentPathIndex() {
        return currentPathIndex;
    }

    public void setCurrentPathIndex(int currentPathIndex) {
        this.currentPathIndex = currentPathIndex;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setPath(TileBase[] newPath) {
        this.path = newPath;
        this.currentPathIndex = 0;
    }

    public TileBase getCurrentTile() {
        return currentTile;
    }

    public void setCurrentTile(TileBase tile) {
        this.currentTile = tile;
    }
}
