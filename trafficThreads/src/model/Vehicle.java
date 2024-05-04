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

    public boolean isVehicleStopped() {
        return currentPathIndex >= path.length;
    }

    protected TileBase[] generateTilePath(TileBase[][] tileMap) {
        List<TileBase> pathTiles = new ArrayList<>();

        // Seleciona aleatoriamente um ponto de entrada
        TileBase entryTile = getRandomEntryTile(tileMap);
        pathTiles.add(entryTile);

        // Enquanto não alcançar um ponto de saída
        while (!entryTile.isExitTile(tileMap)) {
            // Obtém as direções disponíveis para o próximo Tile
            List<String> availableDirections = entryTile.getDirections();

            // Escolhe aleatoriamente uma das direções disponíveis
            String chosenDirection = availableDirections.get(new Random().nextInt(availableDirections.size()));

            // Obtém a próxima posição com base na direção escolhida
            int nextX = entryTile.getPosX();
            int nextY = entryTile.getPosY();

            switch (chosenDirection) {
                case "UP":
                    nextY--;
                    break;
                case "DOWN":
                    nextY++;
                    break;
                case "LEFT":
                    nextX--;
                    break;
                case "RIGHT":
                    nextX++;
                    break;
            }

            boolean isNextTileValid = nextX >= 0 && nextX < tileMap[0].length && nextY >= 0 && nextY < tileMap.length;

            if (isNextTileValid) {
                TileBase nextTile = tileMap[nextY][nextX];
                // Adiciona o próximo Tile ao percurso
                pathTiles.add(nextTile);
                entryTile = nextTile;
            } else {
                // Caso contrário, encerra o loop
                break;
            }
        }

        // Converte a lista de tiles para um array e retorna
        return pathTiles.toArray(new TileBase[0]);
    }

    private TileBase getRandomEntryTile(TileBase[][] tileMap) {
        List<TileBase> entryTiles = findTilesByType(tileMap, true);
        return entryTiles.get(new Random().nextInt(entryTiles.size()));
    }

    private List<TileBase> findTilesByType(TileBase[][] tileMap, boolean findEntryTiles) {
        List<TileBase> entryTiles = new ArrayList<>();

        for (TileBase[] tiles : tileMap) {
            for (TileBase tile : tiles) {
                if (findEntryTiles && tile.isEntryTile(tileMap)) {
                    entryTiles.add(tile);
                }

                if (!findEntryTiles && tile.isExitTile(tileMap)) {
                    entryTiles.add(tile);
                }
            }
        }

        return entryTiles;
    }

    protected void resolveCrossing(TileBase firstCrossingTile) {
        ArrayList<TileBase> crossingTiles = new ArrayList<>();
        int currentTileIndex = this.currentPathIndex;

        while (firstCrossingTile.isCrossing()) {
            crossingTiles.add(firstCrossingTile);

            firstCrossingTile = this.path[currentTileIndex];
            currentTileIndex++;
        }

        ArrayList<TileBase> reservedTiles = new ArrayList<>();

        for (TileBase crossingTile : crossingTiles) {
            boolean wasTileReserved = crossingTile.reserveTile(this);

            if (wasTileReserved) {
                reservedTiles.add(crossingTile);
            }
        }

        if (reservedTiles.size() != crossingTiles.size()) {
            for (TileBase tileToUnreserve : reservedTiles) {
                tileToUnreserve.removeReservedVehicle(this);
            }
        } else {
            for (TileBase crossingTile : reservedTiles) {
                moveVehicle(crossingTile);
            }

            for (TileBase crossingTile : reservedTiles) {
                crossingTile.removeReservedVehicle(this);
            }
        }
    }

    protected void moveVehicle(TileBase tileToMove) {
        boolean vehicleMoved = tileToMove.moveVehicleToTile(this);

        if (vehicleMoved) {
            this.currentPathIndex++;
        }

        try {
            Thread.sleep(this.vehicleSpeed * 100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (currentPathIndex < path.length) {
            TileBase nextTile = path[currentPathIndex];

            if (nextTile.isCrossing()) {
                resolveCrossing(nextTile);
            } else {
                moveVehicle(nextTile);
            }
        }

        this.currentTile.removeVehicleFromTile();
        this.stop();
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
