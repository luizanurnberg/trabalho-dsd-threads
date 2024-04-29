package view;

import controller.Controller;
import controller.SimulationController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


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
    private ButtonGroup gridGroup;
    private ButtonGroup exclusionTypeGroup;

    public Menu(){
        super.setSize(new Dimension(600, 600));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(this.jpPainel);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        super.setVisible(true);

        gridGroup = new ButtonGroup();
        exclusionTypeGroup = new ButtonGroup();

        gridGroup.add(rbGrid1);
        gridGroup.add(rbGrid2);
        gridGroup.add(rbGrid3);

        exclusionTypeGroup.add(rbMonitor);
        exclusionTypeGroup.add(rbSemaphore);
        exclusionTypeGroup.add(rbMessageExchange);

        btnStart.addActionListener((ActionEvent e) -> {
            if(!validateInputs()) {
                JOptionPane.showMessageDialog(null,"Campos em branco, favor preencher!");
            } else {

                String selectedGrid = getSelectedGrid();
                int exclusionType = getExclusionType();
                int numberOfVehicles = getNumberVehicles();
                int numberOfSimultaneousVehicles = getNumberSimultaneousVehicles();
                int rangeInsertion = getRangeInsertion();

                SimulationController simulationController = new SimulationController();
                simulationController.startSimulation(selectedGrid, exclusionType, numberOfVehicles, numberOfSimultaneousVehicles, rangeInsertion);

            }
        });
    }

    private boolean validateInputs() {
        if (tfNumberVehicles.getText().isEmpty() || tfNumberSimultaneousVehicles.getText().isEmpty() || tfRangeInsertion.getText().isEmpty()) {
            return false;
        }
        return true;
    }

    public String getSelectedGrid() {
        if (this.rbGrid1.isSelected()) {
            return "/home/luiza/trabalho-dsd-threads/trafficThreads/src/grids/mesh1.txt";
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

    public int getNumberVehicles() {
        return Integer.parseInt(tfNumberVehicles.getText());
    }

    public int getNumberSimultaneousVehicles() {
        return Integer.parseInt(tfNumberSimultaneousVehicles.getText());
    }

    public int getRangeInsertion() {
        return Integer.parseInt(tfRangeInsertion.getText());
    }
}
