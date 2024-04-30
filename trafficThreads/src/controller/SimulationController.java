package controller;

import view.Simulation;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SimulationController {

    public void startSimulation(String selectedGrid, int exclusionType, int numVehicles, int numSimultaneousVehicles, int rangeInsertion) {
        try {
            int[][] grid = loadGridFromFile(selectedGrid);
            Icon[][] icons = prepareIconsForGrid(grid);
            Simulation simulation = new Simulation(grid, icons, exclusionType, numVehicles, numSimultaneousVehicles, rangeInsertion);
            simulation.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int[][] loadGridFromFile(String filePath) throws IOException {
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

    private Icon[][] prepareIconsForGrid(int[][] grid) {
        Icon[][] icons = new Icon[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                icons[i][j] = getIconForValue(grid[i][j]);
            }
        }
        return icons;
    }

    private Icon getIconForValue(int value) {
        switch (value) {
            case 0:
                return new ImageIcon("/home/luiza/trabalho-dsd-threads/trafficThreads/src/icons/grass_background.png");
//            case 1:
//                return new ImageIcon("estrada_direita.png");
//            case 2:
//                return new ImageIcon("estrada_direita.png");
//            case 3:
//                return new ImageIcon("estrada_baixo.png");
//            case 4:
//                return new ImageIcon("estrada_esquerda.png");
//            case 5:
//                return new ImageIcon("cruzamento_cima.png");
//            case 6:
//                return new ImageIcon("cruzamento_direita.png");
//            case 7:
//                return new ImageIcon("cruzamento_baixo.png");
//            case 8:
//                return new ImageIcon("cruzamento_esquerda.png");
//            case 9:
//                return new ImageIcon("cruzamento_cima_direita.png");
//            case 10:
//                return new ImageIcon("cruzamento_cima_esquerda.png");
//            case 11:
//                return new ImageIcon("cruzamento_direita_baixo.png");
//            case 12:
//                return new ImageIcon("cruzamento_esquerda_baixo.png");
            default:
                return null;
        }
    }
}
