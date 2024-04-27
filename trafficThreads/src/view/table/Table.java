package view.table;

import controller.ControllerObserver;

import javax.swing.table.AbstractTableModel;

public class Table extends AbstractTableModel {

    private ControllerObserver controller;

    public Table(ControllerObserver controller) {
        this.controller = controller;
    }

    @Override
    public int getRowCount() {
        return this.controller.getTraffic().length;
    }

    @Override
    public int getColumnCount() {
        return this.controller.getTraffic()[0].length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return this.controller.getTraffic()[rowIndex][columnIndex];
    }
}
