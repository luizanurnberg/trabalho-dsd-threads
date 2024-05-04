package view;

import controller.SimulationController;
import model.Tile.TileBase;
import model.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Simulation extends JFrame {
    private JPanel jpPainel;
    private JButton btnFinish;
    private TileBase[][] tilesGrid;
    private static boolean simulationFinished = false;
    private Menu menu;


    public Simulation(TileBase[][] tilesGrid) {
        super("Simulation");
        this.tilesGrid = tilesGrid;
        this.menu = menu;
    }

    public void initializeSimulationFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 900);
        setLocationRelativeTo(null);
        setResizable(false);

        jpPainel = new JPanel(new GridLayout(tilesGrid.length, tilesGrid[0].length));

        jpPainel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        jpPainel.setBackground(Color.WHITE);

        plotImagesOnMap(this.tilesGrid, jpPainel);

        btnFinish = new JButton("Finalizar");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnFinish);
        btnFinish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Encerre a simulação
                SimulationController.end();
                new Menu();
                dispose();
            }
        });

        add(jpPainel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    public static boolean isSimulationFinished() {
        return simulationFinished;
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

