package model;

import model.Tile.TileBase;

import java.util.*;

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
        List<TileBase> pathTiles = new ArrayList<>();

        // Seleciona aleatoriamente um ponto de entrada
        TileBase entryTile = getRandomEntryTile(tileMap);
        pathTiles.add(entryTile);

        // Enquanto não alcançar um ponto de saída
        while (!entryTile.isExitTile(tileMap)) {
            // Obtém as direções disponíveis para o próximo Tile
            List<String> availableDirections = entryTile.getDirections();

            // Embaralha a lista de direções para escolher aleatoriamente
            Collections.shuffle(availableDirections);

            boolean foundValidDirection = false;
            for (String chosenDirection : availableDirections) {
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

                // Verifica se as quatro posições ao redor do próximo tile estão livres
                boolean isNextTileValid = true;
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        int nx = nextX + dx;
                        int ny = nextY + dy;
                        if (nx < 0 || nx >= tileMap[0].length || ny < 0 || ny >= tileMap.length || !tileMap[ny][nx].isAvaliable()) {
                            isNextTileValid = false;
                            break;
                        }
                    }
                    if (!isNextTileValid) {
                        break;
                    }
                }

                if (isNextTileValid) {
                    // Se todas as posições estiverem livres, avance para o próximo tile
                    TileBase nextTile = tileMap[nextY][nextX];
                    // Adiciona o próximo Tile ao percurso
                    pathTiles.add(nextTile);
                    entryTile = nextTile;
                    foundValidDirection = true;
                    break;
                }
            }

            // Se não foi encontrada uma direção válida, encerra o loop
            if (!foundValidDirection) {
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

    @Override
    public void run() {
        while (currentPathIndex < path.length) {
            TileBase nextTile = path[currentPathIndex];
            boolean vehicleMoved = nextTile.moveVehicleToTile(this);

            if (vehicleMoved) {
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
