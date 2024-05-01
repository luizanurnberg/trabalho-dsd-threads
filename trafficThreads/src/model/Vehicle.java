package model;

import model.Tile.TileBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Vehicle extends Thread {
    private TileBase currentTile;
    private TileBase[] path;
    private int currentPathIndex;
    private String imagePath;
    private int vehicleSpeed;

    public Vehicle(String imagePath, int vehicleSpeed) {
        this.imagePath = imagePath;
        this.vehicleSpeed = vehicleSpeed;
    }

    public void setupVehicle(TileBase[][] tileMap) {
        TileBase[] tilePath = generateTilePath(tileMap);
        this.path = tilePath;
        this.currentTile = tilePath[0];
        this.currentPathIndex = 0;
    }

    protected TileBase[] generateTilePath(TileBase[][] tileMap) {
        List<TileBase> entryTiles = findEntryTiles(tileMap);
        TileBase entryTile = entryTiles.get(new Random().nextInt(entryTiles.size()));
        int entryX = entryTile.getPosX();
        int entryY = entryTile.getPosY();
        TileBase[] generatedList;

        if (entryY == 0 || entryY == tileMap.length - 1) { // Entrada em uma coluna
            int numRows = tileMap.length;
            generatedList = new TileBase[numRows];
            if (entryY == 0) { // Da esquerda para a direita
                for (int i = 0; i < numRows; i++) {
                    generatedList[i] = tileMap[i][entryX];
                }
            } else { // Da direita para a esquerda
                for (int i = 0; i < numRows; i++) {
                    generatedList[i] = tileMap[numRows - 1 - i][entryX];
                }
            }
        } else { // Entrada em uma linha
            int numColumns = tileMap[0].length;
            generatedList = new TileBase[numColumns];
            if (entryX == 0) { // De cima para baixo
                for (int j = 0; j < numColumns; j++) {
                    generatedList[j] = tileMap[entryY][j];
                }
            } else { // De baixo para cima
                for (int j = 0; j < numColumns; j++) {
                    generatedList[j] = tileMap[entryY][numColumns - 1 - j];
                }
            }
        }

        return generatedList;
    }


    private List<TileBase> findEntryTiles(TileBase[][] tileMap) {
        List<TileBase> entryTiles = new ArrayList<>();
        for (TileBase[] tiles : tileMap) {
            for (TileBase tile : tiles) {
                if (tile.isEntryTile(tileMap)) {
                    entryTiles.add(tile);
                }
            }
        }
        return entryTiles;
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
                Thread.sleep(this.vehicleSpeed * 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.currentTile.removeVehicleFromTile();
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

    public int getVehicleSpeed() {
        return vehicleSpeed;
    }

    public void setVehicleSpeed(int vehicleSpeed) {
        this.vehicleSpeed = vehicleSpeed;
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
