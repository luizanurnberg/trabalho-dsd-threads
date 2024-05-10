package controller;

import constants.ExclusionType;
import constants.GridType;
import constants.TerrainType;
import model.SimulationParams;
import model.Tile.TileBase;
import model.Tile.TileMonitorImpl;
import model.Tile.TileSemaphoreImpl;
import model.Tile.TileSocketImpl;
import model.Vehicle;
import view.Simulation;
import view.SimulationMeshTable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
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
    private Simulation simulationPanel;
    private SimulationParams simulationParams;

    private int rangeInsertion;

    public Simulation getSimulationPanel() {
        return simulationPanel;
    }

    public SimulationController(SimulationParams simulationParams) {
        runningVehicles = new ArrayList<>();
        availableVehicles = new ArrayList<>();

        this.simulationParams = simulationParams;
    }


    public void startSimulation() {
        try {
            this.rangeInsertion = simulationParams.getRangeInsertion();

            TileBase[][] tilesGrid = loadTilesFromFile(simulationParams.getSelectedGrid(), simulationParams.getExclusionType());

            simulationPanel = new Simulation(tilesGrid, simulationParams);
            simulationPanel.initializeSimulationFrame();

            createVehicles(simulationParams.getNumberOfVehicles(), tilesGrid);
            runVehicles(simulationParams.getNumberOfSimultaneousVehicles());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateUI(TileBase tile) {
        this.getSimulationMeshTable().fireTableCellUpdated(tile.getPosY(), tile.getPosX());
        this.getSimulationMeshTable().fireTableDataChanged();
        this.getSimulationPanel().getJpMeshContainer().updateUI();
    }

    public SimulationMeshTable getSimulationMeshTable() {
        return (SimulationMeshTable) this.getSimulationPanel().getTbTileMap().getModel();
    }

    private void createVehicles(int numVehicles, TileBase[][] tilesGrid) {
        for (int i = 0; i < numVehicles; i++) {
            String imagePath = getRandomVehicleImagePath();
            Vehicle vehicle = new Vehicle(imagePath, this);
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

    public void runVehicles(int numSimultaneousVehicles) {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                addVehicles(numSimultaneousVehicles);
                simulationPanel.setVehiclesRunningLabel(String.valueOf(runningVehicles.size()));
                simulationPanel.setVehiclesRemainingLabel(String.valueOf(availableVehicles.size()));
            }
        }, 0, this.rangeInsertion * 100L);
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
                TileBase tile = createTile(values[x], x, y, exclusionType, x);
                grid[y][x] = tile;
            }
        }

        reader.close();
        return grid;
    }

    private TileBase createTile(String meshValue, int posX, int posY, ExclusionType exclusionType, int serverPort) {
        TileBase tile = null;

        switch (exclusionType) {
            case MONITOR:
                tile = new TileMonitorImpl();
                break;
            case SEMAPHORE:
                tile = new TileSemaphoreImpl();
                break;
            case SOCKTES:
                tile = new TileSocketImpl(serverPort);
                break;
        }

        int currentValue = parseInt(meshValue);
        TerrainType terrainType = TerrainType.getByValue(currentValue);

        tile.setDirections(terrainType.getDirections());
        tile.setImagePath(terrainType.getImagePath());
        tile.setTileCurrentImage(terrainType.getImagePath());
        tile.setPosX(posX);
        tile.setPosY(posY);

        return tile;
    }
}
