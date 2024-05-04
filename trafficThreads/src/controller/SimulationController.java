package controller;

import java.util.List;
import java.util.ArrayList;

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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.parseInt;

public class SimulationController {
    //  maybe pass constant to enum
    private static final String[] VEHICLE_IMAGE_PATHS = {
            "vehicle_jeep.png",
            "vehicle_kwid.png",
            "vehicle_audi.png",
            "vehicle_bmw.png"
    };
    private List<Vehicle> runningVehicles;
    private List<Vehicle> availableVehicles;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private int currentIndex = 0;

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
            TileBase[][] tilesGrid = loadTilesFromFile(selectedGrid, exclusionType);

            Simulation simulation = new Simulation(tilesGrid);
            simulation.initializeSimulationFrame();

            createVehicles(numVehicles, tilesGrid, vehicleSpeed);

            runVehicles(numSimultaneousVehicles, rangeInsertion);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createVehicles(int numVehicles, TileBase[][] tilesGrid, int vehicleSpeed) {
        for (int i = 0; i < numVehicles; i++) {
            String imagePath = getRandomVehicleImagePath();
            Vehicle vehicle = new Vehicle(imagePath, vehicleSpeed);
            vehicle.setupVehicle(tilesGrid);
            availableVehicles.add(vehicle);
        }
    }

    private String getRandomVehicleImagePath() {
        int index = ThreadLocalRandom.current().nextInt(VEHICLE_IMAGE_PATHS.length);

        return VEHICLE_IMAGE_PATHS[index];
    }

    public void runVehicles(int numSimultaneousVehicles, int rangeInsertion) {
        scheduler.scheduleAtFixedRate(() -> {
            removeFinishedVehicles();

            // Calcular quantos veículos podem ser adicionados
            int vehiclesToAdd = numSimultaneousVehicles - runningVehicles.size();

            for (int i = 0; i < vehiclesToAdd; i++) {
                if (!availableVehicles.isEmpty()) {
                    Vehicle newVehicle = availableVehicles.remove(0);
                    runningVehicles.add(newVehicle);
                    newVehicle.start();
                } else {
                    break;
                }
            }
        }, 0, rangeInsertion, TimeUnit.SECONDS);
    }

    private void removeFinishedVehicles() {
        List<Vehicle> finishedVehicles = new ArrayList<>();
        for (Vehicle vehicle : runningVehicles) {
            if (!vehicle.isAlive()) {
                finishedVehicles.add(vehicle);
            }
        }
        runningVehicles.removeAll(finishedVehicles);
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
