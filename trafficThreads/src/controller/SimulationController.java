package controller;

import constants.ExclusionType;
import constants.GridType;
import constants.TerrainType;
import model.Tile.TileBase;
import model.Tile.TileMonitorImpl;
import model.Tile.TileSemaphoreImpl;
import model.Tile.TileSocketImpl;
import model.Vehicle;
import view.Simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Integer.parseInt;

public class SimulationController {
    private static final String[] VEHICLE_IMAGE_PATHS = {
            "vehicle_jeep.png",
            "vehicle_kwid.png",
            "vehicle_audi.png",
            "vehicle_bmw.png"
    };
    private List<Vehicle> runningVehicles;
    private List<Vehicle> availableVehicles;
    Duration timeout;
    private Simulation simulationPanel;


    public SimulationController() {
        runningVehicles = new ArrayList<>();
        availableVehicles = new ArrayList<>();
    }

    public void startSimulation(
            GridType selectedGrid,
            ExclusionType exclusionType,
            int numVehicles,
            int numSimultaneousVehicles,
            int rangeInsertion,
            int vehicleSpeed
    ) {
        try {
            timeout = Duration.ofSeconds(rangeInsertion);

            TileBase[][] tilesGrid = loadTilesFromFile(selectedGrid, exclusionType);

            simulationPanel = new Simulation(tilesGrid);
            simulationPanel.initializeSimulationFrame();

            createVehicles(numVehicles, tilesGrid, vehicleSpeed);

            runVehicles(numSimultaneousVehicles, rangeInsertion);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createVehicles(int numVehicles, TileBase[][] tilesGrid, int vehicleSpeed) {
        for (int i = 0; i < numVehicles; i++) {
            String imagePath = getRandomVehicleImagePath();
            Vehicle vehicle = new Vehicle(imagePath);
            vehicle.setupVehicle(tilesGrid);
            availableVehicles.add(vehicle);
        }
    }

    private String getRandomVehicleImagePath() {
        int index = ThreadLocalRandom.current().nextInt(VEHICLE_IMAGE_PATHS.length);

        return VEHICLE_IMAGE_PATHS[index];
    }

    private void addVehicles(int numSimultaneousVehicles) {
        removeFinishedVehicles();
        int numRunningVehicles = numSimultaneousVehicles - runningVehicles.size();

        for (int i = 0; i < numRunningVehicles; i++) {
            if (!availableVehicles.isEmpty()) {
                Vehicle vehicle = availableVehicles.remove(0);
                runningVehicles.add(vehicle);
                vehicle.start();
            }

        }
    }

    public void runVehicles(int numSimultaneousVehicles, int rangeInsertion) {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                addVehicles(numSimultaneousVehicles);
                simulationPanel.setVehiclesRunningLabel(String.valueOf(runningVehicles.size()));
                simulationPanel.setVehiclesRemainingLabel(String.valueOf(availableVehicles.size()));
            }
        }, 0, rangeInsertion * 100L);
    }

    private void removeFinishedVehicles() {
        List<Vehicle> finishedVehiclesToRemove = new ArrayList<>();

        for (Vehicle vehicle : runningVehicles) {
            if (!vehicle.isAlive() || vehicle.isVehicleStopped()) {
                finishedVehiclesToRemove.add(vehicle);
            }
        }

        runningVehicles.removeAll(finishedVehiclesToRemove);
    }


    private TileBase[][] loadTilesFromFile(GridType gridType, ExclusionType exclusionType) throws IOException {
        String filePath = gridType.getImagePath();

        URL relativeFilePath = SimulationController.class.getClassLoader().getResource(filePath);

        BufferedReader reader = new BufferedReader(new InputStreamReader(relativeFilePath.openStream()));

        int numberOfRows = parseInt(reader.readLine());
        int numberOfColumns = parseInt(reader.readLine());

        TileBase[][] grid = new TileBase[numberOfRows][numberOfColumns];

        for (int y = 0; y < numberOfRows; y++) {
            String[] values = reader.readLine().split("\\s+");

            for (int x = 0; x < values.length; x++) {
                TileBase tile = createTile(values[x], x, y, exclusionType);
                grid[y][x] = tile;
            }
        }

        reader.close();
        return grid;
    }

    private TileBase createTile(String meshValue, int posX, int posY, ExclusionType exclusionType) {
        TileBase tile = null;

        switch (exclusionType) {
            case MONITOR:
                tile = new TileMonitorImpl();
                break;
            case SEMAPHORE:
                tile = new TileSemaphoreImpl();
                break;
            case SOCKTES:
                tile = new TileSocketImpl();
                break;
        }

        int currentValue = parseInt(meshValue);
        TerrainType terrainType = TerrainType.getByValue(currentValue);

        tile.setDirections(terrainType.getDirections());
        tile.setImagePath(terrainType.getImagePath());
        tile.setPosX(posX);
        tile.setPosY(posY);

        return tile;
    }
}
