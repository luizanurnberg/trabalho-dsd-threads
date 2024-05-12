package view;

import controller.SimulationController;
import model.SimulationParams;
import model.Tile.TileBase;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.io.IOException;

public class Simulation extends JFrame {
    private JPanel jpMeshContainer;
    private JPanel jContainerPanel;
    private JButton btnFinish;
    private JLabel vehiclesRunningText;
    private JLabel vehiclesRemainingText;
    private JLabel vehiclesRunningLabel;
    private JLabel vehiclesRemainingLabel;
    private JTable tbTileMap;
    private SimulationParams simulationParams;
    private TileBase[][] tilesGrid;
    private SimulationController simulationController;

    public Simulation(TileBase[][] tilesGrid, SimulationParams simulationParams, SimulationController simulationController) {
        super("Simulation");
        this.tilesGrid = tilesGrid;
        this.simulationParams = simulationParams;
        this.simulationController = simulationController;
    }

    public void setVehiclesRunningLabel(String newValue) {
        this.vehiclesRunningLabel.setText(newValue);
    }

    public void setVehiclesRemainingLabel(String newValue) {
        this.vehiclesRemainingLabel.setText(newValue);
    }


    private void meshTableRender() {
        tbTileMap.setModel(new SimulationMeshTable(this.tilesGrid));

        tbTileMap.setRowHeight(30);

        tbTileMap.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tbTileMap.setIntercellSpacing(new Dimension(0, 0));

        tbTileMap.setDefaultRenderer(Object.class, new SimulationMeshCell());

        TableColumnModel columnModel = tbTileMap.getColumnModel();

        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setMaxWidth(25);
        }
    }

    public void initializeSimulationFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 900);
        setResizable(false);
        setLocationRelativeTo(null);

        jContainerPanel = new JPanel();

        meshTableRender();

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnFinish = new JButton("Finalizar");
        btnFinish.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(btnFinish);

        JPanel vehiclesRunningPanel = new JPanel();
        vehiclesRunningPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        vehiclesRunningPanel.add(vehiclesRunningText);
        vehiclesRunningPanel.add(vehiclesRunningLabel);
        buttonPanel.add(vehiclesRunningPanel);

        JPanel vehiclesRemainingPanel = new JPanel();
        vehiclesRemainingPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        vehiclesRemainingPanel.add(vehiclesRemainingText);
        vehiclesRemainingPanel.add(vehiclesRemainingLabel);
        buttonPanel.add(vehiclesRemainingPanel);

        add(tbTileMap, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        btnFinish.addActionListener(e -> {
            try {
                this.simulationController.end();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            dispose();
        });

        setVisible(true);
    }


    public JPanel getJpMeshContainer() {
        return jpMeshContainer;
    }

    public void setJpMeshContainer(JPanel jpMeshContainer) {
        this.jpMeshContainer = jpMeshContainer;
    }

    public JPanel getjContainerPanel() {
        return jContainerPanel;
    }

    public void setjContainerPanel(JPanel jContainerPanel) {
        this.jContainerPanel = jContainerPanel;
    }

    public JButton getBtnFinish() {
        return btnFinish;
    }

    public void setBtnFinish(JButton btnFinish) {
        this.btnFinish = btnFinish;
    }

    public JLabel getVehiclesRunningText() {
        return vehiclesRunningText;
    }

    public void setVehiclesRunningText(JLabel vehiclesRunningText) {
        this.vehiclesRunningText = vehiclesRunningText;
    }

    public JLabel getVehiclesRemainingText() {
        return vehiclesRemainingText;
    }

    public void setVehiclesRemainingText(JLabel vehiclesRemainingText) {
        this.vehiclesRemainingText = vehiclesRemainingText;
    }

    public JLabel getVehiclesRunningLabel() {
        return vehiclesRunningLabel;
    }

    public void setVehiclesRunningLabel(JLabel vehiclesRunningLabel) {
        this.vehiclesRunningLabel = vehiclesRunningLabel;
    }

    public JLabel getVehiclesRemainingLabel() {
        return vehiclesRemainingLabel;
    }

    public void setVehiclesRemainingLabel(JLabel vehiclesRemainingLabel) {
        this.vehiclesRemainingLabel = vehiclesRemainingLabel;
    }

    public void setTbTileMap(JTable tbTileMap) {
        this.tbTileMap = tbTileMap;
    }

    public SimulationParams getSimulationParams() {
        return simulationParams;
    }

    public void setSimulationParams(SimulationParams simulationParams) {
        this.simulationParams = simulationParams;
    }

    public TileBase[][] getTilesGrid() {
        return tilesGrid;
    }

    public void setTilesGrid(TileBase[][] tilesGrid) {
        this.tilesGrid = tilesGrid;
    }

    public JTable getTbTileMap() {
        return this.tbTileMap;
    }
}

