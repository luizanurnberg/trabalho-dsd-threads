package view;

import controller.Controller;
import view.table.Table;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Simulation extends JFrame implements ViewObserver{
    private JButton btnFinish;
    private JPanel jpPainel;
    private JTable tbGrids;
    private Menu menu;
    private Controller controller;

    public Simulation(Menu menu){
        super.setSize(new Dimension(900, 900));
        this.menu = menu;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(this.jpPainel);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        super.setVisible(true);

        int numberVehicles = menu.getNumberVehicles();
        int numberSimultaneousVehicles = menu.getNumberSimultaneousVehicles();
        int rangeInsertion = menu.getRangeInsertion();
        String selectedGrid = menu.getSelectedGrid();
        int exclusionType = menu.getExclusionType();


//    lbNumberVehicles.setText("Number Vehicles: " + numberVehicles);
//    lbNumberSimultaneousVehicles.setText("Number Simultaneous Vehicles: " + numberSimultaneousVehicles);
//    lbRangeInsertion.setText("Range Insertion: " + rangeInsertion);
//    lbSelectedGrid.setText("Selected Grid: " + selectedGrid);
//    lbExclusionType.setText("Exclusion Type: " + exclusionType);
    }

    @Override
    public void updateTable() {

    }

    @Override
    public void updateTableModel(Table table) {

    }

    @Override
    public void updateButton(Boolean start) {

    }
}
