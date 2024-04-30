package view;

import javax.swing.*;
import java.awt.*;

public class Simulation extends JFrame {
    private JPanel jpPainel;
    private JButton btnFinish;
    private String selectedGrid;
    private int exclusionType;
    private int numVehicles;
    private int numSimultaneousVehicles;
    private int rangeInsertion;
    private int[][] grid;
    private Icon[][] icons;

    public Simulation(int[][] grid, Icon[][] icons, int exclusionType, int numVehicles, int numSimultaneousVehicles, int rangeInsertion) {
        super("Simulation");
        this.grid = grid;
        this.exclusionType = exclusionType;
        this.numVehicles = numVehicles;
        this.numSimultaneousVehicles = numSimultaneousVehicles;
        this.rangeInsertion = rangeInsertion;
        this.icons = icons;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 900);
        setLocationRelativeTo(null);
        setResizable(false);

        jpPainel = new JPanel(new GridLayout(grid.length, grid[0].length));
        jpPainel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                JLabel label = new JLabel(icons[i][j]);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                jpPainel.add(label);
            }
        }

        btnFinish = new JButton("Finalizar");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnFinish);

        add(jpPainel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }
}

