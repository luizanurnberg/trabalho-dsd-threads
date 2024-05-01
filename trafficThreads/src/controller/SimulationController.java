package controller;

import constants.TerrainType;
import model.NewModels.Tile.TileBase;
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
    public void startSimulation(String selectedGrid, int exclusionType, int numVehicles, int numSimultaneousVehicles, int rangeInsertion) {
        try {
            int[][] grid = loadGridFromFile(selectedGrid);

            TileBase[][] tilesGrid = loadTilesFromFile(selectedGrid);

            Simulation simulation = new Simulation(
                    grid,
                    exclusionType,
                    numVehicles,
                    numSimultaneousVehicles,
                    rangeInsertion,
                    tilesGrid
            );

            simulation.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private TileBase[][] loadTilesFromFile(String filePath) throws IOException {
        URL relativeFilePath = SimulationController.class.getClassLoader().getResource(filePath);

        BufferedReader reader = new BufferedReader(new InputStreamReader(relativeFilePath.openStream()));

        int numberOfRows = parseInt(reader.readLine());
        int numberOfColumns = parseInt(reader.readLine());

        TileBase[][] grid = new TileBase[numberOfRows][numberOfColumns];

        for (int x = 0; x < numberOfRows - 1; x++) {
            String[] values = reader.readLine().split("\\s+");
            for (int y = 0; y < numberOfColumns - 1; y++) {
                TileBase tile = new TileBase();
                TerrainType terrainType = TerrainType.getByValue(parseInt(values[0]));

                tile.setDirections(terrainType.getDirections());
                tile.setImagePath(terrainType.getImagePath());
                tile.setPosX(x);
                tile.setPosY(y);

                grid[x][y] = tile;
            }
        }

        reader.close();
        return grid;
    }

    private int[][] loadGridFromFile(String filePath) throws IOException {
        URL relativeFilePath = SimulationController.class.getClassLoader().getResource(filePath);

        BufferedReader reader = new BufferedReader(new InputStreamReader(relativeFilePath.openStream()));

        int rows = parseInt(reader.readLine());
        int cols = parseInt(reader.readLine());

        int[][] grid = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            String[] values = reader.readLine().split("\\s+");
            for (int j = 0; j < cols; j++) {
                grid[i][j] = parseInt(values[j]);
            }
        }

        reader.close();
        return grid;
    }
}
