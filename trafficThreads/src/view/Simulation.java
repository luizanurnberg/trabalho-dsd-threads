package view;

import model.Tile.TileBase;

import javax.swing.*;
import java.awt.*;

public class Simulation extends JFrame {
    private JPanel jpPainel;

    private JPanel jContainerPanel;
    private JButton btnFinish;
    private JLabel vehiclesRunningText;
    private JLabel vehiclesRemainingText;
    private JLabel vehiclesRunningLabel;
    private JLabel vehiclesRemainingLabel;

    private TileBase[][] tilesGrid;

    public Simulation(TileBase[][] tilesGrid) {
        super("Simulation");
        this.tilesGrid = tilesGrid;
    }

    public void setVehiclesRunningLabel(String newValue) {
        this.vehiclesRunningLabel.setText(newValue);
    }

    public void setVehiclesRemainingLabel(String newValue) {
        this.vehiclesRemainingLabel.setText(newValue);
    }

    public void initializeSimulationFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 1024);
        setLocationRelativeTo(null);
        setResizable(false);

        jContainerPanel = new JPanel();

        jpPainel = new JPanel(new GridLayout(tilesGrid.length, tilesGrid[0].length));

        jpPainel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        jpPainel.setBackground(Color.WHITE);

        plotImagesOnMap(this.tilesGrid, jpPainel);

        JPanel buttonPanel = new JPanel();

        btnFinish = new JButton("Finalizar");

        buttonPanel.add(btnFinish);

        buttonPanel.add(vehiclesRunningText);
        buttonPanel.add(vehiclesRunningLabel);

        buttonPanel.add(vehiclesRemainingText);
        buttonPanel.add(vehiclesRemainingLabel);

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
                label.setOpaque(true);
                label.setBackground(Color.white);
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

