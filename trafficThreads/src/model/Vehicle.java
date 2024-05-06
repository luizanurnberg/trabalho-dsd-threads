package model;

import model.Tile.TileBase;

import java.util.*;

public class Vehicle extends Thread {
    private TileBase currentTile;
    private ArrayList<TileBase> path = new ArrayList<>();
    private int currentPathIndex;
    private String imagePath;
    private int vehicleSpeed;

    private int attempts = 0;
    private TileBase[][] tileMap;

    public Vehicle(String imagePath) {
        this.imagePath = imagePath;
        this.vehicleSpeed = generateRandomVehicleSpeed(50, 150);
    }

    public void setupVehicle(TileBase[][] tileMap) {
        this.tileMap = tileMap;
    }

    public boolean isVehicleStopped() {
        return this.path.isEmpty();
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

    private ArrayList<TileBase> findCrossingTiles(TileBase currentTile) {
        ArrayList<TileBase> crossingTiles = new ArrayList<>();
        int currentTileIndex = this.currentPathIndex;

        while (currentTile.isCrossing()) {
            crossingTiles.add(currentTile);
            currentTileIndex++;
            currentTile = this.path.get(currentTileIndex);
        }

        crossingTiles.add(this.path.get(currentTileIndex));

        return crossingTiles;
    }

    protected ArrayList<TileBase> reserveCrossingTiles(ArrayList<TileBase> crossingTiles) {
        ArrayList<TileBase> reservedTiles = new ArrayList<>();

        ArrayList<TileBase> tilesToReserve = (ArrayList<TileBase>) crossingTiles.clone();

        tilesToReserve.sort(Comparator.comparing(Object::toString));

        for (TileBase crossingTile : tilesToReserve) {
            boolean wasTileReserved = crossingTile.reserveTile(this);

            if (wasTileReserved) {
                reservedTiles.add(crossingTile);
            }
        }

        return reservedTiles;
    }

    protected boolean resolveCrossing(TileBase firstCrossingTile) {
        boolean isCrossingResolved = false;

        ArrayList<TileBase> crossingTiles = findCrossingTiles(firstCrossingTile);

        boolean isLastPathOccupied = !crossingTiles.get(crossingTiles.size() - 1).isAvaliable();

        if (isLastPathOccupied) {
            return false;
        }

        ArrayList<TileBase> reservedTiles = reserveCrossingTiles(crossingTiles);

        boolean hasVehicleReservedAllCrossing = reservedTiles.size() == crossingTiles.size();

        if (!hasVehicleReservedAllCrossing) {
            for (TileBase tileToUnreserve : reservedTiles) {
                tileToUnreserve.removeReservedVehicle(this);
                tileToUnreserve.setTileCurrentImage();
            }
        }

        if (hasVehicleReservedAllCrossing) {
            for (TileBase crossingTile : crossingTiles) {
                moveVehicle(crossingTile);

                try {
                    this.sleep(this.vehicleSpeed);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            for (TileBase crossingTile : reservedTiles) {
                crossingTile.removeReservedVehicle(this);
            }

            isCrossingResolved = true;
        }

        if (!isCrossingResolved) {
            int vehicleSleepSpeed = generateRandomVehicleSpeed(400, 1500);

            try {
                this.sleep(vehicleSleepSpeed);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return isCrossingResolved;
    }


    protected void moveVehicle(TileBase tileToMove) {
        boolean vehicleMoved;

        try {
            vehicleMoved = tileToMove.moveVehicleToTile(this);

            if (vehicleMoved) {
                this.attempts = 0;
                this.path.remove(0);
            }
        } catch (Exception error) {
            System.out.println(error);
        }
    }

    public static int generateRandomVehicleSpeed(int minMs, int maxMs) {
        Random random = new Random();
        int minTime = minMs;
        int maxTime = maxMs;

        int randomTime = random.nextInt((maxTime - minTime) + 1) + minTime;

        return randomTime;
    }

    private void setupVehiclePath() {
        ArrayList<TileBase> tilePath = generateTilePath(tileMap);
        this.currentTile = tilePath.get(0);
        this.currentPathIndex = 0;
        this.path = tilePath;
    }

    @Override
    public void run() {
        setupVehiclePath();

        while (!this.path.isEmpty()) {
            if (attempts >= 400) {
                attempts = 0;
            }

            TileBase nextTile = path.get(0);

            boolean moved = true;

            if (nextTile.isCrossing()) {
                moved = resolveCrossing(nextTile);
            } else {
                moveVehicle(nextTile);
            }

            if (!moved) {
                attempts++;
            }

            nextTile.setTileCurrentImage();

            try {
                this.sleep(this.vehicleSpeed);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        this.currentTile.removeVehicleFromTile(this);

        System.out.println("Vehicle finished path: " + this.getName());
        this.stop();
    }

    public ArrayList<TileBase> getPath() {
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

    public void setPath(ArrayList<TileBase> newPath) {
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
