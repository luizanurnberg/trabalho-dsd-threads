package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Menu extends JFrame{
    private JPanel jpPainel;
    private JLabel lbMenu;
    private JLabel lbNumberVehicles;
    private JTextField tfNumberVehicles;
    private JLabel lbNumberSimultaneousVehicles;
    private JTextField tfNumberSimultaneousVehicles;
    private JLabel lbRangeInsertion;
    private JTextField tfRangeInsertion;
    private JLabel lbGrid;
    private JRadioButton rbGrid1;
    private JRadioButton rbGrid2;
    private JRadioButton rbGrid3;
    private JLabel lbMutualExclusion;
    private JRadioButton rbMonitor;
    private JRadioButton rbSemaphore;
    private JRadioButton rbMessageExchange;
    private JButton btnStart;

    public Menu(){
        super.setSize(new Dimension(600, 600));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(this.jpPainel);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        super.setVisible(true);
        btnStart.addActionListener((ActionEvent e) -> {
            Simulation simulation = new Simulation();
            simulation.setVisible(true);
            dispose();
        });
    }

    public String getSelectedGrid() {
        if (this.rbGrid1.isSelected()) {
            return "mesh1.txt";
        }
        if (this.rbGrid2.isSelected()) {
            return "mesh2.txt";
        }
        if (this.rbGrid3.isSelected()) {
            return "mesh3.txt";
        }
        return null;
    }

    public int getExclusionType() {
        if (this.rbSemaphore.isSelected()) {
            return 1;
        }
        if (this.rbMonitor.isSelected()) {
            return 2;
        }
        if (this.rbMessageExchange.isSelected()) {
            return 3;
        }
        return 0;
    }
}
