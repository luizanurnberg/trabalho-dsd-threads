package controller;

import view.Menu;
import view.Simulation;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class SimulationController {
    public void startSimulation(String selectedGrid, int exclusionType, int numVehicles, int numSimultaneousVehicles, int rangeInsertion) {
        try {
            int[][] grid = loadGridFromFile(selectedGrid);

            Simulation simulation = new Simulation(
                    grid,
                    exclusionType,
                    numVehicles,
                    numSimultaneousVehicles,
                    rangeInsertion
            );

            simulation.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int[][] loadGridFromFile(String filePath) throws IOException {
        URL relativeFilePath = SimulationController.class.getClassLoader().getResource(filePath);

        BufferedReader reader = new BufferedReader(new InputStreamReader(relativeFilePath.openStream()));

        int rows = Integer.parseInt(reader.readLine());
        int cols = Integer.parseInt(reader.readLine());

        int[][] grid = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            String[] values = reader.readLine().split("\\s+");
            for (int j = 0; j < cols; j++) {
                grid[i][j] = Integer.parseInt(values[j]);
            }
        }

        reader.close();
        return grid;
    }

    protected static java.net.URL getResource(String path) {
        return Menu.class.getClassLoader().getResource(path);
    }
}
