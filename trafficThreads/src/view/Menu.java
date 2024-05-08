package view;

import constants.ExclusionType;
import constants.GridType;
import controller.SimulationController;
import model.SimulationParams;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Menu extends JFrame {
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

    public Menu() {
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

        btnStart.addActionListener((ActionEvent e) -> onPressStartHandler());
    }

    private void onPressStartHandler() {
        if (!validateInputs()) {
            JOptionPane.showMessageDialog(null, "Campos em branco, favor preencher!");
        } else {
            GridType selectedGrid = getSelectedGrid();
            ExclusionType exclusionType = getExclusionType();

            int numberOfVehicles = getNumberVehicles();
            int numberOfSimultaneousVehicles = getNumberSimultaneousVehicles();
            int rangeInsertion = getRangeInsertion();

            SimulationParams simulationParams = new SimulationParams(
                    selectedGrid,
                    exclusionType,
                    numberOfVehicles,
                    numberOfSimultaneousVehicles,
                    rangeInsertion
            );

            SimulationController simulationController = new SimulationController(simulationParams);
            simulationController.startSimulation();
        }
    }

    private boolean validateInputs() {
        if (
                tfNumberVehicles.getText().isEmpty() ||
                        tfNumberSimultaneousVehicles.getText().isEmpty() ||
                        tfRangeInsertion.getText().isEmpty()
        ) {
            return false;
        }
        return true;
    }

    public GridType getSelectedGrid() {
        if (this.rbGrid1.isSelected()) {
            return GridType.MESH_ONE;
        }
        if (this.rbGrid2.isSelected()) {
            return GridType.MESH_TWO;
        }
        if (this.rbGrid3.isSelected()) {
            return GridType.MESH_THREE;
        }
        return null;
    }

    public ExclusionType getExclusionType() {
        if (this.rbSemaphore.isSelected()) {
            return ExclusionType.SEMAPHORE;
        }
        if (this.rbMonitor.isSelected()) {
            return ExclusionType.MONITOR;
        }
        if (this.rbMessageExchange.isSelected()) {
            return ExclusionType.SOCKTES;
        }

        return null;
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
