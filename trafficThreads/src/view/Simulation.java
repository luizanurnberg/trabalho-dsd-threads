package view;

import com.sun.tools.javac.Main;
import constants.TerrainType;
import model.NewModels.Tile.TileBase;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
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
    private TileBase[][] tilesGrid;

    public Simulation(
            int[][] grid,
            int exclusionType,
            int numVehicles,
            int numSimultaneousVehicles,
            int rangeInsertion,
            TileBase[][] tilesGrid
    ) throws IOException {
        super("Simulation");
        this.grid = grid;
        this.tilesGrid = tilesGrid;
        this.exclusionType = exclusionType;
        this.numVehicles = numVehicles;
        this.numSimultaneousVehicles = numSimultaneousVehicles;
        this.rangeInsertion = rangeInsertion;

        initializeSimulationFrame();
    }

    private void initializeSimulationFrame() throws IOException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 900);
        setLocationRelativeTo(null);
        setResizable(false);

        jpPainel = new JPanel(new GridLayout(grid.length, grid[0].length));

        jpPainel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        jpPainel.setBackground(Color.WHITE);

        plotImagesOnMap(this.tilesGrid, jpPainel);

        btnFinish = new JButton("Finalizar");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnFinish);

        TileBase tileBase = new TileBase();
        tileBase.isEntryTile(tileBase);

        add(jpPainel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void plotImagesOnMap(TileBase[][] grid, JPanel jpPainel) {
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                TileBase currentTile = grid[y][x];

                String imagePath = currentTile.getImagePath();
                String relativePath = "icons/" + imagePath;

                ImageIcon icon = new ImageIcon(getResource(relativePath));

                JLabel label = new JLabel(icon);

                currentTile.setTileLabel(label);

                currentTile.getTileLabel().setHorizontalAlignment(SwingConstants.CENTER);
                jpPainel.add(currentTile.getTileLabel());
            }
        }
    }

    protected static java.net.URL getResource(String path) {
        return Simulation.class.getClassLoader().getResource(path);
    }
}

