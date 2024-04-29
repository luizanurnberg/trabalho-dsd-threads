package controller;

import view.Simulation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SimulationController {

    public int[][] loadGridFromFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

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

    public void startSimulation(String selectedGrid, int exclusionType, int numVehicles, int numSimultaneousVehicles, int rangeInsertion) {
        try {
            int[][] grid = loadGridFromFile(selectedGrid);
            Simulation simulation = new Simulation(grid, exclusionType, numVehicles, numSimultaneousVehicles, rangeInsertion);
            simulation.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
