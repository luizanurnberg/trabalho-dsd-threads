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

    public boolean isVehicleStopped() {
        return currentPathIndex >= path.length;
    }

    protected TileBase[] generateTilePath(TileBase[][] tileMap) {
        List<TileBase> pathTiles = new ArrayList<>();
        LinkedList<TileBase> recentTiles = new LinkedList<>();

        // Seleciona aleatoriamente um ponto de entrada
        TileBase entryTile = getRandomEntryTile(tileMap);
        pathTiles.add(entryTile);

        // Enquanto não alcançar um ponto de saída
        while (!entryTile.isExitTile(tileMap) && pathTiles.size() < tileMap.length * tileMap[0].length) {
            // Obtém as direções disponíveis para o próximo Tile
            List<String> availableDirections = entryTile.getDirections();

            // Remove os Tiles recentes do mapa de direções disponíveis
            List<String> filteredDirections = new ArrayList<>(availableDirections);
            for (TileBase recentTile : recentTiles) {
                Iterator<String> iterator = filteredDirections.iterator();
                while (iterator.hasNext()) {
                    String direction = iterator.next();
                    if (leadsToTile(direction, entryTile, recentTile)) {
                        iterator.remove();
                    }
                }
            }

            // Se todas as direções levam a Tiles recentes, interrompe o loop
            if (filteredDirections.isEmpty()) {
                break;
            }

            // Escolhe aleatoriamente uma das direções disponíveis
            String chosenDirection = filteredDirections.get(new Random().nextInt(filteredDirections.size()));

            // Adiciona o Tile atual à lista de Tiles recentes
            recentTiles.addFirst(entryTile);
            // Mantém apenas os últimos 4 Tiles recentes
            if (recentTiles.size() > 4) {
                recentTiles.removeLast();
            }

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

        // Converte a lista de Tiles para um array e retorna
        return pathTiles.toArray(new TileBase[0]);
    }

    // Verifica se a direção leva ao Tile recente
    private boolean leadsToTile(String direction, TileBase currentTile, TileBase recentTile) {
        int nextX = currentTile.getPosX();
        int nextY = currentTile.getPosY();

        switch (direction) {
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

        return nextX == recentTile.getPosX() && nextY == recentTile.getPosY();
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
            currentTileIndex++;
            firstCrossingTile = this.path[currentTileIndex];
        }

        crossingTiles.add(this.path[currentTileIndex]);

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


    private void clearLastTiles() {
        int lastTiles = this.path.length - this.currentPathIndex;
        for (int i = 0; i < lastTiles; i++) {
            this.path[i].removeReservedVehicle(this);
        }
    }

    @Override
    public void run() {
        int deadlockAttempts = 0;

        while (true) {
            TileBase nextTile = path[currentPathIndex];

            if (nextTile.isCrossing()) {
                resolveCrossing(nextTile);
            } else {
                moveVehicle(nextTile);
            }

            if (currentPathIndex >= path.length) {
                break;
            }

            // Verifica se houve deadlock e tentou mais de 3 vezes percorrer o caminho
            if (nextTile == path[currentPathIndex] && nextTile.isCrossing()) {
                deadlockAttempts++;
                if (deadlockAttempts > 2) {
                    for (int i = currentPathIndex; i < path.length; i++) {
                        TileBase tile = path[i];
                        if (tile.isCrossing()) {
                            currentPathIndex = i;
                            deadlockAttempts = 0;
                            break;
                        }
                    }
                }
            } else {
                deadlockAttempts = 0;
            }

            try {
                Thread.sleep(this.vehicleSpeed * 100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.currentTile.removeVehicleFromTile(this);
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
