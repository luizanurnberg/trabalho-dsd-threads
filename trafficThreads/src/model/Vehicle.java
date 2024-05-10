package model;

import controller.SimulationController;
import model.Tile.TileBase;

import java.io.IOException;
import java.util.*;

public class Vehicle extends Thread {
    protected TileBase currentTile;
    protected ArrayList<TileBase> path = new ArrayList<>();
    protected String imagePath;
    protected int vehicleSpeed;
    protected TileBase[][] tileMap;
    protected SimulationController simulationController;

    public Vehicle(String imagePath, SimulationController simulationController) {
        this.imagePath = imagePath;
        this.vehicleSpeed = generateRandomVehicleSpeed(100, 150);
        this.simulationController = simulationController;
    }

    public void setupVehicle(TileBase[][] tileMap) {
        ArrayList<TileBase> tilePath = generateTilePath(tileMap);

        this.tileMap = tileMap;
        this.path = tilePath;
    }

    public boolean isVehicleStopped() {
        return this.path.isEmpty();
    }

    private ArrayList<TileBase> findCrossingTiles() {
        ArrayList<TileBase> crossingTiles = new ArrayList<>();
        for (int i = 0; i < this.path.size(); i++) {
            TileBase tile = this.path.get(i);
            crossingTiles.add(tile);

            if (!tile.isCrossing()) {
                break;
            }
        }
        return crossingTiles;
    }

    protected ArrayList<TileBase> reserveCrossingTiles(ArrayList<TileBase> crossingTiles) throws IOException {
        ArrayList<TileBase> reservedTiles = new ArrayList<>();

        for (TileBase crossingTile : crossingTiles) {
            if (crossingTile.tryAcquire()) {
                reservedTiles.add(crossingTile);
            } else {
                this.releaseTiles(reservedTiles);
                break;
            }
        }

        return crossingTiles;
    }

    private void releaseTiles(ArrayList<TileBase> tilesToRelease) throws IOException {
        for (TileBase tile : tilesToRelease) {
            tile.release();
        }
    }

    protected void resolveCrossing() throws IOException {
        ArrayList<TileBase> crossingTiles = this.findCrossingTiles();
        ArrayList<TileBase> reservedCrossings = this.reserveCrossingTiles(crossingTiles);

        if (reservedCrossings.size() == crossingTiles.size()) {
            for (TileBase reservedCrossing : reservedCrossings) {
                this.path.remove(reservedCrossing);
                this.moveVehicle(reservedCrossing, false);
            }
        }

//        this.currentTile = reservedCrossings.get(reservedCrossings.size() - 1);
//        reservedCrossings.remove(reservedCrossings.size() - 1);
//        this.releaseTiles(reservedCrossings);

        this.sleepVehicle();
    }


    private void sleepVehicle() {
        try {
            this.sleep(this.vehicleSpeed);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected void moveVehicle(TileBase tileToMove, boolean shouldReserve) throws IOException {
        if (tileToMove.isEmpty()) {
            boolean reserved = false;

            if (shouldReserve) {
                do {
                    if (tileToMove.tryAcquire()) {
                        reserved = true;
                    }
                } while (!reserved);
            }

            tileToMove.addVehicle(this);

            TileBase previousTile = this.currentTile;

            if (previousTile != null) {
                previousTile.removeVehicle();
                this.simulationController.updateUI(previousTile);
                previousTile.release();
            }

            this.currentTile = tileToMove;
            this.simulationController.updateUI(tileToMove);

            this.sleepVehicle();
        }
    }

    @Override
    public void run() {
        while (!path.isEmpty()) {
            int nextRoadIndex = 0;
            if (path.get(nextRoadIndex).isCrossing()) {
                try {
                    resolveCrossing();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                TileBase road = this.path.remove(nextRoadIndex);
                try {
                    this.moveVehicle(road, true);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        this.currentTile.removeVehicle();
        try {
            this.currentTile.release();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.simulationController.updateUI(this.currentTile);

        System.out.println("Vehicle finished path: " + this.getName());
        this.stop();
    }

    public static int generateRandomVehicleSpeed(int minMs, int maxMs) {
        Random random = new Random();
        int minTime = minMs;
        int maxTime = maxMs;

        int randomTime = random.nextInt((maxTime - minTime) + 1) + minTime;

        return randomTime;
    }

    public ArrayList<TileBase> getPath() {
        return path;
    }

    public String getImagePath() {
        return imagePath;
    }

    protected ArrayList<TileBase> generateTilePath(TileBase[][] tileMap) {
        ArrayList<TileBase> pathTiles = new ArrayList<>();
        LinkedList<TileBase> recentTiles = new LinkedList<>();

        TileBase entryTile = getRandomEntryTile(tileMap);
        pathTiles.add(entryTile);

        while (!entryTile.isExitTile(tileMap) && pathTiles.size() < tileMap.length * tileMap[0].length) {
            List<String> availableDirections = entryTile.getDirections();

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

            if (filteredDirections.isEmpty()) {
                break;
            }

            String chosenDirection = filteredDirections.get(new Random().nextInt(filteredDirections.size()));

            recentTiles.addFirst(entryTile);
            if (recentTiles.size() > 4) {
                recentTiles.removeLast();
            }

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
                pathTiles.add(nextTile);
                entryTile = nextTile;
            } else {
                break;
            }
        }

        return pathTiles;
    }

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
}
