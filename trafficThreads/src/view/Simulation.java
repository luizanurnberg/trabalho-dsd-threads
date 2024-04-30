package view;

import com.sun.tools.javac.Main;
import constants.TerrainType;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Simulation extends JFrame {
    private JPanel jpPainel;
    private JButton btnFinish;
    private String selectedGrid;
    private int exclusionType;
    private int numVehicles;
    private int numSimultaneousVehicles;
    private int rangeInsertion;
    private int[][] grid;

    public Simulation(int[][] grid, int exclusionType, int numVehicles, int numSimultaneousVehicles, int rangeInsertion) {
        super("Simulation");
        this.grid = grid;
        this.exclusionType = exclusionType;
        this.numVehicles = numVehicles;
        this.numSimultaneousVehicles = numSimultaneousVehicles;
        this.rangeInsertion = rangeInsertion;

        initializeSimulationFrame();
    }

    private void initializeSimulationFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 900);
        setLocationRelativeTo(null);
        setResizable(false);

        jpPainel = new JPanel(new GridLayout(grid.length, grid[0].length));

        jpPainel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        jpPainel.setBackground(Color.WHITE);

        plotImagesOnMap(grid,jpPainel);

        btnFinish = new JButton("Finalizar");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnFinish);

        add(jpPainel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void plotImagesOnMap(int[][] grid, JPanel jpPainel) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                String imagePath = TerrainType.getImagePath(this.grid[i][j]);
                String relativePath = "icons/" + imagePath;

                ImageIcon icon = new ImageIcon(getResource(relativePath));

                JLabel label = new JLabel(icon);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                jpPainel.add(label);
            }
        }
    }

    protected static java.net.URL getResource(String path) {
        return Simulation.class.getClassLoader().getResource(path);
    }
}

