package controller;

import constants.TerrainType;
import model.NewModels.Tile.TileBase;
import model.NewModels.Tile.TileMonitorImpl;
import model.NewModels.Tile.TileSemaphoreImpl;
import model.NewModels.Vehicle;
import view.Menu;
import view.Simulation;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import static java.lang.Integer.parseInt;

public class SimulationController {

    public void startSimulation(
            String selectedGrid,
            int exclusionType,
            int numVehicles,
            int numSimultaneousVehicles,
            int rangeInsertion
    ) {
        try {
            TileBase[][] tilesGrid = loadTilesFromFile(selectedGrid);

            Simulation simulation = new Simulation(tilesGrid);
            simulation.setVisible(true);

            runSimulation(tilesGrid);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void runSimulation(TileBase[][] tilesGrid) {
        for (int i = 0; i <50 ; i++) {
            Vehicle vehicle = new Vehicle("vehicle_jeep.png");
            vehicle.setupVehicle(tilesGrid);
            vehicle.start();
        }
    }

    private TileBase[][] loadTilesFromFile(String filePath) throws IOException {
        URL relativeFilePath = SimulationController.class.getClassLoader().getResource(filePath);

        BufferedReader reader = new BufferedReader(new InputStreamReader(relativeFilePath.openStream()));

        int numberOfRows = parseInt(reader.readLine());
        int numberOfColumns = parseInt(reader.readLine());

        TileBase[][] grid = new TileBase[numberOfRows][numberOfColumns];

        for (int y = 0; y < numberOfRows; y++) {
            String[] values = reader.readLine().split("\\s+");

            for (int x = 0; x < values.length; x++) {
                TileBase tile = new TileSemaphoreImpl();
                int currentValue = parseInt(values[x]);
                TerrainType terrainType = TerrainType.getByValue(currentValue);

                tile.setDirections(terrainType.getDirections());
                tile.setImagePath(terrainType.getImagePath());
                tile.setPosX(x);
                tile.setPosY(y);

                grid[y][x] = tile;
            }
        }

        reader.close();
        return grid;
    }
}
